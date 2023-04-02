package logica;

import java.io.Serializable;

public class Spa implements Servicio, Serializable {
	private String id;
	private int precioPorPersona;
	private String descripcion;
	private boolean asignarAHabitacion;

	public Spa(String id, int precioPorPersona, String descripcion, boolean asignarAHabitacion) {
		super();
		this.id = id;
		this.precioPorPersona = precioPorPersona;
		this.descripcion = descripcion;
		this.asignarAHabitacion = asignarAHabitacion;
	}

	public String getId() {
		return id;
	}



	public int getPrecio() {
		return precioPorPersona;
	}



	public String getDescripcion() {
		return descripcion;
	}



	public boolean isAsignarAHabitacion() {
		return asignarAHabitacion;
	}



	@Override
	public boolean registrarServicio(boolean pagoInmediato, boolean asignarAHabitacion, Huesped titular) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean asignarServicioCliente(Cliente cliente) {
		try {
			cliente.consumos.add(this);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
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
}
