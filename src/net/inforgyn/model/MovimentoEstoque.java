package net.inforgyn.model;

import java.util.Date;

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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import net.inforgyn.constante.TipoLancamentoEnum;
import net.inforgyn.impl.EntityPersistence;

@Entity
@Table(name="pdv_movimento_estoque")
public class MovimentoEstoque extends EntityPersistence{

	private static final long serialVersionUID = 1L;

	private Date data;
	private Produto produto;
	private Integer qtde;
	private TipoLancamentoEnum tipoLancamento;
	private Usuario usuario;
	
	public MovimentoEstoque() {
	}

	public MovimentoEstoque(Date data, Produto produto, Integer qtde, TipoLancamentoEnum tipoLancamento,
			Usuario usuario) {
		super();
		this.data = data;
		this.produto = produto;
		this.qtde = qtde;
		this.tipoLancamento = tipoLancamento;
		this.usuario = usuario;
	}



	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}
	
	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.DETACH, optional=false)
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(columnDefinition="TIMESTAMP DEFAULT NOW()", insertable=false, nullable=false)
	public Date  getData() {
		return data;
	}

	@ManyToOne(cascade=CascadeType.REMOVE, fetch=FetchType.LAZY, optional=false)
	public Produto getProduto() {
		return produto;
	}

	public Integer getQtde() {
		return qtde;
	}

	@Enumerated(EnumType.STRING)
	@Column(name="tipo_lancamento")
	public TipoLancamentoEnum getTipoLancamento() {
		return tipoLancamento;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public void setQtde(Integer qtde) {
		this.qtde = qtde;
	}

	public void setTipoLancamento(TipoLancamentoEnum tipoLancamento) {
		this.tipoLancamento = tipoLancamento;
	}
}
