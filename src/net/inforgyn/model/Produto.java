package net.inforgyn.model;

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
import javax.persistence.OneToOne;
import javax.persistence.Table;

import net.inforgyn.constante.StatusProdutoEnum;
import net.inforgyn.impl.EntityPersistence;
import net.inforgyn.util.StringUtil;

@Entity
@Table(name="pdv_produto")
public class Produto extends EntityPersistence {

	private static final long serialVersionUID = 1L;
	
	private String descricao;
	private StatusProdutoEnum status;
	private String unidade;
	private Usuario usuario;
	private Estoque estoque;
	private Fornecedor fornecedor;
	private ValorProduto valor;
	private String codBarras;
	
	public Produto() {
		super();
		status = StatusProdutoEnum.ATIVO;
		estoque = new Estoque();
		fornecedor = new Fornecedor();
		valor = new ValorProduto();
	}
	
	public Produto(Long id, String descricao) {
		super();
		this.id = id;
		this.descricao = descricao;
	}
	
	public Produto(Long id) {
		super();
		this.id = id;
	}
	
	public Produto(Long id, String descricao, String codBarras) {
		super();
		this.id = id;
		this.descricao = descricao;
		this.codBarras = codBarras;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Column(name="cod_barras", unique=true, length=48)
	public String getCodBarras() {
		return codBarras;
	}

	public void setCodBarras(String codBarras) {
		this.codBarras = codBarras;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Produto other = (Produto) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@OneToOne(fetch=FetchType.LAZY, mappedBy="produto", cascade=CascadeType.ALL)
	public ValorProduto getValor() {
		return valor;
	}

	public void setValor(ValorProduto valor) {
		this.valor = valor;
	}

	@Column(length=50)
	public String getDescricao() {
		return descricao;
	}
	
	@ManyToOne(cascade=CascadeType.REMOVE, fetch=FetchType.LAZY, optional=false)
	public Fornecedor getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}

	@OneToOne(fetch=FetchType.LAZY, mappedBy="produto", cascade=CascadeType.ALL)
	public Estoque getEstoque() {
		return estoque;
	}

	public void setEstoque(Estoque estoque) {
		this.estoque = estoque;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	@Enumerated(EnumType.STRING)
	public StatusProdutoEnum getStatus() {
		return status;
	}

	@Column(length=10)
	public String getUnidade() {
		return unidade;
	}

	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.DETACH, optional=false)
	public Usuario getUsuario() {
		return usuario;
	}

	public void setDescricao(String descricao) {
		this.descricao = StringUtil.captalizar(descricao);
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setStatus(StatusProdutoEnum status) {
		this.status = status;
	}

	public void setUnidade(String unidade) {
		this.unidade = unidade;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
}
