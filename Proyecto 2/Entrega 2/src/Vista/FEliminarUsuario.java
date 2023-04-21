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

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Controlador.Control;

public class FEliminarUsuario extends JFrame {
	private Control control;
	private JPanel pContenido = new JPanel(new BorderLayout());
	private JPanel pBanner = new JPanel(new GridBagLayout());
	private GridBagConstraints gbcBotones = new GridBagConstraints();
	private JLabel lblTitle = new JLabel("Eliminar Usuario");
	private JPanel pBotones = new JPanel(new GridBagLayout());
	private JLabel lblLogin = new JLabel("Login del usuario a eliminar:");
	private JTextField tfUsuario = new JTextField();
	private JButton btEliminar = new JButton("Eliminar Usuario");
	private JButton btVolver = new JButton("Volver");
	
	FEliminarUsuario(Control control){
		this.control = control;
		Image icon = Toolkit.getDefaultToolkit().getImage("docs/icon.png");  
		this.setIconImage(icon);
		this.setTitle("Sistema PMS (Property Managment System)");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(900, 900);
		this.setPreferredSize(new Dimension(850, 850));
		this.setBackground(new Color(171, 194, 255));
		this.setVisible(true);
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
		
		this.pBanner.setPreferredSize(new Dimension(850, 130));
		this.pBanner.setBackground(new Color(28, 81, 223));
		this.pBanner.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		Font font2 = new Font("Segoe UI", Font.BOLD, 29);
		this.lblTitle.setFont(font2);
		this.lblTitle.setForeground(new Color(255, 255, 255));
		this.pBanner.add(lblTitle, gbc);
		this.pContenido.add(this.pBanner, BorderLayout.NORTH);
		
		Font font1 = new Font("Segoe UI", Font.PLAIN, 26); 
		this.lblLogin.setFont(font1);
		this.btEliminar.setFont(font2);
		this.btEliminar.setBackground(new Color(28, 81, 223));
		this.btEliminar.setForeground(new Color(255, 255, 255));
		this.btEliminar.setPreferredSize(new Dimension(400, 90));
		this.btVolver.setFont(font2);
		this.btVolver.setBackground(new Color(28, 81, 223));
		this.btVolver.setForeground(new Color(255, 255, 255));
		this.btVolver.setPreferredSize(new Dimension(400, 90));
		
		this.tfUsuario.setFont(font1);
		
		this.pBotones.setPreferredSize(new Dimension(850, 720));
		this.pBotones.setBackground(new Color(171, 194, 255));
		this.pBotones.setLayout(new GridBagLayout());
		gbcBotones.insets = new Insets(0, 0, 10, 0);
		gbcBotones.gridx = 0;
		gbcBotones.gridy = 0;
		gbcBotones.gridwidth = 1;
		gbcBotones.fill = gbcBotones.HORIZONTAL;
		this.pBotones.add(this.lblLogin, this.gbcBotones);
		gbcBotones.gridy ++;
		this.pBotones.add(this.tfUsuario, this.gbcBotones);
		gbcBotones.gridy ++;
		this.pBotones.add(this.btEliminar, this.gbcBotones);
		gbcBotones.gridy ++;
		this.pBotones.add(this.btVolver, this.gbcBotones);
		
		this.pContenido.add(this.pBotones, BorderLayout.WEST);
		
		// Agregar ActionListener a cada botón
		this.btEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				eliminarUsuario();
			}
		});
		
		this.btVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				volver();
			}
		});
		
	}

	protected void eliminarUsuario() {
		if (this.control.eliminarUsuario(this.tfUsuario.getText())) {
			JOptionPane.showMessageDialog(this, "Usuario eliminado con éxito.", "Usuario eliminado del sistema", JOptionPane.INFORMATION_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(this, "Ningún usuario coincide con el login ingresado. Revise y vuelva a intentarlo.", "Usuario eliminado del sistema", JOptionPane.INFORMATION_MESSAGE);
		}
		
	}
	
	protected void volver() {
		this.setVisible(false);
		FMenuAdministrador menu =new FMenuAdministrador(this.control);
		menu.setVisible(true);
		this.dispose();
		
		
	}
}
