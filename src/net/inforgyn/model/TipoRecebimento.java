package net.inforgyn.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import net.inforgyn.impl.EntityPersistence;
import net.inforgyn.util.StringUtil;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name="pdv_tipo_recebimento")
public class TipoRecebimento extends EntityPersistence{
	
	private static final long serialVersionUID = 1L;

	private String descricao;
	
	public TipoRecebimento() {
		super();
	}

	public TipoRecebimento(Long id, String descricao, Usuario usuario) {
		super();
		this.id = id;
		this.descricao = descricao;
		this.usuario = usuario;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		
		TipoRecebimento other = (TipoRecebimento) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@NotBlank
	@Column(length=30)
	public String getDescricao() {
		return descricao;
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long getId(){
		return this.id;
	}

	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.DETACH, optional=false)
	public Usuario getUsuario() {
		return usuario;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	public void setDescricao(String descricao) {
		this.descricao = StringUtil.captalizar(descricao);
	}

	public void setId(Long id){
		this.id = id;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
}
