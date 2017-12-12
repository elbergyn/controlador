package net.inforgyn.neg;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import net.inforgyn.comparator.ItemVendaComparator;
import net.inforgyn.model.ItemVenda;
import net.inforgyn.model.Orcamento;
import net.inforgyn.model.Produto;
import net.inforgyn.model.ValorProduto;
import net.inforgyn.repository.OrcamentoDao;
import net.inforgyn.solid.Money;
import net.inforgyn.util.UsuarioSessaoUtil;
import net.inforgyn.util.faces.FacesUtil;

@RequestScoped
public class OrcamentoNeg {
	@Inject
	private OrcamentoDao dao;
	@Inject
	private ValorProdutoNeg valorNeg;

	public Orcamento salvar(Orcamento orcamento) {
		orcamento.setUsuario(UsuarioSessaoUtil.getUsuario());
		dao.salvar(orcamento);
		return orcamento;
	}

	public void alterar(Orcamento orcamento) {
		dao.alterar(orcamento);
	}
	
	public void excluir(Orcamento orcamento){
		dao.excluir(orcamento);
	}
	
	@Produces
	public List<Orcamento> orcamentosAbertos(){
		return dao.orcamentosAbertos();
	}

	public void addItemVenda(Orcamento orcamento, ItemVenda itemVenda) {
		if (itemVenda.getProduto() == null) {
			FacesUtil.alertMessageSimples("Informe o produto");
			itemVenda.setProduto(new Produto());
		} else {		
			boolean add = true;
					
			for(ItemVenda iv : orcamento.getItensVenda()){
				if(iv.getProduto().getId().equals(itemVenda.getProduto().getId())){
					iv.setQtde(iv.getQtde() + itemVenda.getQtde());
					iv.setValorTotal(iv.getValorUnitario().multiply(new BigDecimal(iv.getQtde())));
					add = false;
					break;
				}
			}
			if(add){
				ValorProduto valor = valorNeg.pesquisarPorIdProduto(itemVenda.getProduto().getId());
				itemVenda.setValorUnitario(valor.getValorVenda());
				itemVenda.setValorCusto(valor.getValorCusto());
				itemVenda.setValorTotal(valor.getValorVenda().multiply(new BigDecimal(itemVenda.getQtde())));
				
				orcamento.getItensVenda().add(itemVenda);
			}
			orcamento.setValorItens(new BigDecimal(0));
			for(ItemVenda iv : orcamento.getItensVenda()){
				orcamento.setValorItens(orcamento.getValorItens().add(iv.getValorTotal()));
			}
			orcamento.setValorTotal(orcamento.getValorItens().add(orcamento.getValorTaxaSevico()));
			if(orcamento.getId() != null){
				alterar(orcamento);
			}
			
			ordenarItensVenda(orcamento.getItensVenda());
			
			calcularValorTaxa(orcamento);
		}
	}
	
	public void calcularValorTaxa(Orcamento orcamento) {
		Money itens = Money.valueOf(orcamento.getValorItens());
		Money taxaServ = Money.valueOf(itens.getBigDecimal().multiply(
				new BigDecimal(orcamento.getPercentualTaxaServico())));

		if (itens != null && orcamento.getCobrarTaxaServico()) {
			orcamento.setValorTaxaSevico(taxaServ.getBigDecimal());
			orcamento.setValorTotal(itens.somar(taxaServ).getBigDecimal());
		} else {
			orcamento.setPercentualTaxaServico(0.0);
			orcamento.setValorTaxaSevico(new BigDecimal(0));
			orcamento.setValorTotal(itens.getBigDecimal());
		}
	}
	
	public void ordenarItensVenda(List<ItemVenda> itens){
		ItemVendaComparator comparator = new ItemVendaComparator();
		Collections.sort(itens, comparator);
	}
	
	public void removeItemVenda(Orcamento orcamento, ItemVenda itemVenda){
		orcamento.setValorItens(orcamento.getValorItens().subtract(itemVenda.getValorUnitario()));
		orcamento.setValorTotal(orcamento.getValorTotal().subtract(itemVenda.getValorUnitario()));
		for(ItemVenda iv : orcamento.getItensVenda()){
			if(iv.getProduto().getId().equals(itemVenda.getProduto().getId())){
				iv.setQtde(itemVenda.getQtde() - 1);
				if(iv.getQtde() == 0){
					orcamento.getItensVenda().remove(iv);
					break;
				}
			}
		}
		if(orcamento.getId() != null){
			alterar(orcamento);
		}
		calcularValorTaxa(orcamento);
	}

	public Orcamento pesquisar(Long id) {
		return (Orcamento) dao.pesquisarPorId(Orcamento.class, id);
	}

	public Orcamento carregarLazy(Orcamento orcamento, String... relacoes) {
		orcamento = dao.carregarLazy(orcamento, relacoes);
		ordenarItensVenda(orcamento.getItensVenda());
		return orcamento;
	}
}
