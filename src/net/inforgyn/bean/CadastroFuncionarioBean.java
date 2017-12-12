package net.inforgyn.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import net.inforgyn.model.Funcionario;
import net.inforgyn.neg.FuncionarioNeg;
import net.inforgyn.util.faces.FacesUtil;

@Named
@ViewScoped
public class CadastroFuncionarioBean implements CadastroBean, Serializable{

	private static final long serialVersionUID = 1L;
	@Inject private Funcionario funcionario;
	@Inject	private FuncionarioNeg funcionarioNeg;
	@Inject	private List<Funcionario> funcionarios;
	
	public CadastroFuncionarioBean() {
		super();
	}

	@PostConstruct
	public void init(){
	}
	
	@Override
	public void novo() {
		funcionario = new Funcionario();
	}
	
	@Override
	public void salvar() {
		if(funcionario.getId() == null){
			funcionarioNeg.salvar(funcionario);
			FacesUtil.infoMessageSimples("Funcionario cadastrado: "+this.funcionario.getNome());
			funcionarios.add(funcionario);
		}else {
			funcionarioNeg.alterar(funcionario);
			FacesUtil.infoMessageSimples("Funcionario alterado: "+this.funcionario.getNome());
		}
		novo();
	}
	
	public void excluir(){
		funcionarioNeg.excluir(funcionario);
		funcionarios.remove(funcionario);
		FacesUtil.infoMessageSimples("Excluído: "+this.funcionario.getNome());
		novo();
	}
	
	public List<Funcionario> listarFuncionarios(){
		return funcionarios;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}
}
