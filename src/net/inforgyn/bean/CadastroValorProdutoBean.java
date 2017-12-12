package net.inforgyn.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.LazyDataModel;

import net.inforgyn.model.Produto;
import net.inforgyn.model.ValorProduto;
import net.inforgyn.neg.ProdutoNeg;
import net.inforgyn.util.faces.FacesUtil;

@Named
@ViewScoped
public class CadastroValorProdutoBean implements CadastroBean, Serializable {

	private static final long serialVersionUID = 1L;

	@Inject	ProdutoNeg neg;
	private Produto produto;
	@Inject	LazyDataModel<Produto> produtos;
	private Integer qtde;

	public LazyDataModel<Produto> getValores() {
		return produtos;
	}

	@PostConstruct
	private void init() {
		produto = new Produto();
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	@Override
	public void novo() {
		produto = new Produto();
		qtde = 0;
	}

	public List<Produto> produtosAutoComplete(String filtro) {
		List<Produto> produtosFiltrados = new ArrayList<Produto>();
		for (Produto prod : produtos) {
			if (prod.getDescricao().toLowerCase()
					.contains(filtro.toLowerCase())) {
				produtosFiltrados.add(prod);
			}
		}
		return produtosFiltrados;
	}
	
	public void onCellEdit(CellEditEvent event){
		Object ant = event.getOldValue();
		Object novo = event.getNewValue();
		
		if(!ant.toString().equals(novo.toString())){
			DataTable dataModel = (DataTable) event.getSource();
			produto = (Produto) dataModel.getRowData();
			
			if(novo instanceof Integer){
				produto.getEstoque().setQtde((Integer)novo);
			}else{
				//calcularValor();
			}
			neg.alterar(produto);
			
			novo();
		}
	}
	
	public void onRowEdit(RowEditEvent event){
			DataTable dataModel = (DataTable) event.getSource();
			produto = (Produto) dataModel.getRowData();
			produto.getEstoque().setQtde(qtde);
			//calcularValor();
			
			neg.alterar(produto);
			
			novo();
		
	}
	
	public void onRowCancel(RowEditEvent event){
		DataTable dataModel = (DataTable) event.getSource();
		produto = (Produto) dataModel.getRowData();
		ValorProduto valor = neg.carregarValor(produto);
		produto.setValor(valor);
	}

	public void calcularValor(Produto produto) {
		neg.calcularValor(produto);
	}

	public void calcularPercentual() {
		neg.calcularPercentual(produto);
	}

	@Override
	public void salvar() {

		calcularPercentual();
		
		neg.alterar(produto);

		FacesUtil.infoMessageSimples("Valor do produto cadastrado");

		novo();
	}

	public Integer getQtde() {
		return qtde;
	}

	public void setQtde(Integer qtde) {
		this.qtde = qtde;
	}
}
