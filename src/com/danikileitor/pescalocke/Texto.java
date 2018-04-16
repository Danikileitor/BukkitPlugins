package com.danikileitor.pescalocke;

public class Texto {

	private String clave;
	private String es;
	private String en;

	public Texto(String clave, String es, String en) {
		this.clave= clave;
		this.es = es;
		this.en = en;
	}

	public String getClave() {
		return clave;
	}

	public String getEnglish() {
		return en;
	}

	public String getSpanish() {
		return es;
	}

}
