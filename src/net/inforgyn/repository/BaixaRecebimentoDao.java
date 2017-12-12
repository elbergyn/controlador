package net.inforgyn.repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.inforgyn.adapter.MovimentoAdapter;
import net.inforgyn.constante.SituacaoPagamentoEnum;
import net.inforgyn.constante.TipoMovimentoEnum;
import net.inforgyn.impl.EntityPersistence;
import net.inforgyn.model.BaixaRecebimento;
import net.inforgyn.model.Credito;
import net.inforgyn.model.Debito;
import net.inforgyn.model.Movimento;
import net.inforgyn.model.Usuario;
import net.inforgyn.util.DataUtil;
import net.inforgyn.util.UsuarioSessaoUtil;

public class BaixaRecebimentoDao extends DaoModel{
	
	public void atualizarSituacao() {
		StringBuilder sb = new StringBuilder();
		sb.append("update ");
		sb.append(Credito.class.getName());
		sb.append(" set situacao = :situacaoNova where id in (");
		sb.append(" select id from ");
		sb.append(Credito.class.getName());
		sb.append(" where situacao = :situacao");
		sb.append(" AND vencimento < date(now())) ");
				
		Map<String, Object> parametros = new HashMap<String, Object>();
		/*parametros.put("situacao", SituacaoPagamentoEnum.QUITADO);*/
		parametros.put("situacao", SituacaoPagamentoEnum.A_VENCER);
		parametros.put("situacaoNova", SituacaoPagamentoEnum.ATRASO);
		
		persistence.atualizarHql(sb.toString(), parametros);
	}
	
	public void excluir(List<BaixaRecebimento> baixas) {
		List<Movimento> movimentos = new ArrayList<Movimento>();
		for (BaixaRecebimento baixa : baixas) {
			Movimento mov = new Movimento(null, new Date(),
					"Cancelar recebimento: " + baixa.getCredito().getDescricao()+
					" "+baixa.getCredito().getParcela()+
					"/"+baixa.getCredito().getParcelaTotal(),
					baixa.getValor(), UsuarioSessaoUtil.getUsuario(),
					TipoMovimentoEnum.DEBITO);
			movimentos.add(mov);
		}

		persistence.excluir(baixas);
		persistence.salvar(movimentos);
		
	}

	public List<BaixaRecebimento> listar() {
		Usuario usuario = UsuarioSessaoUtil.getUsuario();
		return persistence.listarPorUsuarioComPai(BaixaRecebimento.class, usuario);
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

	public List<BaixaRecebimento> listarRecebimentosDia() {
		StringBuilder sb = new StringBuilder();
		sb.append("from ").append(BaixaRecebimento.class.getName()).append(" baixa ");
		sb.append(" inner join fetch baixa.credito ");
		sb.append(" where date(baixa.lancamento) = date(:lancamento)");
		
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("lancamento", DataUtil.getDataAtual());
		
		return persistence.pesquisaHql(sb.toString(), parametros, BaixaRecebimento.class);
	}


	@Override
	public EntityPersistence pesquisarPorId(Class classe, Long id) {
		return persistence.pesquisarPorId(classe, id);
	}

	public List<BaixaRecebimento> pesquisarRecebimentos(
			Map<String, Object> filtro) {
		StringBuilder sb = new StringBuilder();
		sb.append("from ");
		sb.append(BaixaRecebimento.class.getName());
		sb.append(" where usuario = :usuario ");

		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("usuario", UsuarioSessaoUtil.getUsuario());
		if (filtro != null) {
			if (filtro.get("descricao") != null
					&& !filtro.get("descricao").equals("")) {
				sb.append(" and lower(credito.descricao) like lower(:descricao) ");
				parametros
						.put("descricao", "%" + filtro.get("descricao") + "%");
			}
			if (filtro.get("tipoRecebimento") != null) {
				sb.append(" and credito.tipoRecebimento = :tipoRecebimento ");
				parametros
						.put("tipoRecebimento", filtro.get("tipoRecebimento"));
			}
			if (filtro.get("inicioRecebimento") != null) {
				sb.append(" and dataPagamento >= :inicioRecebimento ");
				parametros.put("inicioRecebimento",
						filtro.get("inicioRecebimento"));
			}
			if (filtro.get("fimRecebimento") != null) {
				sb.append(" and dataPagamento <= :fimRecebimento ");
				parametros.put("fimRecebimento", filtro.get("fimRecebimento"));
			}
			if (filtro.get("situacao") != null) {
				sb.append(" and credito.situacao = :situacao ");
				parametros.put("situacao", filtro.get("situacao"));
			}
		}
		//sb.append(" orderby =  ");
		List<BaixaRecebimento> baixas = persistence.pesquisarHql(
				sb.toString(), parametros, BaixaRecebimento.class);
		return persistence.carregarLazy(baixas, "credito");
	}
	
	@Override
	public EntityPersistence salvar(EntityPersistence entity) {
		persistence.salvar(entity);
		return entity;
	}

	@SuppressWarnings("unchecked")
	public Map<String, BigDecimal> valorTotalPesquisa(Map<String, Object> filtro) {
		StringBuilder sb = new StringBuilder();
		sb.append("select new Map(sum(valor) as total) from ");
		sb.append(BaixaRecebimento.class.getName());
		sb.append(" where usuario = :usuario ");

		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("usuario", UsuarioSessaoUtil.getUsuario());
		if (filtro != null) {
			if (filtro.get("descricao") != null
					&& !filtro.get("descricao").equals("")) {
				sb.append(" and lower(credito.descricao) like lower(:descricao) ");
				parametros
						.put("descricao", "%" + filtro.get("descricao") + "%");
			}
			if (filtro.get("tipoRecebimento") != null) {
				sb.append(" and credito.tipoRecebimento = :tipoRecebimento ");
				parametros
						.put("tipoRecebimento", filtro.get("tipoRecebimento"));
			}
			if (filtro.get("inicioRecebimento") != null) {
				sb.append(" and dataPagamento >= :inicioRecebimento ");
				parametros.put("inicioRecebimento",
						filtro.get("inicioRecebimento"));
			}
			if (filtro.get("fimRecebimento") != null) {
				sb.append(" and dataPagamento <= :fimRecebimento ");
				parametros.put("fimRecebimento", filtro.get("fimRecebimento"));
			}
			if (filtro.get("situacao") != null) {
				sb.append(" and credito.situacao = :situacao ");
				parametros.put("situacao", filtro.get("situacao"));
			}
		}
		return (Map<String, BigDecimal>)persistence.pesquisarHqlUnique(sb.toString(), parametros);
	}
}
