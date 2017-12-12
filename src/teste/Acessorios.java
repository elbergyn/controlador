package teste;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import net.inforgyn.impl.EntityPersistence;

@Entity
public class Acessorios extends EntityPersistence{
	private static final long serialVersionUID = 1L;
	private String descricao;
	
	public Acessorios(String descricao) {
		super();
		this.descricao = descricao;
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long getId(){
		return this.id;
	}
	
	public void setId(Long id){
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}
