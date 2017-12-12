package net.inforgyn.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import net.inforgyn.model.Banco;
import net.inforgyn.neg.BancoNeg;
import net.inforgyn.util.faces.FacesUtil;

@Named
@ViewScoped
public class CadastroBancoBean implements CadastroBean, Serializable{

	private static final long serialVersionUID = 1L;
	@Inject private Banco banco;
	@Inject	private BancoNeg bancoNeg;
	@Inject	private List<Banco> bancos;
	
	public CadastroBancoBean() {
		super();
	}

	@PostConstruct
	public void init(){
	}
	
	@Override
	public void novo() {
		banco = new Banco();
	}
	
	@Override
	public void salvar() {
		if(banco.getId() == null){
			bancoNeg.salvar(banco);
			FacesUtil.infoMessageSimples("Banco cadastrado: "+this.banco.getDescricao());
			bancos.add(banco);
		}else {
			bancoNeg.alterar(banco);
			FacesUtil.infoMessageSimples("Banco alterado: "+this.banco.getDescricao());
		}
		novo();
	}
	
	public void excluir(){
		bancoNeg.excluir(banco);
		bancos.remove(banco);
		FacesUtil.infoMessageSimples("Excluído: "+this.banco.getDescricao());
		novo();
	}
	
	public List<Banco> listarBancos(){
		return bancos;
	}

	public Banco getBanco() {
		return banco;
	}

	public void setBanco(Banco banco) {
		this.banco = banco;
	}
}
