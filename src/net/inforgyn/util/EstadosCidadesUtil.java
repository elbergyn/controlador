package net.inforgyn.util;

import java.util.List;
import javax.faces.model.SelectItem;

public class EstadosCidadesUtil {
    public static SelectItem[] getListTipoLogradouro() {
        SelectItem[] itens = new SelectItem[5];
        itens[0] = new SelectItem("Rua");
        itens[1] = new SelectItem("Alameda");
        itens[2] = new SelectItem("Avenida");
        itens[3] = new SelectItem("Rodovia");
        itens[4] = new SelectItem("Viela");
        return itens;
    }
    
    /*public SelectItem[] getListEstado() {
    	        try {
            EstadoDao objDAO = new EstadoDao();
            List<Estado> lista = objDAO.listar();
            SelectItem[] itens = new SelectItem[lista.size()];
            int i = 0;
            for (Estado m : lista) {
                itens[i++] = new SelectItem(m, m.getDescricao() + " - " + m.getUf());
            }
            return itens;
        } catch (Exception e) {
            return null;
        }
    }*/
}
