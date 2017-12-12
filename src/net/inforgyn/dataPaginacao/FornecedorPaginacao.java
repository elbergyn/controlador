package net.inforgyn.dataPaginacao;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import net.inforgyn.model.Fornecedor;
import net.inforgyn.repository.FornecedorDao;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

public class FornecedorPaginacao extends LazyDataModel<Fornecedor>{
	
	private static final long serialVersionUID = 1L;
	private FiltroPaginacao filtro;
	@Inject	FornecedorDao dao;
	
	@Override
	public List<Fornecedor> load(int first, int pageSize,
			String sortField, SortOrder order,
			Map<String, Object> filters) {
		filtro = new FiltroPaginacao(SortOrder.ASCENDING.equals(order),
				filters, sortField, first, pageSize);
		setRowCount(dao.totalPaginacao(filtro, Fornecedor.class));
				
		return dao.pesquisaPaginacao(filtro, Fornecedor.class);
	}
	
}
