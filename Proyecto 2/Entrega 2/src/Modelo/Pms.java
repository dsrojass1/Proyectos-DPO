package Modelo;

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
import java.util.Observable;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import persistencia.Persistencia;
import Vista.Presentacion;

public class Pms extends Observable implements Serializable {
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
				if(grupoRegistrado.getAcompanantes()!= null) {
				for (Acompanante acompanante: grupoRegistrado.getAcompanantes()) {
					historial += acompanante.generarHistorial();
				}
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
			if(servicio.getId().equals(idServicio)) 
			{
				srvc = servicio;
			}
		}
		//verificar que el servicio y el grupo existan
		if (srvc != null && grupo!=null) 
		{
			if(documentosClientes.size()>0)
			{
				//asignar a clientes acompañantes
				ArrayList<Acompanante> acompanantes = getAcompañantesbyDocuments(documentosClientes, grupo);
				if(acompanantes!= null)
				{
					for (Acompanante acompanante : acompanantes) 
					{
						//añadir consumo
						srvc.asignarServicioCliente(acompanante);
						//acompanante.getHuesped().consumos.add(srvc);
					}
				}
			}
			// añadir consumo al huesped si es necesario
			if (usarHTitular == true) 
			{
				srvc.asignarServicioCliente(grupo.getHuesped());
			}
			
			Reserva reserva = getReserva(grupo);
			reserva.sumarDeuda(srvc.getPrecio());
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
				
			done = true;
		}
		
		return done;
	}
	
	
	public String hacerReserva(Huesped huesped, int cantidadClientes, ArrayList<Acompanante> acompanantes, ArrayList<LocalDate> nochesSeleccionadas, ArrayList<Habitacion> habitacionesTomadas) {
			//Crear las variables para la reserva
			//Huesped huesped = new Huesped(nombreTitular, documentoTitular, emailTitular, celularTitular);
			
			//ArrayList<Acompanante> acompanantes = CrearAcompanantes(datosAcompañantes, cantidadClientes, huesped);
			
			//crear el grupo
			 Grupo newGrupo = new Grupo(huesped, acompanantes);
			//seleccionar la(s) habitacion(s)
			 //obtener habitaciones disponibles
			 //ArrayList<Habitacion> habitacionesDisponibles = getHabitacionesDisponibilidadFechas(nochesSeleccionadas);
			 //mostrar
			 //no es necesario
			 //String inventarioH = mostrarInventarioHabitaciones(habitacionesDisponibles);
			 //System.out.println(inventarioH);
			 //Asignar habitacion para cada acompañante y para el titular
			 //habitacionesTomadas; // = AsignarHabitaciones(acompanantes, huesped, habitacionesDisponibles, cantidadClientes);
			 //realizar reserva
			 if(habitacionesTomadas.isEmpty() == false)
			 	{
					 Reserva newReserva = new Reserva(newGrupo, nochesSeleccionadas, habitacionesTomadas);
					 //agregar a inventarios
					 grupos.add(newGrupo);
					 reservas.add(newReserva);
					 return "Se ha registrado el grupo con el titular " + newGrupo.getHuesped().getNombre();
			 	}
			 else 
			 {
				 return "No se ha podido hacer el registro";
			 }
		
	}
	
	public ArrayList<Acompanante> CrearAcompanantes(ArrayList<String> datosAcompañantes,int cantidadClientes, Huesped huesped)
	{
		ArrayList<Acompanante> acompanantes = new ArrayList<Acompanante>();
		if (cantidadClientes > 0) 
		{
			//Crear acompañantes
			for(String datoAcompanante : datosAcompañantes) 
			{
				String[] datos = datoAcompanante.split(";");
				String nombre = datos[0], documento = datos[1], email=datos[2], celular=datos[3];
				Acompanante a = new Acompanante(nombre, documento, email, celular, huesped);
				acompanantes.add(a);
			}
			return acompanantes;
		}else 
		{
			return null;
		}
	}
	
	public String cancelarReserva(String documentoTitular, int diasUsados) {
		//Variables necesarias para ejecucion
				Reserva reserva = getReserva(documentoTitular);
				if(reserva !=null)
				{
					Grupo grupo = reserva.getGrupo();
					//Mostrar historial de grupo
					String historialGrupo=generarHistorialGrupo(documentoTitular);
					//System.out.println(historialGrupo);
					//Mostrar pagos, duedas, etc
					
					//System.out.println("El monto que le falta por pagar es: ");
					//System.out.println(reserva.getDeuda()+getValorHabitaciones(reserva, diasUsados));
					int pay =reserva.getDeuda()+getValorHabitaciones(reserva, diasUsados);
					String valor="\nEl monto que le falta por pagar es:\n"+pay;
					
					//agregar al historial de huespedes
					this.historialHuespedes.add(historialGrupo);
					//eliminar datos de las habitaciones
					for(Habitacion habb : reserva.getHabitacionesSeleccionadas()) 
					{
						habb.hacerCheckOut();
					}
					
					//eliminar los grupo del inventario de grupos
					grupos.remove(grupo);
					//eliminar reserva
					reservas.remove(reserva);
					return historialGrupo+valor;
				}
				
				return "Hubo un error";
	}
	
	public String realizarCheckOut(String documentoTitular) {
		
		//Variables necesarias para ejecucion
		Reserva reserva = getReserva(documentoTitular);
		if(reserva !=null)
		{
			Grupo grupo = reserva.getGrupo();
			//Mostrar historial de grupo
			String historialGrupo=generarHistorialGrupo(documentoTitular);
			//System.out.println(historialGrupo);
			//Mostrar pagos, duedas, etc
			int pay = reserva.getDeuda()+getValorHabitaciones(reserva, reserva.getNochesSeleccionadas().size());
			String valor="\nEl monto que le falta por pagar es:\n"+pay;
			
			//agregar al historial de huespedes
			this.historialHuespedes.add(historialGrupo);
			
			//eliminar datos de las habitaciones
			for(Habitacion habb : reserva.getHabitacionesSeleccionadas()) 
			{
				habb.hacerCheckOut();
			}
			//eliminar los grupo del inventario de grupos
			grupos.remove(grupo);
			//eliminar reserva
			reservas.remove(reserva);
			return historialGrupo+valor;
		}
		
		return "Hubo un error";
	}
	
	
	//-------------------------------------------------------------------
	//FUNCIONES DE BUSQUEDA-----------------------------------------
	//--------------------------------------------------------------
	
	public int getValorHabitaciones(Reserva reserva, int nDias) 
	{
		int valor=0;
		for(Habitacion habitacion : reserva.getHabitacionesSeleccionadas()) 
		{
			valor+=habitacion.getPrecio()*nDias;
		}
		return valor;
	}
	
	public ArrayList<Acompanante> getAcompañantesbyDocuments(ArrayList<String> Documentos, Grupo grupo)
	{
		ArrayList<Acompanante> groupAcompanates=grupo.getAcompanantes();
		if(Documentos.size()>0 && groupAcompanates!=null)
		{
			ArrayList<Acompanante> acompananteByID=new ArrayList<Acompanante>();
			for(Acompanante acompanante : groupAcompanates) 
			{
				for(String documento : Documentos) 
				{
					if(acompanante.getDocumento().equals(documento)) 
					{
						acompananteByID.add(acompanante);
					}
				}
			}
			return acompananteByID;
		}else 
		{
			return null;
		}
	}
	
	public Grupo getGrupoTD (String documentoTitular) 
	//buscar un grupo dado el documento del huesped titular
	{
		Grupo grupo=null;
			
		//buscar grupo
			
		for(Grupo g : grupos) 
		{
			if(g.getHuesped().getDocumento().equals(documentoTitular)) 
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
				if(r.getGrupo().getHuesped().documento.equals(documentoTitular)) 
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
	    //Obtener un arrylits de fechas entre 2 fechas elegdas
		LocalDate start = LocalDate.parse(date1, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
	    LocalDate end = LocalDate.parse(date2, DateTimeFormatter.ofPattern("dd-MM-yyyy"));

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
	
	
	public Habitacion buscarAsignarHabitacion(Cliente cliente, ArrayList<Habitacion> inventario, String id, HashMap<String, Integer> cH, Huesped titular) 
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
		this.setChanged();
		this.notifyObservers(this.usuarios.size());
		
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
	    inventario.append(String.format("| %-4s | %-35s | %-10s | %-33s | %-9s | %-6s | %-6s | %-6s | %-6s | %-6s |\n",
	                    "Id", "Descripcion", "Tipo", "Ubicacion", "Capacidad", "Balcon", "Vista", "Cocina", "#Camas", "Precio"));
	    inventario.append("----------------------------------------------------------------------------------------------------------------------------------------------\n");
	    for (Habitacion habitacionEnInventario: this.inventarioHabitaciones) {
	        inventario.append(String.format("| %4s | %-35s | %-10s | %-33s | %9d | %-6s | %-6s | %-6s | %6d |  %6s |\n",
	                        habitacionEnInventario.getId(), habitacionEnInventario.getDescripcion(), habitacionEnInventario.getTipo(),
	                        habitacionEnInventario.getUbicacion(), habitacionEnInventario.getCapacidad(), 
	                        habitacionEnInventario.isBalcon() ? "Si" : "No", habitacionEnInventario.isVista() ? "Si" : "No",
	                        habitacionEnInventario.isCocinaIntegrada() ? "Si" : "No", habitacionEnInventario.getCamas().size(), habitacionEnInventario.getPrecio()));
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
	
	public ArrayList<String[]> getServicios()
	{
		ArrayList<String[]> Inventario = new ArrayList<String[]>();
		for (Servicio servicio: this.inventarioServicios) 
		{
			String[] s = {servicio.getId(), Integer.toString(servicio.getPrecio()), servicio.getDescripcion()}; 
			Inventario.add(s);
		}
		return Inventario;
		
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
	
	public String getNumeroUsuariosRegistrados() {
		String str = "";
		str += this.usuarios.size();
		return str;
	}
}


