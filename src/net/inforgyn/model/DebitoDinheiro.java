package net.inforgyn.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import net.inforgyn.constante.SituacaoPagamentoEnum;
import net.inforgyn.constante.TipoPagamentoEnum;

@Entity
@Table(name = "ctr_debito_dinheiro")
@NamedQuery(name = "listarDebitoDinheiro", query = "from DebitoDinheiro")
public class DebitoDinheiro extends Debito {

	private static final long serialVersionUID = 1L;

	public DebitoDinheiro(Long id, String descricao, Date lancamento,
			SituacaoPagamentoEnum situacao, BigDecimal valor, Date vencimento,
			TipoPagamentoEnum tipoPagamento, Integer parcela,
			Integer parcelaTotal) {
		super(id, descricao, lancamento, situacao, valor, vencimento,
				tipoPagamento, parcela, parcelaTotal);
	}

	public DebitoDinheiro() {
		super();
	}
}
