package net.inforgyn.model;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

import net.inforgyn.impl.EntityPersistence;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

@Entity
@Table(name="ctr_bandeira_cartao")
@NamedQuery(name="listarBandeirasCartao", query="from BandeiraCartao")
public class BandeiraCartao extends EntityPersistence{
	
	private static final long serialVersionUID = 1L;
	
	private byte[] arquivo;
	private String descricao;
	private StreamedContent imagem;
	private String tipoArquivo;
	private Usuario usuario;
	
	public BandeiraCartao() {
		super();
	}
	
	public BandeiraCartao(Long id, String descricao, byte[] arquivo) {
		super();
		this.id = id;
		this.descricao = descricao;
		this.arquivo = arquivo;
		this.imagem = imagem;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BandeiraCartao other = (BandeiraCartao) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Lob
	public byte[] getArquivo() {
		return arquivo;
	}
	
	@Column(length=15,nullable=false)
	public String getDescricao() {
		return descricao;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long getId(){
		return this.id;
	}

	@Transient
	public StreamedContent getImagem() {
		if(imagem == null && arquivo != null){
			InputStream is = new ByteArrayInputStream(arquivo);
			imagem = new DefaultStreamedContent(is, tipoArquivo, String.valueOf(System.currentTimeMillis()));
		}
		return imagem;
	}

	public String getTipoArquivo() {
		return tipoArquivo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((id == null) ? 0 : id.hashCode());
		return result;
	}

	public void setArquivo(byte[] arquivo) {
		this.arquivo = arquivo;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public void setId(Long id){
		this.id = id;
	}

	public void setImagem(StreamedContent imagem) {
		this.imagem = imagem;
	}

	public void setTipoArquivo(String tipoArquivo) {
		this.tipoArquivo = tipoArquivo;
	}

	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.DETACH, optional=false)
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
}
