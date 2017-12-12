
package net.inforgyn.util;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;

public class RedimencionarImagemUtil {
    public byte[] redimensionar(InputStream imagem) throws Exception {
        byte[] bytesImagem = null;
        Graphics2D graphisImagem = null;
        ByteArrayOutputStream baos = null;
        try {
            //transforma InputStream em uma BufferedImage
            BufferedImage bufImagemLida = ImageIO.read(imagem);
            //cria imagem para
            //151 largura 189 altura
            BufferedImage imagemRedimensionada = new BufferedImage(170, 189, BufferedImage.TYPE_INT_RGB);
            //realiza o redimensionamento da imagem
            graphisImagem = imagemRedimensionada.createGraphics();
            graphisImagem.drawImage(bufImagemLida, 0, 0, 170, 189, null);
            baos = new ByteArrayOutputStream();
            //escreve a imagem no OutputStream
            ImageIO.write(imagemRedimensionada, "", baos);
            //transforma o OutputStream em array de bytes e retorna
            bytesImagem = baos.toByteArray();
        } catch (IOException e) {
            throw new Exception("Erro ao dimencionar imagem");
        } finally {
            //libera recursos
            if (graphisImagem != null) {
                graphisImagem.dispose();
            }
            if (baos != null) {
                try {
                    baos.close();
                } catch (IOException e) {
                }
            }
        }
        return bytesImagem;
    }
}
