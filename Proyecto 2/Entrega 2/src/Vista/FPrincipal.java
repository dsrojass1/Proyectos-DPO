package Vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import Controlador.Control;
import persistencia.Persistencia;

public class FPrincipal extends JFrame implements ActionListener, Observer  {

	// Atributos
	private Control control;
	private JFrame FRegistrarse;
	private JFrame FMenuAdministrador;
	
	private JPanel pCenter = new JPanel(new GridBagLayout());
	private JLabel lBienvenida = new JLabel("Bienvenido al sistema PMS", SwingConstants.CENTER);
	private JLabel lUsuario = new JLabel("Usuario");
	private JTextField tfUsuario = new JTextField();
	private JLabel lPassword = new JLabel("Contraseña");
	private JPasswordField pfPassword = new JPasswordField();
	private JButton btnIniciarSesion = new JButton("Iniciar Sesión");;
	private JButton btnRegistrarse = new JButton("Registrarse");
	private JLabel lInfoUsuarios = new JLabel("0" , SwingConstants.CENTER);
	private JLabel lInfoUsuarios2 = new JLabel("Usuarios registrados en el sistema:", SwingConstants.CENTER);
	private JLabel lDerechos = new JLabel ("Proyecto 2 - Diseño y Programación Orientada a Objetos 23-1 ©", SwingConstants.CENTER);

	// Constructor
	public FPrincipal() {

		this.control = new Control(this);
		this.FRegistrarse = new FRegistrarse(this.control);
		this.FMenuAdministrador = new FMenuAdministrador(this.control);
		control.cargarSerializable();
		
		// Configurar ventana
		Image icon = Toolkit.getDefaultToolkit().getImage("docs/icon.png");  
		this.setIconImage(icon);
		this.setTitle("Sistema PMS (Property Managment System)");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(900, 900);
		this.setLayout(new BorderLayout());
		this.setVisible(true);
		 
		
		
		// Crear paneles para los bordes izquierdo y derecho
		JPanel leftPanel = new JPanel();
		JPanel rightPanel = new JPanel();
		leftPanel.setBackground(new Color(16, 46, 128));
		leftPanel.setPreferredSize(new Dimension(100, 600));
		rightPanel.setBackground(new Color(16, 46, 128));
		rightPanel.setPreferredSize(new Dimension(100, 600));

		// Agregar paneles de los bordes y panel central
		this.add(leftPanel, BorderLayout.WEST);
		this.add(rightPanel, BorderLayout.EAST);
		
		// Configurar panel central y sus elementos
		pCenter.setBackground(new Color(116, 201, 255));
		Font font1 = new Font("Segoe UI", Font.PLAIN, 26); 
		Font font2 = new Font("Segoe UI", Font.BOLD, 26);
		lBienvenida.setFont(font2); 
		lUsuario.setFont(font1);
		tfUsuario.setFont(font1);
		lPassword.setFont(font1);
		pfPassword.setFont(font1); 
		btnIniciarSesion.addActionListener(this);
		btnIniciarSesion.setFont(font1); 
		btnIniciarSesion.setBackground(new Color(28, 81, 223));
		btnIniciarSesion.setForeground(Color.WHITE);
		btnRegistrarse.setFont(font1);
		btnRegistrarse.setBackground(new Color(28, 81, 223));
		btnRegistrarse.setForeground(Color.WHITE);
		btnRegistrarse.addActionListener(this);
		lInfoUsuarios = new JLabel(control.getNumeroUsuarios());
		
		// Agregar componentes al panel central
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(5, 5, 5, 5); // margen entre los componentes

		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		pCenter.add(lBienvenida, c);

		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		pCenter.add(lUsuario, c);

		c.gridx = 1;
		c.gridy = 1;
		pCenter.add(tfUsuario, c);

		c.gridx = 0;
		c.gridy = 2;
		pCenter.add(lPassword, c);

		c.gridx = 1;
		c.gridy = 2;
		pCenter.add(pfPassword, c);

		c.gridx = 0;
		c.gridy = 3;
		c.gridwidth = 2;
		pCenter.add(btnIniciarSesion, c);

		c.gridx = 0;
		c.gridy = 4;
		pCenter.add(btnRegistrarse, c);
		
		c.gridx = 0;
		c.gridy = 5;
		c.gridwidth = 1;
		pCenter.add(lInfoUsuarios2, c);
		
		c.gridx = 1;
		c.gridy = 5;
		c.gridwidth = 1;
		pCenter.add(lInfoUsuarios, c);
		
		c.gridx = 0;
		c.gridy = 6;
		c.gridwidth = 2;
		pCenter.add(lDerechos, c);


		// Agregar panel central al JFrame
		this.add(pCenter, BorderLayout.CENTER);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton boton = (JButton)e.getSource();	
		String c = boton.getText();
		
		if (c.equals("Registrarse")) {
			this.setVisible(false);
		    this.FRegistrarse.setVisible(true);
		    this.dispose();
		}
		else if (c.equals("Iniciar Sesión")){
			String autenticacion = control.getAutenticacionUsuario(this.tfUsuario.getText(), this.pfPassword.getText());
			if (autenticacion.equalsIgnoreCase("Administrador")){
				this.setVisible(false);
			    this.FMenuAdministrador.setVisible(true);
			    this.dispose();
			}
			else if (autenticacion.equalsIgnoreCase("Empleado")) {
				//TODO IMPLEMENTAR MENU EMPLEADO
			}
			else {
				JOptionPane.showMessageDialog(this, "Login o contraseña incorrectos. Revise y vuelva a intentarlo.", "Inicio de Sesión Fallido", JOptionPane.INFORMATION_MESSAGE);
			}
		}
		
	}
	
	@Override
	public void update(Observable o, Object arg) {
		this.lInfoUsuarios.setText((String) arg);
		
	}
	
	
	
	// Método principal
		public static void main(String[] args) {
			new FPrincipal();
		}

		

}

