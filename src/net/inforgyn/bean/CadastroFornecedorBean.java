package net.inforgyn.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.LazyDataModel;

import net.inforgyn.model.Fornecedor;
import net.inforgyn.neg.FornecedorNeg;
import net.inforgyn.util.faces.FacesUtil;

@Named
@ViewScoped
public class CadastroFornecedorBean implements CadastroBean, Serializable{

	private static final long serialVersionUID = 1L;
	@Inject private Fornecedor fornecedor;
	@Inject	private FornecedorNeg fornecedorNeg;
	@Inject private LazyDataModel<Fornecedor> fornecedores;
	
	public CadastroFornecedorBean() {
		super();
	}

	@PostConstruct
	public void init(){
	}
	
	@Override
	public void novo() {
		fornecedor = new Fornecedor();
	}
	
	@Override
	public void salvar() {
		if(fornecedor.getId() == null){
			fornecedorNeg.salvar(fornecedor);
			FacesUtil.infoMessageSimples("Fornecedor cadastrado: "+this.fornecedor.getDescricao());
		}else {
			fornecedorNeg.alterar(fornecedor);
			FacesUtil.infoMessageSimples("Fornecedor alterado: "+this.fornecedor.getDescricao());
		}
		novo();
	}
	
	public void excluir(){
		fornecedorNeg.excluir(fornecedor);
		FacesUtil.infoMessageSimples("Excluído: "+this.fornecedor.getDescricao());
		novo();
	}

	public LazyDataModel<Fornecedor> getFornecedores() {
		return fornecedores;
	}

	public Fornecedor getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}
}
