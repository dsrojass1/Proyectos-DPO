package logica;

import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import persistencia.Persistencia;

public class Pms implements Serializable {
	private ArrayList<Habitacion> inventarioHabitaciones;
	private ArrayList<Tarifa> tarifasVigentes;
	private ArrayList<Servicio> inventarioServicios;
	private ArrayList<Reserva> reservas;
	private ArrayList<Grupo> grupos;
	private ArrayList<String> facturas;
	private ArrayList<String> historialHuespedes;
	private ArrayList<Usuario> usuarios;
	
	private Persistencia persistencia;

	public Pms() {
		this.inventarioHabitaciones = new ArrayList<Habitacion>();
		this.tarifasVigentes = new ArrayList<Tarifa>();
		this.inventarioServicios = new ArrayList<Servicio>();
		this.reservas = new ArrayList<Reserva>();
		this.grupos = new ArrayList<Grupo>();
		this.facturas = new ArrayList<String>();
		this.historialHuespedes = new ArrayList<String>();
		this.usuarios = new ArrayList<Usuario>();
		this.persistencia = new Persistencia();
		
	}
	
	
	
	//---------------------------------------------------------------------------------------------------------------------
	// SERVICIOS -------------------------------------------------------------------------------------------------------
	//---------------------------------------------------------------------------------------------------------------------
	public String generarHistorialGrupo(String idTitular) {
		String historial = "No se ha encontrado ningún grupo registrado a nombre del titular";
		for(Grupo grupoRegistrado: this.grupos) {
			if (grupoRegistrado.getHuesped().getDocumento().equalsIgnoreCase(idTitular)) {
				historial = "Huesped: \n" + grupoRegistrado.getHuesped().generarHistorial();
				historial += "Acompañantes: \n";
				for (Acompanante acompanante: grupoRegistrado.getAcompanantes()) {
					historial += acompanante.generarHistorial();
				}
			}
		}
		return historial;
	}
	
	public boolean registrarServicio(String idServicio, boolean pagoInmediato, boolean asignarAHabitacion, String documentoTitular,
			ArrayList<String> documentosClientes) {
		// TODO Auto-generated method stub
		return true;
	}
	
	public String hacerReserva(String nombreTitular, String documentoTitular, String emailTitular,
			String celularTitular, int cantidadClientes, ArrayList<String> datosAcompañantes, ArrayList<LocalDate> nochesSeleccionadas) {
			Huesped huesped = new Huesped(nombreTitular, documentoTitular, emailTitular, celularTitular);
			String[] datosacompanantes= new String[cantidadClientes * 5];
			/* CREO QUE ESTO YA NO HACE FALTA
			for(int i=1; i < cantidadClientes; i++) {
				
			}
			*/
			
		return "";
	}
	
	public boolean cancelarReserva(String documentoTitular) {
		// TODO Auto-generated method stub
		return true;
	}
	
	public boolean realizarCheckOut(String documentoTitular) {
		// TODO Auto-generated method stub
		return true;
	}
	
	
	//---------------------------------------------------------------------------------------------------------------------
	// CREAR --------------------------------------------------------------------------------------------------------------
	//---------------------------------------------------------------------------------------------------------------------
	public boolean crearHabitacion(String id, String descripcion, String tipo, String ubicacion, int capacidad, boolean balcon, boolean vista,
			boolean cocinaIntegrada, ArrayList<String> camas) {
		for (Habitacion habitacionEnInventario: this.inventarioHabitaciones) {
			if(habitacionEnInventario.getId().equals(id)) {
				return false;
			}
		}
		
		ArrayList<Cama> nuevasCamas = new ArrayList<Cama>();
		for (String strCama: camas) {
			String[] atributos = strCama.split(";");
			Cama nuevaCama = new Cama(atributos[0], Integer.parseInt(atributos[1]), Boolean.parseBoolean(atributos[2]));

			nuevasCamas.add(nuevaCama);
		}
		
		Habitacion nuevaHabitacion = new Habitacion(id, descripcion, tipo, ubicacion, capacidad, balcon, vista, cocinaIntegrada, nuevasCamas);
		this.inventarioHabitaciones.add(nuevaHabitacion);
		return true;
	}
	
	public boolean crearTarifa(int precio, String tipo, String fechaInicioVigencia, String fechaFinVigencia, String diasDeLaSemana) {
		boolean rtn = true;
		String[] arregloFechaInicio = fechaInicioVigencia.split("/");
		LocalDate fechaInicio = LocalDate.of(Integer.parseInt(arregloFechaInicio[0]), Integer.parseInt(arregloFechaInicio[1]), Integer.parseInt(arregloFechaInicio[2]));
		String[] arregloFechaFin = fechaFinVigencia.split("/");
		LocalDate fechaFin = LocalDate.of(Integer.parseInt(arregloFechaFin[0]), Integer.parseInt(arregloFechaFin[1]), Integer.parseInt(arregloFechaFin[2]));
		for (Tarifa tarifaRegistrada: this.tarifasVigentes) {
			if (tarifaRegistrada.getFechaInicioVigencia().isEqual(fechaInicio) && tarifaRegistrada.getFechaFinVigencia().isEqual(fechaFin)) {
				rtn = false;
			}
		}
		String[] arregloDiasDeLaSemana = diasDeLaSemana.trim().split("\\s*,\\s*");
		ArrayList<String> diasVigencia = new ArrayList<String>(Arrays.asList(arregloDiasDeLaSemana));
		Tarifa nuevaTarifa = new Tarifa(precio, tipo, fechaInicio, fechaFin, diasVigencia);
		this.tarifasVigentes.add(nuevaTarifa);
		
		for (Habitacion habitacionRegistrada: this.inventarioHabitaciones) {
			if (habitacionRegistrada.getTipo().equalsIgnoreCase(tipo)) {
				habitacionRegistrada.agregarTarifa(nuevaTarifa);
			}
		}
		return rtn;
	}
	
	public boolean crearMenuRestaurante(String idMenu, int precio, String descripcion, String horaInicio, String horaFin,
			boolean llevarAHabitacion) {
		LocalTime inicio = LocalTime.parse(horaInicio);
		LocalTime fin = LocalTime.parse(horaFin);
		for (Servicio servicioRegistrado: this.inventarioServicios) {
			if (servicioRegistrado.getId().equalsIgnoreCase(idMenu)) {
				return false;
			}
		}
		MenuRestaurante nuevoMenu = new MenuRestaurante(idMenu, precio, descripcion, inicio, fin, llevarAHabitacion);
		this.inventarioServicios.add(nuevoMenu);
		return true;
	}
	
	public boolean crearServicioSpa(String idSpa, int precio, String descripcion, boolean asignarAHabitacion) {
		for (Servicio servicioRegistrado: this.inventarioServicios) {
			if (servicioRegistrado.getId().equalsIgnoreCase(idSpa)) {
				return false;
			}
		}
		Spa nuevoSpa = new Spa(idSpa, precio, descripcion, asignarAHabitacion);
		this.inventarioServicios.add(nuevoSpa);
		return true;
	}
	
	public boolean crearServicioGuia(String idGuia, int precio, String descripcion, boolean asignarAHabitacion) {
		for (Servicio servicioRegistrado: this.inventarioServicios) {
			if (servicioRegistrado.getId().equalsIgnoreCase(idGuia)) {
				return false;
			}
		}
		Spa nuevoSpa = new Spa(idGuia, precio, descripcion, asignarAHabitacion);
		this.inventarioServicios.add(nuevoSpa);
		return true;
	}
	
	
	
	//---------------------------------------------------------------------------------------------------------------------
	// CARGA DE ARCHIVOS -------------------------------------------------------------------------------------------------------
	//---------------------------------------------------------------------------------------------------------------------
	public void cargarDatosDeArchivo() {	
		
		//CARGA DE HABITACIONES
		ArrayList<String> lineasInventarioHabitaciones = persistencia.leer("inventarioHabitaciones");
		if (!lineasInventarioHabitaciones.isEmpty()) {
			for (String lineaInventarioHabitacion: lineasInventarioHabitaciones) {
				String[] atributosHabitacion = lineaInventarioHabitacion.split(";");
				
				ArrayList<Cama> camasPersistidas = new ArrayList<Cama>();
				String[] camas = atributosHabitacion[8].split(",");
				for (int i = 0; i < camas.length; i++) {
					String[] atributosCamas = camas[i].split("-");
					Cama camaPersistida = new Cama(atributosCamas[0], Integer.parseInt(atributosCamas[1]), Boolean.parseBoolean(atributosCamas[2]));
					camasPersistidas.add(camaPersistida);			
				}
				
				Habitacion habitacionPersistida = new Habitacion(atributosHabitacion[0], atributosHabitacion[1], atributosHabitacion[2], 
						atributosHabitacion[3], Integer.parseInt(atributosHabitacion[4]), Boolean.parseBoolean(atributosHabitacion[5]),
						Boolean.parseBoolean(atributosHabitacion[6]), Boolean.parseBoolean(atributosHabitacion[7]), camasPersistidas);
				this.inventarioHabitaciones.add(habitacionPersistida);
			}
		}
	}
		
	
	
	
	//---------------------------------------------------------------------------------------------------------------------
	// INICIO DE SESION Y REGISTRO ----------------------------------------------------------------------------------------
	//---------------------------------------------------------------------------------------------------------------------
	public String getAutenticacionDeUsuario(String loginUsuario, String contrasena) {
		for (Usuario usuarioRegistrado: this.usuarios) {
			if (usuarioRegistrado.getLogin().equals(loginUsuario) && usuarioRegistrado.getConstrasena().equals(contrasena)) {
				return usuarioRegistrado.getRol();
			}
		}
		return "Usuario no encontrado";
	}
	
	public boolean registrarUsuario(String loginUsuario, String contrasena, String rol) {
		for (Usuario usuarioRegistrado: this.usuarios) {
			if (usuarioRegistrado.getLogin().equals(loginUsuario)) {
				return false;
			}
		}
		
		Usuario nuevoUsuario = new Usuario(loginUsuario, contrasena, rol);
		this.usuarios.add(nuevoUsuario);
		
		return true;
	}
	
	public boolean eliminarUsuario(String login) {
	    for (Usuario usuarioRegistrado: this.usuarios) {
	        if (usuarioRegistrado.getLogin().equals(login)) {
	            this.usuarios.remove(usuarioRegistrado);
	            return true;
	        }
	    }
	    return false;
	}
	
	//---------------------------------------------------------------------------------------------------------------------
	// IMPRESIÓN DE INVENTARIOS  ----------------------------------------------------------------------------------------
	//---------------------------------------------------------------------------------------------------------------------
	public String notificacionEstadoDeCarga() {
		//NOTIFICA EL NUMERO DE ELEMENTOS EN LOS ATRIBUTOS PARA VERIFICAR LA PERSISTENCIA
		String notificacion = "Habitaciones en Inventario: "+ this.inventarioHabitaciones.size() +"\n"
							+ "Tarifas vigentes: "+ this.tarifasVigentes.size()+"\n"
							+ "Servicios en inventario: "+ this.inventarioServicios.size()+"\n"
							+ "Grupos: "+ this.grupos.size()+"\n"
							+ "Facturas: "+ this.facturas.size()+"\n"
							+ "Historiales de húespedes: "+ this.historialHuespedes.size()+"\n"
							+ "Usuarios registrados en el sistema: "+this.usuarios.size()+"\n"
							+ "----------------------------------------------------------------------";
		return notificacion;
	}
	
	public String mostrarInventarioHabitaciones() {
	    StringBuilder inventario = new StringBuilder();
	    inventario.append("----------------------------------------------------------------------------------------------------------------------------------------------\n");
	    inventario.append(String.format("| %-4s | %-35s | %-10s | %-33s | %-9s | %-6s | %-6s | %-6s | %-6s |\n",
	                    "Id", "Descripcion", "Tipo", "Ubicacion", "Capacidad", "Balcon", "Vista", "Cocina", "#Camas"));
	    inventario.append("----------------------------------------------------------------------------------------------------------------------------------------------\n");
	    for (Habitacion habitacionEnInventario: this.inventarioHabitaciones) {
	        inventario.append(String.format("| %4s | %-35s | %-10s | %-33s | %9d | %-6s | %-6s | %-6s | %6d |\n",
	                        habitacionEnInventario.getId(), habitacionEnInventario.getDescripcion(), habitacionEnInventario.getTipo(),
	                        habitacionEnInventario.getUbicacion(), habitacionEnInventario.getCapacidad(), 
	                        habitacionEnInventario.isBalcon() ? "Si" : "No", habitacionEnInventario.isVista() ? "Si" : "No",
	                        habitacionEnInventario.isCocinaIntegrada() ? "Si" : "No", habitacionEnInventario.getCamas().size()));
	        inventario.append("----------------------------------------------------------------------------------------------------------------------------------------------\n");
	    }
	    return inventario.toString();
	}


	
	public String mostrarInventarioServicios() {
		StringBuilder inventario = new StringBuilder();
		inventario.append("---------------------------------------------------------\n");
		inventario.append(String.format("| %4s | %10s | %-33s |\n", "Id", "Precio", "Descripcion"));
		inventario.append("---------------------------------------------------------\n");
		for (Servicio servicioEnInventario: this.inventarioServicios) {
			inventario.append(String.format("| %4s | %10d | %-33s |\n", servicioEnInventario.getId(), servicioEnInventario.getPrecio(), servicioEnInventario.getDescripcion()));
			inventario.append("---------------------------------------------------------\n");
		}
		return inventario.toString();
	}
	
}


