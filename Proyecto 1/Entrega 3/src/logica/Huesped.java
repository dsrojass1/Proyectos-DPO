package logica;

import java.io.Serializable;

public class Huesped extends Cliente implements Serializable {

	public Huesped(String nombre, String documento, String email, String celular) {
		super(nombre, documento, email, celular);
	}
	
}
