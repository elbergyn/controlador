package net.inforgyn.repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.inforgyn.constante.TipoMovimentoEnum;
import net.inforgyn.model.Movimento;
import net.inforgyn.util.NumericUtil;
import net.inforgyn.util.UsuarioSessaoUtil;

public class MovimentoDao extends DaoModel{
	
	public List<Movimento> listarUltimosMovimentos() {
		return persistence.listarComQtdeOrder(Movimento.class, UsuarioSessaoUtil.getUsuario(), 20, "dataLancamento desc");
	}

	public Map<String, Object> saldo(Date inicio, Date limite) {
		
		StringBuilder sb = new StringBuilder();
		sb.append("Select tipoMovimento, sum(valor) from ");
		sb.append(Movimento.class.getName());
		sb.append(" where usuario = :usuario ");
		
		Map<String, Object> filtro = new HashMap<String, Object>();
		
		if(inicio != null){
			sb.append(" and dataLancamento >= :inicio ");
			filtro.put("inicio", inicio);
		}
		if(limite != null){
			sb.append(" and dataLancamento <= :limite ");
			filtro.put("limite", limite);
		}
		
		sb.append(" group by tipoMovimento");
		
		filtro.put("usuario", UsuarioSessaoUtil.getUsuario());
		
		List<Object> result = persistence.pesquisaHql(sb.toString(), filtro);
		
		Map<String, Object> saldo = new HashMap<String, Object>();

		BigDecimal credito = new BigDecimal(0);
		BigDecimal debito = new BigDecimal(0);
		
		saldo.put("Crédito", credito);
		saldo.put("Débito", debito);
		
		for(Object obj : result){
			Object[] item = (Object[]) obj;
			
			if(((TipoMovimentoEnum)item[0]).equals(TipoMovimentoEnum.CREDITO)){
				credito = (BigDecimal)item[1];
			}else{
				debito = (BigDecimal)item[1];
			}
			
			saldo.put(((TipoMovimentoEnum)item[0]).getDescricao(), NumericUtil.moeda((BigDecimal)item[1]));
		}				
		
		saldo.put("total", NumericUtil.moeda(credito.subtract(debito)));
		saldo.put("valorTotal", credito.subtract(debito));
		
		return saldo;
	}

	public List<Movimento> listarMovimentos(Map<String, Object> filtro) {
		StringBuilder sb = new StringBuilder();
		sb.append("from ");
		sb.append(Movimento.class.getName());
		sb.append(" where usuario = :usuario");
		
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("usuario", UsuarioSessaoUtil.getUsuario());
		
		if(filtro != null){
			if(filtro.get("descricao") != null && filtro.get("descricao") != ""){
				sb.append(" and lower(descricao) like :descricao ");
				parametros.put("descricao", "%"+filtro.get("descricao")+"%");
			}
			if(filtro.get("dataMovimentoIni") != null){
				sb.append(" and dataLancamento >= :dataMovimentoIni ");
				parametros.put("dataMovimentoIni", filtro.get("dataMovimentoIni"));
			}
			if(filtro.get("dataMovimentoFim") != null){
				sb.append(" and dataLancamento <= :dataMovimentoFim ");
				parametros.put("dataMovimentoFim", filtro.get("dataMovimentoFim"));
			}
			if(filtro.get("tipoMovimento") != null){
				sb.append(" and tipoMovimento = :tipoMovimento");
				parametros.put("tipoMovimento", filtro.get("tipoMovimento"));
			}
		}
		
		return persistence.pesquisarHql(sb.toString(), parametros, Movimento.class);
	}
	
}
