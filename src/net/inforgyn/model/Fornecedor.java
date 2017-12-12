package net.inforgyn.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import net.inforgyn.impl.EntityPersistence;
import net.inforgyn.util.StringUtil;

@Entity
@Table(name="pdv_fornecedor")
public class Fornecedor extends EntityPersistence{

	private static final long serialVersionUID = 1L;
	
	private String celular;
	private String descricao;
	private String telefone;
	private Usuario usuario;
	private String vendedor;
	
	public Fornecedor() {
		super();
	}

	@Column(length=10)
	public String getCelular() {
		return celular;
	}

	@Column(length=50)
	public String getDescricao() {
		return descricao;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}
	
	@Column(length=10)
	public String getTelefone() {
		return telefone;
	}

	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.DETACH, optional=false)
	public Usuario getUsuario() {
		return usuario;
	}

	@Column(length=30)
	public String getVendedor() {
		return vendedor;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public void setDescricao(String descricao) {
		this.descricao = StringUtil.captalizar(descricao);
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public void setVendedor(String vendedor) {
		this.vendedor = StringUtil.captalizar(vendedor);
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
		Fornecedor other = (Fornecedor) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
