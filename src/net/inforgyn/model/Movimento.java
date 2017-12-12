package net.inforgyn.model;

import java.math.BigDecimal;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import net.inforgyn.constante.TipoMovimentoEnum;
import net.inforgyn.impl.EntityPersistence;

@Entity
@Table(name="ctr_movimento")
public class Movimento extends EntityPersistence{

	private static final long serialVersionUID = 1L;
	
	private Date dataLancamento;
	private String descricao;
	private TipoMovimentoEnum tipoMovimento;
	private Usuario usuario;
	private BigDecimal valor;

	public Movimento() {
		super();
		dataLancamento = new Date();
	}

	public Movimento(Long id, Date dataLancamento, String descricao, BigDecimal valor,
			Usuario usuario, TipoMovimentoEnum tipoMovimento) {
		super();
		this.id = id;
		this.dataLancamento = dataLancamento;
		this.descricao = descricao;
		this.valor = valor;
		this.usuario = usuario;
		this.tipoMovimento = tipoMovimento;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(columnDefinition="TIMESTAMP DEFAULT NOW()", insertable=false, name="data_lancamento", nullable=false)
	public Date getDataLancamento() {
		return dataLancamento;
	}
	
	@Column(nullable=false, length=50)
	public String getDescricao() {
		return descricao;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long getId(){
		return id;
	}

	@Enumerated(EnumType.STRING)
	@Column(name="tipo_movimento")
	public TipoMovimentoEnum getTipoMovimento() {
		return tipoMovimento;
	}

	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.DETACH, optional=false)
	public Usuario getUsuario() {
		return usuario;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setDataLancamento(Date dataLancamento) {
		this.dataLancamento = dataLancamento;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public void setId(Long id){
		this.id = id;
	}

	public void setTipoMovimento(TipoMovimentoEnum tipoMovimento) {
		this.tipoMovimento = tipoMovimento;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
}
