package net.inforgyn.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;
import javax.servlet.ServletContext;

import org.primefaces.model.UploadedFile;


public class ImagemUtil {
	
	public static void uploadImagem(InputStream image, String name, String path) {
		String extension = name.substring(name.lastIndexOf(".") + 1);
		
		FacesContext aFacesContext = FacesContext.getCurrentInstance();
		ServletContext context = (ServletContext) aFacesContext
				.getExternalContext().getContext();

		String realPath = context.getRealPath("/")+path;
		
		try {
			BufferedImage img = ImageIO.read(image);

			ImageIO.write(img, extension, new File(realPath + name));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static BufferedImage getBufferedImage(UploadedFile file) {
		BufferedImage img = null;
		try {
			img = ImageIO.read(file.getInputstream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return img;
	}
	
	private static void teste(){
		FacesContext context = FacesContext.getCurrentInstance();    
	    final InputStream io = context.getExternalContext().getResourceAsStream("//resources//images//profile.gif"); 
	}
}