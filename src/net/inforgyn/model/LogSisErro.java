package net.inforgyn.model;

import java.util.Date;

import javax.annotation.PostConstruct;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import net.inforgyn.impl.EntityPersistence;

@Entity
@Table(name="log_erro")
public class LogSisErro extends EntityPersistence{

	private static final long serialVersionUID = 1L;
	
	private Date data;
	private String mensagem;
	private String usuarioDesc;
	private Long usuarioId;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(columnDefinition="TIMESTAMP DEFAULT NOW()", nullable=false, insertable=false)
	public Date getData() {
		return data;
	}
	
	@Override
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long getId(){
		return this.id;
	}

	@Column(columnDefinition="text")
	public String getMensagem() {
		return mensagem;
	}

	@Column(length=30, name="usuario_desc")
	public String getUsuarioDesc() {
		return usuarioDesc;
	}

	@Column(name="usuario_id")
	public Long getUsuarioId() {
		return usuarioId;
	}

	@PostConstruct
	private void init() {
		//data = new Date();
	}

	public void setData(Date data) {
		this.data = data;
	}

	@Override
	public void setId(Long id){
		this.id = id;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public void setUsuarioDesc(String usuarioDesc) {
		this.usuarioDesc = usuarioDesc;
	}

	public void setUsuarioId(Long usuarioId) {
		this.usuarioId = usuarioId;
	}
}
