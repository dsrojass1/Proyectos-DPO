package view;
import java.awt.Window.Type;
import java.io.Console;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

import logica.Pms;
import persistencia.Persistencia;


public class Presentacion {
	private Pms pms;
	public static Scanner scanner;
	
	public Presentacion() {
		scanner = new Scanner(System.in);
		try {
            pms = (Pms) Persistencia.deserializar("docs/" + "pms" + ".ser");
            System.out.println("Datos cargados desde el archivo " + "docs/" + "pms" + ".ser");
        } catch (IOException | ClassNotFoundException e) {
            pms = new Pms();
            System.out.println("No se pudo cargar el archivo " + "docs/" + "pms" + ".ser" + ". Se creará un nuevo objeto Pms.");
        }
		System.out.println(pms.notificacionEstadoDeCarga());
		cargando();
	}
	
	public void iniciar() {
		int opcionDeInicio = 99;
    	while (opcionDeInicio != 0) {
    		System.out.println("----------------------------------------------------------------------");
            System.out.println("Bienvenido a el sistema PMS (Property Managament System)             |");
            System.out.println("----------------------------------------------------------------------");
            System.out.println("Seleccione una opcion para continuar:");
            System.out.println("0. Salir");
            System.out.println("1. Registrarse en el sistema");
            System.out.println("2. Iniciar sesión");
            System.out.print  ("> ");
            opcionDeInicio = scanner.nextInt();
            
            if (opcionDeInicio == 0) {
            	System.out.println("Aplicación cerrada. Vuelva pronto!");
            }
            
            else if (opcionDeInicio == 1) {
            	
            	boolean registroExitoso = registrarUsuario();
            	if (registroExitoso) {
            		System.out.println("Usuario registrado con éxito");
            		guardarCambios();
            	} else {
            		System.out.println("Un usuario con el mismo login ya ha sido registrado antes");
            	}
            	
            }
            else if (opcionDeInicio == 2) {
            	
            	String inicioDeSesion = iniciarSesion();
            	if (inicioDeSesion.equalsIgnoreCase("Administrador")) {
            		menuAdministrador();
            	} 
            	else if (inicioDeSesion.equalsIgnoreCase("Empleado")) {
            		menuEmpleado();
            	}
            	else {
            		System.out.println("Login o contraseña incorrectos. Vuelva a intentarlo.");
            	}
            	
            }
    	}  
	}
	
	private String iniciarSesion() {
		System.out.print("Ingrese su login:\n> ");
		String login = scanner.next();
		System.out.print("Ingrese su contraseña:\n> ");
		String contrasena = scanner.next();
		return pms.getAutenticacionDeUsuario(login, contrasena);
	}
	
	private boolean registrarUsuario() {
		System.out.print("Ingrese un nuevo login:\n> ");
		String nuevoLogin = scanner.next();
		System.out.print("Cree una contraseña:\n> ");
		String nuevaContrasena = scanner.next();
		System.out.print("Digite su rol:\n> ");
		String nuevoRol = scanner.next();
		
		return pms.registrarUsuario(nuevoLogin, nuevaContrasena, nuevoRol);
	}
	
	
    
    
	//---------------------------------------------------------------------------------------------------------------------
	// ADMINISTRADOR -------------------------------------------------------------------------------------------------------
	//---------------------------------------------------------------------------------------------------------------------
    private void menuAdministrador() {
    	int opcion = 99;
    	while (opcion != 0) {
    		System.out.println("----------------------------------------------------------------------");
            System.out.println("Bienvenido Administrador!                                            |");
            System.out.println("----------------------------------------------------------------------");
            System.out.println("Seleccione una opcion para continuar:");
            System.out.println("0. Cerrar sesion");
            System.out.println("1. Guardar cambios");
            System.out.println("2. Eliminar usuario");
            System.out.println("3. Crear habitación");
            System.out.println("4. Cargar archivo habitaciones");
            System.out.println("5. Crear tarifa");
            System.out.println("6. Crear menu restaurante");
            System.out.println("7. Crear servicio de Spa");
            System.out.println("8. Crear servicio guía turístico");
            System.out.println("9. Generar historial de un grupo de huéspedes");
            System.out.println("10. Consultar inventario habitaciones");
            System.out.println("11. Consultar inventario servicios");
            System.out.println("Estimado usuari@, no olvide guardar los cambios!!");
            System.out.print  ("> ");
            opcion = scanner.nextInt();
            scanner.nextLine();
            
            if (opcion == 0) {
            	
            		System.out.println("Sesión cerrada de manera segura.\nVolviendo a registro...");
            		
            }
            else if (opcion == 1) {
            	
            		guardarCambios();
            		
            }
            else if (opcion == 2) {
            	
            		if (eliminarUsuario()) {
            			System.out.println("Usuario eliminado del sistema exitosamente.");
            		}
            		else {
            			System.out.println("Ningun usuario coincide con el login ingresado. Inténtelo de nuevo.");
            		}
            		
            }
            else if (opcion == 3) {
            	
            		if (crearHabitacion()) {
            			System.out.println("Habitacion añadida al inventario con éxito.");
            		}
            		else {
            			System.out.println("Ya existe una habitación con el id ingresado. Inténtelo de nuevo.");
            		}
            		
            }
            else if (opcion == 4) {
            	
            		System.out.println("Se cargarán las habitaciones del archivo docs/inventarioHabitaciones.txt");
            		pms.cargarDatosDeArchivo();
            		cargando();
            		System.out.println("Habitaciones cargadas con éxito.");
            		
            }
            else if (opcion == 5) {
            	
        		if (crearTarifa()) {
        			System.out.println("Tarifa fijada con éxito para las fechas y dias seleccionados.");
        		}
        		else {
        			System.out.println("Existe otra tarifa para el mismo tipo de habitacion en las mismas fechas.");
        			System.out.println("El sistema tomará la tarifa más económica.");
        		}
        		
            }
            else if (opcion == 6) {
            	
            	if (crearMenu()) {
            		System.out.println("Nuevo menú del restaurante añadido al inventario.");
            	} else {
            		System.out.println("Ya exite un servicio con el mismo id. Por favor, vuelva a intentarlo");
            	}
        	
        		
            }
            else if (opcion == 7) {
            	
            	if (crearSpa()) {
            		System.out.println("Nuevo servicio de spa añadido al inventario.");
            	} else {
            		System.out.println("Ya exite un servicio con el mismo id. Por favor, vuelva a intentarlo");
            	}
        		
            }
            else if (opcion == 8) {
            	
            	if (crearGuia()) {
            		System.out.println("Nuevo servicio de guía turístico añadido al inventario.");
            	} else {
            		System.out.println("Ya exite un servicio con el mismo id. Por favor, vuelva a intentarlo");
            	}
        		
            }
            else if (opcion == 9) {
            	
            	System.out.println(generarHistorialGrupo());
            	System.out.println("*El precio del servicio de hospedaje depende de la tarifa vigente en el momento de reservar.");
            	System.out.println("para conocer el monto pagado por el cliente, consultar las facturas.");
        		
            }
            else if (opcion == 10) {
            	
            	System.out.print(consultarInventarioHabitaciones());
        		
            }
            else if (opcion == 11) {
            	
            	System.out.print(consultarInventarioServicios());
        		
            }
    	}
    }
    
    private boolean eliminarUsuario() {
    	System.out.print("Ingrese el login del usuario a eliminar:\n> ");
		String login = scanner.nextLine();
    	return pms.eliminarUsuario(login);
    }
    
    private boolean crearHabitacion() {
    	System.out.print("Establece el id del servicio:\n> ");
    	String id = scanner.nextLine();
    	
    	System.out.print("Añada una descripcion del servicio:\n> ");
    	String descripcion = scanner.nextLine();
    	
    	System.out.print("Digite el tipo de habitacion (Estandar, suit o doble):\n> ");
    	String tipo = scanner.nextLine();
    	
    	System.out.print("Digite la ubicacion de la habitacion:\n> ");
    	String ubicacion = scanner.nextLine();
    	
    	System.out.print("Establezca la capacidad de la habitacion:\n> ");
    	int capacidad = scanner.nextInt();
    	scanner.nextLine();
    	
    	System.out.print("Seleccione si la habitacion tiene balcón (true o false):\n> ");
    	boolean balcon = scanner.nextBoolean();
    	scanner.nextLine();
    	
    	System.out.print("Seleccione si la habitacion tiene vista (true o false):\n> ");
    	boolean vista = scanner.nextBoolean();
    	scanner.nextLine();
    	
    	System.out.print("Seleccione si la habitacion tiene cocina integrada (true o false):\n> ");
    	boolean cocinaIntegrada = scanner.nextBoolean();
    	scanner.nextLine();
    	
    	System.out.print("A continuación, digite el número de camas con las que contará la habitación:\n> ");
    	int numeroDeCamas = scanner.nextInt();
    	scanner.nextLine();
    	ArrayList<String> arregloDeCamas = new ArrayList<String>();
    	for (int i = 1; i <= numeroDeCamas; i++) {
    		System.out.println("Para la cama numero '"+i+"', especifique:");
    		System.out.print("Tamaño (medidas AltoxAncho):\n> ");
    		String tamaño = scanner.nextLine();
    		
    		System.out.print("Capacidad (entero):\n> ");
    		String capacidadCama = scanner.nextLine();
    		
    		System.out.print("Solo para niños (true o false):\n> ");
    		String soloParaNinos = scanner.nextLine();
    		
    		arregloDeCamas.add(tamaño + ";" + capacidadCama + ";" + soloParaNinos);
    	}
    	return pms.crearHabitacion(id, descripcion, tipo, ubicacion, capacidad, balcon, vista, cocinaIntegrada, arregloDeCamas);
    }
    
    private boolean crearTarifa() {
    	System.out.print("Digite el precio de la tarifa:\n> ");
    	int precio = scanner.nextInt();
    	scanner.nextLine();
    	System.out.print("Espefique tipo de habitación:\n> ");
    	String tipo = scanner.nextLine();
    	System.out.print("Especifique la fecha inicio de vigencia (AAAA/MM/DD):\n> ");
    	String fechaInicio = scanner.nextLine();
    	System.out.print("Especifique la fecha fin de vigencia (AAAA/MM/DD):\n> ");
    	String fechaFin = scanner.nextLine();
    	System.out.print("Digite los días de la semana (separados por coma) en los que la tarifa va a entrar en vigencia:\n> ");
    	String diasDeLaSemana = scanner.nextLine();
    	return pms.crearTarifa(precio, tipo, fechaInicio, fechaFin, diasDeLaSemana);
    }
    
    private boolean crearMenu() {
    	System.out.print("Digite el id del servicio:\n> ");
    	String id = scanner.nextLine();
    	System.out.print("Digite el precio del menú:\n> ");
    	int precio = scanner.nextInt();
    	scanner.nextLine();
    	System.out.print("Añade una descripción del menú:\n> ");
    	String descripcion = scanner.nextLine();
    	System.out.print("Especifique la hora de inicio en el que el menú estará disponible (HH:MM):\n> ");
    	String strHoraInicio = scanner.nextLine();
    	System.out.print("Especifique la hora fin en el que el menú estará disponible (HH:MM):\n> ");
    	String strHoraFin = scanner.nextLine();
    	System.out.print("Especifique si el menu se puede llevar a la habitacion (true o false):\n> ");
    	boolean asignarAHabitacion = scanner.nextBoolean();
    	scanner.nextLine();
    	return pms.crearMenuRestaurante(id, precio, descripcion, strHoraInicio, strHoraFin, asignarAHabitacion);
    	
    }
    
    private boolean crearSpa() {
    	System.out.print("Ingrese el id del servicio:\n> ");
    	String id = scanner.nextLine();
    	System.out.print("Digite el precio del servicio:\n> ");
    	int precio = scanner.nextInt();
    	scanner.nextLine();
    	System.out.print("Añada una descripción del servicio:\n> ");
    	String descripcion = scanner.nextLine();
    	System.out.print("Especifique si el servicio puede asignarse la habitación (true o false):\n> ");
    	boolean asignarAHabitacion = scanner.nextBoolean();
    	scanner.nextLine();
    	return pms.crearServicioSpa(id, precio, descripcion, asignarAHabitacion);
    }
    
    private boolean crearGuia() {
    	System.out.print("Ingrese el id del servicio:\n> ");
    	String id = scanner.nextLine();
    	System.out.print("Digite el precio del guía turistico:\n> ");
    	int precio = scanner.nextInt();
    	scanner.nextLine();
    	System.out.print("Añada una descripción del servicio:\n> ");
    	String descripcion = scanner.nextLine();
    	System.out.print("Especifique si el servicio puede asignarse la habitación (true o false):\n> ");
    	boolean asignarAHabitacion = scanner.nextBoolean();
    	scanner.nextLine();
    	return pms.crearServicioGuia(id, precio, descripcion, asignarAHabitacion);
    	
    }
    
    private String generarHistorialGrupo() {
    	System.out.print("Ingrese la identificación del huésped titular del grupo\n> ");
    	String idTitular = scanner.nextLine();
    	return pms.generarHistorialGrupo(idTitular);
    }
    
    
    
	//---------------------------------------------------------------------------------------------------------------------
	// EMPLEADO -------------------------------------------------------------------------------------------------------
	//---------------------------------------------------------------------------------------------------------------------
    private void menuEmpleado() {
    	int opcion = 99;
    	while (opcion != 0) {
    		System.out.println("----------------------------------------------------------------------");
            System.out.println("Bienvenido Empleado!								                 |");
            System.out.println("----------------------------------------------------------------------");
            System.out.println("Seleccione una opcion para continuar:");
            System.out.println("0. Cerrar sesion");
            System.out.println("1. Guardar cambios");
            System.out.println("2. Consultar inventario habitaciones");
            System.out.println("3. Consultar inventario servicios");
            System.out.println("4. Registrar consumo");
            System.out.println("5. Hacer CheckOut");
            System.out.println("6. Realizar reserva");
            System.out.println("7. Cancelar reserva");
            System.out.print  ("> ");
            opcion = scanner.nextInt();
            scanner.nextLine();
            
            if (opcion == 0) {
            	System.out.println("Sesión cerrada con éxito.\nVolviendo a registro...");
            }
            else if (opcion == 1) {
            	guardarCambios();
            }
            else if (opcion == 2) {
            	System.out.print(consultarInventarioHabitaciones());
            }
            else if (opcion == 3) {
            	System.out.print(consultarInventarioServicios());
            }
            else if(opcion == 4) 
            {
            	registrarServicio();
            }
            else if(opcion == 5) 
            {
            	hacerCheckOut();
            }
            else if (opcion == 6) {
            	realizarReserva();
            }
            else if (opcion == 7) 
            {
            	cancelarReserva();
            }
    	}
    }
    
    
    private void registrarServicio() {
    	//obtener las variables que permitiran buscar al cliente
    	System.out.println("Digite el numero de documento del huesped titular: ");
    	String documentoTitular=scanner.nextLine();
    	ArrayList<String> documentoClientes= new ArrayList<String>();
    	
    	//obtener la id del servicio
    	System.out.println("Digite el id del servicio: ");
    	String idServicio=scanner.nextLine();
    	
    	//pedir los dicumentos de los acompañantes y verificar si el titular tambien usara el servicio
    	System.out.println("Digite el numero de clientes que usaran el servicio");
    	int nClientes=scanner.nextInt();
    	for (int i = 0; i < nClientes; i++) {
			documentoClientes.add(scanner.nextLine());
		}
    	System.out.println("¿El huesped titular tambien usara el servicio? (true/false)");
    	boolean huespedTUse=Boolean.getBoolean(scanner.nextLine());
    	//Asignar servicio a la habitacion?
    	System.out.println("¿Asignar el servicio a la habitación? (true/false)");
    	boolean asignarHabitacion= Boolean.getBoolean(scanner.nextLine());
    	
    	//Se hara el pago de manera inmediata?
    	System.out.println("¿Hacer pago inmediato? (true/false)");
    	boolean pagoInmediato= Boolean.getBoolean(scanner.nextLine());
    	
    	//registrar el servicio
    	boolean registred = pms.registrarServicio(idServicio, pagoInmediato, asignarHabitacion, documentoTitular, documentoClientes, huespedTUse);
    	
    	//Confirmar accion
    	if(registred == true) 
    	{
    		System.out.println("Servicio registrado.");
    	}else 
    	{
    		System.out.println("Hubo un error, verifique que el documento del huesped y el id del servicio sean correctos");
    	}
    	
    	
    }
    
    private void hacerCheckOut() {
    	
    	//obtener las variables que permitiran buscar al cliente
    	System.out.println("Digite el numero de documento del huesped titular: ");
    	String documentoTitular=scanner.nextLine();
    	pms.realizarCheckOut(documentoTitular);
    }
    
    private void realizarReserva() {
    	//datos del huesped titular
    	String nombreTitular = "", documentoTitular = "", emailTitular = "", celularTitular = "";
    	System.out.println("¿Cual es tu nombre?");
    	nombreTitular = scanner.nextLine();
    	System.out.println("¿Cual es tu documento?");
    	documentoTitular = scanner.nextLine();
    	System.out.println("¿Cual es tu email?");
    	emailTitular = scanner.nextLine();
    	System.out.println("¿Cual es tu celular?");
    	celularTitular = scanner.nextLine();
    	int cantidadClientes = 0;
    	System.out.println("¿Cual es la cantidad de clientes?");
    	cantidadClientes = scanner.nextInt();
    	
    	//datos de los acompañantes
    	ArrayList<String> datosAcompanantes=new ArrayList<String>();
    	for(int i= 1; i < cantidadClientes; i++) {
    		String acompanante="";
    		System.out.println("¿Cual es el nombre del "+ i + " acompañante");
        	acompanante += scanner.next();
        	System.out.println("¿Cual es el documento del "+ i + " acompañante");
        	acompanante += ";"+scanner.next();
        	System.out.println("¿Cual es el email del "+ i + " acompañante");
        	acompanante += ";"+scanner.next();
        	System.out.println("¿Cual es el celular del "+ i + " acompañante");
        	acompanante += ";"+scanner.next();
        	acompanante += ";"+nombreTitular;
        	datosAcompanantes.add(acompanante);
    	}
    	
    	//Manejar las fechas de hospedaje
    	System.out.println("¿Desde que fecha desea hospedarse? (formato: DD/MM/YYYY)");
    	String date1= scanner.next();
    	System.out.println("¿Hasta que fecha desea hospedarse? (formato: DD/MM/YYYY)");
    	String date2= scanner.next();
    	
    	//obtener el rango de fechas
    	ArrayList<LocalDate> rangoFechas = pms.sortListByDate(date1, date2);
    	//verificar que habitaciones estan libres en el rango de fechas--------
    	
    	
    	//Hacer la reserva
    	System.out.println(pms.hacerReserva(nombreTitular, documentoTitular, emailTitular, celularTitular, cantidadClientes, datosAcompanantes, rangoFechas));	
    	
    }
    
    private void cancelarReserva() {
    	
    }
    
    
    
	//---------------------------------------------------------------------------------------------------------------------
	// TODOS LOS USUARIOS -------------------------------------------------------------------------------------------------------
	//---------------------------------------------------------------------------------------------------------------------
    private void guardarCambios() {
    	try {
            Persistencia.serializar(pms, "docs/" + "pms" + ".ser");
            System.out.println("Datos guardados con éxito en el archivo docs/pms.ser");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error al guardar los datos en el archivo docs/pms.ser");
        }
    }
    
    private String consultarInventarioHabitaciones() {
    	return pms.mostrarInventarioHabitaciones();
    }
    
    private String consultarInventarioServicios() {
    	return pms.mostrarInventarioServicios();
    }
    
    
    
    
	//---------------------------------------------------------------------------------------------------------------------
	// MAIN -------------------------------------------------------------------------------------------------------
	//---------------------------------------------------------------------------------------------------------------------
    public static void main(String[] args) {
		Presentacion presentacion = new Presentacion();
		presentacion.iniciar();
	}
    
    public void cargando() {
    	//INVOCAR PARA DAR LA ENTENDER AL USUARIO QUE SE ESTÁ CARGANDO ALGO CON UNA PAUSA
    	try {
    	    Thread.sleep(800);
    	} catch (InterruptedException e) {
    	    System.out.println(e);
    	}
    }
    
}