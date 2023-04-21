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
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Controlador.Control;

public class FCrearTarifa extends JFrame {
	private Control control;
	private JPanel pContenido = new JPanel(new BorderLayout());
	private JPanel pBotones = new JPanel(new GridBagLayout());
	private JPanel pBanner = new JPanel(new GridBagLayout());
	private GridBagConstraints gbcBotones = new GridBagConstraints();
	private JLabel lblTitle = new JLabel("Crear Tarifa");
	
	private ArrayList<JLabel> lblArreglo = new ArrayList<JLabel>();
	private HashMap<String, JTextField> tfHash = new HashMap<String, JTextField>();
	
	private JButton btGuardarTarifa = new JButton("Guardar Tarifa");
	private JButton btVolver = new JButton("Volver");
	
	FCrearTarifa(Control control){
		// Configuraciones del Frame
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
		this.pBotones.setPreferredSize(new Dimension(850, 720));
		this.pBotones.setBackground(new Color(171, 194, 255));
		this.pContenido.add(this.pBotones, BorderLayout.CENTER);
		
		// Crear los labels con sus respectivos text fields
		String [] textoLabels = {"Precio:", "Tipo Habitación:", "Fecha Inicio Vigencia: (AAAA/MM/DD)", "Fecha Fin Vigencia: (AAAA/MM/DD)", "Días Vigencia: (Separados por \",\")"};
		Font font1 = new Font("Segoe UI", Font.PLAIN, 26); 
		this.gbcBotones.gridx = 0;
		this.gbcBotones.gridy = 0;
		this.gbcBotones.fill = gbcBotones.HORIZONTAL;
		this.gbcBotones.insets = new Insets(5, 5, 5, 5);
		for (String txt: textoLabels) {
			JLabel nuevoLbl = new JLabel(txt);
			nuevoLbl.setFont(font1);
			JTextField nuevoTf = new JTextField();
			nuevoTf.setFont(font1);
			this.tfHash.put(txt, nuevoTf);
			this.pBotones.add(nuevoLbl, gbcBotones);
			this.gbcBotones.gridy ++;
			this.pBotones.add(nuevoTf, gbcBotones);
			this.gbcBotones.gridy ++;
		}
		
		// Crear botones crear tarifa y volver
		this.btGuardarTarifa.setFont(font2);
		this.btGuardarTarifa.setBackground(new Color(28, 81, 223));
		this.btGuardarTarifa.setForeground(new Color(255, 255, 255));
		
		this.btVolver.setFont(font2);
		this.btVolver.setBackground(new Color(28, 81, 223));
		this.btVolver.setForeground(new Color(255, 255, 255));
		
		JPanel pVolver = new JPanel(new GridBagLayout());
		GridBagConstraints gbcVolver = new GridBagConstraints();
		pVolver.setBackground(new Color(171, 194, 255));
		gbcVolver.gridx = 0;
		gbcVolver.gridy = 0;
		gbcVolver.insets = new Insets(10, 10, 10, 10);
		pVolver.add(btGuardarTarifa, gbcVolver);
		gbcVolver.gridx ++;
		pVolver.add(btVolver, gbcVolver);
		
		pBotones.add(pVolver, gbcBotones);
		
		this.btVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				volver();
			}
		});
		
		//AGREGAR ACTIONLISTENER A LOS BOTONES
		
		this.btGuardarTarifa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				guardarTarifa();
			}
		});
		
	}

	protected void guardarTarifa() {
		if (control.crearTarifa(Integer.parseInt(this.tfHash.get("Precio:").getText()), this.tfHash.get("Tipo Habitación:").getText(), this.tfHash.get("Fecha Inicio Vigencia: (AAAA/MM/DD)").getText(), this.tfHash.get("Fecha Fin Vigencia: (AAAA/MM/DD)").getText(), this.tfHash.get("Días Vigencia: (Separados por \",\")").getText())) {
			JOptionPane.showMessageDialog(this, "Tarifa agregada al inventario con éxito.", "Tarifa añadida al inventario", JOptionPane.INFORMATION_MESSAGE);
		}
		else {
			JOptionPane.showMessageDialog(this, "Ya hay una tarifa para el mismo tipo de habitación en el mismo rango de fechas. Se tomará la más económica.", "Tarifa idéntica detectada", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	protected void volver() {
		this.setVisible(false);
		FMenuAdministrador menu = new FMenuAdministrador(this.control);
		menu.setVisible(true);
		this.dispose();
		
	}
		

}
