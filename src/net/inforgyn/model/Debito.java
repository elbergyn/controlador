package net.inforgyn.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import net.inforgyn.constante.SituacaoPagamentoEnum;
import net.inforgyn.constante.TipoPagamentoEnum;
import net.inforgyn.impl.EntityPersistence;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@SequenceGenerator(name = "debito_id_seq", sequenceName = "debito_id_seq")
public abstract class Debito extends EntityPersistence {

	private static final long serialVersionUID = 1L;

	protected CategoriaDespesa categoria;
	protected ContaPagar conta;
	protected String descricao;
	protected Date lancamento;
	protected List<BaixaPagamento> pagamentos;
	protected Integer parcela;
	protected Integer parcelaTotal;
	protected SituacaoPagamentoEnum situacao;
	protected TipoPagamentoEnum tipoPagamento;
	protected Usuario usuario;
	protected BigDecimal valor;
	protected Date vencimento;

	public Debito() {
		super();
	}

	public Debito(Long id, String descricao, Date lancamento,
			SituacaoPagamentoEnum situacao, BigDecimal valor, Date vencimento, TipoPagamentoEnum tipoPagamento, 
			Integer parcela, Integer parcelaTotal) {
		super();
		this.id = id;
		this.descricao = descricao;
		this.lancamento = lancamento;
		this.situacao = situacao;
		this.valor = valor;
		this.vencimento = vencimento;
		this.tipoPagamento = tipoPagamento;
		this.parcela = parcela;
		this.parcelaTotal = parcelaTotal;
	}
	
	//@Transient
	/**Sem lazy não inicializa os pagamentos que são lazy**/
	@ManyToOne(optional=false, cascade=CascadeType.DETACH, fetch=FetchType.LAZY)
	public CategoriaDespesa getCategoria() {
		return categoria;
	}

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	public ContaPagar getConta() {
		return conta;
	}

	@Column(nullable=false)
	public String getDescricao() {
		return descricao;
	}

	// @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Override
	@Id
	@GeneratedValue(generator = "debito_id_seq")
	public Long getId() {
		return id;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(columnDefinition="TIMESTAMP DEFAULT NOW()", insertable=true, updatable=true, nullable=false)
	public Date getLancamento() {
		return lancamento;
	}

	@OneToMany(cascade=CascadeType.DETACH, fetch=FetchType.LAZY, mappedBy="debito")
	public List<BaixaPagamento> getPagamentos() {
		return pagamentos;
	}

	public Integer getParcela() {
		return parcela;
	}

	@Column(name="parcela_total")
	public Integer getParcelaTotal() {
		return parcelaTotal;
	}

	@Enumerated(EnumType.STRING)
	@Column(name="situacao_pagamento")
	public SituacaoPagamentoEnum getSituacao() {
		return situacao;
	}

	@Enumerated(EnumType.STRING)
	@Column(name="tipo_pagamento")
	public TipoPagamentoEnum getTipoPagamento() {
		return tipoPagamento;
	}

	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.DETACH, optional=false)
	public Usuario getUsuario() {
		return usuario;
	}

	@Column(nullable = false)
	public BigDecimal getValor() {
		return valor;
	}

	@Transient
	public BigDecimal getValorPago() {
		BigDecimal valor = new BigDecimal(0);
		if(pagamentos != null && !pagamentos.isEmpty()){
			for(BaixaPagamento baixa : pagamentos){
				valor = valor.add(baixa.getValor());
			}
		}
		return valor;
	}

	@Temporal(TemporalType.DATE)
	public Date getVencimento() {
		return vencimento;
	}

	public void setCategoria(CategoriaDespesa categoria) {
		this.categoria = categoria;
	}

	public void setConta(ContaPagar conta) {
		this.conta = conta;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public void setLancamento(Date lancamento) {
		this.lancamento = lancamento;
	}

	public void setPagamentos(List<BaixaPagamento> pagamentos) {
		this.pagamentos = pagamentos;
	}

	public void setParcela(Integer parcela) {
		this.parcela = parcela;
	}

	public void setParcelaTotal(Integer parcelaTotal) {
		this.parcelaTotal = parcelaTotal;
	}

	public void setSituacao(SituacaoPagamentoEnum situacao) {
		this.situacao = situacao;
	}

	public void setTipoPagamento(TipoPagamentoEnum tipoPagamento) {
		this.tipoPagamento = tipoPagamento;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public void setVencimento(Date vencimento) {
		this.vencimento = vencimento;
	}
}
