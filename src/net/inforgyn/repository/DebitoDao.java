package net.inforgyn.repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.inforgyn.constante.SituacaoPagamentoEnum;
import net.inforgyn.impl.EntityPersistence;
import net.inforgyn.model.BaixaPagamento;
import net.inforgyn.model.Cheque;
import net.inforgyn.model.Debito;
import net.inforgyn.model.DebitoCheque;
import net.inforgyn.model.EmitirCheque;
import net.inforgyn.model.filterPesquisa.FilterDebito;
import net.inforgyn.model.filterPesquisa.FiltroBaixaPagamento;
import net.inforgyn.persistence.Transactional;
import net.inforgyn.util.DataUtil;
import net.inforgyn.util.StringUtil;
import net.inforgyn.util.UsuarioSessaoUtil;

public class DebitoDao extends DaoModel{
	
	public List<Debito> listarDebitosAbertos(FiltroBaixaPagamento filtro) {
		StringBuilder sb = new StringBuilder();
		sb.append("from ");
		sb.append(Debito.class.getName()).append(" debito ");
		sb.append(" inner join fetch debito.categoria ");
		sb.append(" where debito.situacao <> :situacao ");
		sb.append(" and debito.usuario = :usuario ");
		
		Map<String, Object> parametros = new HashMap<String, Object>();
		
		if(filtro != null){
			if (filtro.getMes() != null && (Integer)filtro.getMes() != 0) {
				sb.append(" and extract(month from debito.vencimento) = :mes ");
				parametros.put("mes", filtro.getMes());
			}		
			if(filtro.getCartao() != null){
				sb.append(" and debito.cartao = :cartao");
				parametros.put("cartao", filtro.getCartao());
			}
			if(filtro.getCheque() != null){
				sb.append(" and debito.emitirCheque.cheque = :cheque");
				parametros.put("cheque", filtro.getCheque());
			}
			if (filtro.getAno() != null && (Integer)filtro.getAno() != 0) {
				sb.append(" and year(debito.vencimento) = :ano ");
				parametros.put("ano", filtro.getAno());
			}
			if (filtro.getCategoria() != null) {
				sb.append(" and debito.categoria = :categoria ");
				parametros.put("categoria", filtro.getCategoria());
			}
		}		
		
		sb.append(" order by debito.vencimento");
		
		parametros.put("situacao", SituacaoPagamentoEnum.QUITADO);
		parametros.put("usuario", UsuarioSessaoUtil.getUsuario());
		
		return persistence.pesquisarHql(sb.toString(), parametros, Debito.class);
	}

	public List<DebitoCheque> listarChequesEmitidos(FilterDebito filtro) {
		StringBuilder sb = new StringBuilder();
		sb.append("select debito from ");
		sb.append(DebitoCheque.class.getName());
		sb.append(" debito ");
		sb.append(" join debito.emitirCheque emitir ");
		sb.append(" join emitir.cheque cheque ");
		sb.append(" join debito.usuario usuario ");
		sb.append(" where 1=1 ");
		
		Map<String, Object> parametros = new HashMap<String, Object>();
		
		if(DataUtil.isNotNull(filtro.getDataEmissaoIni())){
			sb.append(" and debito.lancamento >= :dataEmissaoIni ");
			parametros.put("dataEmissaoIni", filtro.getDataEmissaoIni());
		}
		if(DataUtil.isNotNull(filtro.getDataEmissaoFim())){
			sb.append(" and debito.lancamento <= :dataEmissaoFim ");
			parametros.put("dataEmissaoFim", filtro.getDataEmissaoFim());
		}
		if(DataUtil.isNotNull(filtro.getDataVencimentoIni())){
			sb.append(" and debito.vencimento >= :dataVencimentoIni ");
			parametros.put("dataVencimentoIni", filtro.getDataVencimentoIni());
		}
		if(DataUtil.isNotNull(filtro.getDataVencimentoFim())){
			sb.append(" and debito.vencimento <= :dataVencimentoFim ");
			parametros.put("dataVencimentoFim", filtro.getDataVencimentoFim());
		}
		if(StringUtil.isNotEmpty(filtro.getDescricao())){
			sb.append(" and lower(debito.descricao) like lower(:descricao) ");
			parametros.put("descricao", "%"+filtro.getDescricao()+"%");
		}
		if(filtro.getCheque() != null){
			sb.append(" and cheque = :cheque ");
			parametros.put("cheque", ((Cheque)filtro.getCheque()));
		}
		if(filtro.getSituacao() != null){
			sb.append(" and debito.situacao = :situacao");
			parametros.put("situacao", filtro.getSituacao());
		}
		if(filtro.getCategoria() != null){
			sb.append(" and debito.categoria = :categoria ");
			parametros.put("categoria", filtro.getCategoria());
		}
		sb.append(" and usuario.id = :usuario  ");
		sb.append(" order by debito.vencimento");
				
		parametros.put("usuario", UsuarioSessaoUtil.getUsuario().getId());
		
		return persistence.pesquisarHql(sb.toString(), parametros, DebitoCheque.class);
	}

	public boolean varificarChequeEmitido(EmitirCheque cheque) {		
		StringBuilder sb = new StringBuilder();
		sb.append("select debito from ");
		sb.append(DebitoCheque.class.getName());
		sb.append(" debito ");
		sb.append(" join debito.emitirCheque emitir ");
		sb.append(" join emitir.cheque cheque ");
		sb.append(" join debito.usuario usuario ");
		sb.append(" where emitir.numeroCheque = :numero");
		sb.append(" and cheque.id = :cheque");
		sb.append(" and usuario.id = :usuario");
		
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("numero", cheque.getNumeroCheque());
		parametros.put("cheque", cheque.getCheque().getId());
		parametros.put("usuario", UsuarioSessaoUtil.getUsuario().getId());
		
		return persistence.validarEntityHql(sb.toString(), parametros);
	}

	public List<Debito> listarDebitos(FilterDebito filtro) {
		StringBuilder sb = new StringBuilder();
		sb.append("select debito from ");
		sb.append(Debito.class.getName());
		sb.append(" debito ");
		sb.append(" join debito.usuario usuario ");
		sb.append(" join fetch debito.categoria categoria ");
		sb.append(" where usuario.id = :usuario ");
		
		Map<String, Object> parametros = new HashMap<String, Object>();
		
		if(DataUtil.isNotNull(filtro.getDataEmissaoIni())){
			sb.append(" and debito.lancamento >= :dataEmissaoIni ");
			parametros.put("dataEmissaoIni", filtro.getDataEmissaoIni());
		}
		if(DataUtil.isNotNull(filtro.getDataEmissaoFim())){
			sb.append(" and debito.lancamento <= :dataEmissaoFim ");
			parametros.put("dataEmissaoFim", filtro.getDataEmissaoFim());
		}
		if(DataUtil.isNotNull(filtro.getDataVencimentoIni())){
			sb.append(" and debito.vencimento >= :dataVencimentoIni ");
			parametros.put("dataVencimentoIni", filtro.getDataVencimentoIni());
		}
		if(DataUtil.isNotNull(filtro.getDataVencimentoFim())){
			sb.append(" and debito.vencimento <= :dataVencimentoFim ");
			parametros.put("dataVencimentoFim", filtro.getDataVencimentoFim());
		}
		if(StringUtil.isNotEmpty(filtro.getDescricao())){
			sb.append(" and lower(debito.descricao) like lower(:descricao) ");
			parametros.put("descricao", "%"+filtro.getDescricao()+"%");
		}
		if(filtro.getSituacao() != null){
			sb.append(" and debito.situacao = :situacao");
			parametros.put("situacao", filtro.getSituacao());
		}
		if(filtro.getCategoria() != null){
			sb.append(" and debito.categoria = :categoria ");
			parametros.put("categoria", filtro.getCategoria());
		}
		
		if(filtro.getTipoPagamento() != null){
			sb.append(" and debito.tipoPagamento = :tipoPagamento");
			parametros.put("tipoPagamento", filtro.getTipoPagamento());
			
			if(filtro.getCheque() != null){
				sb.append(" and debito.emitirCheque.cheque = :cheque");
				parametros.put("cheque", filtro.getCheque());
			}
			
			if(filtro.getCartao() != null){
				sb.append(" and debito.cartao = :cartao ");
				parametros.put("cartao", filtro.getCartao());
			}
		}
		
		sb.append(" order by debito.situacao, debito.vencimento");
				
		parametros.put("usuario", UsuarioSessaoUtil.getUsuario().getId());
		
		List<Debito> debitos = persistence.pesquisarHql(sb.toString(), parametros, Debito.class);
		
		//debitos = persistence.carregarLazy(debitos, "categoria");
		
		return debitos;
	}

	@Transactional
	public void atualizarStatusDebitos(List<BaixaPagamento> baixas) {
		List<Debito> debitos = new ArrayList<Debito>();
		
		for(BaixaPagamento baixa : baixas){
			Debito debito = baixa.getDebito();
			
			debito = (Debito)persistence.carregarLazy(debito, "pagamentos");
			if(!debito.getValor().equals(debito.getValorPago()) && debito.getValorPago().longValue() > 0){
				debito.setSituacao(SituacaoPagamentoEnum.PAG_PARCIAL);
			}else if(DataUtil.dataAnterior(debito.getVencimento(), new Date())){
				debito.setSituacao(SituacaoPagamentoEnum.ATRASO);
			}else if(DataUtil.dataPosterior(debito.getVencimento(), new Date())
					|| DataUtil.dataIgual(debito.getVencimento(), new Date())){
				debito.setSituacao(SituacaoPagamentoEnum.A_VENCER);				
			}else{
				debito.setSituacao(SituacaoPagamentoEnum.QUITADO);
			}
			debitos.add(debito);
		}
		persistence.salvar(debitos);
	}

	@SuppressWarnings("unchecked")
	public Map<String, BigDecimal> valorTotalPesquisa(FilterDebito filtro) {
		StringBuilder sb = new StringBuilder();
		sb.append("select new Map(sum(debito.valor) as total, sum(pag.valor) as pago) from ");
		sb.append(Debito.class.getName());
		sb.append(" debito ");
		sb.append(" join debito.usuario usuario ");
		sb.append(" left join debito.pagamentos pag ");
		sb.append(" where usuario = :usuario ");
		
		Map<String, Object> parametros = new HashMap<String, Object>();
		
		if(DataUtil.isNotNull(filtro.getDataEmissaoIni())){
			sb.append(" and debito.lancamento >= :dataEmissaoIni ");
			parametros.put("dataEmissaoIni", filtro.getDataEmissaoIni());
		}
		if(DataUtil.isNotNull(filtro.getDataEmissaoFim())){
			sb.append(" and debito.lancamento <= :dataEmissaoFim ");
			parametros.put("dataEmissaoFim", filtro.getDataEmissaoFim());
		}
		if(DataUtil.isNotNull(filtro.getDataVencimentoIni())){
			sb.append(" and debito.vencimento >= :dataVencimentoIni ");
			parametros.put("dataVencimentoIni", filtro.getDataVencimentoIni());
		}
		if(DataUtil.isNotNull(filtro.getDataVencimentoFim())){
			sb.append(" and debito.vencimento <= :dataVencimentoFim ");
			parametros.put("dataVencimentoFim", filtro.getDataVencimentoFim());
		}
		if(StringUtil.isNotEmpty(filtro.getDescricao())){
			sb.append(" and lower(debito.descricao) like lower(:descricao) ");
			parametros.put("descricao", "%"+filtro.getDescricao()+"%");
		}
		if(filtro.getSituacao() != null){
			sb.append(" and debito.situacao = :situacao");
			parametros.put("situacao", filtro.getSituacao());
		}
		if(filtro.getCategoria() != null){
			sb.append(" and debito.categoria = :categoria ");
			parametros.put("categoria", filtro.getCategoria());
		}
		
		if(filtro.getTipoPagamento() != null){
			sb.append(" and debito.tipoPagamento = :tipoPagamento");
			parametros.put("tipoPagamento", filtro.getTipoPagamento());
			
			if(filtro.getCheque() != null){
				sb.append(" and debito.emitirCheque.cheque = :cheque");
				parametros.put("cheque", filtro.getCheque());
			}
			
			if(filtro.getCartao() != null){
				sb.append(" and debito.cartao = :cartao ");
				parametros.put("cartao", filtro.getCartao());
			}
		}
				
		parametros.put("usuario", UsuarioSessaoUtil.getUsuario());
		return (Map<String, BigDecimal>) persistence.pesquisarHqlUnique(sb.toString(), parametros);
	}

	public Debito carregarLazy(Debito debito, String... string) {
		return (Debito)persistence.carregarLazy(debito, string);
	}

	public List<BaixaPagamento> carregarLazy(List<Debito> debitos, String string) {
		return persistence.carregarLazy(debitos, "pagamentos");
	}
}
