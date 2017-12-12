package net.inforgyn.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import net.inforgyn.impl.EntityPersistence;

@Entity
@Table(name="pdv_venda")
public class Venda extends EntityPersistence {

	private static final long serialVersionUID = 1L;
	
	private Date data;
	private Orcamento orcamento;
	private List<VendaPagamento> pagamentos;
	private Double percentualDesconto;
	private BigDecimal valorDesconto;
	private BigDecimal valorTotal;

	public Venda() {
		super();
		percentualDesconto = 0.0;
		valorDesconto = new BigDecimal(0);
		valorTotal = new BigDecimal(0);
		pagamentos = new ArrayList<VendaPagamento>();
		orcamento = new Orcamento();
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="data_lancamento", columnDefinition="TIMESTAMP DEFAULT NOW()", insertable=false, nullable=false)
	public Date getData() {
		return data;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	@OneToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
	public Orcamento getOrcamento() {
		return orcamento;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	@JoinColumn(name = "venda_id")
	public List<VendaPagamento> getPagamentos() {
		return pagamentos;
	}

	@Column(name = "perc_desconto")
	public Double getPercentualDesconto() {
		return percentualDesconto;
	}

	@Column(name = "valor_desconto")
	public BigDecimal getValorDesconto() {
		return valorDesconto;
	}

	@Transient
	public BigDecimal getValorPago() {
		BigDecimal valor = new BigDecimal(0);
		if (this.getPagamentos() != null) {
			for (VendaPagamento vp : this.getPagamentos()) {
				valor = valor.add(vp.getValor());
			}
		}
		return valor;
	}

	@Column(name = "valor_total")
	public BigDecimal getValorTotal() {
		return valorTotal;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setOrcamento(Orcamento orcamento) {
		this.orcamento = orcamento;
	}

	public void setPagamentos(List<VendaPagamento> pagamentos) {
		this.pagamentos = pagamentos;
	}

	public void setPercentualDesconto(Double percentualDesconto) {
		this.percentualDesconto = percentualDesconto;
	}

	public void setValorDesconto(BigDecimal valorDesconto) {
		this.valorDesconto = valorDesconto;
	}

	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}

}
