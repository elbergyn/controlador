package teste;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import net.inforgyn.impl.EntityPersistence;

@Entity
public class Carro extends EntityPersistence{
	private static final long serialVersionUID = 1L;
	private String descricao;
	private List<Acessorios> acessorios;
		
	public Carro(String descricao, List<Acessorios> acessorios) {
		super();
		this.descricao = descricao;
		this.acessorios = acessorios;
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
	
	@ManyToMany
	public List<Acessorios> getAcessorios() {
		return acessorios;
	}
	public void setAcessorios(List<Acessorios> acessorios) {
		this.acessorios = acessorios;
	}
	
	
}
