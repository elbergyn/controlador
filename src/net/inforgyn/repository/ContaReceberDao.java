package net.inforgyn.repository;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import net.inforgyn.constante.SituacaoPagamentoEnum;
import net.inforgyn.impl.EntityPersistence;
import net.inforgyn.model.BaixaRecebimento;
import net.inforgyn.model.ContaReceber;
import net.inforgyn.persistence.JPAPersistenceUtil;
import net.inforgyn.util.DataUtil;
import net.inforgyn.util.UsuarioSessaoUtil;

public class ContaReceberDao extends DaoModel{

	public List<ContaReceber> listarEmAberto() {
		StringBuilder sb = new StringBuilder();
		sb.append("from ").append(ContaReceber.class.getName());
		sb.append(" where usuario = :usuario and situacao <> :situacao ");

		Map<String, Object> filtro = new HashMap<String, Object>();
		filtro.put("situacao", SituacaoPagamentoEnum.QUITADO);
		filtro.put("usuario", UsuarioSessaoUtil.getUsuario());

		return persistence.pesquisarHql(sb.toString(), filtro,
				ContaReceber.class);
	}

	public ContaReceber carregarCreditos(ContaReceber conta) {
		return (ContaReceber) persistence.carregarLazy(conta, "creditos");
	}

	public void atualizarStatusContas(List<BaixaRecebimento> recebimentos) {
		StringBuilder sb = new StringBuilder();
		Set<Long> contasId = new HashSet<Long>();
		List<EntityPersistence> contas = new ArrayList<EntityPersistence>();

		for (BaixaRecebimento baixa : recebimentos) {
			Long idConta = baixa.getCredito().getConta().getId();
			contasId.add(idConta);
			contas.add(persistence.pesquisarPorId(new ContaReceber(), idConta));
		}

		sb.append("select new Map(c.id as id, sum(b.valor) as pagamentos, ");
		sb.append("c.valorTotal as total, c.vencimento as vencimento) ");
		sb.append("from ").append(BaixaRecebimento.class.getName())
				.append(" b ");
		sb.append("right join b.credito.conta c ");
		sb.append(" where c.id in (");
		sb.append(contasId.toString().replace("[", "").replace("]", ""));
		sb.append(") ");
		sb.append(" group by c.id");

		List<Object> valores = persistence.pesquisaHql(sb.toString(),
				null);

		for (Object obj : valores) {
			Map<String, Object> valor = (Map<String, Object>) obj;
			for (EntityPersistence entity : contas) {
				ContaReceber conta = (ContaReceber) entity;
				if (valor.get("id").equals(conta.getId())) {
					if (valor.get("total").equals(valor.get("pagamentos"))) {
						conta.setSituacao(SituacaoPagamentoEnum.QUITADO);
					} else {
						BigDecimal total = (BigDecimal) valor.get("total");
						BigDecimal pagamentos = (BigDecimal) valor
								.get("pagamentos");
						if ((pagamentos == null || pagamentos.longValue() == 0)
								&& DataUtil.dataAnterior(conta.getVencimento(),
										new Date())) {
							conta.setSituacao(SituacaoPagamentoEnum.ATRASO);
						} else if ((pagamentos == null || pagamentos.longValue() == 0)
								&& DataUtil.dataPosterior(conta.getVencimento(), new Date()) 
								|| DataUtil.dataIgual(conta.getVencimento(), new Date())) {
							conta.setSituacao(SituacaoPagamentoEnum.A_VENCER);
						} else if (total.longValue() > pagamentos.longValue()) {
							conta.setSituacao(SituacaoPagamentoEnum.PAG_PARCIAL);
						}
					}
				}
			}
		}

		persistence.atualizar(contas);
	}

	/**Utilizado pela rotina automatica para atualizar status das contas**/
	public void atualizarStatusContas() {
		StringBuilder sb = new StringBuilder();
		sb.append("update ");
		sb.append(ContaReceber.class.getName());
		sb.append(" set situacao = :situacaoNova where id in (");
		sb.append(" select id from ");
		sb.append(ContaReceber.class.getName());
		sb.append(" where situacao = :situacao");
		sb.append(" AND vencimento < date(now())) ");
				
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("situacao", SituacaoPagamentoEnum.A_VENCER);
		parametros.put("situacaoNova", SituacaoPagamentoEnum.ATRASO);
		
		persistence.atualizarHql(sb.toString(), parametros);		
	}

}
