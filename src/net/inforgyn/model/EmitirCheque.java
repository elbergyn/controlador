package net.inforgyn.model;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import net.inforgyn.qualifierCdi.EmitirChequeQ;

@Embeddable
@EmitirChequeQ
public class EmitirCheque{
	
	protected Cheque cheque;
	protected Long numeroCheque;
	private Set<Long> numeros = new LinkedHashSet<Long>();
	
	public EmitirCheque() {
		super();
	}

	public EmitirCheque(Cheque cheque) {
		super();
		this.cheque = cheque;
	}

	@ManyToOne(cascade=CascadeType.DETACH, optional=false)
	public Cheque getCheque() {
		return cheque;
	}

	@Column(name="numero_cheque", nullable=false)
	public Long getNumeroCheque() {
		return numeroCheque;
	}

	@Transient
	public Set<Long> getNumeros() {
		return numeros;
	}

	public void setCheque(Cheque cheque) {
		this.cheque = cheque;
	}

	public void setNumeroCheque(Long numeroCheque) {
		this.numeroCheque = numeroCheque;
	}

	public void setNumeros(Set<Long> numeros) {
		this.numeros = numeros;
	}
}
