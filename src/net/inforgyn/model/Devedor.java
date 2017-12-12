package net.inforgyn.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import net.inforgyn.impl.EntityPersistence;

@Entity
@Table(name="ctr_devedor")
@NamedQuery(name="listarDevedores", query="from Devedor")
public class Devedor extends EntityPersistence{
	private static final long serialVersionUID = 1L;

	private String celular;
	private List<Credito> creditos;
	private String descricao;
	private String email;
	private String fixo;
	private Usuario usuario;
	
	public Devedor() {
		super();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Devedor other = (Devedor) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Column(length=15)
	public String getCelular() {
		return celular;
	}

	@OneToMany(cascade=CascadeType.DETACH, fetch=FetchType.LAZY, orphanRemoval=true, mappedBy="devedor")
	public List<Credito> getCreditos() {
		return creditos;
	}
	
	public String getDescricao() {
		return descricao;
	}

	@Size(max=40)
	public String getEmail() {
		return email;
	}

	@Column(length=15)
	public String getFixo() {
		return fixo;
	}

	@Override
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
				+ ((id == null) ? 0 : id.hashCode());
		return result;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public void setCreditos(List<Credito> creditos) {
		this.creditos = creditos;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setFixo(String fixo) {
		this.fixo = fixo;
	}

	@Override
	public void setId(Long id){
		this.id = id;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
		
}
