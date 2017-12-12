package net.inforgyn.bean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import net.inforgyn.constante.SituacaoPagamentoEnum;
import net.inforgyn.constante.TipoRecebimentoEnum;
import net.inforgyn.model.BaixaRecebimento;
import net.inforgyn.model.ContaReceber;
import net.inforgyn.model.Credito;
import net.inforgyn.model.Devedor;
import net.inforgyn.neg.CreditoNeg;
import net.inforgyn.util.NumericUtil;


@Named
@ViewScoped
public class PesqContaReceberBean implements PesquisaBean {
	
	private List<ContaReceber> contas;
	@Inject private CreditoNeg creditoNeg;
	private Date dataVencimentoFim;
	private Date dataVencimentoIni;
	private String descricao;
	private Devedor devedor;
	@Inject private List<Devedor> devedores;
	private SituacaoPagamentoEnum situacao;
	private TipoRecebimentoEnum tipoRecebimento;
	
	private Map<String, BigDecimal> valores;
	
	public String getValorTotal(){
		if(valores != null){
			return NumericUtil.moeda((BigDecimal)valores.get("total"));
		}
		return "";
	}
	
	public String styleAtraso(Credito conta){
		return conta.getSituacao().equals(SituacaoPagamentoEnum.ATRASO) ? "atraso" : "";
	}
	
	public boolean getMostrarTotal(){
		return contas != null && contas.size() > 0;
	}
	
	public String getValorAberto(){
		if(valores != null){
			BigDecimal total = valores.get("total");
			if (total == null){
				total = new BigDecimal(0);
			}
			BigDecimal pago = valores.get("pago");
			if (pago == null) {
				pago = new BigDecimal(0);
			}
			return NumericUtil.moeda(total.subtract(pago));
		}
		return "";
	}
	
	@Override
	public String cadastrar() {
		return "pretty:cadContaReceber";
	}
	
	public List<ContaReceber> getContas() {
		return contas;
	}
	
	public Date getDataVencimentoFim() {
		return dataVencimentoFim;
	}
	
	public Date getDataVencimentoIni() {
		return dataVencimentoIni;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public Devedor getDevedor() {
		return devedor;
	}

	public SituacaoPagamentoEnum getSituacao() {
		return situacao;
	}

	public TipoRecebimentoEnum getTipoRecebimento() {
		return tipoRecebimento;
	}
	
	public List<ContaReceber> listarContas(){
		return contas;
	}

	public List<Devedor> listarDevedores(String filtro){
		List<Devedor> filtrados = new ArrayList<Devedor>();
		for(Devedor d : devedores){
			if(d.getDescricao().toLowerCase().startsWith(filtro.toLowerCase())){
				filtrados.add(d);
			}
		}
		return filtrados;
	}

	public SelectItem[] listarSituacaoPagamentos() {
		SelectItem[] itens = new SelectItem[SituacaoPagamentoEnum.values().length];
		int i = 0;
		for (SituacaoPagamentoEnum e : SituacaoPagamentoEnum.values()) {
			//if(!e.equals(SituacaoPagamentoEnum.QUITADO)){
				itens[i++] = new SelectItem(e, e.getDescricao());
			//}
		}
		return itens;
	}

	public SelectItem[] listarTipoRecebimento() {
		SelectItem[] itens = new SelectItem[TipoRecebimentoEnum.values().length];
		int i = 0;
		for (TipoRecebimentoEnum e : TipoRecebimentoEnum.values()) {
			itens[i++] = new SelectItem(e, e.getDescricao());
		}
		return itens;
	}

	@Override
	public String novo() {
		return "pretty:pesqContaReceber";
	}

	@Override
	public void pesquisar() {
		Map<String, Object> filtro = new HashMap<String, Object>();
		filtro.put("descricao", descricao);
		filtro.put("tipoRecebimento", tipoRecebimento);
		filtro.put("situacao", situacao);
		filtro.put("dataVencimentoIni", dataVencimentoIni);
		filtro.put("dataVencimentoFim", dataVencimentoFim);
		filtro.put("devedor", devedor);
		
		contas = creditoNeg.listarCreditos(filtro);
		valores = creditoNeg.valorTotalPesquisa(filtro);
	}

	public void setContas(List<ContaReceber> contas) {
		this.contas = contas;
	}

	public void setDataVencimentoFim(Date dataVencimentoFim) {
		this.dataVencimentoFim = dataVencimentoFim;
	}

	public void setDataVencimentoIni(Date dataVencimentoIni) {
		this.dataVencimentoIni = dataVencimentoIni;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public void setDevedor(Devedor devedor) {
		this.devedor = devedor;
	}



	public void setSituacao(SituacaoPagamentoEnum situacao) {
		this.situacao = situacao;
	}

	public void setTipoRecebimento(TipoRecebimentoEnum tipoRecebimento) {
		this.tipoRecebimento = tipoRecebimento;
	}
}
