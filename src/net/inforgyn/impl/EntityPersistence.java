package net.inforgyn.impl;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import net.inforgyn.model.Usuario;

public class EntityPersistence implements Serializable {
	private static final long serialVersionUID = 1L;
	protected Long id;
	protected Usuario usuario;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
}
