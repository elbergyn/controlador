package net.inforgyn.constante;

public enum LocalImagemEnum {
	BANDEIRACARTAO("/resources/images/bandeira");
	
	private final String local;
	
	private LocalImagemEnum(String local){
		this.local = local;
	}
	
	public String getLocal(){
		return this.local;
	}
}
