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
import net.inforgyn.constante.TipoRecebimentoEnum;
import net.inforgyn.impl.EntityPersistence;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@SequenceGenerator(name = "credito_id_seq", sequenceName = "credito_id_seq")
public abstract class Credito extends EntityPersistence {

	private static final long serialVersionUID = 1L;

	protected ContaReceber conta;
	protected String descricao;
	protected Devedor devedor;
	protected Date lancamento;
	protected Integer parcela;
	protected Integer parcelaTotal;
	protected List<BaixaRecebimento> recebimentos;
	protected SituacaoPagamentoEnum situacao;
	protected TipoRecebimentoEnum tipoRecebimento;
	protected Usuario usuario;
	protected BigDecimal valor;
	protected Date vencimento;

	public Credito() {
		super();
	}
	
	public Credito(Long id, ContaReceber conta, String descricao, Devedor devedor,
			Date lancamento, List<BaixaRecebimento> recebimentos,
			SituacaoPagamentoEnum situacao,
			TipoRecebimentoEnum tipoRecebimento, Usuario usuario,
			BigDecimal valor, Date vencimento, Integer parcela, Integer parcelaTotal) {
		super();
		this.conta = conta;
		this.descricao = descricao;
		this.devedor = devedor;
		this.lancamento = lancamento;
		this.recebimentos = recebimentos;
		this.situacao = situacao;
		this.tipoRecebimento = tipoRecebimento;
		this.usuario = usuario;
		this.valor = valor;
		this.vencimento = vencimento;
		this.id = id;
		this.parcela = parcela;
		this.parcelaTotal = parcelaTotal;
	}

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	public ContaReceber getConta() {
		return conta;
	}

	@Column(nullable=false)
	public String getDescricao() {
		return descricao;
	}

	@ManyToOne(optional=false, fetch=FetchType.EAGER)
	public Devedor getDevedor() {
		return devedor;
	}

	@Override
	@Id
	@GeneratedValue(generator = "credito_id_seq")
	public Long getId() {
		return id;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(columnDefinition="TIMESTAMP DEFAULT NOW()", insertable=false, nullable=false)
	public Date getLancamento() {
		return lancamento;
	}

	public Integer getParcela() {
		return parcela;
	}

	@Column(name="parcela_total")
	public Integer getParcelaTotal() {
		return parcelaTotal;
	}

	@OneToMany(cascade=CascadeType.REMOVE, fetch=FetchType.LAZY, mappedBy="credito")
	public List<BaixaRecebimento> getRecebimentos() {
		return recebimentos;
	}

	@Enumerated(EnumType.STRING)
	@Column(name="situacao_pagamento")
	public SituacaoPagamentoEnum getSituacao() {
		return situacao;
	}

	@Enumerated(EnumType.STRING)
	@Column(name="tipo_recebimento")
	public TipoRecebimentoEnum getTipoRecebimento() {
		return tipoRecebimento;
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
		if(recebimentos != null && !recebimentos.isEmpty()){
			for(BaixaRecebimento baixa : recebimentos){
				valor = valor.add(baixa.getValor());
			}
		}
		return valor;
	}

	@Temporal(TemporalType.DATE)
	public Date getVencimento() {
		return vencimento;
	}

	public void setConta(ContaReceber conta) {
		this.conta = conta;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public void setDevedor(Devedor devedor) {
		this.devedor = devedor;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public void setLancamento(Date lancamento) {
		this.lancamento = lancamento;
	}

	public void setParcela(Integer parcela) {
		this.parcela = parcela;
	}

	public void setParcelaTotal(Integer parcelaTotal) {
		this.parcelaTotal = parcelaTotal;
	}

	public void setRecebimentos(List<BaixaRecebimento> recebimentos) {
		this.recebimentos = recebimentos;
	}

	public void setSituacao(SituacaoPagamentoEnum situacao) {
		this.situacao = situacao;
	}

	public void setTipoRecebimento(TipoRecebimentoEnum tipoRecebimento) {
		this.tipoRecebimento = tipoRecebimento;
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
