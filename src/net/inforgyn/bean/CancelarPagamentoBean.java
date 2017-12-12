package net.inforgyn.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import net.inforgyn.model.BaixaPagamento;
import net.inforgyn.neg.BaixaPagamentoNeg;
import net.inforgyn.qualifierCdi.ListCancelarPagamentoQ;
import net.inforgyn.util.faces.FacesUtil;

@Named
@ViewScoped
public class CancelarPagamentoBean implements CadastroBean, Serializable {

	private static final long serialVersionUID = 1L;
	@Inject @ListCancelarPagamentoQ 
	private List<BaixaPagamento> baixas;
	private List<BaixaPagamento> cancelarBaixas;
	@Inject BaixaPagamentoNeg pagamentoNeg;
	private boolean permitirCancelar = false;
	
	public void checked() {
		if (null != cancelarBaixas && !cancelarBaixas.isEmpty()) {
			permitirCancelar = true;
		}
	}

	public List<BaixaPagamento> getBaixas() {
		return baixas;
	}

	public List<BaixaPagamento> getCancelarBaixas() {
		return cancelarBaixas;
	}
	
	public boolean isPermitirCancelar() {
		return permitirCancelar;
	}
	
	@Override
	public void novo() {	
		cancelarBaixas = new ArrayList<BaixaPagamento>();
	}
	
	public void rowToggleSelect() {
		if (cancelarBaixas != null && !cancelarBaixas.isEmpty()) {
			permitirCancelar = true;
		} else {
			permitirCancelar = false;
		}
	}

	@Override
	public void salvar() {
		if (cancelarBaixas != null && !cancelarBaixas.isEmpty()) {
			pagamentoNeg.cancelarBaixaPagamento(cancelarBaixas);
			baixas.removeAll(cancelarBaixas);
			FacesUtil.infoMessageSimples("Cancelamento realizado");
			novo();
		} else {
			FacesUtil.infoMessageSimples("Selecione o item a ser realizado o cancelamento");
		}
	}

	public void setCancelarBaixas(List<BaixaPagamento> cancelarBaixas) {
		this.cancelarBaixas = cancelarBaixas;
	}

	public void unChecked() {
		if (null == cancelarBaixas || cancelarBaixas.size() == 0) {
			permitirCancelar = false;
		}
	}
}