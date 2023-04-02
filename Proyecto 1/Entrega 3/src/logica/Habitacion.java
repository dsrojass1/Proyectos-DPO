package logica;

import java.io.Serializable;
import java.util.ArrayList;

public class Habitacion implements Servicio, Serializable {
	private String id;
	private ArrayList<Tarifa> tarifas;
	private String descripcion;
	private String tipo;
	private String ubicacion;
	private int capacidad;
	private boolean balcon;
	private boolean vista;
	private boolean cocinaIntegrada;
	private ArrayList<Cama> camas;
	private ArrayList<Servicio> serviciosAsignados;
	private Huesped titular;
	private ArrayList<Cliente> hosepedados;
	
	public Habitacion(String id, String descripcion, String tipo, String ubicacion,
			int capacidad, boolean balcon, boolean vista, boolean cocinaIntegrada, ArrayList<Cama> camas) {
		this.id = id;
		this.tarifas = new ArrayList<Tarifa>();
		this.descripcion = descripcion;
		this.tipo = tipo;
		this.ubicacion = ubicacion;
		this.capacidad = capacidad;
		this.balcon = balcon;
		this.vista = vista;
		this.cocinaIntegrada = cocinaIntegrada;
		this.camas = camas;
		this.serviciosAsignados = new ArrayList<Servicio>();
		this.hosepedados = new ArrayList<Cliente>();
	}

	public String getId() {
		return id;
	}



	public ArrayList<Tarifa> getTarifas() {
		return tarifas;
	}



	public String getDescripcion() {
		return descripcion;
	}



	public String getTipo() {
		return tipo;
	}



	public String getUbicacion() {
		return ubicacion;
	}



	public int getCapacidad() {
		return capacidad;
	}



	public boolean isBalcon() {
		return balcon;
	}



	public boolean isVista() {
		return vista;
	}



	public boolean isCocinaIntegrada() {
		return cocinaIntegrada;
	}



	public ArrayList<Cama> getCamas() {
		return camas;
	}



	public ArrayList<Servicio> getServiciosAsignados() {
		return serviciosAsignados;
	}



	public Huesped getTitular() {
		return titular;
	}



	public ArrayList<Cliente> getHosepedados() {
		return hosepedados;
	}
	
	public void agregarTarifa(Tarifa tarifa) {
		this.tarifas.add(tarifa);
	}
	
	public void agregarCama(Cama cama) {
		this.camas.add(cama);
	}
	
	public void agregarServicio(Servicio servicio) {
		this.serviciosAsignados.add(servicio);
	}
	
	public void hacerCheckOut() {
		
	}
	
	public boolean asignarCliente(Cliente hospedado) {
		if (this.hosepedados.contains(hospedado)) {
			return false;
		}
		else {
			this.hosepedados.add(hospedado);
			return true;
		}
	}
	
	public boolean asignarTitular(Huesped titular) {
		
		if (this.titular != null && this.titular.getDocumento().equalsIgnoreCase(titular.getDocumento())) {
			return false;
		}
		else {
			this.titular = titular;
			return true;
		}
	}



	@Override
	public boolean registrarServicio(boolean pagoInmediato, boolean asignarAHabitacion, Huesped titular) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean asignarServicioCliente(Cliente cliente) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void registrarPago() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String generarFactura() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public int getPrecio() {
		return 0;
	}
	
}
