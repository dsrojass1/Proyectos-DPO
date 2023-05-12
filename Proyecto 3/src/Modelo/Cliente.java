package Modelo;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class Cliente implements Serializable {
	protected String nombre;
	protected String documento;
	protected String email;
	protected String celular;
	protected ArrayList<Servicio> consumos;
	
	public Cliente(String nombre, String documento, String email, String celular) {
		this.nombre = nombre;
		this.documento = documento;
		this.email = email;
		this.celular = celular;
		this.consumos = new ArrayList<Servicio>();
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public String getDocumento() {
		return documento;
	}
	
	public String getEmail() {
		return email;
	}
	
	public String getCelular() {
		return celular;
	}
	
	public ArrayList<Servicio> getConsumos() {
		return consumos;
	}
	
	public String generarHistorial() {
		String historialCliente;
		historialCliente = this.nombre +"\t"+ this.documento +"\t"+ this.celular +"\t"+ this.email +
						"\n"+"Consumos:\n";
		for (Servicio consumo: consumos) {
			historialCliente += "ID: " +consumo.getId() + " // " + "Descripcion: " + consumo.getDescripcion() + " // " + "Precio: " +consumo.getPrecio() + "\n";
		}
		return historialCliente;
	}
}
