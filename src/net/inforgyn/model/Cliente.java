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
@Table(name="pdv_cliente")
public class Cliente extends EntityPersistence{

	private static final long serialVersionUID = 1L;
	
	private String nome;
	private String telefone;
	private String celular;
	private Usuario usuario;
	
	public Cliente() {
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}
	
	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.DETACH, optional=false)
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@Column(length=50, nullable=false)
	public String getNome() {
		return nome;
	}

	@Column(length=14)
	public String getTelefone() {
		return telefone;
	}

	@Column(length=14)
	public String getCelular() {
		return celular;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setNome(String nome) {
		this.nome = StringUtil.captalizar(nome);
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

}
