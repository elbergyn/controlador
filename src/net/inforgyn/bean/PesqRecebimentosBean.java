package net.inforgyn.bean;

import java.math.BigDecimal;
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
import net.inforgyn.neg.BaixaRecebimentoNeg;
import net.inforgyn.util.NumericUtil;

@Named
@ViewScoped
public class PesqRecebimentosBean implements PesquisaBean{

	private String descricao;
	private Date fimRecebimento;
	private Date inicioRecebimento;
	@Inject private BaixaRecebimentoNeg recebimentoNeg;
	private List<BaixaRecebimento> recebimentos;
	private SituacaoPagamentoEnum situacao;
	private TipoRecebimentoEnum tipoRecebimento;
	private Map<String, BigDecimal> valores;
	
	@Override
	public String cadastrar() {
		return "pretty:cadBaixaRecebimento";
	}
	
	public boolean getMostrarTotal(){
		return recebimentos != null && recebimentos.size() > 0;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public Date getFimRecebimento() {
		return fimRecebimento;
	}
	
	public Date getInicioRecebimento() {
		return inicioRecebimento;
	}

	public List<BaixaRecebimento> getRecebimentos() {
		return recebimentos;
	}

	public SituacaoPagamentoEnum getSituacao() {
		return situacao;
	}

	public TipoRecebimentoEnum getTipoRecebimento() {
		return tipoRecebimento;
	}

	public String getValorTotal(){
		if(valores != null){
			return NumericUtil.moeda((BigDecimal)valores.get("total"));
		}
		return "";
	}

	public SelectItem[] listarSituacaoRecebimentos() {
		SelectItem[] itens = new SelectItem[2];
		itens[0] = new SelectItem(SituacaoPagamentoEnum.PAG_PARCIAL, SituacaoPagamentoEnum.PAG_PARCIAL.getDescricao());
		itens[1] = new SelectItem(SituacaoPagamentoEnum.QUITADO, SituacaoPagamentoEnum.QUITADO.getDescricao());
		
		return itens;
	}

	public SelectItem[] listarTiposRecebimentos() {
		SelectItem[] itens = new SelectItem[TipoRecebimentoEnum.values().length];
		int i = 0;
		for (TipoRecebimentoEnum e : TipoRecebimentoEnum.values()) {
			itens[i++] = new SelectItem(e, e.getDescricao());
		}
		return itens;
	}

	@Override
	public String novo() {
		return "pretty:pesqRecebimentos";
	}

	@Override
	public void pesquisar() {
		Map<String, Object> filtro = new HashMap<String, Object>();
		filtro.put("descricao", descricao);
		filtro.put("tipoRecebimento", tipoRecebimento);
		filtro.put("inicioRecebimento", inicioRecebimento);
		filtro.put("fimRecebimento", fimRecebimento);
		filtro.put("situacao", situacao);
		
		recebimentos = recebimentoNeg.pesquisarRecebimentos(filtro);
		valores = recebimentoNeg.valorTotalPesquisa(filtro);
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public void setFimRecebimento(Date fimRecebimento) {
		this.fimRecebimento = fimRecebimento;
	}

	public void setInicioRecebimento(Date inicioRecebimento) {
		this.inicioRecebimento = inicioRecebimento;
	}

	public void setRecebimentos(List<BaixaRecebimento> recebimentos) {
		this.recebimentos = recebimentos;
	}

	public void setSituacao(SituacaoPagamentoEnum situacao) {
		this.situacao = situacao;
	}

	public void setTipoRecebimento(TipoRecebimentoEnum tipoRecebimento) {
		this.tipoRecebimento = tipoRecebimento;
	}

}
