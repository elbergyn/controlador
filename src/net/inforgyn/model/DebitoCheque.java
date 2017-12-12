package net.inforgyn.model;

import java.math.BigDecimal;
import java.util.Date;
import javax.inject.Inject;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import net.inforgyn.constante.SituacaoPagamentoEnum;
import net.inforgyn.constante.TipoPagamentoEnum;
import net.inforgyn.qualifierCdi.EmitirChequeQ;

@Entity
@Table(name = "ctr_debito_cheque")
@NamedQuery(name = "listarDebitosCheque", query = "from DebitoCheque")
public class DebitoCheque extends Debito {

	private static final long serialVersionUID = 1L;

	@Inject
	@EmitirChequeQ
	private EmitirCheque emitirCheque;

	public DebitoCheque() {
		super();
	}

	public DebitoCheque(Long id, String descricao, Date lancamento,
			SituacaoPagamentoEnum situacao, BigDecimal valor, Date vencimento,
			TipoPagamentoEnum tipoPagamento, Cheque cheque, Long numero,
			Integer parcela, Integer parcelaTotal) {
		super(id, descricao, lancamento, situacao, valor, vencimento,
				tipoPagamento, parcela, parcelaTotal);
		this.emitirCheque.cheque = cheque;
		this.emitirCheque.numeroCheque = numero;
	}

	@Embedded
	public EmitirCheque getEmitirCheque() {
		return emitirCheque;
	}

	public void setEmitirCheque(EmitirCheque emitirCheque) {
		this.emitirCheque = emitirCheque;
	}
}
