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
@Table(name="pdv_caixa_fechamento")
public class CaixaFechamento extends EntityPersistence {
	
	private static final long serialVersionUID = 1L;

	private Caixa caixa;
	private Date data;
	private TipoRecebimento recebimento;
	private Usuario usuario;
	private BigDecimal valor;
	
	public CaixaFechamento() {
	}
	
	public CaixaFechamento(Long id) {
		this.id = id;
	}
	
	public CaixaFechamento(Long idRecebimento, String descRecebimento, BigDecimal valor) {
		super();
		recebimento = new TipoRecebimento(idRecebimento, descRecebimento, null);
		this.valor = valor;
	}

	@ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY, optional = false)
	public Caixa getCaixa() {
		return caixa;
	}
		
	/*public CaixaFechamento(Long idRecebimento, String descRecebimento, BigDecimal valor) {
		super();
		this.recebimento.setId(idRecebimento);
		this.recebimento.setDescricao(descRecebimento);
		this.valor = valor;
	}*/
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data", columnDefinition = "TIMESTAMP DEFAULT NOW()", insertable = false, nullable = false)
	public Date getData() {
		return data;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}
	
	@ManyToOne(cascade=CascadeType.DETACH, fetch=FetchType.EAGER, optional=false)
	public TipoRecebimento getRecebimento() {
		return recebimento;
	}

	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.DETACH, optional=false)
	public Usuario getUsuario() {
		return usuario;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setCaixa(Caixa caixa) {
		this.caixa = caixa;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public void setId(Long id){
		this.id = id;
	}

	public void setRecebimento(TipoRecebimento recebimento) {
		this.recebimento = recebimento;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
}
