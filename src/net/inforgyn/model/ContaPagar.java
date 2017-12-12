package net.inforgyn.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import net.inforgyn.constante.TipoPagamentoEnum;

@Entity
@Table(name = "ctr_conta_pagar")
@NamedQuery(name = "listarContaPagar", query = "from ContaPagar")
public class ContaPagar extends Conta {
	private static final long serialVersionUID = 1L;

	private CategoriaDespesa categoria;
	private List<Debito> debitos = new ArrayList<Debito>();
	private TipoPagamentoEnum tipoPagamento;

	public ContaPagar() {
		super();
		this.parcelaTotal = 1;
	}
	
	public ContaPagar(Long id) {
		super();
		this.id = id;
	}

	@ManyToOne(optional=false, cascade=CascadeType.DETACH)
	public CategoriaDespesa getCategoria() {
		return categoria;
	}

	//@Column(updatable=true, insertable=true)
	@OneToMany(cascade={CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "conta")
	public List<Debito> getDebitos() {
		return debitos;
	}

	@Column(name="tipo_pagamento")
	@Enumerated(EnumType.STRING)
	public TipoPagamentoEnum getTipoPagamento() {
		return tipoPagamento;
	}

	public void setCategoria(CategoriaDespesa categoria) {
		this.categoria = categoria;
	}

	public void setDebitos(List<Debito> debitos) {
		this.debitos = debitos;
	}

	public void setTipoPagamento(TipoPagamentoEnum tipoPagamento) {
		this.tipoPagamento = tipoPagamento;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ContaPagar other = (ContaPagar) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
