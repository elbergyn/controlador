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
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import net.inforgyn.constante.StatusCaixaEnum;
import net.inforgyn.impl.EntityPersistence;
import net.inforgyn.util.DataUtil;
import net.inforgyn.util.NumericUtil;

@Entity
@Table(name="pdv_caixa")
public class Caixa extends EntityPersistence {

	private static final long serialVersionUID = 1L;

	private List<CaixaMovimento> movimentos;
	private Date data;
	private StatusCaixaEnum status;
	private BigDecimal valorFinal;
	private BigDecimal valorInicial;

	public Caixa() {
		super();
		data = new Date();
		valorInicial = new BigDecimal(0);
		valorFinal = new BigDecimal(0);
		status = StatusCaixaEnum.ABERTO;
	}

	@Transient
	public String getValorTotalMoeda() {
		return NumericUtil.moeda(valorFinal);
	}

	public void setMovimentos(List<CaixaMovimento> movimentos) {
		this.movimentos = movimentos;
	}

	@OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, mappedBy = "caixa")
	public List<CaixaMovimento> getMovimentos() {
		return movimentos;
	}

	@Temporal(TemporalType.DATE)
	public Date getData() {
		return data;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	@Enumerated(EnumType.STRING)
	public StatusCaixaEnum getStatus() {
		return status;
	}

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH, optional = false)
	public Usuario getUsuario() {
		return usuario;
	}

	@Column(name = "valor_final")
	public BigDecimal getValorFinal() {
		return valorFinal;
	}

	@Column(name = "valor_inicial")
	public BigDecimal getValorInicial() {
		return valorInicial;
	}

	@Transient
	public String getDataString() {
		return DataUtil.getDataPadraoString(data);
	}

	public void setCaixas(List<CaixaMovimento> movimentos) {
		this.movimentos = movimentos;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setStatus(StatusCaixaEnum status) {
		this.status = status;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public void setValorFinal(BigDecimal valorFinal) {
		this.valorFinal = valorFinal;
	}

	public void setValorInicial(BigDecimal valorInicial) {
		this.valorInicial = valorInicial;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((data == null) ? 0 : data.hashCode());
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
		Caixa other = (Caixa) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
