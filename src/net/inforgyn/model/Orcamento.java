package net.inforgyn.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import net.inforgyn.constante.StatusVendaEnum;
import net.inforgyn.impl.EntityPersistence;

@Entity
@Table(name="pdv_orcamento")
public class Orcamento extends EntityPersistence {

	private static final long serialVersionUID = 1L;

	private Cliente cliente;
	private Boolean cobrarTaxaServico;
	private Date data;
	private Funcionario funcionario;
	private List<ItemVenda> itensVenda;
	private String observacao;
	private Double percentualTaxaServico;
	private StatusVendaEnum status;
	private Usuario usuario;
	private BigDecimal valorTaxaSevico;
	private BigDecimal valorItens;
	private BigDecimal valorTotal;

	public Orcamento() {
		data = new Date();
		percentualTaxaServico = 0.0;
		cobrarTaxaServico = false;
		status = StatusVendaEnum.ORCAMENTO;
		valorTaxaSevico = new BigDecimal(0.0);
		valorItens = new BigDecimal(0.0);
		valorTotal = new BigDecimal(0.0);
		itensVenda = new ArrayList<ItemVenda>();
	}	
	
	@Column(name="valor_itens")
	public BigDecimal getValorItens() {
		return valorItens;
	}

	public void setValorItens(BigDecimal valorItens) {
		this.valorItens = valorItens;
	}

	@ManyToOne(optional = true, fetch = FetchType.LAZY, targetEntity = Cliente.class)
	public Cliente getCliente() {
		return cliente;
	}
	
	@Column(name="cobrar_tx_servico")
	public Boolean getCobrarTaxaServico() {
		return cobrarTaxaServico;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="data_lancamento", columnDefinition="TIMESTAMP DEFAULT NOW()", insertable=false, nullable=false)
	public Date getData() {
		return data;
	}

	@ManyToOne(optional = true, fetch = FetchType.LAZY, targetEntity = Funcionario.class)
	public Funcionario getFuncionario() {
		return funcionario;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name="orcamento_id")
	public List<ItemVenda> getItensVenda() {
		return itensVenda;
	}

	@Lob
	public String getObservacao() {
		return observacao;
	}

	@Column(precision=2, name="perc_tx_servico")
	public Double getPercentualTaxaServico() {
		return percentualTaxaServico;
	}

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	public StatusVendaEnum getStatus() {
		return status;
	}

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH, optional = false)
	public Usuario getUsuario() {
		return usuario;
	}

	@Column(name="valor_tx_servico")
	public BigDecimal getValorTaxaSevico() {
		return valorTaxaSevico;
	}

	@Column(name="valor_total")
	public BigDecimal getValorTotal() {
		return valorTotal;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public void setCobrarTaxaServico(Boolean cobrarTaxaServico) {
		this.cobrarTaxaServico = cobrarTaxaServico;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setItensVenda(List<ItemVenda> itensVenda) {
		this.itensVenda = itensVenda;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public void setPercentualTaxaServico(Double percentualTaxaServico) {
		this.percentualTaxaServico = percentualTaxaServico;
	}

	public void setStatus(StatusVendaEnum status) {
		this.status = status;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public void setValorTaxaSevico(BigDecimal valorTaxaSevico) {
		this.valorTaxaSevico = valorTaxaSevico;
	}
	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}
}
