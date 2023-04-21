package Controlador;

import java.io.IOException;
import java.util.ArrayList;

import Modelo.Pms;
import Vista.FPrincipal;
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
			boolean cocinaIntegrada, ArrayList<String> camas) {
		return pms.crearHabitacion(id, descripcion, tipo, ubicacion, capacidad, balcon, vista, cocinaIntegrada, camas);
		
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
}
