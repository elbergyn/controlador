package net.inforgyn.model.filterPesquisa;

import net.inforgyn.model.CartaoCredito;
import net.inforgyn.model.CategoriaDespesa;
import net.inforgyn.model.Cheque;

public class FiltroBaixaPagamento {
	private Integer mes;
	private CartaoCredito cartao;
	private Cheque cheque;
	private Integer Ano;
	private CategoriaDespesa categoria;
	
	public FiltroBaixaPagamento() {
		super();
	}

	public Integer getMes() {
		return mes;
	}

	public CartaoCredito getCartao() {
		return cartao;
	}

	public Cheque getCheque() {
		return cheque;
	}

	public Integer getAno() {
		return Ano;
	}

	public CategoriaDespesa getCategoria() {
		return categoria;
	}

	public void setMes(Integer mes) {
		this.mes = mes;
	}

	public void setCartao(CartaoCredito cartao) {
		this.cartao = cartao;
	}

	public void setCheque(Cheque cheque) {
		this.cheque = cheque;
	}

	public void setAno(Integer ano) {
		Ano = ano;
	}

	public void setCategoria(CategoriaDespesa categoria) {
		this.categoria = categoria;
	}
}
