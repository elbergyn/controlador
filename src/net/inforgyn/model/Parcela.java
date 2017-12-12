package net.inforgyn.model;

import java.util.Date;

import net.inforgyn.solid.Money;

public class Parcela {
	private Integer numero;
	private Money valor;
	private Date vencimento;
	
	public Parcela(Integer numero, Money valor, Date vencimento) {
		super();
		this.numero = numero;
		this.valor = valor;
		this.vencimento = vencimento;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public Money getValor() {
		return valor;
	}

	public void setValor(Money valor) {
		this.valor = valor;
	}

	public Date getVencimento() {
		return vencimento;
	}

	public void setVencimento(Date vencimento) {
		this.vencimento = vencimento;
	}
}
