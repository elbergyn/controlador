package net.inforgyn.bean;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import net.inforgyn.constante.TipoMovimentoEnum;
import net.inforgyn.model.Movimento;
import net.inforgyn.neg.MovimentoNeg;

@Named
@ViewScoped
public class PesqMovimentacaoBean implements PesquisaBean {

	private List<Movimento> movimentos;
	private String descricao;
	private Date dataMovimentoIni;
	private Date dataMovimentoFim;
	private TipoMovimentoEnum tipoMovimento;
	@Inject MovimentoNeg movimentoNeg;
	private Map<String, Object> saldo;
	
	@Override
	public void pesquisar() {
		Map<String, Object> filtro = new HashMap<String, Object>();
		filtro.put("descricao", descricao);
		filtro.put("dataMovimentoIni", dataMovimentoIni);
		filtro.put("dataMovimentoFim", dataMovimentoFim);
		filtro.put("tipoMovimento", tipoMovimento);
		
		movimentos = movimentoNeg.listarMovimentos(filtro);
		carregarSaldo();
	}
	
	@PostConstruct
	public void init(){
		movimentos = movimentoNeg.listarUltimosMovimentos();
		carregarSaldo();
	}

	@Override
	public String cadastrar() {
		return "pretty:cadMovimento";
	}
	
	private void carregarSaldo(){
		saldo = movimentoNeg.saldo(dataMovimentoIni, dataMovimentoFim);
	}

	public Map<String, Object> getSaldo() {
		return saldo;
	}

	@Override
	public String novo() {
		return "pretty:pesqMovimentos";
	}
	
	public SelectItem[] listarTipoMovimento(){
		SelectItem[] itens= new SelectItem[TipoMovimentoEnum.values().length];
		int i = 0;
		for (TipoMovimentoEnum m : TipoMovimentoEnum.values()) {
			itens[i++] = new SelectItem(m, m.getDescricao()); 
		}
		return itens;
	}

	public List<Movimento> getMovimentos() {
		return movimentos;
	}

	public void setMovimentos(List<Movimento> movimentos) {
		this.movimentos = movimentos;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Date getDataMovimentoIni() {
		return dataMovimentoIni;
	}

	public void setDataMovimentoIni(Date dataMovimentoIni) {
		this.dataMovimentoIni = dataMovimentoIni;
	}

	public Date getDataMovimentoFim() {
		return dataMovimentoFim;
	}

	public void setDataMovimentoFim(Date dataMovimentoFim) {
		this.dataMovimentoFim = dataMovimentoFim;
	}

	public TipoMovimentoEnum getTipoMovimento() {
		return tipoMovimento;
	}

	public void setTipoMovimento(TipoMovimentoEnum tipoMovimento) {
		this.tipoMovimento = tipoMovimento;
	}
}
