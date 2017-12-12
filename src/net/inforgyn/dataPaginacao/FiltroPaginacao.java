package net.inforgyn.dataPaginacao;

import java.io.Serializable;
import java.util.Map;

public class FiltroPaginacao implements Serializable{

	private static final long serialVersionUID = 1L;

	private boolean order;
	private Map<String, Object> filters;
	private String sortField;
	private int first;
	private int pageSize;
	
	public FiltroPaginacao() {}
	
	public FiltroPaginacao(boolean order, Map<String, Object> filters,
			String sortField, int first, int pageSize) {
		super();
		this.order = order;
		this.filters = filters;
		this.sortField = sortField;
		this.first = first;
		this.pageSize = pageSize;
	}

	public boolean isOrder() {
		return order;
	}

	public Map<String, Object> getFilters() {
		return filters;
	}

	public String getSortField() {
		return sortField;
	}

	public int getFirst() {
		return first;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setOrder(boolean order) {
		this.order = order;
	}

	public void setFilters(Map<String, Object> filters) {
		this.filters = filters;
	}

	public void setSortField(String sortField) {
		this.sortField = sortField;
	}

	public void setFirst(int first) {
		this.first = first;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
}
