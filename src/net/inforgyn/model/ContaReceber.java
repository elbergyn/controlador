package net.inforgyn.model;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

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
import javax.persistence.Transient;

import net.inforgyn.constante.TipoRecebimentoEnum;

@Entity
@Table(name="ctr_conta_receber")
@NamedQuery(name="listarContaReceber", query="from ContaReceber")
public class ContaReceber extends Conta{

	private static final long serialVersionUID = 1L;
	
	private List<Credito> creditos;
	private Devedor devedor;
	private Set<Long> numerosCheque = new LinkedHashSet<Long>();
	private TipoRecebimentoEnum tipoRecebimento;
	
	public ContaReceber() {
		super();
		parcelaTotal = 1;
	}
	
	public ContaReceber(Long id){
		super();
		this.id = id;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "conta")
	public List<Credito> getCreditos() {
		return creditos;
	}
	
	@ManyToOne(cascade=CascadeType.DETACH, optional=false)
	public Devedor getDevedor() {
		return devedor;
	}

	@Transient
	public Set<Long> getNumerosCheque() {
		return numerosCheque;
	}

	@Enumerated(EnumType.STRING)
	@Column(name="tipo_recebimento")
	public TipoRecebimentoEnum getTipoRecebimento() {
		return tipoRecebimento;
	}

	public void setCreditos(List<Credito> creditos) {
		this.creditos = creditos;
	}

	public void setDevedor(Devedor devedor) {
		this.devedor = devedor;
	}

	public void setNumerosCheque(Set<Long> numeros) {
		this.numerosCheque = numeros;
	}

	public void setTipoRecebimento(TipoRecebimentoEnum tipoRecebimento) {
		this.tipoRecebimento = tipoRecebimento;
	}
}
