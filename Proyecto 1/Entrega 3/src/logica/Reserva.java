package logica;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

public class Reserva implements Serializable {
	private Grupo grupo;
	private ArrayList<LocalDate> nochesSeleccionadas;
	private ArrayList<Habitacion> habitacionesSeleccionadas;
	
	public Reserva(Grupo grupo, ArrayList<LocalDate> nochesSeleccionadas,
			ArrayList<Habitacion> habitacionesSeleccionadas) {
		this.grupo = grupo;
		this.nochesSeleccionadas = nochesSeleccionadas;
		this.habitacionesSeleccionadas = habitacionesSeleccionadas;
	}

	public Grupo getGrupo() {
		return grupo;
	}

	public ArrayList<LocalDate> getNochesSeleccionadas() {
		return nochesSeleccionadas;
	}

	public ArrayList<Habitacion> getHabitacionesSeleccionadas() {
		return habitacionesSeleccionadas;
	} 
		
}
