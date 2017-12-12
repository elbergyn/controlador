package net.inforgyn.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import net.inforgyn.model.TipoRecebimento;
import net.inforgyn.neg.TipoRecebimentoNeg;
import net.inforgyn.util.faces.FacesUtil;

@Named
@ViewScoped
public class CadastroTipoRecebimentoBean implements CadastroBean, Serializable{

	private static final long serialVersionUID = 1L;
	@Inject private TipoRecebimento tipoRecebimento;
	@Inject	private TipoRecebimentoNeg tipoRecebimentoNeg;
	@Inject	private List<TipoRecebimento> tipoRecebimentos;
	
	public CadastroTipoRecebimentoBean() {
		super();
	}

	@PostConstruct
	public void init(){
	}
	
	@Override
	public void novo() {
		tipoRecebimento = new TipoRecebimento();
	}
	
	@Override
	public void salvar() {
		if(tipoRecebimento.getId() == null){
			tipoRecebimentoNeg.salvar(tipoRecebimento);
			FacesUtil.infoMessageSimples("Tipo de recebimento cadastrado: "+this.tipoRecebimento.getDescricao());
			tipoRecebimentos.add(tipoRecebimento);
		}else {
			tipoRecebimentoNeg.alterar(tipoRecebimento);
			FacesUtil.infoMessageSimples("Tipo de recebimento alterado: "+this.tipoRecebimento.getDescricao());
		}
		novo();
	}
	
	public void excluir(){
		tipoRecebimentoNeg.excluir(tipoRecebimento);
		tipoRecebimentos.remove(tipoRecebimento);
		FacesUtil.infoMessageSimples("Excluído: "+this.tipoRecebimento.getDescricao());
		novo();
	}
	
	public List<TipoRecebimento> listarTipoRecebimentos(){
		return tipoRecebimentos;
	}

	public TipoRecebimento getTipoRecebimento() {
		return tipoRecebimento;
	}

	public void setTipoRecebimento(TipoRecebimento tipoRecebimento) {
		this.tipoRecebimento = tipoRecebimento;
	}
}
