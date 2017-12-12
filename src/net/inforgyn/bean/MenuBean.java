package net.inforgyn.bean;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.swing.text.StyledEditorKit.BoldAction;

import net.inforgyn.constante.TipoUsuarioEnum;
import net.inforgyn.model.Usuario;
import net.inforgyn.util.RedirecionarPaginaUtil;
import net.inforgyn.util.UsuarioSessaoUtil;

import org.primefaces.component.tabview.TabView;
import org.primefaces.event.TabChangeEvent;

@Named
@SessionScoped
public class MenuBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer index;

	public MenuBean() {
		index = 0;
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	public void onChange(TabChangeEvent event) {
		if(event.getTab().getId().equals("sair")){
			UsuarioSessaoUtil.logoff();
			RedirecionarPaginaUtil.redirect("/");
		}else{
			TabView view = (TabView) event.getSource();
			index = view.getActiveIndex();
		}
	}

	public Boolean getUsuarioAdm() {
		return UsuarioSessaoUtil.getUsuario().equals(null)?false:UsuarioSessaoUtil.getUsuario().getTipo().equals(TipoUsuarioEnum.ADM);
	}
}
