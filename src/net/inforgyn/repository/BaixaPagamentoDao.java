package net.inforgyn.repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.inforgyn.constante.SituacaoPagamentoEnum;
import net.inforgyn.constante.TipoMovimentoEnum;
import net.inforgyn.constante.TipoPagamentoEnum;
import net.inforgyn.model.BaixaPagamento;
import net.inforgyn.model.Debito;
import net.inforgyn.model.DebitoCartao;
import net.inforgyn.model.DebitoCheque;
import net.inforgyn.model.Movimento;
import net.inforgyn.model.Usuario;
import net.inforgyn.model.filterPesquisa.FilterPagamento;
import net.inforgyn.persistence.Transactional;
import net.inforgyn.util.DataUtil;
import net.inforgyn.util.StringUtil;
import net.inforgyn.util.UsuarioSessaoUtil;

public class BaixaPagamentoDao extends DaoModel{

	public List<BaixaPagamento> listar() {
		Usuario usuario = UsuarioSessaoUtil.getUsuario();
		return persistence.listarPorUsuarioComPai(BaixaPagamento.class,
				usuario);
	}

	public void atualizarSituacao() {
		StringBuilder sb = new StringBuilder();
		sb.append("update ");
		sb.append(Debito.class.getName());
		sb.append(" set situacao = :situacaoNova where id in (");
		sb.append(" select id from ");
		sb.append(Debito.class.getName());
		sb.append(" where situacao = :situacao");
		sb.append(" AND vencimento < date(now())) ");

		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("situacao", SituacaoPagamentoEnum.A_VENCER);
		parametros.put("situacaoNova", SituacaoPagamentoEnum.ATRASO);

		persistence.atualizarHql(sb.toString(), parametros);
	}

	public List<BaixaPagamento> pesquisarPagamentos(FilterPagamento filtro) {
		StringBuilder sb = new StringBuilder();
		sb.append("from ");
		sb.append(BaixaPagamento.class.getName()).append(" baixa");
		sb.append(" inner join fetch baixa.debito ");
		sb.append(" inner join fetch baixa.debito.categoria ");
		sb.append(" where baixa.usuario = :usuario ");

		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("usuario", UsuarioSessaoUtil.getUsuario());
		if (filtro != null) {
			if (StringUtil.isNotEmpty(filtro.getDescricao())) {
				sb.append(" and lower(baixa.debito.descricao) like lower(:descricao) ");
				parametros.put("descricao", "%" + filtro.getDescricao() + "%");
			}
			if (filtro.getTipoPagamento() != null) {
				sb.append(" and baixa.debito.tipoPagamento = :tipoPagamento ");
				parametros.put("tipoPagamento", filtro.getTipoPagamento());
			}
			if (DataUtil.isNotNull(filtro.getInicioPagamento())) {
				sb.append(" and baixa.dataPagamento >= :inicioPagamento ");
				parametros.put("inicioPagamento", filtro.getInicioPagamento());
			}
			if (DataUtil.isNotNull(filtro.getFimPagamento())) {
				sb.append(" and baixa.dataPagamento <= :fimPagamento ");
				parametros.put("fimPagamento", filtro.getFimPagamento());
			}
			if (DataUtil.isNotNull(filtro.getInicioVencimento())) {
				sb.append(" and baixa.debito.vencimento >= :inicioVencimento ");
				parametros.put("inicioVencimento", filtro.getInicioVencimento());
			}
			if (DataUtil.isNotNull(filtro.getFimVencimento())) {
				sb.append(" and baixa.debito.vencimento <= :fimVencimento ");
				parametros.put("fimVencimento", filtro.getFimVencimento());
			}
			if (filtro.getCategoria() != null) {
				sb.append(" and baixa.debito.categoria = :categoria ");
				parametros.put("categoria", filtro.getCategoria());
			}
			if (filtro.getSituacao() != null) {
				sb.append(" and baixa.debito.situacao = :situacao ");
				parametros.put("situacao", filtro.getSituacao());
			}
		}
		List<BaixaPagamento> baixas = persistence.pesquisarHql(
				sb.toString(), parametros, BaixaPagamento.class);
		return baixas;
	}

	@Transactional
	public void excluir(List<BaixaPagamento> baixas) {
		List<Movimento> movimentos = new ArrayList<Movimento>();
		for (BaixaPagamento baixa : baixas) {
			StringBuilder descricao = new StringBuilder();
			descricao.append("Cancelar pagamento: ");
			descricao.append(baixa.getDebito().getDescricao());
			
			if(TipoPagamentoEnum.CARTAO.equals(baixa.getDebito().getTipoPagamento())){
				descricao.append(" - ");
				DebitoCartao debito = (DebitoCartao)baixa.getDebito();
				descricao.append(debito.getCartao().getDescricao());
			}
			if(TipoPagamentoEnum.CHEQUE.equals(baixa.getDebito().getTipoPagamento())){
				DebitoCheque debito = (DebitoCheque)baixa.getDebito();
				descricao.append(" - "+debito.getEmitirCheque().getCheque().getDescricao());
			}
			if(baixa.getDebito().getParcelaTotal() > 1){
				descricao.append(" - "+baixa.getDebito().getParcela()+"/"+baixa.getDebito().getParcelaTotal());
			}
			
			Movimento mov = new Movimento(null, new Date(),
					descricao.toString(),					
					baixa.getValor(), UsuarioSessaoUtil.getUsuario(),
					TipoMovimentoEnum.CREDITO);
			movimentos.add(mov);
		}

		persistence.excluir(baixas);
		persistence.salvar(movimentos);

	}

	public List<BaixaPagamento> listarPagamentosDia() {
		StringBuilder sb = new StringBuilder();
		sb.append("from ").append(BaixaPagamento.class.getName()).append(" baixa ");
		sb.append(" inner join fetch baixa.debito ");
		sb.append(" inner join fetch baixa.debito.categoria ");
		sb.append(" where date(baixa.lancamento) = date(:lancamento)");
		
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("lancamento", DataUtil.getDataAtual());
		
		return persistence.pesquisaHql(sb.toString(), parametros, BaixaPagamento.class);
	}

	@SuppressWarnings("unchecked")
	public Map<String, BigDecimal> valorTotalPesquisa(FilterPagamento filtro) {
		StringBuilder sb = new StringBuilder();
		sb.append("select new Map(sum(valor) as total) from ");
		sb.append(BaixaPagamento.class.getName()).append(" baixa");
		sb.append(" where baixa.usuario = :usuario ");
		
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("usuario", UsuarioSessaoUtil.getUsuario());
		if (filtro != null) {
			if (StringUtil.isNotEmpty(filtro.getDescricao())) {
				sb.append(" and lower(baixa.debito.descricao) like lower(:descricao) ");
				parametros.put("descricao", "%" + filtro.getDescricao() + "%");
			}
			if (filtro.getTipoPagamento() != null) {
				sb.append(" and baixa.debito.tipoPagamento = :tipoPagamento ");
				parametros.put("tipoPagamento", filtro.getTipoPagamento());
			}
			if (DataUtil.isNotNull(filtro.getInicioPagamento())) {
				sb.append(" and baixa.dataPagamento >= :inicioPagamento ");
				parametros.put("inicioPagamento", filtro.getInicioPagamento());
			}
			if (DataUtil.isNotNull(filtro.getFimPagamento())) {
				sb.append(" and baixa.dataPagamento <= :fimPagamento ");
				parametros.put("fimPagamento", filtro.getFimPagamento());
			}
			if (DataUtil.isNotNull(filtro.getInicioVencimento())) {
				sb.append(" and baixa.debito.vencimento >= :inicioVencimento ");
				parametros.put("inicioVencimento", filtro.getInicioVencimento());
			}
			if (DataUtil.isNotNull(filtro.getFimVencimento())) {
				sb.append(" and baixa.debito.vencimento <= :fimVencimento ");
				parametros.put("fimVencimento", filtro.getFimVencimento());
			}
			if (filtro.getCategoria() != null) {
				sb.append(" and baixa.debito.categoria = :categoria ");
				parametros.put("categoria", filtro.getCategoria());
			}
			if (filtro.getSituacao() != null) {
				sb.append(" and baixa.debito.situacao = :situacao ");
				parametros.put("situacao", filtro.getSituacao());
			}
		}
		
		return (Map<String, BigDecimal>)persistence.pesquisarHqlUnique(sb.toString(), parametros);
	}

	public List<Integer> listarAnos() {
		StringBuilder sb = new StringBuilder();
		sb.append("select year(debito.vencimento) "); 
		sb.append(" from ").append(Debito.class.getName()).append(" debito ");
		sb.append(" where debito.situacao <> :situacao ");
		sb.append(" and debito.usuario = :usuario ");
		sb.append(" group by year(debito.vencimento) ");
		sb.append(" order by year(debito.vencimento) ");
		
		Map<String, Object> parametros = new HashMap<String, Object>();		
		parametros.put("situacao", SituacaoPagamentoEnum.QUITADO);
		parametros.put("usuario", UsuarioSessaoUtil.getUsuario());
		
		return persistence.pesquisarHql(sb.toString(), parametros, Integer.class);
	}
}
