package net.inforgyn.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import net.inforgyn.impl.EntityPersistence;

@Entity
@Table(name="pdv_item_venda")
public class ItemVenda extends EntityPersistence{

	private static final long serialVersionUID = 1L;
	
	private Produto produto;
	private Integer qtde;
	private BigDecimal valorTotal;
	private BigDecimal valorUnitario;
	private BigDecimal valorCusto;
	
	public ItemVenda() {
		qtde = 1;
		produto = new Produto();
	}
	
	@Column(name="valor_custo", precision=15, scale=2)
	public BigDecimal getValorCusto() {
		return valorCusto;
	}

	public void setValorCusto(BigDecimal valorCusto) {
		this.valorCusto = valorCusto;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	@ManyToOne(fetch=FetchType.LAZY, optional=false)
	public Produto getProduto() {
		return produto;
	}

	public Integer getQtde() {
		return qtde;
	}

	@Column(name="valor_total", precision=15, scale=2)
	public BigDecimal getValorTotal() {
		return valorTotal;
	}

	@Column(name="valor_unitario", precision=15, scale=2)
	public BigDecimal getValorUnitario() {
		return valorUnitario;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public void setQtde(Integer qtde) {
		this.qtde = qtde;
	}

	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}

	public void setValorUnitario(BigDecimal valorUnitario) {
		this.valorUnitario = valorUnitario;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		ItemVenda other = (ItemVenda) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
