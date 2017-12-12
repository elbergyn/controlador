package net.inforgyn.neg;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import net.inforgyn.impl.EntityPersistence;
import net.inforgyn.model.CategoriaDespesa;
import net.inforgyn.model.Usuario;
import net.inforgyn.repository.CategoriaDespesaDao;
import net.inforgyn.util.UsuarioSessaoUtil;

public class CategoriaDespesaNeg {
	@Inject
	private CategoriaDespesaDao dao;

	public CategoriaDespesa alterar(CategoriaDespesa categoria) {
		dao.alterar(categoria);
		return (CategoriaDespesa) categoria;
	}

	public void excluir(EntityPersistence categoria) {
		dao.excluir(categoria);
	}

	@Produces
	public List<CategoriaDespesa> listarCategorias() {
		return dao.listarPorUsuarioComPai(CategoriaDespesa.class, UsuarioSessaoUtil.getUsuario());
	}

	public CategoriaDespesa salvar(CategoriaDespesa categoria) {
		categoria.setUsuario(UsuarioSessaoUtil.getUsuario());
		dao.salvar(categoria);
		return (CategoriaDespesa) categoria;
	}
	
	public void gerarCategoriasPadrao(Usuario usuario){
		List<CategoriaDespesa> categorias = new ArrayList<CategoriaDespesa>();
		categorias.add(new CategoriaDespesa(null, "Alimentação", usuario));
		categorias.add(new CategoriaDespesa(null, "Diversos", usuario));
		categorias.add(new CategoriaDespesa(null, "Lazer", usuario));
		categorias.add(new CategoriaDespesa(null, "Mensal", usuario));
		
		dao.salvar(categorias);
	}
}
