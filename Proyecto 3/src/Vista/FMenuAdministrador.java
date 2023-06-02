package Vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import Controlador.Control;

public class FMenuAdministrador extends JFrame {
	private Control control;
	
	private JPanel pContenido;
	private JPanel pTitulo;
	private JPanel pBotones;
	
	private JButton btEliminarUsuario;
	private JButton btCrearHabitacion;
	private JButton btCrearTarifa;
	private JButton btCrearMenuRestaurante;
	private JButton btCrearServicioSpa;
	private JButton btCrearServicioGuiaTuristico;
	private JButton btGenerarHistorialGrupo;
	private JButton btGuardarCambios;
	private JButton btCerrarSesion;
	private JButton btnCambiarInfoHotel = new JButton("Cambiar características propias del hotel");
	private JButton btGraficar;
	
	
	FMenuAdministrador(Control control){
		// Configurar frame
		this.control = control;
		Image icon = Toolkit.getDefaultToolkit().getImage("docs/icon.png");  
		this.setIconImage(icon);
		this.setTitle("Sistema PMS (Property Managment System)");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(900, 900);
		this.setLayout(new BorderLayout());
		
		// Crear paneles para los bordes izquierdo y derecho
		JPanel leftPanel = new JPanel();
		JPanel rightPanel = new JPanel();
		JPanel topPanel = new JPanel();
		JPanel bottomPanel = new JPanel();
		leftPanel.setBackground(new Color(90,183,250));
		leftPanel.setPreferredSize(new Dimension(50, 900));
		rightPanel.setBackground(new Color(90,183,250));
		rightPanel.setPreferredSize(new Dimension(50, 900));
		topPanel.setBackground(new Color(90,183,250));
		topPanel.setPreferredSize(new Dimension(900, 50));
		bottomPanel.setBackground(new Color(90,183,250));
		bottomPanel.setPreferredSize(new Dimension(900, 50));

		// Agregar paneles de los bordes y panel central
		this.add(leftPanel, BorderLayout.WEST);
		this.add(rightPanel, BorderLayout.EAST);
		this.add(topPanel, BorderLayout.NORTH);
		this.add(bottomPanel, BorderLayout.SOUTH);
		
		
		// Crear el panel en donde se pondrá el contenido
		this.pContenido = new JPanel();
		this.pContenido.setPreferredSize(new Dimension(850, 850));
		this.pContenido.setBackground(new Color(171, 194, 255));
		this.pContenido.setLayout(new BorderLayout());
		this.add(this.pContenido, BorderLayout.CENTER);
		
		// Crear banner
		this.pTitulo = new JPanel();
		this.pTitulo.setPreferredSize(new Dimension(850, 130));
		this.pTitulo.setBackground(new Color(28, 81, 223));
		this.pTitulo.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		JLabel tituloBanner = new JLabel("Bienvenido Administrador!");
		Font font2 = new Font("Segoe UI", Font.BOLD, 29);
		tituloBanner.setFont(font2);
		tituloBanner.setForeground(new Color(255, 255, 255));
		this.pTitulo.add(tituloBanner, gbc);
		this.pContenido.add(this.pTitulo, BorderLayout.NORTH);
		
		
		// Crear los botones
		Font font1 = new Font("Segoe UI", Font.PLAIN, 26); 
		
		btEliminarUsuario = new JButton("Eliminar Usuario");
		btEliminarUsuario.setFont(font1); 
		btEliminarUsuario.setBackground(new Color(28, 81, 223));
		btEliminarUsuario.setForeground(Color.WHITE);
		btEliminarUsuario.setPreferredSize(new Dimension(400, 90));
		
		btCrearHabitacion = new JButton("Crear Habitacion");
		btCrearHabitacion.setFont(font1); 
		btCrearHabitacion.setBackground(new Color(28, 81, 223));
		btCrearHabitacion.setForeground(Color.WHITE);
		btCrearHabitacion.setPreferredSize(new Dimension(400, 90));
		
		btCrearTarifa = new JButton("Crear Tarifa");
		btCrearTarifa.setFont(font1); 
		btCrearTarifa.setBackground(new Color(28, 81, 223));
		btCrearTarifa.setForeground(Color.WHITE);
		btCrearTarifa.setPreferredSize(new Dimension(400, 90));
		
		btCrearMenuRestaurante = new JButton("Crear Menú Restaurante");
		btCrearMenuRestaurante.setFont(font1); 
		btCrearMenuRestaurante.setBackground(new Color(28, 81, 223));
		btCrearMenuRestaurante.setForeground(Color.WHITE);
		btCrearMenuRestaurante.setPreferredSize(new Dimension(400, 90));
		
		btCrearServicioSpa = new JButton("Crear Servicio Spa");
		btCrearServicioSpa.setFont(font1); 
		btCrearServicioSpa.setBackground(new Color(28, 81, 223));
		btCrearServicioSpa.setForeground(Color.WHITE);
		btCrearServicioSpa.setPreferredSize(new Dimension(400, 90));
		
		btCrearServicioGuiaTuristico = new JButton("Crear Servicio Guía Turístico");
		btCrearServicioGuiaTuristico.setFont(font1); 
		btCrearServicioGuiaTuristico.setBackground(new Color(28, 81, 223));
		btCrearServicioGuiaTuristico.setForeground(Color.WHITE);
		btCrearServicioGuiaTuristico.setPreferredSize(new Dimension(400, 90));
		
		btGenerarHistorialGrupo = new JButton("Generar Historial Grupo");
		btGenerarHistorialGrupo.setFont(font1); 
		btGenerarHistorialGrupo.setBackground(new Color(28, 81, 223));
		btGenerarHistorialGrupo.setForeground(Color.WHITE);
		btGenerarHistorialGrupo.setPreferredSize(new Dimension(400, 90));
		
		btnCambiarInfoHotel.setFont(font1); 
		btnCambiarInfoHotel.setBackground(new Color(28, 81, 223));
		btnCambiarInfoHotel.setForeground(Color.WHITE);
		btnCambiarInfoHotel.setPreferredSize(new Dimension(400, 90));
		
		btGraficar = new JButton("Graficar ventas");
		btGraficar.setFont(font1); 
		btGraficar.setBackground(new Color(28, 81, 223));
		btGraficar.setForeground(Color.WHITE);
		btGraficar.setPreferredSize(new Dimension(400, 90));
		
		btGuardarCambios = new JButton("Guardar Cambios");
		btGuardarCambios.setFont(font1); 
		btGuardarCambios.setBackground(new Color(28, 81, 223));
		btGuardarCambios.setForeground(Color.WHITE);
		btGuardarCambios.setPreferredSize(new Dimension(400, 90));
		
		btCerrarSesion = new JButton("Cerrar Sesión");
		btCerrarSesion.setFont(font1); 
		btCerrarSesion.setBackground(new Color(28, 81, 223));
		btCerrarSesion.setForeground(Color.WHITE);
		btCerrarSesion.setPreferredSize(new Dimension(400, 90));
		
		// Agregar los botones al pContenido
		this.pBotones = new JPanel();
		
		this.pBotones.setPreferredSize(new Dimension(850, 720));
		this.pBotones.setBackground(new Color(171, 194, 255));
		this.pBotones.setLayout(new GridBagLayout());
		GridBagConstraints gbcBotones = new GridBagConstraints();
		gbcBotones.insets = new Insets(0, 0, 11, 0);
		gbcBotones.gridx = 0;
		gbcBotones.gridy = 0;
		gbcBotones.gridwidth = 1;
		gbcBotones.fill = gbcBotones.HORIZONTAL;
		this.pBotones.add(btEliminarUsuario, gbcBotones);
		gbcBotones.gridy ++;
		this.pBotones.add(btCrearHabitacion, gbcBotones);
		gbcBotones.gridy ++;
		this.pBotones.add(btCrearTarifa, gbcBotones);
		gbcBotones.gridy ++;
		this.pBotones.add(btCrearMenuRestaurante, gbcBotones);
		gbcBotones.gridy ++;
		this.pBotones.add(btCrearServicioSpa, gbcBotones);
		gbcBotones.gridy ++;
		this.pBotones.add(btCrearServicioGuiaTuristico, gbcBotones);
		gbcBotones.gridy ++;
		this.pBotones.add(btGenerarHistorialGrupo, gbcBotones);
		gbcBotones.gridy ++;
		this.pBotones.add(btGraficar, gbcBotones);
		gbcBotones.gridy ++;
		this.pBotones.add(btnCambiarInfoHotel, gbcBotones);
		gbcBotones.gridy ++;
		this.pBotones.add(btGuardarCambios, gbcBotones);
		gbcBotones.gridy ++;
		this.pBotones.add(btCerrarSesion, gbcBotones);
		
		this.pContenido.add(this.pBotones, BorderLayout.WEST);
				
		// Agregar ActionListener a cada botón
		btEliminarUsuario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				eliminarUsuario();
			}
		});
				
		btCrearHabitacion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				crearHabitacion();
			}
		});
				
		btCrearTarifa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				crearTarifa();
			}
		});
				
		btCrearMenuRestaurante.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				crearMenuRst();
			}
		});
				
		btCrearServicioSpa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				crearSpa();
			}
		});
				
		btCrearServicioGuiaTuristico.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				crearGuia();
			}
		});
				
		btGenerarHistorialGrupo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				generarHistorial();
			}
		});
		
		btnCambiarInfoHotel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cambiarCaracteristicasPropiasHotel();
			}
		});
		
		btGraficar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				graficar();
			}
		});
				
		btGuardarCambios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				control.guardarCambios();
				msgGuardarCambios();
			}
		});
				
		btCerrarSesion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				msgCerrarSesion();
			}
		});
	}

	protected void cambiarCaracteristicasPropiasHotel() {
	    boolean isParqueaderoPago = Boolean.parseBoolean(JOptionPane.showInputDialog(this,"El hotel tiene Parqueadero pago? (true/false)"));
	    boolean isParqueaderoGratuito = Boolean.parseBoolean(JOptionPane.showInputDialog(this,"El hotel tiene Parqueadero gratuito? (true/false)"));
	    boolean isPiscina = Boolean.parseBoolean(JOptionPane.showInputDialog(this,"El hotel tiene Piscina? (true/false)"));
	    boolean isZonasHumedas = Boolean.parseBoolean(JOptionPane.showInputDialog("El hotel tiene Zonas húmedas? (true/false)"));
	    boolean isBbq = Boolean.parseBoolean(JOptionPane.showInputDialog(this,"El hotel tiene BBQ? (true/false)"));
	    boolean isWifiGratis = Boolean.parseBoolean(JOptionPane.showInputDialog(this,"El hotel tiene Wifi gratis? (true/false)"));
	    boolean isRecepcion24Horas = Boolean.parseBoolean(JOptionPane.showInputDialog(this,"El hotel tiene Recepción 24 horas? (true/false)"));
	    boolean isAdmiteMascotas = Boolean.parseBoolean(JOptionPane.showInputDialog(this,"El hotel admite mascotas? (true/false)"));
		JOptionPane.showMessageDialog(this, "Características fijadas con éxito, no olvide guardar los cambios.", "Características Propias del Hotel Cambiadas", JOptionPane.INFORMATION_MESSAGE);

	    this.control.cambiarCaracteristicasHotel(isParqueaderoPago, isParqueaderoGratuito, isPiscina, isZonasHumedas, isBbq, isWifiGratis, isRecepcion24Horas, isAdmiteMascotas);
	}

	protected void graficar() 
	{
		this.setVisible(false);
		new FGraficar(control);
		this.dispose();
	}
	
	protected void eliminarUsuario() {
		this.setVisible(false);
		new FEliminarUsuario(control);
		this.dispose();
		
	}
	
	protected void crearHabitacion() {
		this.setVisible(false);
		new FCrearHabitacion(control);
		this.dispose();
		
	}
	
	protected void crearTarifa() {
		this.setVisible(false);
		new FCrearTarifa(control);
		this.dispose();
		
	}
	
	protected void crearMenuRst() {
		this.setVisible(false);
		new FCrearMenuRestaurante(control);
		this.dispose();
		
	}
	
	protected void crearSpa() {
		this.setVisible(false);
		new FCrearServicioSpa(control);
		this.dispose();
		
	}
	
	protected void crearGuia() {
		this.setVisible(false);
		new FCrearGuia(control);
		this.dispose();
		
	}

	protected void generarHistorial() {
		this.setVisible(false);
		new FGenerarHistorialGrupo(control);
		this.dispose();
		
	}
	

	protected void msgGuardarCambios() {
		JOptionPane.showMessageDialog(this, "Cambios guardados con éxito.", "Guardar", JOptionPane.INFORMATION_MESSAGE);		
	}
	
	protected void msgCerrarSesion() {
		if (JOptionPane.showConfirmDialog(this, "Está seguro que quiere cerrar sesión? Recuerde primero guardar los cambios") ==0) {
			this.setVisible(false);
			new FPrincipal();
			this.dispose();
		}
	}
	

	
}
