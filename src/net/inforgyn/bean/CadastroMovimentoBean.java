package net.inforgyn.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import net.inforgyn.constante.TipoMovimentoEnum;
import net.inforgyn.model.Movimento;
import net.inforgyn.neg.MovimentoNeg;
import net.inforgyn.util.DataUtil;
import net.inforgyn.util.faces.FacesUtil;

@Named
@ViewScoped
public class CadastroMovimentoBean implements CadastroBean, Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject private MovimentoNeg movimentoNeg;
	@Inject private Movimento movimento;
	@Inject private List<Movimento> movimentos;
	private Map<String, Object> saldo;

	@Override
	public void novo() {
		movimento = new Movimento();
	}

	@Override
	public void salvar() {
		if(movimento.getId() == null){
			movimento = movimentoNeg.salvar(movimento);
			movimentos.add(movimento);
			FacesUtil.infoMessageSimples("Movimento cadastrado: "+this.movimento.getDescricao());
		}else {
			movimento = movimentoNeg.alterar(movimento);
			FacesUtil.infoMessageSimples("Movimento alterado: "+this.movimento.getDescricao());
		}
		carregarSaldo();
		novo();
	}
	
	private void carregarSaldo(){
		saldo = movimentoNeg.saldo(null, null);
	}

	public Map<String, Object> getSaldo() {
		return saldo;
	}

	@PostConstruct
	public void init(){
	}
	
	public Date getDataAtual(){
		return DataUtil.getDataAtual();
	}
	
	public void excluir(){
		movimentos.remove(movimento);
		movimentoNeg.excluir(movimento);
		System.out.println("Excluído: "+this.movimento.getDescricao());
	}

	public Movimento getMovimento() {
		return movimento;
	}

	public void setMovimento(Movimento movimento) {
		this.movimento = movimento;
	}
	
	public List<Movimento> listarMovimentos(){
		carregarSaldo();
		return movimentos;
	}
	
	public SelectItem[] listarTipoMovimento(){
		SelectItem[] itens= new SelectItem[TipoMovimentoEnum.values().length];
		int i = 0;
		for (TipoMovimentoEnum m : TipoMovimentoEnum.values()) {
			itens[i++] = new SelectItem(m, m.getDescricao()); 
		}
		return itens;
	}

}
