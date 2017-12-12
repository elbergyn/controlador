package net.inforgyn.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import net.inforgyn.impl.EntityPersistence;

@Entity
@Table(name="parametros_sistema")
public class ParametrosSistema extends EntityPersistence{

	private static final long serialVersionUID = 1L;
	
	private String tema;
	private Usuario usuario;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long getId(){
		return this.id;
	}

	public String getTema() {
		return tema;
	}

	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.DETACH, optional=false)
	public Usuario getUsuario() {
		return usuario;
	}

	public void setId(Long id){
		this.id = id;
	}
	
	public void setTema(String tema) {
		this.tema = tema;
	}
	
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
}
