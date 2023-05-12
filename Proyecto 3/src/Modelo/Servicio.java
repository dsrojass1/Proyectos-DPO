package Modelo;

public interface Servicio {
	
	public abstract boolean registrarServicio(boolean pagoInmediato, boolean asignarAHabitacion, Huesped titular);
	
	public abstract boolean asignarServicioCliente(Cliente cliente);
	
	public abstract void registrarPago();
	
	public abstract String generarFactura();
	
	public abstract String getId();
	
	public abstract int getPrecio();
	
	public abstract String getDescripcion();
	
}
