package net.inforgyn.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import net.inforgyn.model.Devedor;
import net.inforgyn.neg.DevedorNeg;
import net.inforgyn.util.faces.FacesUtil;

@Named
@ViewScoped
public class CadastroDevedorBean implements CadastroBean, Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject private Devedor devedor;
	@Inject private List<Devedor> devedores;
	@Inject private DevedorNeg devedorNeg;
	private Boolean requerido = false;
	
	public void requererDescricao(){
		requerido = true;
	}
	
	public void anularDescricao(){
		requerido = false;
	}
	
	public Boolean getRequerido(){
		return requerido;
	}

	public void excluir(){
		devedorNeg.excluir(devedor);
		devedores.remove(devedor);
		FacesUtil.infoMessageSimples("Excluído: "+this.devedor.getDescricao());
	}

	public Devedor getDevedor() {
		return devedor;
	}

	@PostConstruct
	public void init(){
	}
	
	public List<Devedor> listarDevedores(){
		return devedores;
	}

	@Override
	public void novo() {
		devedor = new Devedor();
	}

	@Override
	public void salvar() {
		if(devedor.getId() == null){
			devedor = devedorNeg.salvar(devedor);
			devedores.add(devedor);
			FacesUtil.infoMessageSimples("Devedor cadastrado: "+this.devedor.getDescricao());
		}else {
			devedor = devedorNeg.alterar(devedor);
			FacesUtil.infoMessageSimples("Devedor alterado: "+this.devedor.getDescricao());
		}
		novo();
	}
	
	public void setDevedor(Devedor devedor) {
		this.devedor = devedor;
	}
}
