package net.inforgyn.constante;

public enum PeriodoPagamentoEnum {
	DEZ(10), 
	QUINZE(15), 
	VINTE(20), 
	TRINTA(30);
	
	private final Integer periodo;

	private PeriodoPagamentoEnum(Integer periodo){
		this.periodo = periodo;
	}
	
	public Integer getPeriodo(){
		return this.periodo;
	}
}
