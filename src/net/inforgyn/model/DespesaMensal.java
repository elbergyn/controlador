package net.inforgyn.model;

import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import net.inforgyn.impl.EntityPersistence;
import net.inforgyn.util.StringUtil;

@Entity
@Table(name="ctr_despesa_mensal")
@NamedQuery(name="listarDespesasMensais", query="from DespesaMensal")
public class DespesaMensal extends EntityPersistence{
	private static final long serialVersionUID = 1L;
	
	private String descricao;
	private Integer diaVencimento;
	private BigDecimal valor;
	private Usuario usuario;
	
	public DespesaMensal() {
		super();
	}

	public DespesaMensal(Long id, String descricao, Integer diaVencimento, BigDecimal valor,
			Usuario usuario) {
		super();
		this.id = id;
		this.descricao = descricao;
		this.diaVencimento = diaVencimento;
		this.valor = valor;
		this.usuario = usuario;
	}
	
	@Override
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long getId(){
		return this.id;
	}
	
	@Override
	public void setId(Long id){
		this.id = id;
	}

	@Column(length=40)
	public String getDescricao() {
		return StringUtil.captalizar(descricao);
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@Column(name="dia_vencimento")
	public Integer getDiaVencimento() {
		return diaVencimento;
	}

	public void setDiaVencimento(Integer diaVencimento) {
		this.diaVencimento = diaVencimento;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.DETACH, optional=false)
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
}
