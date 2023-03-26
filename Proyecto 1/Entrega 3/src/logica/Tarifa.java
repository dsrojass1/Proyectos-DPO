package logica;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

public class Tarifa implements Serializable {
	private int precio;
	private String tipoHabitacion;
	private LocalDate fechaInicioVigencia;
	private LocalDate fechaFinVigencia;
	private ArrayList<String> diasVigencia;
	
	public Tarifa(int precio, String tipoHabitacion, LocalDate fechaInicioVigencia, LocalDate fechaFinVigencia,
			ArrayList<String> diasVigencia) {
		this.precio = precio;
		this.tipoHabitacion = tipoHabitacion;
		this.fechaInicioVigencia = fechaInicioVigencia;
		this.fechaFinVigencia = fechaFinVigencia;
		this.diasVigencia = diasVigencia;
	}

	public int getPrecio() {
		return precio;
	}

	public String getTipoHabitacion() {
		return tipoHabitacion;
	}

	public LocalDate getFechaInicioVigencia() {
		return fechaInicioVigencia;
	}

	public LocalDate getFechaFinVigencia() {
		return fechaFinVigencia;
	}

	public ArrayList<String> getDiasVigencia() {
		return diasVigencia;
	}
	
	public boolean estaEnRango(LocalDate fecha) {
	    // Verificar si la fecha está dentro del rango de vigencia
	    if (fecha.isBefore(fechaInicioVigencia) || fecha.isAfter(fechaFinVigencia)) {
	        return false;
	    }
	    
	    // Verificar si la fecha corresponde a un día de la semana en los que la tarifa es válida
	    String diaSemana = fecha.getDayOfWeek().toString();
	    if (!diasVigencia.contains(diaSemana)) {
	        return false;
	    }
	    
	    // Si la fecha cumple con ambas condiciones, la tarifa está en rango
	    return true;
	}
	
}
