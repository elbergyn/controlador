package net.inforgyn.neg;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import net.inforgyn.decorator.DataMenu;
import net.inforgyn.model.CartaoCredito;
import net.inforgyn.repository.CartaoCreditoDao;
import net.inforgyn.util.DataUtil;
import net.inforgyn.util.UsuarioSessaoUtil;

public class CartaoCreditoNeg {
	@Inject
	private CartaoCreditoDao dao;

	public CartaoCredito alterar(CartaoCredito cartao) {
		dao.alterar(cartao);
		return (CartaoCredito) cartao;
	}

	public CartaoCredito salvar(CartaoCredito cartao) {
		cartao.setUsuario(UsuarioSessaoUtil.getUsuario());
		dao.salvar(cartao);
		return cartao;
	}

	public void excluir(CartaoCredito cartao) {
		dao.excluir(cartao);
	}
	
	@Produces
	public List<CartaoCredito> listarCartoes() {
		return dao.listarPorUsuarioComPai(CartaoCredito.class, UsuarioSessaoUtil.getUsuario());
	}
	
	public List<DataMenu> verificarVencimentoCartao(CartaoCredito cartao){
		List<DataMenu> datas = new ArrayList<DataMenu>();
		
		Calendar hoje = GregorianCalendar.getInstance();
		
		Calendar vencimento = Calendar.getInstance();
		vencimento.set(Calendar.YEAR, hoje.get(Calendar.YEAR));
		vencimento.set(Calendar.MONTH, hoje.get(Calendar.MONTH));		
		vencimento.set(Calendar.DAY_OF_MONTH, cartao.getDiaVencimento());
		
		if(DataUtil.dataAnterior(vencimento.getTime(), hoje.getTime())
				|| DataUtil.dataIgual(hoje.getTime(), vencimento.getTime()));{
			vencimento.set(Calendar.MONTH, vencimento.get(Calendar.MONTH)+1);
		}
		
		if(posteriorFechamento(cartao)){//5 dias posterior ao fechamento, período de correção
			datas.add(new DataMenu(DataUtil.subtrairMes(vencimento.getTime(), 1)));
		}
		
		datas.add(new DataMenu(vencimento.getTime()));
		
		if(intervaloFechamento(vencimento.getTime(), hoje.getTime(), cartao)){
			Date novoVencimento = DataUtil.adicionarMes(vencimento.getTime(), 1);
			datas.add(new DataMenu(novoVencimento));
		}
		
		
		/*if(intervaloFechamento(vencimento.getTime(), hoje.getTime())){
			
			if(DataUtil.dataPosterior(hoje.getTime(), vencimento.getTime())){
				Date novoVencimento = DataUtil.adicionarMes(vencimento.getTime(), 1);
				Date novoVencimento = vencimento.getTime();
				datas.add(new DataMenu(novoVencimento));
				
				novoVencimento = DataUtil.adicionarMes(vencimento.getTime(), 2);
				datas.add(new DataMenu(novoVencimento));
			}else{
				datas.add(new DataMenu(vencimento.getTime()));
				
				Date novoVencimento = DataUtil.adicionarMes(vencimento.getTime(), 1);
				datas.add(new DataMenu(novoVencimento));
			}
		}else{
			if(DataUtil.dataIgual(hoje.getTime(), vencimento.getTime()) 
					|| DataUtil.dataPosterior(hoje.getTime(), vencimento.getTime())){
				Date novoVencimento = DataUtil.adicionarMes(vencimento.getTime(), 1);
				datas.add(new DataMenu(novoVencimento));
			}else{
				datas.add(new DataMenu(vencimento.getTime()));
			}
		}*/
		
		return datas;
	}
	
	private boolean intervaloFechamento(Date vencimento, Date hoje, CartaoCredito cartao){
		Date fechamento = DataUtil.subtrairDias(vencimento, cartao.getDiasFechamento()+5);
		
		if(DataUtil.dataAnterior(fechamento, hoje)){
			return true;
		}
		return false;
	}
	
	private boolean posteriorFechamento(CartaoCredito cartao){
		Calendar vencMes = Calendar.getInstance();
		Calendar hoje = Calendar.getInstance();
		
		vencMes.set(Calendar.YEAR, hoje.get(Calendar.YEAR));
		vencMes.set(Calendar.MONTH, hoje.get(Calendar.MONTH));		
		vencMes.set(Calendar.DAY_OF_MONTH, cartao.getDiaVencimento());
		
		Date cinco_posterior = DataUtil.adicionarDias(vencMes.getTime(), 5);
		
		if(DataUtil.dataAnterior(hoje.getTime(),cinco_posterior)){
			return true;
		}
		return false;
	}
}
