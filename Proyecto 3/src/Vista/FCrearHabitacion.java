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

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import Controlador.Control;

public class FCrearHabitacion extends JFrame {
	
	private Control control;
	private JPanel pContenido = new JPanel(new BorderLayout());
	private JPanel pBotones = new JPanel(new GridBagLayout());
	private JPanel pBanner = new JPanel(new GridBagLayout());
	private GridBagConstraints gbcBotones = new GridBagConstraints();
	private JLabel lblTitle = new JLabel("Crear Habitación");
	
	private JTextField tfId = new JTextField();
	private JTextField tfDescripcion = new JTextField();
	private JTextField tfTipoHabitacion = new JTextField();
	private JTextField tfUbicacion = new JTextField();
	
	private JCheckBox cbBalcon = new JCheckBox();
	private JCheckBox cbVista = new JCheckBox();
	private JCheckBox cbCocina = new JCheckBox();
	
	private JTextField tfCamas = new JTextField();
	
	private JTextField tfMetrosCuadrados = new JTextField();
	private JCheckBox cbTieneAireAcondicionado = new JCheckBox();
	private JCheckBox cbTieneCalefaccion = new JCheckBox();
	private JCheckBox cbTieneTv = new JCheckBox();
	private JCheckBox cbTieneCafetera = new JCheckBox();
	private JCheckBox cbTieneRopaHipoalergenica = new JCheckBox();
	private JCheckBox cbTienePlancha = new JCheckBox();
	private JCheckBox cbTieneSecadorPelo = new JCheckBox();
	private JTextField tfVoltajeAc = new JTextField();
	private JCheckBox cbTieneTomasUsbA = new JCheckBox(); 
	private JCheckBox cbTieneTomasUsbC = new JCheckBox(); 
	private JCheckBox cbIncluyeDesayuno = new JCheckBox(); 
	
	
	
	private JButton btConfigCamas = new JButton("Configurar Camas");
	private JButton btVolver = new JButton("Volver");
	
	
	FCrearHabitacion(Control control){
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
		topPanel.setPreferredSize(new Dimension(900, 20));
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
		
		// Crear los labels
		ArrayList<JLabel> arregloLabels = new ArrayList<JLabel>();
		ArrayList<String> arregloTxtLabels = new ArrayList<String>();
		arregloTxtLabels.add("Id del Servicio:");
		arregloTxtLabels.add("Descripción:");
		arregloTxtLabels.add("Tipo de habitación:");
		arregloTxtLabels.add("Ubicación:");
		arregloTxtLabels.add("Balcón:");
		arregloTxtLabels.add("Vista:");
		arregloTxtLabels.add("Cocina integrada:");
		arregloTxtLabels.add("Número de camas:");
		arregloTxtLabels.add("Tamaño en metros cuadrados:");
		arregloTxtLabels.add("Tiene aire acondicionado:");
		arregloTxtLabels.add("Tiene calefaccion:");
		arregloTxtLabels.add("Tiene TV:");
		arregloTxtLabels.add("Tiene cafetera:");
		arregloTxtLabels.add("Tiene ropa de cama y tapetes hipoalergénicos:");
		arregloTxtLabels.add("Tiene plancha:");
		arregloTxtLabels.add("Tiene secador de pelo:");
		arregloTxtLabels.add("Voltaje AC:");
		arregloTxtLabels.add("Tiene tomas USB-A:");
		arregloTxtLabels.add("Tiene tomas USB-C:");
		arregloTxtLabels.add("Incluye desayuno:");
		Font font1 = new Font("Segoe UI", Font.PLAIN, 15); 
		this.gbcBotones.gridx = 0;
		this.gbcBotones.gridy = 0;
		this.gbcBotones.gridwidth = 1;
		this.gbcBotones.anchor = GridBagConstraints.WEST;
		this.gbcBotones.insets = new Insets(3, 3, 3, 3);
		for (String txtlbl: arregloTxtLabels) {
			JLabel nuevoLabel = new JLabel(txtlbl);
			nuevoLabel.setFont(font1);
			this.pBotones.add(nuevoLabel, this.gbcBotones);
			this.gbcBotones.gridy++;
			arregloLabels.add(nuevoLabel);
		}
		
		// Configurar y Agregar textfields
		tfId.setFont(font1);
		tfId.setPreferredSize(new Dimension(400, 23));
		tfDescripcion.setFont(font1);
		tfTipoHabitacion.setFont(font1);
		tfUbicacion.setFont(font1);
		this.gbcBotones.gridx = 1;
		this.gbcBotones.gridy = 0;
		this.gbcBotones.fill = this.gbcBotones.HORIZONTAL;
		this.pBotones.add(tfId, this.gbcBotones);
		this.gbcBotones.gridy ++;
		this.pBotones.add(tfDescripcion, this.gbcBotones);
		this.gbcBotones.gridy ++;
		this.pBotones.add(tfTipoHabitacion, this.gbcBotones);
		this.gbcBotones.gridy ++;
		this.pBotones.add(tfUbicacion, this.gbcBotones);
		this.gbcBotones.gridy ++;
		this.gbcBotones.fill = this.gbcBotones.NONE;
		this.pBotones.add(this.cbBalcon, this.gbcBotones);
		this.gbcBotones.gridy ++;
		this.pBotones.add(this.cbVista, this.gbcBotones);
		this.gbcBotones.gridy ++;
		this.pBotones.add(this.cbCocina, this.gbcBotones);
		this.gbcBotones.gridy ++;
		
		this.tfCamas.setPreferredSize(new Dimension(120, 23));
		this.tfCamas.setFont(font1);
		this.pBotones.add(this.tfCamas, this.gbcBotones);
		
		this.gbcBotones.gridy ++;
		
		this.tfMetrosCuadrados.setPreferredSize(new Dimension(120, 23));
		this.tfMetrosCuadrados.setFont(font1);
		this.pBotones.add(this.tfMetrosCuadrados, this.gbcBotones);
		
		this.gbcBotones.gridy ++;
		
		this.pBotones.add(this.cbTieneAireAcondicionado, this.gbcBotones);
		this.gbcBotones.gridy ++;
		
		this.pBotones.add(this.cbTieneCalefaccion, this.gbcBotones);
		this.gbcBotones.gridy ++;

		this.pBotones.add(this.cbTieneTv, this.gbcBotones);
		this.gbcBotones.gridy ++;
		
		this.pBotones.add(this.cbTieneCafetera, this.gbcBotones);
		this.gbcBotones.gridy ++;
		
		this.pBotones.add(this.cbTieneRopaHipoalergenica, this.gbcBotones);
		this.gbcBotones.gridy ++;
		
		this.pBotones.add(this.cbTienePlancha, this.gbcBotones);
		this.gbcBotones.gridy ++;
		
		this.pBotones.add(this.cbTieneSecadorPelo, this.gbcBotones);
		this.gbcBotones.gridy ++;
		
		this.tfVoltajeAc.setPreferredSize(new Dimension(120, 23));
		this.tfMetrosCuadrados.setFont(font1);
		this.pBotones.add(this.tfVoltajeAc, this.gbcBotones);
		this.gbcBotones.gridy ++;
		
		this.pBotones.add(this.cbTieneTomasUsbA, this.gbcBotones);
		this.gbcBotones.gridy ++;
		
		this.pBotones.add(this.cbTieneTomasUsbC, this.gbcBotones);
		this.gbcBotones.gridy ++;
		
		this.pBotones.add(this.cbIncluyeDesayuno, this.gbcBotones);
		this.gbcBotones.gridy ++;
		this.gbcBotones.gridy ++;
		
		// CONFIGURAR Y AGREGAR BOTONES
		this.btConfigCamas.setFont(font2);
		this.btConfigCamas.setBackground(new Color(28, 81, 223));
		this.btConfigCamas.setForeground(new Color(255, 255, 255));
	

		this.btVolver.setFont(font2);
		this.btVolver.setBackground(new Color(28, 81, 223));
		this.btVolver.setForeground(new Color(255, 255, 255));

		
		this.gbcBotones.gridy ++;
		this.gbcBotones.gridx = 0;
		this.pBotones.add(this.btConfigCamas, this.gbcBotones);
		this.gbcBotones.gridx ++;
		this.pBotones.add(this.btVolver, this.gbcBotones);
		
		//AGREGAR ACTIONLISTENER A LOS BOTONES
		
		this.btConfigCamas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				configCamas();
			}
		});
		
		this.btVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				volver();
			}
		});

	}

	protected void configCamas() {
		ArrayList<String> camas = new ArrayList<String>();
		int capacidadHabitacion = 0;
		for (int i = 1; i <= Integer.parseInt(this.tfCamas.getText()); i++) {
			
			String tamanio = JOptionPane.showInputDialog(this, "Ingrese el tamaño de la cama '"+i+"' (altoxancho):");

	        
	        int capacidad = Integer.parseInt(JOptionPane.showInputDialog(this, "Digite la capacidad de la cama '"+i+"' :"));

	        
	        boolean soloParaNinios = JOptionPane.showInputDialog(this, "La cama '"+i+"' es sólo para niños? (true o false):").equalsIgnoreCase("true");
	        
	        String nuevaCama = tamanio + ";" + capacidad + ";" + soloParaNinios;
	        camas.add(nuevaCama);
	        capacidadHabitacion += capacidad;
		}
		
		if (control.crearHabitacion(this.tfId.getText(), this.tfDescripcion.getText(), this.tfTipoHabitacion.getText(), this.tfUbicacion.getText(), capacidadHabitacion, this.cbBalcon.isSelected(), this.cbVista.isSelected(), this.cbCocina.isSelected(), camas,
				//PROYECTO 3
				Integer.parseInt(this.tfMetrosCuadrados.getText()), this.cbTieneAireAcondicionado.isSelected(), this.cbTieneCalefaccion.isSelected(), this.cbTieneTv.isSelected(), this.cbTieneCafetera.isSelected(), this.cbTieneRopaHipoalergenica.isSelected(), 
				this.cbTienePlancha.isSelected(), this.cbTieneSecadorPelo.isSelected(), Integer.parseInt(this.tfVoltajeAc.getText()), this.cbTieneTomasUsbA.isSelected(), this.cbTieneTomasUsbC.isSelected() , this.cbIncluyeDesayuno.isSelected()
				)) {
			JOptionPane.showMessageDialog(this, "Habitacion agregada al inventario con éxito.", "Habitacion añadida al inventario", JOptionPane.INFORMATION_MESSAGE);
		}else {
			JOptionPane.showMessageDialog(this, "Ya existe una habitación con el id ingresado. Verifique el id y vuelva a intentarlo.", "Error al crear habitación", JOptionPane.INFORMATION_MESSAGE);
		}
		
	}
	
	protected void volver() {
		this.setVisible(false);
		FMenuAdministrador menu = new FMenuAdministrador(this.control);
		menu.setVisible(true);
		this.dispose();
		
	}


}
