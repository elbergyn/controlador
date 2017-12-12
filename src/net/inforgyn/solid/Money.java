package net.inforgyn.solid;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;

import net.inforgyn.util.StringUtil;

public final class Money {
	private long value;
	private static Currency currency;
	private static Locale locale = Locale.getDefault();
	
	static{
		currency = Currency.getInstance(locale);
	}

	// métodos de criação
	public static Money valueOf(String value) {
		return valueOf(new BigDecimal(value));
	}
	
	public static Money valueOf(Integer value) {
		return valueOf(new BigDecimal(value));
	}
	
	public static Money valueOf(Double value) {
		return valueOf(new BigDecimal(value));
	}

	public static Money valueOf(BigDecimal value) {
		value = new BigDecimal(value.doubleValue()).setScale(2, RoundingMode.HALF_DOWN);
		return new Money(toLongRepresentation(value));
	}

	// métodos para redução de e para long
	private static long toLongRepresentation(BigDecimal value) {
		return value.movePointRight(currency.getDefaultFractionDigits())
				.longValue();
	}

	private static BigDecimal toBigRepresentation(long amount) {
		BigDecimal value = new BigDecimal(amount);
		return value.movePointLeft(currency.getDefaultFractionDigits());
	}

	// contrutor privado
	private Money(long value) {
		this.value = value;
	}

	public BigDecimal getBigDecimal() {
		return toBigRepresentation(value);
	}

	public Currency getCurrency() {
		return currency;
	}

	public Money somar(Money other) {
		return new Money(this.value + other.value);
	}
	
	public Money subtrair(Money other){
		return new Money(this.value - other.value);
	}
	
	public String getValorMoeda(){
		return NumberFormat.getCurrencyInstance().format(toBigRepresentation(this.value));
	}
	
	public static String valorMoeda(Object obj){
		if(StringUtil.isNotEmpty(obj.toString())){
			return NumberFormat.getCurrencyInstance().format(obj);
		}
		return "";
	}

	public Money multiplicar(Number factor) {
		BigDecimal bigFactor;
		if (factor instanceof BigDecimal) {
			bigFactor = (BigDecimal) factor;
		} if (factor instanceof Double){
			bigFactor = new BigDecimal(factor.doubleValue());
		} else {
			bigFactor = new BigDecimal(factor.toString());
		}

		long result = bigFactor.multiply(new BigDecimal(this.value))
				.longValue();

		return new Money(result);
	}

	public Money[] dividir (int parcelas){
       
		BigInteger bigValue =  BigInteger.valueOf(this.value);
		BigInteger[] result = bigValue.divideAndRemainder(BigInteger.valueOf(parcelas));
	       
       Money[] distribution = new Money[parcelas];
     
      // todos os valores são iguais
	    for(int i = 0; i < parcelas; i++){
	    	distribution[i] = new Money(result[0].longValue());
	    }
        
     // adiciona o resto no primeiro
      // substituindo o valor atual nessa posição
	    if(result[1].longValue() == 1L){
	    	distribution[0] = distribution[0].somar(new Money(result[1].longValue()));
	    }else{
	    	Integer sobra = result[1].intValue();
	    	for(int i = 0; i < sobra; i++){
	    		distribution[i] = distribution[i].somar(new Money(1L));
	    	}
	    }
	      
      return distribution;
  }
}