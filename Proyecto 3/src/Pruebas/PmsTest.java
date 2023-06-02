package Pruebas;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Modelo.Acompanante;
import Modelo.Cama;
import Modelo.Habitacion;
import Modelo.Huesped;
import Modelo.Pms;

public class PmsTest {

	private Pms pms;
	
	public PmsTest() 
	{
		Setup();
	}
	
	public void Setup() 
	{
		this.pms = new Pms();
	}
	
	@Test
	public void testCrearHabitacion1() 
	{
		ArrayList<String> camastest = new ArrayList<String>();
		camastest.add("20x20;2;false");
		assertTrue(this.pms.crearHabitacion("001", "una habitacion grande", "tercer piso", "suite",
				2, false, false, false, camastest, 4, false, false, false, false, false,
				false, false, 0, false, false, false));
		
		assertFalse(this.pms.crearHabitacion("001", "una habitacion grande", "tercer piso", "suite",
				2, false, false, false, camastest, 4, false, false, false, false, false,
				false, false, 0, false, false, false));
	}
	
	@Test
	public void testCrearTarifa() 
	{
		ArrayList<String> camastest = new ArrayList<String>();
		camastest.add("20x20;2;false");
		this.pms.crearHabitacion("001", "una habitacion grande", "tercer piso", "suite",
				2, false, false, false, camastest, 4, false, false, false, false, false,
				false, false, 0, false, false, false);
		String fechaInicio="2023/06/02";
		String fechaFin="2023/07/02";
		
		assertTrue(this.pms.crearTarifa(200000, "suite", fechaInicio, fechaFin, "lunes, martes, miercoles"));
		assertFalse(this.pms.crearTarifa(200000, "suite", fechaInicio, fechaFin, "lunes, martes, miercoles"));
		
	}
	
	@Test
	public void testReservar() 
	{
		Huesped huesped = new Huesped("John Doe", "0000", "JohnD@email.com", "0800JohnDoe");
		assertEquals("No se ha podido hacer el registro", this.pms.hacerReserva(huesped, 1, new ArrayList<Acompanante>(),
				new ArrayList<LocalDate>(), new ArrayList<Habitacion>()));
	}
}
