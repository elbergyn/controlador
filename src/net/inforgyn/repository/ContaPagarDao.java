package net.inforgyn.repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.inforgyn.constante.SituacaoPagamentoEnum;
import net.inforgyn.impl.EntityPersistence;
import net.inforgyn.model.BaixaPagamento;
import net.inforgyn.model.Conta;
import net.inforgyn.model.ContaPagar;
import net.inforgyn.util.DataUtil;
import net.inforgyn.util.UsuarioSessaoUtil;

public class ContaPagarDao extends DaoModel {
	public void atualizarStatusContas(List<BaixaPagamento> baixas) {
		if (baixas.size() > 0) {
			StringBuilder sb = new StringBuilder();
			Set<Long> contasId = new HashSet<Long>();
			List<EntityPersistence> contas = new ArrayList<EntityPersistence>();

			for (BaixaPagamento baixa : baixas) {
				Long idConta = baixa.getDebito().getConta().getId();
				contasId.add(idConta);
			}
			
			sb.append("select new Map(c.id as id, sum(b.valor) as pagamentos, ");
			sb.append("c.valorTotal as total, c.vencimento as vencimento) ");
			sb.append("from ").append(BaixaPagamento.class.getName())
					.append(" b ");
			sb.append("right outer join b.debito.conta c ");
			sb.append(" where c.id in (");
			sb.append(contasId.toString().replace("[", "").replace("]", ""));
			sb.append(") ");
			sb.append(" group by c.id, c.dataLancamento, c.descricao, c.parcelaTotal, ");
			sb.append("c.situacao, c.usuario, c.valorTotal, c.vencimento");

			List<Object> valores = persistence.pesquisaHql(sb.toString(), null);

			for (Object obj : valores) {
				Map<String, Object> valor = (Map<String, Object>) obj;
				Long idConta = (Long) valor.get("id");
				Conta conta = (ContaPagar) persistence
						.pesquisar(new ContaPagar(idConta));

				if (valor.get("total").equals(valor.get("pagamentos"))) {
					conta.setSituacao(SituacaoPagamentoEnum.QUITADO);
				} else {
					BigDecimal total = (BigDecimal) valor.get("total");
					BigDecimal pago = (BigDecimal) valor.get("pagamentos");
					if ((baixas == null || pago == null || pago.longValue() == 0)
							&& DataUtil.dataAnterior(conta.getVencimento(),
									new Date())) {
						conta.setSituacao(SituacaoPagamentoEnum.ATRASO);
					} else if ((baixas == null || pago == null || pago
							.longValue() == 0)
							&& DataUtil.dataPosterior(conta.getVencimento(),
									new Date())
							|| DataUtil.dataIgual(conta.getVencimento(),
									new Date())) {
						conta.setSituacao(SituacaoPagamentoEnum.A_VENCER);
					} else if (total.longValue() > pago.longValue()) {
						conta.setSituacao(SituacaoPagamentoEnum.PAG_PARCIAL);
					}
				}
				contas.add(conta);
			}

			persistence.atualizar(contas);
		}
	}

	public List<ContaPagar> listarEmAberto() {
		StringBuilder sb = new StringBuilder();
		sb.append("from ").append(ContaPagar.class.getName());
		sb.append(" where usuario = :usuario and situacao <> :situacao ");
		sb.append("order by vencimento");

		Map<String, Object> filtro = new HashMap<String, Object>();
		filtro.put("situacao", SituacaoPagamentoEnum.QUITADO);
		filtro.put("usuario", UsuarioSessaoUtil.getUsuario());
		
		return persistence.pesquisarHql(sb.toString(), filtro, ContaPagar.class);
	}

	@Override
	public EntityPersistence pesquisarPorId(Class classe, Long id) {
		return persistence.pesquisarPorId(classe, id);
	}

	public ContaPagar carregarDebitos(ContaPagar conta) {
		return (ContaPagar) persistence.carregarLazy(conta, "debitos");
	}

	public boolean validarExistencia(ContaPagar conta) {
		StringBuilder condicao = new StringBuilder();
		condicao.append(" where descricao = :descricao ");
		condicao.append(" and vencimento = :vencimento");
		condicao.append(" and usuario = :usuario");

		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("descricao", conta.getDescricao());
		parametros.put("vencimento", conta.getVencimento());
		parametros.put("usuario", UsuarioSessaoUtil.getUsuario());

		Long total = persistence.contar(condicao.toString(), ContaPagar.class,
				parametros);

		return total > 0 ? true : false;
	}

	/** Utilizado pela rotina automatica para atualizar status das contas **/
	public void atualizarStatusContas() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("update ");
		sb.append(ContaPagar.class.getName());
		sb.append(" set situacao = :situacaoNova where id in (");
		sb.append(" select id from ");
		sb.append(ContaPagar.class.getName());
		sb.append(" where situacao = :situacao");
		sb.append(" AND vencimento < date(now())) ");

		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("situacao", SituacaoPagamentoEnum.A_VENCER);
		parametros.put("situacaoNova", SituacaoPagamentoEnum.ATRASO);

		persistence.atualizarHql(sb.toString(), parametros);
	}

}
