package net.inforgyn.teste;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

@Entity
@DiscriminatorValue("CLIENTE")
public class ClienteTeste extends UsuarioTeste{
	private List<Telefone> telefones;
	
	public ClienteTeste() {
		super();
	}
	
	@OneToMany(mappedBy="usuarioTeste", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	public List<Telefone> getTelefones() {
		return telefones;
	}

	public void setTelefones(List<Telefone> telefones) {
		this.telefones = telefones;
	}
}
