package net.inforgyn.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import net.inforgyn.model.Cliente;
import net.inforgyn.neg.ClienteNeg;
import net.inforgyn.util.faces.FacesUtil;

@Named
@ViewScoped
public class CadastroClienteBean implements CadastroBean, Serializable{

	private static final long serialVersionUID = 1L;
	@Inject private Cliente cliente;
	@Inject	private ClienteNeg clienteNeg;
	@Inject	private List<Cliente> clientes;
	
	public CadastroClienteBean() {
		super();
	}

	@PostConstruct
	public void init(){
	}
	
	@Override
	public void novo() {
		cliente = new Cliente();
	}
	
	@Override
	public void salvar() {
		if(cliente.getId() == null){
			clienteNeg.salvar(cliente);
			FacesUtil.infoMessageSimples("Cliente cadastrado: "+this.cliente.getNome());
			clientes.add(cliente);
		}else {
			clienteNeg.alterar(cliente);
			FacesUtil.infoMessageSimples("Cliente alterado: "+this.cliente.getNome());
		}
		novo();
	}
	
	public void excluir(){
		clienteNeg.excluir(cliente);
		clientes.remove(cliente);
		FacesUtil.infoMessageSimples("Excluído: "+this.cliente.getNome());
		novo();
	}
	
	public List<Cliente> listarClientes(){
		return clientes;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
}
