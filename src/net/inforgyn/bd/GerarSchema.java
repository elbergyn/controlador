package net.inforgyn.bd;

import java.util.Properties;

import javax.persistence.Persistence;

import org.hibernate.jpa.AvailableSettings;
import org.hibernate.tool.hbm2ddl.SchemaExport;

public class GerarSchema {

	/**através de arquivo cfg**/
    /*public static void main(String[] args) {
        AnnotationConfiguration cfg = new AnnotationConfiguration().configure();
        try {
            new GerarSchema().create(cfg);
            System.out.println("finalizado a exportação!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/	
	
	public static void main(String[] args) {
        
        try {
        	//Persistence.generateSchema("controlador_db", null) ;
        	
        	final Properties prop = new Properties();
        	prop.setProperty(org.hibernate.cfg.AvailableSettings.HBM2DDL_AUTO, "");
        	Persistence.generateSchema("controlador_db", prop);
        	
        	System.out.println("Finalizado");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*private void create(AnnotationConfiguration cfg) {
        String path = getClass().getResource("/").getPath();
        //path netbeans
        //path = path.replace("%20", " ").replace("build/web/WEB-INF/classes", "src/java/gerenciador/bd");
        //path eclipse
        path = path.replace("%20", " ").replace("build/classes", "src/net/inforgyn/bd");
        path += "script_temp.sql";
        //JpaSchemaGenerator.
        
        new SchemaExport(cfg).setOutputFile(path).setDelimiter(";").create(true, true);
    }
    
    public String path(){
    	String path = getClass().getResource("/").getPath();
    	path = path.replace("%20", " ").replace("build/classes", "src/net/inforgyn/bd");
    	System.out.println("Finalizado");
    	return path;
    }*/
}
