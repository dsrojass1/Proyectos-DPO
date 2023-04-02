package logica;

import java.io.Serializable;
import java.time.LocalTime;

public class MenuRestaurante implements Servicio, Serializable {
	private String idMenu;
	private int precio;
	private String descripcion;
	private LocalTime horaInicio;
	private LocalTime horaFin;
	private boolean asignarAHabitacion;
		
	public MenuRestaurante(String idMenu, int precio, String descripcion, LocalTime horaInicio, LocalTime horaFin,
			boolean asignarAHabitacion) {
		this.idMenu = idMenu;
		this.precio = precio;
		this.descripcion = descripcion;
		this.horaInicio = horaInicio;
		this.horaFin = horaFin;
		this.asignarAHabitacion = asignarAHabitacion;
	}

	public String getId() {
		return idMenu;
	}



	public int getPrecio() {
		return precio;
	}



	public String getDescripcion() {
		return descripcion;
	}



	public LocalTime getHoraInicio() {
		return horaInicio;
	}



	public LocalTime getHoraFin() {
		return horaFin;
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
