package net.inforgyn.teste;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import net.inforgyn.persistence.JPAEntityManager;
 
public class JelasticConection {
	public static EntityManagerFactory factory = null;
	
	public static void main(String args[]){
		System.out.println("#####################################################");
		System.out.println("###############Iniciando persistence#################");
		factory = Persistence.createEntityManagerFactory("controlador_db");
		System.out.println("###############persistence iniciado##################");
		System.out.println("###############persistence iniciado##################");
	}   
}