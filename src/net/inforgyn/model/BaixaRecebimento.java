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
@Table(name="ctr_baixa_recebimento")
public class BaixaRecebimento extends EntityPersistence {

	private static final long serialVersionUID = 1L;
	
	private Date dataPagamento;
	private Credito credito;
	private Date lancamento;
	private Usuario usuario;
	private BigDecimal valor;
	
	public BaixaRecebimento() {
		super();
		lancamento = new Date();
	}
	
	@Column(name="data_pagamento")
	@Temporal(TemporalType.DATE)
	public Date getDataPagamento() {
		return dataPagamento;
	}

	@ManyToOne(optional=false, fetch=FetchType.LAZY, cascade=CascadeType.DETACH)
	public Credito getCredito() {
		return credito;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long getId(){
		return id;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(columnDefinition="TIMESTAMP DEFAULT NOW()", insertable=false, nullable=false)
	public Date getLancamento() {
		return lancamento;
	}

	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.DETACH, optional=false)
	public Usuario getUsuario() {
		return usuario;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setDataPagamento(Date dataPagamento) {
		this.dataPagamento = dataPagamento;
	}

	public void setCredito(Credito credito) {
		this.credito = credito;
	}

	public void setId(Long id){
		this.id = id;
	}

	public void setLancamento(Date lancamento) {
		this.lancamento = lancamento;
	}
	
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
}
