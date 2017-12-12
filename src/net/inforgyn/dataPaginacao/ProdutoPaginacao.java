package net.inforgyn.dataPaginacao;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import net.inforgyn.constante.StatusProdutoEnum;
import net.inforgyn.model.Produto;
import net.inforgyn.repository.ProdutoDao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

public class ProdutoPaginacao extends LazyDataModel<Produto>{
	
	private static final long serialVersionUID = 1L;
	private FiltroPaginacao filtro;
	@Inject	ProdutoDao dao;
	
	@Override
	public List<Produto> load(int first, int pageSize,
			String sortField, SortOrder order,
			Map<String, Object> filters) {
		filtro = new FiltroPaginacao(SortOrder.ASCENDING.equals(order),
				filters, sortField, first, pageSize);
		setRowCount(dao.totalPaginacao(filtro, Produto.class));
		
		Criteria criteria = dao.getCriteria(Produto.class);
		criteria.createAlias("valor", "valor");
		criteria.createAlias("estoque", "estoque");
		
		criteria.add(Restrictions.eq("status", StatusProdutoEnum.ATIVO));
		
		return dao.pesquisaPaginacao(criteria, filtro);
	}
	
}
