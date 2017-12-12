package net.inforgyn.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import net.inforgyn.constante.SituacaoPagamentoEnum;
import net.inforgyn.constante.TipoPagamentoEnum;

@Entity
@Table(name = "ctr_debito_cartao")
@NamedQuery(name = "listarDebitoCartao", query = "from DebitoCartao")
public class DebitoCartao extends Debito {

	private static final long serialVersionUID = 1L;
	private CartaoCredito cartao;

	public DebitoCartao(Long id, String descricao, Date lancamento,
			SituacaoPagamentoEnum situacao, BigDecimal valor, Date vencimento,
			TipoPagamentoEnum tipoPagamento, CartaoCredito cartao, Integer parcela, Integer parcelaTotal) {
		super(id, descricao, lancamento, situacao, valor, vencimento,
				tipoPagamento, parcela, parcelaTotal);
		this.cartao = cartao;
	}

	public DebitoCartao() {
		super();
	}

	@ManyToOne(cascade=CascadeType.DETACH, optional=false)
	public CartaoCredito getCartao() {
		return cartao;
	}

	public void setCartao(CartaoCredito cartao) {
		this.cartao = cartao;
	}
}
