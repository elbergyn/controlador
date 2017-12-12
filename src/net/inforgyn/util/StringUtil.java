package net.inforgyn.util;


public class StringUtil {
	public static String captalizar(String texto){
		if(null == texto){
			return "";
		}
		
		StringBuilder result = new StringBuilder();
		String palavras[] = texto.trim().split(" ");
		
		for(String palavra : palavras){
			result.append(palavra.replaceFirst(palavra.substring(0, 1), palavra.substring(0, 1).toUpperCase()));
			result.append(" ");
		}
		
		return result.toString().trim();
	}
	
	public static boolean isEmpty(String texto){
		return null == texto || texto.isEmpty();
	}
	
	public static boolean isNotEmpty(String texto){
		return !isEmpty(texto);
	}
}
