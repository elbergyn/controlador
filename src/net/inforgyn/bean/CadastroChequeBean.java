package net.inforgyn.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import net.inforgyn.model.Banco;
import net.inforgyn.model.Cheque;
import net.inforgyn.neg.BancoNeg;
import net.inforgyn.neg.ChequeNeg;
import net.inforgyn.util.faces.FacesUtil;

@Named
@ViewScoped
public class CadastroChequeBean implements CadastroBean, Serializable{

	private static final long serialVersionUID = 1L;
	@Inject private ChequeNeg chequeNeg;
	@Inject private Cheque cheque;
	@Inject private BancoNeg bancoNeg;
	@Inject private List<Cheque> cheques;

	@Override
	public void novo() {
		cheque = new Cheque();
	}

	@Override
	public void salvar() {
		if(cheque.getId() == null){
			cheque = chequeNeg.salvar(cheque);
			cheques.add(cheque);
			FacesUtil.infoMessageSimples("Cheque cadastrado: "+this.cheque.getDescricao());
		}else {
			cheque = chequeNeg.alterar(cheque);
			FacesUtil.infoMessageSimples("Cheque alterado: "+this.cheque.getDescricao());
		}
		novo();
	}
	
	@PostConstruct
	public void init(){
	}
	
	public void excluir(){
		chequeNeg.excluir(cheque);
		cheques.remove(cheque);
		FacesUtil.infoMessageSimples("Excluído: "+this.cheque.getDescricao());
		novo();
	}
	
	public Cheque getCheque() {
		return cheque;
	}

	public void setCheque(Cheque cheque) {
		this.cheque = cheque;
	}

	public List<Cheque> listarCheques(){
		return cheques;
	}
	
	public List<Banco> listarBancos(){
		return bancoNeg.listarBancos();
	}

}
