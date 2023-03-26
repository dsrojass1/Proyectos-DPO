package logica;

import java.io.Serializable;
import java.util.ArrayList;

public class Grupo implements Serializable {
	private Huesped huesped;
	private ArrayList<Acompanante> acompanantes;
	
	public Grupo(Huesped huesped, ArrayList<Acompanante> acompanantes) {
		this.huesped = huesped;
		this.acompanantes = acompanantes;
	}

	public Huesped getHuesped() {
		return huesped;
	}

	public ArrayList<Acompanante> getAcompanantes() {
		return acompanantes;
	}	
	
	
}
