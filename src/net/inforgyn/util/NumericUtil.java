package net.inforgyn.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class NumericUtil {
	public static String doubleDoisDecimais(Double valor){
		DecimalFormat df = new DecimalFormat("0.00");
		return df.format(valor);
	}
	
	public static String moeda(BigDecimal valor){
		NumberFormat nf = NumberFormat.getCurrencyInstance();  
		String formatado = "";
		try{
			formatado = nf.format (valor);
		}catch(Exception e){
		}
		return formatado;
	}
}
