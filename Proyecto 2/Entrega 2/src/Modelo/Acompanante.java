package Modelo;

import java.io.Serializable;

public class Acompanante extends Cliente implements Serializable {
	private Huesped huesped;

	public Acompanante(String nombre, String documento, String email, String celular, Huesped huesped) {
		super(nombre, documento, email, celular);
		this.huesped = huesped;
	}
	
	public Huesped getHuesped() {
		return huesped;
	}
	
}
