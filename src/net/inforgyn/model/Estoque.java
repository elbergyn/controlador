package net.inforgyn.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import net.inforgyn.impl.EntityPersistence;

@Entity
@Table(name="pdv_estoque")
public class Estoque extends EntityPersistence {
	private static final long serialVersionUID = 1L;
	
	private Produto produto;
	private Integer qtde;
	private Integer qtdeMinima;
	private Usuario usuario;

	public Estoque() {
		qtdeMinima = 0;
		qtde = 0;
	}

	public Estoque(Produto produto, Integer qtde, Integer qtdeMinima) {
		super();
		this.produto = produto;
		this.qtde = qtde;
		this.qtdeMinima = qtdeMinima;
	}
	
	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.DETACH, optional=false)
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	@OneToOne(optional=false, fetch=FetchType.LAZY)
	public Produto getProduto() {
		return produto;
	}

	@Column(columnDefinition="INTEGER DEFAULT 0", nullable=false)
	public Integer getQtde() {
		return qtde;
	}

	@Column(name="qtde_minima",columnDefinition="INTEGER DEFAULT 0", insertable=true, nullable=false)
	public Integer getQtdeMinima() {
		return qtdeMinima;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public void setQtde(Integer qtde) {
		this.qtde = qtde;
	}

	public void setQtdeMinima(Integer qtdeMinima) {
		this.qtdeMinima = qtdeMinima;
	}
	
	public Estoque clone(){
		Estoque clone = new Estoque();
		clone.setId(this.getId());
		clone.setProduto(this.getProduto());
		clone.setQtde(this.getQtde());
		clone.setQtdeMinima(this.getQtdeMinima());
		clone.setUsuario(this.getUsuario());
		return clone;
	}
}
