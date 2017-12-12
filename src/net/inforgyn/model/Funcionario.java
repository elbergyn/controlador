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
@Table(name="pdv_funcionario")
public class Funcionario extends EntityPersistence{

	private static final long serialVersionUID = 1L;

	private String nome;
	private String telefone;
	private String celular;
	private String endereco;
	private Double comissao;
	private Usuario usuario;
	
	public Funcionario() {
		comissao = 0.0;
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

	@Column(length=15)
	public String getTelefone() {
		return telefone;
	}

	@Column(length=15)
	public String getCelular() {
		return celular;
	}

	public String getEndereco() {
		return endereco;
	}

	public Double getComissao() {
		return comissao;
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

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public void setComissao(Double comissao) {
		this.comissao = comissao;
	}
}
