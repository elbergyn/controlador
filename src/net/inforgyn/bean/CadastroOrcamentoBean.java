package net.inforgyn.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import net.inforgyn.constante.StatusVendaEnum;
import net.inforgyn.model.Cliente;
import net.inforgyn.model.Funcionario;
import net.inforgyn.model.ItemVenda;
import net.inforgyn.model.Orcamento;
import net.inforgyn.model.Produto;
import net.inforgyn.neg.OrcamentoNeg;
import net.inforgyn.util.RedirecionarPaginaUtil;
import net.inforgyn.util.faces.FacesUtil;

@Named
@ViewScoped
public class CadastroOrcamentoBean implements CadastroBean,Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private OrcamentoNeg neg;
	private Orcamento orcamento;
	@Inject
	private List<Orcamento> orcamentos;
	@Inject
	private List<Cliente> clientes;
	@Inject
	private List<Funcionario> funcionarios;
	@Inject
	private List<Produto> produtos;
	private ItemVenda itemVenda;

	@PostConstruct
	private void init() {
		limpar();
	}

	@Override
	public void novo() {
		limpar();
	}

	public void limpar() {
		orcamento = new Orcamento();
		itemVenda = new ItemVenda();
	}

	public ItemVenda getItemVenda() {
		return itemVenda;
	}

	public void setItemVenda(ItemVenda itemVenda) {
		this.itemVenda = itemVenda;
	}

	@Override
	public void salvar() {
		if (orcamento.getItensVenda().size() == 0) {
			FacesUtil.alertMessageSimples("Adicione itens ao orçamentos");
		} else {
			if (orcamento.getId() == null) {
				orcamento = neg.salvar(orcamento);
				orcamentos.add(orcamento);
				FacesUtil.infoMessageSimples("Orçamento cadastrado");
			} else {
				neg.alterar(orcamento);
				FacesUtil.infoMessageSimples("Orçamento alterado");
			}
		}
	}

	public void pagamento() {
		salvar();
		if (orcamento.getId() != null) {
			RedirecionarPaginaUtil.redirect("/pagamento/" + orcamento.getId());
		}
	}

	public void carregarOrcamento() {
		if (orcamento.getId() != null) {
			orcamento = neg.pesquisar(orcamento.getId());
			if (orcamento == null) {
				FacesUtil.infoMessageSimples("Não localizado orçamento");
				limpar();
			}else if(orcamento.getStatus().equals(StatusVendaEnum.CANCELADO)){
				FacesUtil.alertMessageSimples("Orçamento cancelado");
				limpar();
			}else if(orcamento.getStatus().equals(StatusVendaEnum.CONCLUIDO)){
				FacesUtil.infoMessageSimples("Orçamento concluído");
				limpar();
			}
		}
	}

	public Orcamento getOrcamento() {
		return orcamento;
	}

	public void setOrcamento(Orcamento orcamento) {
		this.orcamento = orcamento;
	}

	public void selectOrcamento(Orcamento orcamento) {
		orcamento = neg.carregarLazy(orcamento, "funcionario", "itensVenda");
		this.orcamento = orcamento;
	}

	public List<Orcamento> getOrcamentos() {
		return orcamentos;
	}

	public List<Cliente> clientes(String filtro) {
		List<Cliente> lista = new ArrayList<Cliente>();
		if (clientes.size() == 0) {
			FacesUtil.alertMessageSimples("Não há clientes cadastrados");
		} else {
			for (Cliente c : clientes) {
				if (c.getNome().toLowerCase().startsWith(filtro.toLowerCase())) {
					lista.add(c);
				}
			}
		}
		return lista;
	}

	public List<Produto> produtos(String filtro) {
		List<Produto> lista = new ArrayList<Produto>();
		for (Produto p : produtos) {
			if (p.getDescricao().toLowerCase().startsWith(filtro.toLowerCase())) {
				lista.add(p);
			}
		}
		return lista;
	}

	public void adicionarItem() {
		neg.addItemVenda(orcamento, itemVenda);
	}

	public boolean getHabilitarTaxa() {
		return !orcamento.getCobrarTaxaServico();
	}
	
	public void calcularValorTaxa(){
		neg.calcularValorTaxa(orcamento);
	}

	public void removeItem() {
		neg.removeItemVenda(orcamento, itemVenda);
		itemVenda = new ItemVenda();
	}

	public List<Funcionario> funcionarios(String filtro) {
		List<Funcionario> lista = new ArrayList<Funcionario>();
		if (funcionarios.size() == 0) {
			FacesUtil.alertMessageSimples("Realize o cadastro de funcionário");
		} else {
			for (Funcionario f : funcionarios) {
				if (f.getNome().toLowerCase().startsWith(filtro.toLowerCase())) {
					lista.add(f);
				}
			}
		}
		return lista;
	}

	
}
