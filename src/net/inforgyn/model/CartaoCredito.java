package net.inforgyn.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import net.inforgyn.impl.EntityPersistence;
import net.inforgyn.impl.TipoPagamento;


@Entity
@Table(name="ctr_cartao_credito")
@NamedQuery(name="listarCartoesCredito", query="from CartaoCredito")
public class CartaoCredito extends EntityPersistence implements TipoPagamento {

	private static final long serialVersionUID = 1L;

	private BandeiraCartao bandeira;
	private String descricao;
	private Integer diasFechamento;
	private Integer diaVencimento;
	private Integer digitosFinais;
	private Usuario usuario;
	
	public CartaoCredito() {
		super();
		diasFechamento = 10;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CartaoCredito other = (CartaoCredito) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@OneToOne
	public BandeiraCartao getBandeira() {
		return bandeira;
	}
	
	@Column(nullable=false, length=30)
	public String getDescricao() {
		return descricao;
	}
	
	@Column(nullable=false, name="dias_fechamento")
	public Integer getDiasFechamento() {
		return diasFechamento;
	}

	@Column(nullable=false, name="dia_vencimento")
	public Integer getDiaVencimento() {
		return diaVencimento;
	}

	@Column(length=4, name="digitos_finais")
	public Integer getDigitosFinais() {
		return digitosFinais;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long getId(){
		return this.id;
	}

	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.DETACH, optional=false)
	public Usuario getUsuario() {
		return usuario;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	public void setBandeira(BandeiraCartao bandeira) {
		this.bandeira = bandeira;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public void setDiasFechamento(Integer diasFechamento) {
		this.diasFechamento = diasFechamento;
	}

	public void setDiaVencimento(Integer diaVencimento) {
		this.diaVencimento = diaVencimento;
	}

	public void setDigitosFinais(Integer digitosFinais) {
		this.digitosFinais = digitosFinais;
	}

	public void setId(Long id){
		this.id = id;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
}