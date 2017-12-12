package net.inforgyn.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import net.inforgyn.model.LogSisErro;
import net.inforgyn.model.filterPesquisa.FilterLog;
import net.inforgyn.repository.LogDao;

@Named
@ViewScoped
public class PesqLogBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject LogDao dao;
	
	private List<LogSisErro> logs;
	private FilterLog filtro;
		
	public void pesquisar(){
		logs = dao.listar(filtro);
	}

	public void limpar(){
		logs = new ArrayList<LogSisErro>();
	}

	public FilterLog getFiltro() {
		return filtro;
	}

	public void setFiltro(FilterLog filtro) {
		this.filtro = filtro;
	}

	public List<LogSisErro> getLogs() {
		return logs;
	}
}
