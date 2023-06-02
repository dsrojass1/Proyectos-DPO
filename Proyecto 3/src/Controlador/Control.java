package Controlador;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.jfree.chart.ChartPanel;

import Modelo.Acompanante;
import Modelo.Cliente;
import Modelo.Habitacion;
import Modelo.Huesped;
import Modelo.Pms;
import Vista.FInventarioHabitaciones;
import Vista.FPrincipal;
import Vista.Presentacion;
import persistencia.Persistencia;

public class Control {
	private Pms pms;
	
	public Control(FPrincipal principal) {
		this.pms = new Pms();
		//AÑADIR OBSERVADORES A PMS DE SER NECESARIO (VER CASO DE ESTUDIO LISTA DOBLE)
		this.pms.addObserver(principal);
	}
	
	public void guardarCambios() {
		try {
            Persistencia.serializar(this.pms, "docs/" + "pms" + ".ser");
            System.out.println("Datos guardados con éxito en el archivo docs/pms.ser");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error al guardar los datos en el archivo docs/pms.ser");
        }
	}
	
	public void cargarSerializable() {
		// Cargar datos desde archivo serializable
		try {
			pms = (Pms) Persistencia.deserializar("docs/" + "pms" + ".ser");
			System.out.println("Datos cargados desde el archivo " + "docs/" + "pms" + ".ser");
		} catch (IOException | ClassNotFoundException e) {
			pms = new Pms();
			System.out.println("No se pudo cargar el archivo " + "docs/" + "pms" + ".ser" + ". Se creará un nuevo objeto Pms.");
		}
		System.out.println(pms.notificacionEstadoDeCarga());
	}
	
	public String getNumeroUsuarios() {
		return pms.getNumeroUsuariosRegistrados();
	}
	
	public boolean registrarUsuario(String loginUsuario, String contrasena, String rol) {
		return pms.registrarUsuario(loginUsuario, contrasena, rol);
	}
	
	public String getAutenticacionUsuario(String loginUsuario, String contrasena) {
		return pms.getAutenticacionDeUsuario(loginUsuario, contrasena);
	}
	
	public boolean eliminarUsuario(String login) {
		return pms.eliminarUsuario(login);
	}
	
	public boolean crearHabitacion(String id, String descripcion, String tipo, String ubicacion, int capacidad, boolean balcon, boolean vista,
			boolean cocinaIntegrada, ArrayList<String> camas,
			//PARAMETROS PROYECTO 3
			int metrosCuadrados, boolean tieneAireAcondicionado, boolean tieneCalefaccion, boolean tieneTv, boolean tieneCafetera,
			boolean tieneRopaHipoalergenica, boolean tienePlancha, boolean tieneSecadorPelo, int voltajeAC, boolean tieneTomasUsbA,
			boolean tieneTomasUsbC, boolean incluyeDesayuno) {
		return pms.crearHabitacion(id, descripcion, tipo, ubicacion, capacidad, balcon, vista, cocinaIntegrada, camas, metrosCuadrados, tieneAireAcondicionado, tieneCalefaccion, tieneTv, tieneCafetera,
				tieneRopaHipoalergenica,tienePlancha, tieneSecadorPelo,  voltajeAC, tieneTomasUsbA,
				 tieneTomasUsbC,  incluyeDesayuno);
		
	}
	
	public boolean crearTarifa(int precio, String tipo, String fechaInicioVigencia, String fechaFinVigencia, String diasDeLaSemana) {
		return pms.crearTarifa(precio, tipo, fechaInicioVigencia, fechaFinVigencia, diasDeLaSemana);
	}
	
	public boolean crearMenuRst(String idMenu, int precio, String descripcion, String horaInicio, String horaFin,
			boolean llevarAHabitacion) {
		return pms.crearMenuRestaurante(idMenu, precio, descripcion, horaInicio, horaFin, llevarAHabitacion);
	}
	
	public boolean crearSpa(String idSpa, int precio, String descripcion, boolean asignarAHabitacion) {
		return pms.crearServicioSpa(idSpa, precio, descripcion, asignarAHabitacion);
	}
	
	public boolean crearGuiaTuristico(String idGuia, int precio, String descripcion, boolean asignarAHabitacion) {
		return pms.crearServicioGuia(idGuia, precio, descripcion, asignarAHabitacion);
	}
	
	public String generarHistorialGrupo(String idTitular) {
		return pms.generarHistorialGrupo(idTitular);
	}
	
	public ArrayList<String[]> mostrarInventarioServicios() 
	{
		return pms.getServicios();
	}
	
	public String realizarReserva(String nombreTitular, String documentoTitular, String emailTitular,
			String celularTitular, int cantidadClientes, ArrayList<String> datosAcompañantes, String fechainicio, String fechasalida, FInventarioHabitaciones screen) 
	{
		Huesped huesped = new Huesped(nombreTitular, documentoTitular, emailTitular, celularTitular);
		ArrayList<LocalDate> fechas = this.pms.sortListByDate(fechainicio, fechasalida);
		ArrayList<Acompanante> acompanantes = pms.CrearAcompanantes(datosAcompañantes, cantidadClientes, huesped);
		Huesped newh = new Huesped(nombreTitular, documentoTitular, emailTitular, celularTitular);
		ArrayList<Habitacion> habitacionesSeleccionadas = AsignarHabitaciones(getClientesReserva(acompanantes, huesped),
				huesped ,getHabitacionDisponibilidadFechas(fechas), screen);
		return pms.hacerReserva(huesped, cantidadClientes, acompanantes, fechas, habitacionesSeleccionadas);
	}
	
	public String getInventariohabitaciones(ArrayList<Habitacion> hDisponibles) 
	{
		if (hDisponibles== null)
		{
			return pms.mostrarInventarioHabitaciones();
		}else 
		{
			return pms.mostrarInventarioHabitaciones(hDisponibles);
		}
	}
	
	public ArrayList<Habitacion> getHabitacionDisponibilidadFechas(ArrayList<LocalDate> fechas) 
	{
		return pms.getHabitacionesDisponibilidadFechas(fechas);
	}
	
	private ArrayList<Habitacion> AsignarHabitaciones(ArrayList<Cliente> clientes, Huesped huesped,
			ArrayList<Habitacion> habitacionesDispo, FInventarioHabitaciones screen) 
	{
		HashMap<String, Integer> controlCapacidadHabitaciones = new HashMap<String, Integer>();
		
		//crear una varible para tener en cuenta la capacidad de cada habitacion
		
		for(Habitacion h : habitacionesDispo) 
		{
			controlCapacidadHabitaciones.put(h.getId(), h.getCapacidad());
		}
		
		//Asignar las habitaciones para cada cliente
		ArrayList<Habitacion> hTomadas= new ArrayList<Habitacion>();
		
		//crear la pantallita para pedir los ids de las habitaciones que se le asignaran a cada cliente
		
		for(Cliente cliente : clientes) 
		{
			String id;
			String inventario = getInventariohabitaciones(habitacionesDispo);
			//pedir input al usuario
			//imprimir el inventario y preguntar el id de la habitacion
			//System.out.println("Digite ID de la habitacion que desea asignar: ");
			screen.actualizarInventario(inventario);
			id = screen.getnewId(cliente.getNombre());
			//asignar a habitacion a los acompañantes
			Habitacion habita = pms.buscarAsignarHabitacion(cliente, habitacionesDispo, id, controlCapacidadHabitaciones, huesped);
			if(habita != null) 
			{
				hTomadas.add(habita);
			}
		}
		screen.setVisible(false);
		screen.dispose();
		return hTomadas;
	}
	
	ArrayList<Cliente> getClientesReserva(ArrayList<Acompanante>Acompanantes, Huesped huesped)
	{
		ArrayList<Cliente> clientes = new ArrayList<Cliente>();
		if(Acompanantes!=null)
		{
			for(Acompanante acompanante:Acompanantes) 
			{
				clientes.add(acompanante);
			}
		}
		clientes.add(huesped);
		return clientes;
	}
	
	public String HacerCheckout(String idTitular) 
	{
		return pms.realizarCheckOut(idTitular);
	}
	
	public String CancelarReserva (String idT, int nNoches) 
	{
		return pms.cancelarReserva(idT, nNoches);
	}
	
	public Boolean registrarConsumo(String idServicio, Boolean pagoInmediato, Boolean AsignaraHabitacion,
			String DocumentoT, ArrayList<String> DocumentosClientes, Boolean UsarTitular) 
	{
		return pms.registrarServicio(idServicio, pagoInmediato, AsignaraHabitacion,DocumentoT,
				DocumentosClientes, UsarTitular);
	}
	
	public void cambiarCaracteristicasHotel(boolean isParqueaderoPago, boolean isParqueaderoGratuito, 
			boolean isPiscina, boolean isZonasHumedas, boolean isBbq, boolean isWifiGratis, boolean isRecepcion24Horas
	, boolean isAdmiteMascotas) {
		pms.cambiarCaracteristicasHotel(isParqueaderoPago, isParqueaderoGratuito, isPiscina, isZonasHumedas, isBbq, isWifiGratis, isRecepcion24Horas, isAdmiteMascotas);	
	}
	
	public boolean realizarPago(String nombrePasarela, ArrayList<String> informacionTarjeta, int montoTotal) {
		return pms.realizarPago(nombrePasarela, informacionTarjeta, montoTotal);
	}
	
	public ChartPanel graficarConsumosGlobales() 
	{
		return this.pms.graficarHistorialConsumosGlobales();
	}
	
	public ChartPanel graficarValorConsumosPorFecha() 
	{
		return this.pms.graficarValorConsumosPorFecha();
	}
	public ChartPanel graficarConsumosPorSemana() 
	{
		return this.pms.graficarConsumosPorSemana();
	}
	
	public ChartPanel graficarConsumosPorPrecio() 
	{
		return this.pms.graficarConsumosPorPrecio();
	}
}
