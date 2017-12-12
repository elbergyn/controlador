package net.inforgyn.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.model.SelectItem;
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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import net.inforgyn.constante.PeriodoPagamentoEnum;
import net.inforgyn.constante.SituacaoPagamentoEnum;
import net.inforgyn.impl.EntityPersistence;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@SequenceGenerator(name = "conta_id_seq", sequenceName = "conta_id_seq")
public abstract class Conta extends EntityPersistence {

	private static final long serialVersionUID = 1L;

	protected Date dataLancamento;
	protected String descricao;
	protected Integer parcelaTotal;
	protected Integer periodoPagamento;
	protected SituacaoPagamentoEnum situacao;
	protected Usuario usuario;
	protected List<Parcela> valorParcelas = new ArrayList<Parcela>();
	protected BigDecimal valorTotal;
	protected Date vencimento;

	public Conta() {
		super();
		dataLancamento = new Date();
		periodoPagamento = 30;
		parcelaTotal= 1;
	}
	
	@Transient
	public SelectItem[] getPeriodoPagamentoSelectItem() {
		SelectItem[] itens = new SelectItem[PeriodoPagamentoEnum.values().length];
		int i = 0;
		for (PeriodoPagamentoEnum e : PeriodoPagamentoEnum.values()) {
			itens[i++] = new SelectItem(e.getPeriodo(), e.getPeriodo()+" dias");
		}
		return itens;
	}

	public Conta(Long id, Date dataLancamento, String descricao, Integer parcelas,
			SituacaoPagamentoEnum situacao, Usuario usuario,
			BigDecimal valorTotal, Date vencimento, 
			Integer parcelaTotal, Integer periodoPagamento) {
		super();
		this.id = id;
		this.dataLancamento = dataLancamento;
		this.descricao = descricao;
		this.situacao = situacao;
		this.usuario = usuario;
		this.valorTotal = valorTotal;
		this.vencimento = vencimento;
		this.parcelaTotal = parcelaTotal;
		this.periodoPagamento = periodoPagamento;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="data_lancamento", columnDefinition="TIMESTAMP DEFAULT NOW()", insertable=true, updatable=true, nullable=false)
	public Date getDataLancamento() {
		return dataLancamento;
	}

	public String getDescricao() {
		return descricao;
	}

	@Id
	@GeneratedValue(generator = "conta_id_seq")
	public Long getId() {
		return this.id;
	}

	@Column(name="parcela_total")
	public Integer getParcelaTotal() {
		return parcelaTotal;
	}
	
	@Column(name="periodo_pagamento")
	public Integer getPeriodoPagamento(){
		return this.periodoPagamento;
	}
	
	@Enumerated(EnumType.STRING)
	@Column(length=20)
	public SituacaoPagamentoEnum getSituacao() {
		return situacao;
	}
	
	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.DETACH, optional=false)
	public Usuario getUsuario() {
		return usuario;
	}

	@Transient
	public List<Parcela> getValorParcelas() {
		return valorParcelas;
	}

	@Column(nullable=false, name="valor_total")
	public BigDecimal getValorTotal() {
		return valorTotal;
	}

	@Temporal(TemporalType.DATE)
	@Column(nullable=false)
	public Date getVencimento() {
		return vencimento;
	}

	public void setDataLancamento(Date dataLancamento) {
		this.dataLancamento = dataLancamento;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setParcelas(List<Parcela> valorParcelas) {
		this.valorParcelas = valorParcelas;
	}

	public void setParcelaTotal(Integer parcelaTotal) {
		this.parcelaTotal = parcelaTotal;
	}

	public void setPeriodoPagamento(Integer periodoPagamento) {
		this.periodoPagamento = periodoPagamento;
	}

	public void setSituacao(SituacaoPagamentoEnum situacao) {
		this.situacao = situacao;
	}
	
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public void setValorParcelas(List<Parcela> valorParcelas) {
		this.valorParcelas = valorParcelas;
	}

	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}

	public void setVencimento(Date vencimento) {
		this.vencimento = vencimento;
	}
}
