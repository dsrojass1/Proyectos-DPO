package logica;

import java.io.Serializable;

public class GuiaTuristico implements Servicio, Serializable {
	private String id;
	private int precioPorGrupo;
	private String descripcion;
	private boolean asignarAHabitacion;

	public GuiaTuristico(String id, int precioPorGrupo, String descripcion, boolean asignarAHabitacion) {
		this.id = id;
		this.precioPorGrupo = precioPorGrupo;
		this.descripcion = descripcion;
		this.asignarAHabitacion = asignarAHabitacion;
	}
	
	

	public String getId() {
		return id;
	}



	public int getPrecio() {
		return precioPorGrupo;
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
	
}
