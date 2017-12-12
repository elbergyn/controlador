package net.inforgyn.repository;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import net.inforgyn.constante.SituacaoPagamentoEnum;
import net.inforgyn.impl.EntityPersistence;
import net.inforgyn.model.BaixaRecebimento;
import net.inforgyn.model.ContaReceber;
import net.inforgyn.model.Credito;
import net.inforgyn.model.Devedor;
import net.inforgyn.persistence.JPAPersistenceUtil;
import net.inforgyn.persistence.Transactional;
import net.inforgyn.util.DataUtil;
import net.inforgyn.util.UsuarioSessaoUtil;

public class CreditoDao extends DaoModel{
	@Transactional
	public void atualizarStatusCreditos(List<BaixaRecebimento> baixas) {
		List<Credito> creditos = new ArrayList<Credito>();
		
		
		for(BaixaRecebimento baixa : baixas){
			Credito credito = baixa.getCredito();
			
			credito = (Credito)persistence.carregarLazy(credito, "recebimentos");
			if(!credito.getValor().equals(credito.getValorPago()) && credito.getValorPago().longValue() > 0){
				credito.setSituacao(SituacaoPagamentoEnum.PAG_PARCIAL);
			}else if(DataUtil.dataAnterior(credito.getVencimento(), new Date())){
				credito.setSituacao(SituacaoPagamentoEnum.ATRASO);
			}else if(DataUtil.dataPosterior(credito.getVencimento(), new Date())
					|| DataUtil.dataIgual(credito.getVencimento(), new Date())){
				credito.setSituacao(SituacaoPagamentoEnum.A_VENCER);				
			}else{
				credito.setSituacao(SituacaoPagamentoEnum.QUITADO);
			}
			creditos.add(credito);
		}
		persistence.salvar(creditos);
	}

	public List<ContaReceber> listarCreditos(Map<String, Object> filtro) {
		StringBuilder sb = new StringBuilder();
		sb.append("from ");
		sb.append(Credito.class.getName());
		sb.append(" where usuario = :usuario");
		
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("usuario", UsuarioSessaoUtil.getUsuario());
				
		if(filtro != null){		
			if(filtro.get("devedor") != null && !filtro.get("devedor").equals("") ){
				Devedor devedor = (Devedor)filtro.get("devedor");
				sb.append(" and devedor = :devedor ");
				parametros.put("devedor", devedor);
			}
			if(filtro.get("descricao") != null && !filtro.get("descricao").equals("")){
				sb.append(" and lower(descricao) like lower(:descricao) ");
				parametros.put("descricao", "%"+filtro.get("descricao")+"%");
			}
			if(filtro.get("dataVencimentoIni") != null){
				sb.append(" and vencimento >= :dataVencimentoIni ");
				parametros.put("dataVencimentoIni", filtro.get("dataVencimentoIni"));
			}
			if(filtro.get("dataVencimentoFim") != null){
				sb.append(" and vencimento <= :dataVencimentoFim ");
				parametros.put("dataVencimentoFim", filtro.get("dataVencimentoFim"));
			}
			if(filtro.get("situacao") != null){
				sb.append(" and situacao = :situacao");
				parametros.put("situacao", filtro.get("situacao"));
			}
			
			if(filtro.get("tipoRecebimento") != null){
				sb.append(" and tipoRecebimento = :tipoRecebimento");
				parametros.put("tipoRecebimento", filtro.get("tipoRecebimento"));
			}
		}
		
		sb.append(" order by situacao, vencimento");
		
		return persistence.pesquisarHql(sb.toString(), parametros, Credito.class);
	}

	public List<Credito> listarCreditosAbertos(Map<String, Object> filtro) {
		StringBuilder sb = new StringBuilder();
		sb.append("from ");
		sb.append(Credito.class.getName());
		sb.append(" where situacao <> :situacao");
		sb.append(" and usuario = :usuario ");
		
		Map<String, Object> parametros = new HashMap<String, Object>();
		
		if(filtro != null){
			if (filtro.get("mes") != null && (Integer)filtro.get("mes") != 0) {
				sb.append(" and extract(month from vencimento) = :mes ");
				parametros.put("mes", filtro.get("mes"));
			}
			
			if (filtro.get("ano") != null && (Integer)filtro.get("ano") != 0) {
				sb.append(" and year(vencimento) = :ano ");
				parametros.put("ano", filtro.get("ano"));
			}
						
			String devedores =  filtro.get("devedores").toString();
			devedores = devedores.replace("[", "").replace("]", "");
			if(devedores != null && !devedores.equals("") ){
				sb.append(" and devedor.id in ( ");
				sb.append(devedores);
				sb.append(") ");
			}
		}
		
		sb.append(" order by vencimento");
		
		parametros.put("situacao", SituacaoPagamentoEnum.QUITADO);
		parametros.put("usuario", UsuarioSessaoUtil.getUsuario());
		
		return persistence.pesquisarHql(sb.toString(), parametros, Credito.class);
	}

	public List<BaixaRecebimento> listarRecebimentos(Credito credito) {
		StringBuilder sb = new StringBuilder();
		sb.append("select r from ");
		sb.append(Credito.class.getName());
		sb.append(" credito join credito.recebimentos r");
		sb.append(" where credito = :credito");
		
		Map<String, Object> parametro = new HashMap<String, Object>();
		parametro.put("credito", credito);
		
		return persistence.pesquisarHql(sb.toString(), parametro, BaixaRecebimento.class);
	}

	public Map<String, BigDecimal> valorTotalPesquisa(Map<String, Object> filtro) {
		StringBuilder sb = new StringBuilder();
		sb.append("select new Map(sum(credito.valor) as total, sum(pag.valor) as pago) from ");
		sb.append(Credito.class.getName()).append(" credito ");
		sb.append(" left join credito.recebimentos pag ");
		sb.append(" where credito.usuario = :usuario");
		
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("usuario", UsuarioSessaoUtil.getUsuario());
				
		if(filtro != null){
			if(filtro.get("devedor") != null && !filtro.get("devedor").equals("") ){
				Devedor devedor = (Devedor)filtro.get("devedor");
				sb.append(" and credito.devedor = :devedor ");
				parametros.put("devedor", devedor);
			}
			if(filtro.get("descricao") != null && !filtro.get("descricao").equals("")){
				sb.append(" and lower(descricao) like lower(:descricao) ");
				parametros.put("descricao", "%"+filtro.get("descricao")+"%");
			}
			if(filtro.get("dataVencimentoIni") != null){
				sb.append(" and vencimento >= :dataVencimentoIni ");
				parametros.put("dataVencimentoIni", filtro.get("dataVencimentoIni"));
			}
			if(filtro.get("dataVencimentoFim") != null){
				sb.append(" and vencimento <= :dataVencimentoFim ");
				parametros.put("dataVencimentoFim", filtro.get("dataVencimentoFim"));
			}
			if(filtro.get("situacao") != null){
				sb.append(" and credito.situacao = :situacao");
				parametros.put("situacao", filtro.get("situacao"));
			}
			
			if(filtro.get("tipoRecebimento") != null){
				sb.append(" and credito.tipoRecebimento = :tipoRecebimento");
				parametros.put("tipoRecebimento", filtro.get("tipoRecebimento"));
			}
		}
	
		return (Map<String, BigDecimal>)persistence.pesquisarHqlUnique(sb.toString(), parametros);
	}

	public Credito carregarLazy(Credito credito, String string) {
		return (Credito)persistence.carregarLazy(credito, string);
	}

}
