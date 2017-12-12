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

import net.inforgyn.constante.TipoLancamentoCaixaEnum;
import net.inforgyn.impl.EntityPersistence;
import net.inforgyn.util.StringUtil;

@Entity
@Table(name= "pdv_caixa_movimento")
public class CaixaMovimento extends EntityPersistence {

	private static final long serialVersionUID = 1L;

	private Caixa caixa;
	private Date data;
	private String descricao;
	private TipoLancamentoCaixaEnum tipo;
	private TipoRecebimento tipoRecebimento;
	private BigDecimal valor;

	public CaixaMovimento() {
		super();
	}
	
	@ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY, optional = false)
	public Caixa getCaixa() {
		return caixa;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data", columnDefinition = "TIMESTAMP DEFAULT NOW()", insertable = false, nullable = false)
	public Date getData() {
		return data;
	}

	@Column(length = 50)
	public String getDescricao() {
		return StringUtil.captalizar(descricao);
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	@Enumerated(EnumType.STRING)
	public TipoLancamentoCaixaEnum getTipo() {
		return tipo;
	}

	@ManyToOne(cascade=CascadeType.DETACH, fetch=FetchType.EAGER, optional=false)
	public TipoRecebimento getTipoRecebimento() {
		return tipoRecebimento;
	}

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH, optional = false)
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

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setTipo(TipoLancamentoCaixaEnum tipo) {
		this.tipo = tipo;
	}

	public void setTipoRecebimento(TipoRecebimento tipoRecebimento) {
		this.tipoRecebimento = tipoRecebimento;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
}
