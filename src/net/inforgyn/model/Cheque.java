package net.inforgyn.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import net.inforgyn.impl.EntityPersistence;
import net.inforgyn.impl.TipoPagamento;


@Entity
@Table(name="ctr_cheque")
@NamedQuery(name="listarCheques", query="from Cheque")
public class Cheque extends EntityPersistence implements TipoPagamento{

	private static final long serialVersionUID = 1L;
	
	private Banco banco;
	private String descricao;
	private Usuario usuario;
	
	public Cheque() {
		super();
	}

	public Cheque(Long id, Banco banco, String descricao, Usuario usuario) {
		super();
		this.id = id;
		this.banco = banco;
		this.descricao = descricao;
		this.usuario = usuario;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cheque other = (Cheque) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	@ManyToOne(cascade=CascadeType.DETACH, fetch=FetchType.LAZY, optional=false)
	public Banco getBanco() {
		return banco;
	}
	
	@Column(length=30)
	public String getDescricao() {
		return descricao;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long getId(){
		return this.id;
	}

	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.DETACH, optional=false)
	public Usuario getUsuario() {
		return usuario;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((descricao == null) ? 0 : descricao.hashCode());
		return result;
	}

	public void setBanco(Banco banco) {
		this.banco = banco;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public void setId(Long id){
		this.id = id;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
}
