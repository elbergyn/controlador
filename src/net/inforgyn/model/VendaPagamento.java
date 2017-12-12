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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import net.inforgyn.impl.EntityPersistence;

@Entity
@Table(name="pdv_venda_pagamento")
public class VendaPagamento extends EntityPersistence{

	private static final long serialVersionUID = 1L;
	
	private Date data;
	private TipoRecebimento tipoRecebimento;
	private BigDecimal valor;
	private Usuario usuario;
	
	public VendaPagamento() {
		valor = new BigDecimal(0);
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(columnDefinition="TIMESTAMP DEFAULT NOW()", insertable=false, name="data_lancamento", nullable=false)
	public Date getData() {
		return data;
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

	@OneToOne(cascade=CascadeType.DETACH, fetch=FetchType.EAGER, orphanRemoval=true)
	public TipoRecebimento getTipoRecebimento() {
		return tipoRecebimento;
	}

	@Column(nullable=false)
	public BigDecimal getValor() {
		return valor;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setTipoRecebimento(TipoRecebimento tipoRecebimento) {
		this.tipoRecebimento = tipoRecebimento;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	/*@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		VendaPagamento other = (VendaPagamento) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}*/
	
	
}
