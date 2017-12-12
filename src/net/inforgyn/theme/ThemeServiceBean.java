package net.inforgyn.theme;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import net.inforgyn.model.ParametrosSistema;
import net.inforgyn.model.Usuario;
import net.inforgyn.neg.ParametrosNeg;
import net.inforgyn.util.UsuarioSessaoUtil;
import net.inforgyn.util.faces.FacesUtil;

@Named
@SessionScoped
public class ThemeServiceBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<Tema> themes;
	private String theme;
	private ParametrosSistema parametro;
	@Inject	private ParametrosNeg parametrosNeg;

	@PostConstruct
	public void init() {
		themes = new ArrayList<Tema>();
		themes.add(new Tema(0L, "Afterdark", "afterdark"));
		themes.add(new Tema(1L, "Afternoon", "afternoon"));
		themes.add(new Tema(2L, "Afterwork", "afterwork"));
		themes.add(new Tema(3L, "Aristo", "aristo"));
		themes.add(new Tema(4L, "Black-Tie", "black-tie"));
		themes.add(new Tema(5L, "Blitzer", "blitzer"));
		themes.add(new Tema(6L, "Bluesky", "bluesky"));
		themes.add(new Tema(7L, "Bootstrap", "bootstrap"));
		themes.add(new Tema(8L, "Casablanca", "casablanca"));
		themes.add(new Tema(9L, "Cupertino", "cupertino"));
		themes.add(new Tema(10L, "Cruze", "cruze"));
		themes.add(new Tema(11L, "Dark-Hive", "dark-hive"));
		themes.add(new Tema(12L, "Delta", "delta"));
		themes.add(new Tema(13L, "Dot-Luv", "dot-luv"));
		themes.add(new Tema(14L, "Eggplant", "eggplant"));
		themes.add(new Tema(15L, "Excite-Bike", "excite-bike"));
		themes.add(new Tema(16L, "Flick", "flick"));
		themes.add(new Tema(17L, "Glass-X", "glass-x"));
		themes.add(new Tema(18L, "Home", "home"));
		themes.add(new Tema(19L, "Hot-Sneaks", "hot-sneaks"));
		themes.add(new Tema(20L, "Humanity", "humanity"));
		themes.add(new Tema(21L, "Le-Frog", "le-frog"));
		themes.add(new Tema(22L, "Midnight", "midnight"));
		themes.add(new Tema(23L, "Mint-Choc", "mint-choc"));
		themes.add(new Tema(24L, "Overcast", "overcast"));
		themes.add(new Tema(25L, "Pepper-Grinder", "pepper-grinder"));
		themes.add(new Tema(26L, "Redmond", "redmond"));
		themes.add(new Tema(27L, "Rocket", "rocket"));
		themes.add(new Tema(28L, "Sam", "sam"));
		themes.add(new Tema(29L, "Smoothness", "smoothness"));
		themes.add(new Tema(30L, "South-Street", "south-street"));
		themes.add(new Tema(31L, "Start", "start"));
		themes.add(new Tema(32L, "Sunny", "sunny"));
		themes.add(new Tema(33L, "Swanky-Purse", "swanky-purse"));
		themes.add(new Tema(34L, "Trontastic", "trontastic"));
		themes.add(new Tema(35L, "UI-Darkness", "ui-darkness"));
		themes.add(new Tema(36L, "UI-Lightness", "ui-lightness"));
		themes.add(new Tema(37L, "Vader", "vader"));
	}

	public void salvar() {
		parametro.setTema(theme);

		parametrosNeg.Alterar(parametro);

		FacesUtil.infoMessageSimples("Tema salvo");
	}

	public List<Tema> getThemes() {
		return themes;
	}

	public String getTheme() {
		Usuario usuario = UsuarioSessaoUtil.getUsuario();
		if (usuario == null) {
			theme = "trontastic";
		} else {
			//parametro = parametrosNeg.pesquisarPorUsuario(usuario);
			parametro = usuario.getParametros();
			theme = parametro.getTema();
		}
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}
}
