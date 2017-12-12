package net.inforgyn.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import net.inforgyn.constante.TipoLancamentoEnum;
import net.inforgyn.model.Estoque;
import net.inforgyn.model.Produto;
import net.inforgyn.neg.EstoqueNeg;
import net.inforgyn.neg.ProdutoNeg;
import net.inforgyn.util.faces.FacesUtil;

import org.primefaces.event.SelectEvent;

@Named
@ViewScoped
public class CadastroEstoqueBean implements CadastroBean, Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject private List<Estoque> estoques;
	@Inject private EstoqueNeg neg;
	@Inject private Produto produto;
	@Inject private ProdutoNeg produtoNeg;
	private Integer qtde;
	private TipoLancamentoEnum tipoLancamento;
	
	@PostConstruct
	private void init(){
		tipoLancamento = TipoLancamentoEnum.ENTRADA;
		qtde = 0;
	}
	
	public void filtrarPorIdProduto() {
		if (produto.getId() != null) {
			produto = produtoNeg.carregarProdutoEstoque(produto);
			if (produto == null) {
				produto = new Produto();
				FacesUtil.alertMessageSimples("Produto não encontrado pelo código informado");
			}
		}
	}
	
	public String styleClass(Estoque estoque){
		return estoque != null && estoque.getQtde() < estoque.getQtdeMinima()?"alerta":"";
	}
	
	public SelectItem[] getTipoLancamentos(){
		return neg.getTiposLancamento();
	}

	public List<Estoque> getEstoques() {
		return estoques;
	}

	public Produto getProduto() {
		return produto;
	}

	public Integer getQtde() {
		return qtde;
	}

	public TipoLancamentoEnum getTipoLancamento() {
		return tipoLancamento;
	}

	public void itemSelect(SelectEvent event) {
		produto = (Produto)event.getObject();
		filtrarPorIdProduto();
	}

	@Override
	public void novo() {
		produto = new Produto();
		qtde = 0;
		tipoLancamento = TipoLancamentoEnum.ENTRADA;
	}

	public List<Produto> produtosAutoComplete(String filtro) {
		List<Produto> produtosFiltrados = new ArrayList<Produto>();
		for (Estoque estoque : estoques) {
			if (estoque.getProduto().getDescricao().toLowerCase()
					.contains(filtro.toLowerCase())) {
				produtosFiltrados.add(estoque.getProduto());
			}
		}
		return produtosFiltrados;
	}
	
	@Override
	public void salvar() {
		produto.getEstoque().setQtde(qtde);
		if(tipoLancamento.equals(TipoLancamentoEnum.ENTRADA)){
			neg.gerarMovimentoEstoqueEntrada(produto.getEstoque());
		}else{
			neg.gerarMovimentoEstoqueSaida(produto.getEstoque());
		}
		FacesUtil.infoMessageSimples("Estoque alterado: "+produto.getDescricao());
		
		estoques = neg.listarEstoques();
		
		novo();
	}

	public void setProduto(Produto produto) {
	//	this.produto = produto;
	}

	public void setQtde(Integer qtde) {
		this.qtde = qtde;
	}

	public void setTipoLancamento(TipoLancamentoEnum tipoLancamento) {
		this.tipoLancamento = tipoLancamento;
	}
	
	public void setEstoque(Estoque estoque){
		produto = estoque.getProduto();
	}
}
