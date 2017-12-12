package net.inforgyn.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import net.inforgyn.model.BaixaRecebimento;
import net.inforgyn.neg.BaixaRecebimentoNeg;
import net.inforgyn.qualifierCdi.ListCancelarRecementoQ;
import net.inforgyn.util.faces.FacesUtil;

@Named
@ViewScoped
public class CancelarRecebimentoBean implements CadastroBean, Serializable {

	private static final long serialVersionUID = 1L;
	@Inject @ListCancelarRecementoQ 
	private List<BaixaRecebimento> baixas;
	private List<BaixaRecebimento> cancelarBaixas;
	private boolean permitirCancelar = false;
	@Inject BaixaRecebimentoNeg recebimentoNeg;
	
	public void checked() {
		if (null != cancelarBaixas && !cancelarBaixas.isEmpty()) {
			permitirCancelar = true;
		}
	}

	public List<BaixaRecebimento> getBaixas() {
		return baixas;
	}

	public List<BaixaRecebimento> getCancelarBaixas() {
		return cancelarBaixas;
	}
	
	public boolean isPermitirCancelar() {
		return permitirCancelar;
	}
	
	@Override
	public void novo() {	
		cancelarBaixas = new ArrayList<BaixaRecebimento>();
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
			recebimentoNeg.cancelarBaixaRecebimento(cancelarBaixas);
			baixas.removeAll(cancelarBaixas);
			FacesUtil.infoMessageSimples("Cancelamento realizado");
			novo();
		} else {
			FacesUtil.infoMessageSimples("Selecione o item a ser realizado o cancelamento");
		}
	}

	public void setCancelarBaixas(List<BaixaRecebimento> cancelarBaixas) {
		this.cancelarBaixas = cancelarBaixas;
	}

	public void unChecked() {
		if (null == cancelarBaixas || cancelarBaixas.size() == 0) {
			permitirCancelar = false;
		}
	}
}
