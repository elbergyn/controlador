package net.inforgyn.teste;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="tb_telefones")
public class Telefone{	
	private Long id;
	private String numero;
	private UsuarioTeste usuario;
	
	public Telefone() {
		super();
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	@ManyToOne
	@JoinColumn(name="tel_usuario")
	public UsuarioTeste getUsuarioTeste() {
		return usuario;
	}

	public void setUsuarioTeste(UsuarioTeste usuario) {
		this.usuario = usuario;
	}
}
