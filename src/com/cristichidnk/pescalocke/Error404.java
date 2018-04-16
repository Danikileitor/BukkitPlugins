package com.cristichidnk.pescalocke;

public class Error404 extends RuntimeException {
	private static final long serialVersionUID = 6647502596600707177L;

	public Error404() {
		super("No encontrado");
	}

	public Error404(String string) {
		super(string);
	}

}
