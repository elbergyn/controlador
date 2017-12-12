package net.inforgyn.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import net.inforgyn.model.CategoriaDespesa;
import net.inforgyn.neg.CategoriaDespesaNeg;
import net.inforgyn.util.faces.FacesUtil;

@Named
@ViewScoped
public class CadastroCategoriaBean implements CadastroBean, Serializable{

	private static final long serialVersionUID = 1L;
	@Inject private CategoriaDespesaNeg categoriaNeg;
	@Inject private CategoriaDespesa categoria;
	@Inject private List<CategoriaDespesa> categorias;

	@Override
	public void novo() {
		categoria = new CategoriaDespesa();
	}

	@Override
	public void salvar() {
		if(categoria.getId() == null){
			categoria = categoriaNeg.salvar(categoria);
			categorias.add(categoria);
			FacesUtil.infoMessageSimples("Categoria cadastrada: "+this.categoria.getDescricao());
		}else {
			categoria = categoriaNeg.alterar(categoria);
			FacesUtil.infoMessageSimples("Banco alterado: "+this.categoria.getDescricao());
		}
		novo();
	}

	@PostConstruct
	public void init(){
	}
	
	public void excluir(){
		categoriaNeg.excluir(categoria);
		categorias.remove(categoria);
		FacesUtil.infoMessageSimples("Excluído: "+this.categoria.getDescricao());
		novo();
	}

	public CategoriaDespesa getCategoria() {
		return categoria;
	}

	public void setCategoria(CategoriaDespesa categoria) {
		this.categoria = categoria;
	}
	
	public List<CategoriaDespesa> listarCategorias(){
		return categorias;
	}
}
