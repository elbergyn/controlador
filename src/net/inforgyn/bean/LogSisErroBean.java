package net.inforgyn.bean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import net.inforgyn.model.LogSisErro;
import net.inforgyn.model.filterPesquisa.FilterLog;
import net.inforgyn.neg.LogSisNeg;
import net.inforgyn.util.faces.FacesUtil;

@Named
@ViewScoped
public class LogSisErroBean{
	@Inject private List<LogSisErro> logs;
	private List<LogSisErro> logsSelect;
	@Inject private LogSisNeg logNeg;
	@Inject private FilterLog filtro;
	
	public void excluir(){
		logNeg.excluir(logsSelect);
		logs.removeAll(logsSelect);
		logsSelect = new ArrayList<LogSisErro>();
		FacesUtil.infoMessageSimples("Excluído(s) log(s)");
	}
	
	public boolean habilitarExcluir(){
		return logsSelect == null || logsSelect.size() == 0;
	}
	
	public void limpar(){
		filtro = new FilterLog();
		logsSelect = new ArrayList<LogSisErro>();
		pesquisar();
	}
	
	public void pesquisar(){
		logs = logNeg.logs(filtro);
	}
	
	public FilterLog getFiltro() {
		return filtro;
	}

	public void setFiltro(FilterLog filtro) {
		this.filtro = filtro;
	}

	public List<LogSisErro> getLogsSelect() {
		return logsSelect;
	}

	public void setLogsSelect(List<LogSisErro> logsSelect) {
		this.logsSelect = logsSelect;
	}

	public List<LogSisErro> getLogs() {
		return logs;
	}
}
