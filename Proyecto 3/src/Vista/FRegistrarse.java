package Vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import Controlador.Control;
import persistencia.Persistencia;

public class FRegistrarse extends JFrame implements ActionListener{
	private Control control;
	private JPanel pCenter = new JPanel(new GridBagLayout());
	private JLabel lRegistrarse = new JLabel("Para registrarse en el sistema complete: ", SwingConstants.CENTER);
	private JLabel lUsuario = new JLabel("Usuario");
	private JTextField tfUsuario = new JTextField();
	private JLabel lPassword = new JLabel("Contraseña");
	private JTextField tfPassword = new JTextField();
	private JLabel lRol = new JLabel("Rol (administrador/empleado)");
	private JTextField tfRol = new JTextField();
	private JButton btnRegistrarse = new JButton("Registrarse");
	private JButton btnVolver = new JButton("Ya estás registrado? Inicia Sesión");
	
	FRegistrarse(Control control) {
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
		lRegistrarse.setFont(font2);
		lUsuario.setFont(font1);
		tfUsuario.setFont(font1);
		lPassword.setFont(font1);
		tfPassword.setFont(font1);
		lRol.setFont(font1);
		tfRol.setFont(font1);
		btnRegistrarse.addActionListener(this);
		btnRegistrarse.setFont(font1); 
		btnRegistrarse.setBackground(new Color(28, 81, 223));
		btnRegistrarse.setForeground(Color.WHITE);
		btnVolver.addActionListener(this);
		btnVolver.setFont(font1);
		btnVolver.setOpaque(false);
		btnVolver.setBorderPainted(false);
		btnVolver.setContentAreaFilled(false);
		
		// Agregar componentes al panel central
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(5, 5, 5, 5); // margen entre los componentes
		
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		pCenter.add(lRegistrarse, c);

		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		pCenter.add(lUsuario, c);

		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 10;
		pCenter.add(tfUsuario, c);

		c.gridx = 0;
		c.gridy = 3;
		pCenter.add(lPassword, c);

		c.gridx = 0;
		c.gridy = 4;
		pCenter.add(tfPassword, c);
		
		c.gridx = 0;
		c.gridy = 5;
		pCenter.add(lRol, c);
		
		c.gridx = 0;
		c.gridy = 6;
		pCenter.add(tfRol, c);

		c.gridx = 0;
		c.gridy = 7;
		c.gridwidth = 2;
		pCenter.add(btnVolver, c);

		c.gridx = 0;
		c.gridy = 8;
		pCenter.add(btnRegistrarse, c);


		// Agregar panel central al JFrame
		this.add(pCenter, BorderLayout.CENTER);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton boton = (JButton)e.getSource();	
		String c = boton.getText();
		
		if (c.equals("Ya estás registrado? Inicia Sesión")) {
			this.setVisible(false);
			this.setVisible(false);
			FPrincipal principal = new FPrincipal();
			this.dispose();
		}
		else if (c.equals("Registrarse")){
			boolean creado = control.registrarUsuario(this.tfUsuario.getText(), this.tfPassword.getText(), this.tfRol.getText());
			if (creado) {
				JOptionPane.showMessageDialog(this, "Usuario registrado con éxito. Por favor, inicia sesión.", "Registro Exitoso", JOptionPane.INFORMATION_MESSAGE);
				control.guardarCambios();
				this.setVisible(false);
				FPrincipal principal = new FPrincipal();
				this.dispose();
			} else {
				JOptionPane.showMessageDialog(this, "Ya existe un usuario con el mismo login. Vuelve a intentarlo.", "Registro Fallido", JOptionPane.INFORMATION_MESSAGE);

			}
		}
		
	}
	

}
