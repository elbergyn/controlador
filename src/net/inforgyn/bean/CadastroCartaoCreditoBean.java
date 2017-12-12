package net.inforgyn.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import net.inforgyn.model.BandeiraCartao;
import net.inforgyn.model.CartaoCredito;
import net.inforgyn.neg.BandeiraCartaoNeg;
import net.inforgyn.neg.CartaoCreditoNeg;
import net.inforgyn.util.faces.FacesUtil;

@Named
@ViewScoped
public class CadastroCartaoCreditoBean implements CadastroBean, Serializable{

	private static final long serialVersionUID = 1L;
	@Inject private CartaoCreditoNeg cartaoCreditoNeg;
	@Inject private BandeiraCartaoNeg bandeiraCartaoNeg;
	@Inject private CartaoCredito cartao;	
	
	@Inject private List<CartaoCredito> cartoes;
	
	@Override
	public void novo() {
		cartao = new CartaoCredito();
	}

	@Override
	public void salvar() {	
		if(null == cartao.getId()){
			cartao = cartaoCreditoNeg.salvar(cartao);
			cartoes.add(cartao);
			FacesUtil.infoMessageSimples("Cartão cadastrado: "+this.cartao.getDescricao());
		}else {
			cartao = cartaoCreditoNeg.alterar(cartao);
			FacesUtil.infoMessageSimples("Cartão alterado: "+this.cartao.getDescricao());
		}
		novo();
	}
	
	@PostConstruct
	public void init(){
	}
	
	public void excluir(){
		cartaoCreditoNeg.excluir(cartao);
		cartoes.remove(cartao);
		FacesUtil.infoMessageSimples("Excluído: "+this.cartao.getDescricao());
		novo();
	}

	public CartaoCredito getCartao() {
		return cartao;
	}

	public void setCartao(CartaoCredito cartao) {
		this.cartao = cartao;
	}

	public List<CartaoCredito> listarCartoes(){
		return cartoes;
	}
	
	public List<BandeiraCartao> listarBandeiras(){
		return bandeiraCartaoNeg.listarBandeiras();
	}
}
