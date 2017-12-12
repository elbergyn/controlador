package net.inforgyn.dataPaginacao;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import net.inforgyn.constante.StatusProdutoEnum;
import net.inforgyn.model.Produto;
import net.inforgyn.model.ValorProduto;
import net.inforgyn.persistence.Transactional;
import net.inforgyn.repository.ValorProdutoDao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

public class ValorProdutoPaginacao extends LazyDataModel<ValorProduto>{
	
	private static final long serialVersionUID = 1L;
	private FiltroPaginacao filtro;
	@Inject	ValorProdutoDao dao;
	
	@Override
	@Transactional
	public List<ValorProduto> load(int first, int pageSize,
			String sortField, SortOrder order,
			Map<String, Object> filters) {
		filtro = new FiltroPaginacao(SortOrder.ASCENDING.equals(order),
				filters, sortField, first, pageSize);
		
		Criteria criteria = dao.getCriteria(ValorProduto.class);
		criteria.createAlias("produto", "produto");
		criteria.add(Restrictions.eq("produto.status", StatusProdutoEnum.ATIVO));
				
		List<ValorProduto> valores = dao.pesquisaPaginacao(criteria, filtro);
		
		Criteria crit = dao.getCriteria(ValorProduto.class);
		crit.createAlias("produto", "produto");
		crit.add(Restrictions.eq("produto.status", StatusProdutoEnum.ATIVO));
		setRowCount(dao.totalPaginacao(crit, filtro));
		
		for(ValorProduto vp : valores){
			dao.carregarLazy(vp, "produto");
		}
		
		return valores;
	}
	
}
