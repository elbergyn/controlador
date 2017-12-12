package net.inforgyn.model;

import java.math.BigDecimal;
import java.util.Calendar;

import javax.annotation.PostConstruct;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import net.inforgyn.impl.EntityPersistence;
import net.inforgyn.util.UsuarioSessaoUtil;

@Entity
@Table(name="pdv_valor_produto")
public class ValorProduto extends EntityPersistence{
	private static final long serialVersionUID = 1L;
	
	private Calendar data;
	private Double lucroPercentual;
	private Produto produto;
	private BigDecimal valorCusto;
	private BigDecimal valorVenda;
	private Usuario usuario;
	
	public ValorProduto clone(){
		ValorProduto valor = new ValorProduto();
		valor.lucroPercentual = this.lucroPercentual;
		valor.produto = this.produto;
		valor.valorCusto = this.valorCusto;
		valor.valorVenda = this.valorVenda;
		valor.usuario = this.usuario;
		valor.id = this.id;
		return valor;
	}
	
	public ValorProduto() {
	}
	
	@PostConstruct
	public void init(){
		produto = new Produto();
	}
	
	public ValorProduto(Produto produto) {
		lucroPercentual = 0.0;
		this.produto = produto;
		valorCusto = new BigDecimal(0);
		valorVenda = new BigDecimal(0);
		usuario = UsuarioSessaoUtil.getUsuario();
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

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="data_atualizacao", nullable=false, insertable=false, columnDefinition="TIMESTAMP DEFAULT NOW()")
	public Calendar getData() {
		return data;
	}

	@Column(nullable=false, precision=2, name="lucro_percentual")
	public Double getLucroPercentual() {
		return lucroPercentual;
	}

	@OneToOne(optional=false, cascade=CascadeType.ALL)
	public Produto getProduto() {
		return produto;
	}

	@Column(nullable=false, name="valor_custo")
	public BigDecimal getValorCusto() {
		return valorCusto;
	}

	@Column(nullable=false, name="valor_venda")
	public BigDecimal getValorVenda() {
		return valorVenda;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setData(Calendar data) {
		this.data = data;
	}

	public void setLucroPercentual(Double lucroPercentual) {
		this.lucroPercentual = lucroPercentual;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public void setValorCusto(BigDecimal valorCusto) {
		this.valorCusto = valorCusto;
	}

	public void setValorVenda(BigDecimal valorVenda) {
		this.valorVenda = valorVenda;
	}
}
