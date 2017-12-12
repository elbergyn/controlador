package net.inforgyn.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.inforgyn.model.HistoricoValorProduto;
import net.inforgyn.model.filterPesquisa.FilterHistoricoValor;
import net.inforgyn.util.StringUtil;
import net.inforgyn.util.UsuarioSessaoUtil;

public class HistoricoValorProdutoDao extends DaoModel{

	public List<HistoricoValorProduto> listarProdutosComHistorico(FilterHistoricoValor filtro) {
		StringBuilder sb = new StringBuilder();
		sb.append("from ").append(HistoricoValorProduto.class.getName()).append(" valor ");
		sb.append(" where valor.usuario = :usuario");
		
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("usuario", UsuarioSessaoUtil.getUsuario());
		
		if(StringUtil.isNotEmpty(filtro.getDescricaoProd())){
			sb.append(" and upper(produto.descricao) like :descricao");
			parametros.put("descricao", "%"+filtro.getDescricaoProd().toUpperCase()+"%");
		}
		if(null != filtro.getDataInicio()){
			sb.append(" and date(valor.data) >= :datai");
			parametros.put("datai", filtro.getDataInicio());
		}
		if(null != filtro.getDataFim()){
			sb.append(" and date(valor.data) <= :dataf");
			parametros.put("dataf", filtro.getDataFim());
		}
		sb.append(" order by valor.produto.descricao asc");
		//sb.append(" group by valor.produto.id, valor.id");
		return persistence.pesquisaHql(sb.toString(), parametros, HistoricoValorProduto.class);
	}

}
