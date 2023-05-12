package Modelo;

import java.io.Serializable;

public class Cama implements Serializable {
	private String tamaño;
	private int capacidad;
	private boolean soloParaNinos;
	
	public Cama(String tamaño, int capacidad, boolean soloParaNinos) {
		this.tamaño = tamaño;
		this.capacidad = capacidad;
		this.soloParaNinos = soloParaNinos;
	}

	public String getTamaño() {
		return tamaño;
	}

	public int getCapacidad() {
		return capacidad;
	}

	public boolean isSoloParaNinos() {
		return soloParaNinos;
	}	
	
}
