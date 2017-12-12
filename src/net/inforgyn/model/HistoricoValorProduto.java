package net.inforgyn.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import net.inforgyn.impl.EntityPersistence;

@Entity
@Table(name="pdv_historico_valor_produto")
public class HistoricoValorProduto extends EntityPersistence{
	private static final long serialVersionUID = 1L;
	
	private Date data;
	private Long id;
	private Double lucro;
	private Produto produto;
	private Usuario usuario;
	private BigDecimal valorCusto;
	private BigDecimal valorVenda;
	
	public HistoricoValorProduto() {
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(insertable=false, columnDefinition="TIMESTAMP DEFAULT NOW()", name="data_lancamento", nullable=false)
	public Date getData() {
		return data;
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	@Column(nullable=false)
	public Double getLucro() {
		return lucro;
	}

	@ManyToOne(optional=false, cascade=CascadeType.REMOVE, fetch=FetchType.EAGER)
	public Produto getProduto() {
		return produto;
	}

	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.DETACH, optional=false)
	public Usuario getUsuario() {
		return usuario;
	}

	@Column(nullable=false, name="valor_custo")
	public BigDecimal getValorCusto() {
		return valorCusto;
	}

	@Column(nullable=false, name="valor_venda")
	public BigDecimal getValorVenda() {
		return valorVenda;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setLucro(Double lucro) {
		this.lucro = lucro;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public void setValorCusto(BigDecimal valorCusto) {
		this.valorCusto = valorCusto;
	}

	public void setValorVenda(BigDecimal valorVenda) {
		this.valorVenda = valorVenda;
	}
}
