package teste;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.Persistence;

import net.inforgyn.model.Usuario;
import net.inforgyn.persistence.JPAPersistenceUtil;
import net.inforgyn.repository.DaoModel;
import net.inforgyn.teste.executar;
import net.inforgyn.util.UsuarioSessaoUtil;

public class Executar {
	
	@Inject JPAPersistenceUtil util;
	DaoModel dao = new DaoModel() {};
	@Inject static Executar ex;
	
	public static void main(String[] args) {
		ex.util = new JPAPersistenceUtil();
		ex.execucao();
	}
	
	public void execucao(){
		List<Acessorios> list = new ArrayList<Acessorios>();
		Acessorios ac = new Acessorios("Ar");
		Acessorios ac1 = new Acessorios("vidro");
		Acessorios ac2 = new Acessorios("trava");
		list.add(ac);
		list.add(ac1);
		list.add(ac2);
		
		Carro c = new Carro("Fiesta", list);
		
		util.salvar(c);
	}

}
