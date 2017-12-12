package net.inforgyn.bean;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import net.inforgyn.util.RedimencionarImagemUtil;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

@ManagedBean
@SessionScoped
public class UploadImagemBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private UploadedFile file;
	private StreamedContent image;

	public void fileUploadListener(FileUploadEvent e) {
        this.file = e.getFile();
        try {
        	InputStream stream = e.getFile().getInputstream();
        	
        	byte[] img = new RedimencionarImagemUtil().redimensionar(stream);
        	
        	stream = new ByteArrayInputStream(img);
        	System.out.println(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/"));
        	
        	this.image = new DefaultStreamedContent(stream, e.getFile().getContentType(), e.getFile().getFileName());
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
}
