package net.inforgyn.model;

import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="ctr_credito_dinheiro")
@NamedQuery(name="listarCreditoDinheiro", query="from CreditoDinheiro")
public class CreditoDinheiro extends Credito{

	private static final long serialVersionUID = 1L;

	public CreditoDinheiro() {
		super();
	}
	
	
}
