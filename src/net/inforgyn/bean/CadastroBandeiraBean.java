package net.inforgyn.bean;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import net.inforgyn.model.BandeiraCartao;
import net.inforgyn.neg.BandeiraCartaoNeg;
import net.inforgyn.util.RedimencionarImagemUtil;
import net.inforgyn.util.faces.FacesUtil;

@Named
@ViewScoped
public class CadastroBandeiraBean implements CadastroBean, Serializable{

	private static final long serialVersionUID = 1L;

	@Inject	private BandeiraCartaoNeg bandeiraNeg;
	@Inject	private BandeiraCartao bandeira;
	@Inject private UploadImagemBean upload;
	@Inject private List<BandeiraCartao> bandeiras;
	private UploadedFile file;
	private StreamedContent image;
	
	@PostConstruct
	public void init(){
	}
	
	@Override
	public void novo() {
		bandeira = new BandeiraCartao();
	}

	public void fileUploadListener(FileUploadEvent e) {
        this.file = e.getFile();
        try {
        	InputStream stream = e.getFile().getInputstream();
        	
        	byte[] img = new RedimencionarImagemUtil().redimensionar(stream);
        	
        	stream = new ByteArrayInputStream(img);
        	System.out.println(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/"));
        	
        	image = new DefaultStreamedContent(stream, e.getFile().getContentType(), e.getFile().getFileName());
		} catch (Exception e1) {
			e1.printStackTrace();
		}        
    }

	public UploadedFile getFile() {
		return file;
	}

	public StreamedContent getImage() {
		StreamedContent sc = null;
		try {
			if(file != null)
			sc = new DefaultStreamedContent(file.getInputstream(), "image/jpeg");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sc;
	}

	@Override
	public void salvar() {
		if(bandeira.getId() == null){
			FacesContext context = FacesContext.getCurrentInstance();
			//upload = context.getApplication().evaluateExpressionGet(context, "#{uploadImagemBean}", UploadImagemBean.class);
			//bandeira.setArquivo(upload.getFile().getContents());
			//bandeira.setImagem(upload.getImage());
			//bandeira.setTipoArquivo(upload.getFile().getContentType());
			
			//ImagemUtil.dimensionarTamanho(220, 180, upload.getFile());
			bandeiraNeg.salvar(bandeira);
		    
			FacesUtil.infoMessageSimples("Bandeira cadastrada: "+this.bandeira.getDescricao());
			bandeiras.add(bandeira);
		}else {
			bandeira = bandeiraNeg.alterar(bandeira);
			FacesUtil.infoMessageSimples("Bandeira alterada: "+this.bandeira.getDescricao());
		}
		novo();
	}

	public void excluir(){
		bandeiraNeg.excluir(bandeira);
		bandeiras.remove(bandeira);
		FacesUtil.infoMessageSimples("Excluído: "+this.bandeira.getDescricao());
		novo();
	}
	
	

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	public void setImage(StreamedContent image) {
		this.image = image;
	}

	public BandeiraCartao getBandeira() {
		return bandeira;
	}

	public void setBandeira(BandeiraCartao bandeira) {
		this.bandeira = bandeira;
	}

	public List<BandeiraCartao> listarBandeiras(){
		return bandeiras;
	}
}
