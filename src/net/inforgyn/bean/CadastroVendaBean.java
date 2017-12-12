package net.inforgyn.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.SelectEvent;

import net.inforgyn.model.ItemVenda;
import net.inforgyn.model.Produto;
import net.inforgyn.model.Venda;
import net.inforgyn.neg.OrcamentoNeg;
import net.inforgyn.neg.ProdutoNeg;
import net.inforgyn.qualifierCdi.ProdutoComboQ;
import net.inforgyn.util.StringUtil;
import net.inforgyn.util.faces.FacesUtil;

@Named
@ViewScoped
public class CadastroVendaBean implements CadastroBean, Serializable {

	private static final long serialVersionUID = 1L;
	private Venda venda;
	@Inject	@ProdutoComboQ
	List<Produto> produtos;
	@Inject	private ProdutoNeg produtoNeg;
	private ItemVenda item;
	@Inject	private OrcamentoNeg orcamentoNeg;
	private String codBarras;

	@PostConstruct
	private void init() {
		novo();
	}

	@Override
	public void novo() {
		venda = new Venda();
		item = new ItemVenda();
	}
	
	private void novoItem(){
		item = new ItemVenda();
		codBarras = "";
	}

	public void addItem() {
		if (item.getProduto() != null) {
			orcamentoNeg.addItemVenda(venda.getOrcamento(), item);
			novoItem();
		}else if(StringUtil.isNotEmpty(codBarras)){
			Produto produto = produtoNeg.pesquisarPorCodBarras(codBarras);
			if (produto != null) {
				item.setProduto(produto);
				orcamentoNeg.addItemVenda(venda.getOrcamento(), item);
			} else {
				FacesUtil.alertMessageSimples("Produto não localizado");
			}
			novoItem();
		}else {
			FacesUtil.alertMessageSimples("Informe o produto ou código de barras");
		}
	}

	public void removeItem() {
		venda.getOrcamento().getItensVenda().remove(item);
	}

	@Override
	public void salvar() {
		if (venda.getOrcamento().getItensVenda().size() == 0) {
			FacesUtil.alertMessageSimples("Adicione itens a venda");
		} else if (venda.getOrcamento().getId() == null) {
			//venda.setOrcamento(orcamentoNeg.salvar(venda.getOrcamento()));
		}
	}
	
	public void vendaConcluida(SelectEvent event){
		venda = (Venda) event.getObject();
		if(venda.getId() != null){
			FacesUtil.infoMessageSimples("Venda concluída");
			novo();
		}
	}

	public void abrirPesquisa() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("width", 600);
		parametros.put("height", 400);
		parametros.put("contentWidth", 620);
		parametros.put("modal", true);
		
		Map<String, List<String>> viewparam = new HashMap<String, List<String>>();
		

		//RequestContext.getCurrentInstance().openDialog("pesqProduto", parametros, null);
	}

	public Venda getVenda() {
		return venda;
	}

	public List<Produto> produtos(String filtro) {
		List<Produto> lista = new ArrayList<Produto>();
		if (filtro != "") {
			for (Produto p : produtos) {
				if (p.getDescricao().toLowerCase().startsWith(filtro.toLowerCase())) {
					lista.add(p);
				}
			}
			return lista;
		} else {
			return produtos;
		}
	}

	public void setVenda(Venda venda) {
		this.venda = venda;
	}

	public ItemVenda getItem() {
		return item;
	}

	public void setItem(ItemVenda item) {
		this.item = item;
	}

	public String getCodBarras() {
		return codBarras;
	}

	public void setCodBarras(String codBarras) {
		this.codBarras = codBarras;
	}
}
