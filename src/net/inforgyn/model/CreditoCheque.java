package net.inforgyn.model;

import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "ctr_credito_cheque")
@NamedQuery(name = "listarCreditosCheque", query = "from CreditoCheque")
public class CreditoCheque extends Credito {

	private static final long serialVersionUID = 1L;

	private Long numeroCheque;

	public CreditoCheque() {
		super();
	}

	public Long getNumeroCheque() {
		return numeroCheque;
	}

	public void setNumeroCheque(Long numeroCheque) {
		this.numeroCheque = numeroCheque;
	}
}
