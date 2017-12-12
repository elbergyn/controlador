package net.inforgyn.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import net.inforgyn.constante.TipoUsuarioEnum;
import net.inforgyn.impl.EntityPersistence;

@Entity
@NamedQuery(name="listarUsuarios", query="from Usuario")
public class Usuario extends EntityPersistence{
	private static final long serialVersionUID = 1L;
	
	private Date cadastro;
	private String celular;
	private Boolean confirmado;
	private String descricao;
	private String email;
	private ParametrosSistema parametros;
	private String senha;
	private TipoUsuarioEnum tipo;
	private Usuario usuarioPai;
	private Date validade;

	public Usuario() {
		super();
		confirmado = false;
		tipo = TipoUsuarioEnum.COMUM;
	}
	
	@Temporal(TemporalType.DATE)
	public Date getValidade() {
		return validade;
	}
	
	public void setValidade(Date validade) {
		this.validade = validade;
	}

	@Temporal(TemporalType.DATE)
	@Column(nullable=false, insertable=false, columnDefinition="TIMESTAMP DEFAULT NOW()")
	public Date getCadastro() {
		return cadastro;
	}

	@Column(length=15)
	public String getCelular() {
		return celular;
	}

	public Boolean getConfirmado() {
		return confirmado;
	}

	@Transient
	public String getConfirmadoString(){
		return this.confirmado == true ? "Confirmado" : "Não confirmado";
	}

	@Column(nullable=false,length=20, unique=true)
	public String getDescricao() {
		return descricao;
	}

	@Column(nullable=false, unique=true)
	public String getEmail() {
		return email;
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long getId(){
		return this.id;
	}

	@OneToOne(cascade={CascadeType.PERSIST, CascadeType.REMOVE}, optional=true, orphanRemoval=true, mappedBy="usuario")
	public ParametrosSistema getParametros() {
		return parametros;
	}

	@Column(nullable=false,length=128)
	public String getSenha() {
		return senha;
	}
	
	@Enumerated(EnumType.STRING)
	@Column(length=10)
	public TipoUsuarioEnum getTipo() {
		return tipo;
	}

	@ManyToOne(fetch=FetchType.LAZY,optional=true)
	public Usuario getUsuarioPai() {
		return usuarioPai;
	}

	public void setCadastro(Date cadastro) {
		this.cadastro = cadastro;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public void setConfirmado(Boolean confirmado) {
		this.confirmado = confirmado;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao.trim();
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setId(Long id){
		this.id = id;
	}

	public void setParametros(ParametrosSistema parametros) {
		this.parametros = parametros;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public void setTipo(TipoUsuarioEnum tipo) {
		this.tipo = tipo;
	}

	public void setUsuarioPai(Usuario usuarioPai) {
		this.usuarioPai = usuarioPai;
	}
}
