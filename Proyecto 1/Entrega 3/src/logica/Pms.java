package logica;

import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import persistencia.Persistencia;
import view.Presentacion;

public class Pms implements Serializable {
	private ArrayList<Habitacion> inventarioHabitaciones;
	private ArrayList<Tarifa> tarifasVigentes;
	private ArrayList<Servicio> inventarioServicios;
	private ArrayList<Reserva> reservas;
	private ArrayList<Grupo> grupos;
	private ArrayList<String> facturas;
	private ArrayList<String> historialHuespedes;
	private ArrayList<Usuario> usuarios;
	private Scanner scanner;
	
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
		this.scanner = new Scanner(System.in);
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
		ArrayList<String> documentosClientes, boolean usarHTitular) {
		
		//variables necesarias para registro
		boolean done=false;
		Servicio srvc= null;
		Grupo grupo=getGrupoTD(documentoTitular);
		//Obtener servicio por id
		for(Servicio servicio : inventarioServicios) 
		{
			if(servicio.getId() == idServicio) 
			{
				srvc = servicio;
			}
		}
		//verificar que el servicio y el grupo existan
		if (srvc != null && grupo!=null) 
		{
			//asignar a clientes acompañantes
			ArrayList<Acompanante> acompanantes = grupo.getAcompanantes();
			for (Acompanante acompanante : acompanantes) 
			{
				//añadir consumo
				srvc.asignarServicioCliente(acompanante);
				//acompanante.getHuesped().consumos.add(srvc);
			}
			// añadir consumo al huesped si es necesario
			if (usarHTitular == true) 
			{
				srvc.asignarServicioCliente(grupo.getHuesped());
			}
			
			if(pagoInmediato || asignarAHabitacion) 
			{
				Reserva reserva = getReserva(grupo);
				if(pagoInmediato) 
				{
					//restar a la deuda total
					reserva.sumarDeuda(-(srvc.getPrecio()));
				}
				if(asignarAHabitacion) 
				{
					//obtener las habitaciones de la reserva
					ArrayList<Habitacion> sHabitaciones = reserva.getHabitacionesSeleccionadas();
					sHabitaciones.get(0).agregarServicio(srvc);
					
				}
				
			}
			done = true;
		}
		
		return done;
	}
	
	
	public String hacerReserva(String nombreTitular, String documentoTitular, String emailTitular,
			String celularTitular, int cantidadClientes, ArrayList<String> datosAcompañantes, ArrayList<LocalDate> nochesSeleccionadas) {
			//Crear las variables para la reserva
			Huesped huesped = new Huesped(nombreTitular, documentoTitular, emailTitular, celularTitular);
			
			ArrayList<Acompanante> acompanantes = null;
			
			if (cantidadClientes > 1) 
			{
				acompanantes = new ArrayList<Acompanante>();
				//Crear acompañantes
				for(String datoAcompanante : datosAcompañantes) 
				{
					String[] datos = datoAcompanante.split(";");
					String nombre = datos[0], documento = datos[1], email=datos[2], celular=datos[3], huespedT = datos[4];
					Acompanante a = new Acompanante(nombre, documento, email, celular, huesped);
					acompanantes.add(a);
				}
			}
			//crear el grupo
			 Grupo newGrupo = new Grupo(huesped, acompanantes);
			//seleccionar la(s) habitacion(s)
			 //obtener habitaciones disponibles
			 ArrayList<Habitacion> habitacionesDisponibles = getHabitacionesDisponibilidadFechas(nochesSeleccionadas);
			 //mostrar
			 String inventarioH = mostrarInventarioHabitaciones(habitacionesDisponibles);
			 System.out.println(inventarioH);
			 //Asignar habitacion para cada acompañante y para el titular
			 ArrayList<Habitacion> habitacionesTomadas = AsignarHabitaciones(acompanantes, huesped, habitacionesDisponibles, cantidadClientes);
			 //realizar reserva
			 Reserva newReserva = new Reserva(newGrupo, nochesSeleccionadas, habitacionesTomadas);
			 //agregar a inventarios
			 grupos.add(newGrupo);
			 reservas.add(newReserva);
			 
			 
		return "Se ha registrado el grupo con el titular " + newGrupo.getHuesped();
	}
	
	public boolean cancelarReserva(String documentoTitular) {
		// TODO Auto-generated method stub
		return true;
	}
	
	public boolean realizarCheckOut(String documentoTitular) {
		
		//Variables necesarias para ejecucion
		Reserva reserva = getReserva(documentoTitular);
		//Mostrar historial de grupo
		generarHistorialGrupo(documentoTitular);
		
		//Mostrar pagos, duedas, etc
		System.out.println(reserva.getDeuda());
		return true;
	}
	
	
	//-------------------------------------------------------------------
	//FUNCIONES DE BUSQUEDA-----------------------------------------
	//--------------------------------------------------------------
	
	public Grupo getGrupoTD (String documentoTitular) 
	//buscar un grupo dado el documento del huesped titular
	{
		Grupo grupo=null;
			
		//buscar grupo
			
		for(Grupo g : grupos) 
		{
			if(g.getHuesped().getDocumento() == documentoTitular) 
			{
				grupo = g;
			}
		}
		return grupo;
	}
		
	public Reserva getReserva (String documentoTitular) 
	//buscar una reserva dado el documento del huesped titular
	{
		Grupo grupo=getGrupoTD(documentoTitular);
		Reserva reserva = null;
		//buscar reserva
		if(grupo!=null) 
		{
			for(Reserva r : reservas) 
			{
				if(r.getGrupo().getHuesped().documento==documentoTitular) 
				{
					reserva=r;
				}
			}
		}
		return reserva;
	}
	
	public Reserva getReserva(Grupo grupo) 
	{
		//Obtener reserva teniendo en cuenta un grupo
		Reserva reserva=null;
		for(Reserva r : reservas) 
		{
			if(r.getGrupo().getHuesped().getDocumento().equals(grupo.getHuesped().getDocumento())) 
			{
				reserva=r;
			}
		}
		return reserva;
	}
	
	public ArrayList<LocalDate> sortListByDate(String date1, String date2) {
	    LocalDate start = LocalDate.parse(date1, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
	    LocalDate end = LocalDate.parse(date2, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

	    int days = (int) start.until(end, ChronoUnit.DAYS);

	    return (ArrayList<LocalDate>) Stream.iterate(start, d -> d.plusDays(1))
	      .limit(days+1)
	      .collect(Collectors.toList());
	}
	
	public ArrayList<Habitacion> getHabitacionesDisponibilidadFechas(ArrayList<LocalDate> rangoFechas)
	{
		ArrayList<Habitacion> habitacionesDisponibles = new ArrayList<Habitacion>();
		for(Habitacion h : inventarioHabitaciones) 
		{
			//verificar si la habitacion esta disponible en las fechas deseadas
			//obtener la reserva dependiendo del titular de la habitacion------
			//si no tiene titular esta libre
			if(h.getTitular() == null) 
			{
				habitacionesDisponibles.add(h);
			}else 
			{
				//verificar si estara disponible en las fechas esperadas
				//obtener reserva y sus fechas de estancia
				Reserva r=getReserva(h.getTitular().getDocumento());
				boolean disponible=true;
				for(LocalDate fecha : rangoFechas) 
				{
					if(r.getNochesSeleccionadas().contains(fecha) == true) 
					{
						disponible = false;
					}
				}
				if(disponible) 
				{
					habitacionesDisponibles.add(h);
				}
			}
		}
		return habitacionesDisponibles;
	}
	
	
	private ArrayList<Habitacion> AsignarHabitaciones(ArrayList<Acompanante> acompanantes, Huesped huesped,
			ArrayList<Habitacion> habitacionesD, int cantidadClientes) 
	{
		HashMap<String, Integer> controlCapacidadHabitaciones = new HashMap<String, Integer>();
		
		//crear una varible para tener en cuenta la capacidad de cada habitacion
		
		for(Habitacion h : habitacionesD) 
		{
			controlCapacidadHabitaciones.put(h.getId(), h.getCapacidad());
		}
		
		//Asignar las habitaciones para cada acompanante
		ArrayList<Habitacion> hTomadas= new ArrayList<Habitacion>();
		if(acompanantes != null || cantidadClientes>1)
		{
			for(Acompanante a : acompanantes) 
			{
				System.out.println(mostrarInventarioHabitaciones(habitacionesD));
				System.out.println("Digite ID de la habitacion que desea asignar: ");
				String id = Presentacion.scanner.next();
				//asignar a habitacion a los acompañantes
				Habitacion habita = buscarAsignarHabitacion(a, habitacionesD, id, controlCapacidadHabitaciones, huesped);
				if(habita != null) 
				{
					hTomadas.add(habita);
				}
			}
		}
		//para el huesped
		System.out.println("Digite ID de la habitacion que desea asignar para el titular: ");
		String id2 = Presentacion.scanner.next();
		Habitacion habita = buscarAsignarHabitacion(huesped, habitacionesD, id2, controlCapacidadHabitaciones, huesped);
		if(habita != null) 
		{
			hTomadas.add(habita);
		}
		return hTomadas;
	}
	
	private Habitacion buscarAsignarHabitacion(Cliente cliente, ArrayList<Habitacion> inventario, String id, HashMap<String, Integer> cH, Huesped titular) 
	{
		Habitacion habitacion=null;
		for(Habitacion h : inventario) 
		{
			
			if (id.equals(h.getId()))
			{
				h.asignarCliente(cliente);
				h.asignarTitular(titular);
				
				//cambiar la variable de control
				int c = cH.get(id)-1;
				cH.put(id, c);
				// remover la habitacion del inventario si la capacidad llega al maximo
				if(c <= 0) 
				{
					cH.remove(id);
				}
				//marcar la habitacion como ocupada
				
				habitacion = h;
			}
		}
		return habitacion;
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
	
	public String mostrarInventarioHabitaciones(ArrayList<Habitacion> hDisponibles) {
	    //es una sobrecarga para mostrar el inventario de habitaciones disponibles
		StringBuilder inventario = new StringBuilder();
	    inventario.append("----------------------------------------------------------------------------------------------------------------------------------------------\n");
	    inventario.append(String.format("| %-4s | %-35s | %-10s | %-33s | %-9s | %-6s | %-6s | %-6s | %-6s |\n",
	                    "Id", "Descripcion", "Tipo", "Ubicacion", "Capacidad", "Balcon", "Vista", "Cocina", "#Camas"));
	    inventario.append("----------------------------------------------------------------------------------------------------------------------------------------------\n");
	    for (Habitacion habitacionEnInventario: hDisponibles) {
	        inventario.append(String.format("| %4s | %-35s | %-10s | %-33s | %9d | %-6s | %-6s | %-6s | %6d |\n",
	                        habitacionEnInventario.getId(), habitacionEnInventario.getDescripcion(), habitacionEnInventario.getTipo(),
	                        habitacionEnInventario.getUbicacion(), habitacionEnInventario.getCapacidad(), 
	                        habitacionEnInventario.isBalcon() ? "Si" : "No", habitacionEnInventario.isVista() ? "Si" : "No",
	                        habitacionEnInventario.isCocinaIntegrada() ? "Si" : "No", habitacionEnInventario.getCamas().size()));
	        inventario.append("----------------------------------------------------------------------------------------------------------------------------------------------\n");
	    }
	    return inventario.toString();
	}
}


