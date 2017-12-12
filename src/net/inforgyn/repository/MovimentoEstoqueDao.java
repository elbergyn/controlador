package net.inforgyn.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.inforgyn.model.MovimentoEstoque;
import net.inforgyn.model.Produto;
import net.inforgyn.model.filterPesquisa.FilterMovimentoEstoque;
import net.inforgyn.util.DataUtil;
import net.inforgyn.util.StringUtil;
import net.inforgyn.util.UsuarioSessaoUtil;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class MovimentoEstoqueDao extends DaoModel{
	
	public List<Produto> listarProdutosMovimentoEstoque(FilterMovimentoEstoque filtro) {
			StringBuilder sb = new StringBuilder();
			sb.append("select new Produto(p.id, p.descricao) ");
			sb.append("from ").append(MovimentoEstoque.class.getName()).append(" m ");
			sb.append("inner join m.produto p ");
			sb.append("where m.usuario = :usuario ");
			
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("usuario", UsuarioSessaoUtil.getUsuario());
						
			if(StringUtil.isNotEmpty(filtro.getDescricaoProd())){
				sb.append(" and upper(p.descricao) like :descricao ");
				parametros.put("descricao", "%"+filtro.getDescricaoProd().toUpperCase()+"%");
			}
			
			if(filtro.getDataInicio() != null){
				sb.append(" and m.data >= :dataIni ");
				parametros.put("dataIni", filtro.getDataInicio());
			}
			
			if(filtro.getDataFim() != null){
				sb.append(" and date(m.data) <= :dataFim ");
				parametros.put("dataFim", filtro.getDataFim());
			}
			
			if(filtro.getTipoLancamento() != null){
				sb.append(" and m.tipoLancamento = :tipo");
				parametros.put("tipo", filtro.getTipoLancamento());
			}
			
			sb.append(" group by p ");
			sb.append(" order by p.descricao ");
			
		return persistence.executarPesquisaHql(sb.toString(), parametros, Produto.class);
	}

	public List<MovimentoEstoque> listarMovimentoEstoquePorProduto(Produto produto, FilterMovimentoEstoque filtro) {
		Criteria criteria = persistence.getCriteria(MovimentoEstoque.class);
		criteria.createAlias("produto", "produto");
		criteria.add(Restrictions.eq("produto", produto));
		
		if(filtro.getDataInicio() != null){
			criteria.add(Restrictions.ge("data", filtro.getDataInicio()));
		}
		
		if(filtro.getDataFim() != null){
			criteria.add(Restrictions.le("data", DataUtil.adicionarUltimaHoraDia(filtro.getDataFim())));
		}
		
		if(filtro.getTipoLancamento() != null){
			criteria.add(Restrictions.eq("tipoLancamento", filtro.getTipoLancamento()));
		}
		criteria.addOrder(Order.desc("data"));
		
		return persistence.pesquisarListCriteria(criteria);
	}
}
