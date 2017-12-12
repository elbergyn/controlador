package net.inforgyn.util;

import javax.persistence.Persistence;

public class GerarSchema {
	public static void main(String[] args) {
	    Persistence.generateSchema("controlador_persistence", null);
	  }
}
