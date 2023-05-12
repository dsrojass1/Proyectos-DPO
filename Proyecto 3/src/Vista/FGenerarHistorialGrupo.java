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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import Controlador.Control;

public class FGenerarHistorialGrupo extends JFrame {
	private Control control;
	private JPanel pContenido = new JPanel(new BorderLayout());
	private JPanel pBotones = new JPanel(new GridBagLayout());
	private JPanel pBanner = new JPanel(new GridBagLayout());
	private GridBagConstraints gbcBotones = new GridBagConstraints();
	private JLabel lblTitle = new JLabel("Generar Historial Grupo");
	private JLabel lblIngreseId = new JLabel("Identificación del huésped titular:");
	private JTextField tfId = new JTextField();
	private JTextArea textArea = new JTextArea(22, 60);
	private JButton btGenerarHistorial = new JButton("Generar Historial");
	private JButton btVolver = new JButton("Volver");
	
	public FGenerarHistorialGrupo(Control control) {
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
		
		//Configurar y añadir label y text field
		Font font1 = new Font("Segoe UI", Font.PLAIN, 26); 
		this.lblIngreseId.setFont(font1);
		this.tfId.setFont(font1);
		this.tfId.setPreferredSize(new Dimension(700, 40));
		this.gbcBotones.gridx= 0;
		this.gbcBotones.gridy= 0;
		this.gbcBotones.fill= gbcBotones.HORIZONTAL;
		this.gbcBotones.insets = new Insets(10, 10, 10, 10);
		this.pBotones.add(lblIngreseId, gbcBotones);
		this.gbcBotones.gridy++;
		this.pBotones.add(tfId, gbcBotones);
		this.gbcBotones.gridy++;
		
		//Panel de visualización del historial
		JPanel pHistorial = new JPanel(new GridBagLayout());
		GridBagConstraints gbcHistorial = new GridBagConstraints();
		gbcHistorial.gridx= 0;
		gbcHistorial.gridy= 0;
		gbcHistorial.fill = gbcHistorial.BOTH;
		pHistorial.setPreferredSize(new Dimension(700, 380));
		 // 5 filas y 20 columnas
		textArea.setEditable(false);
        
        // Crear un JScrollPane y agregar el JTextArea a él
        JScrollPane scrollPane = new JScrollPane(textArea);
        pHistorial.add(scrollPane, gbcHistorial);
        this.pBotones.add(pHistorial, this.gbcBotones);
        
        //Botones generar y volver
        this.btGenerarHistorial.setFont(font2);
		this.btGenerarHistorial.setBackground(new Color(28, 81, 223));
		this.btGenerarHistorial.setForeground(new Color(255, 255, 255));
				
		this.btVolver.setFont(font2);
		this.btVolver.setBackground(new Color(28, 81, 223));
		this.btVolver.setForeground(new Color(255, 255, 255));
				
		JPanel pVolver = new JPanel(new GridBagLayout());
		GridBagConstraints gbcVolver = new GridBagConstraints();
		pVolver.setBackground(new Color(171, 194, 255));
		gbcVolver.gridx = 0;
		gbcVolver.gridy = 0;
		gbcVolver.insets = new Insets(10, 10, 10, 10);
		pVolver.add(btGenerarHistorial, gbcVolver);
		gbcVolver.gridx ++;
		pVolver.add(btVolver, gbcVolver);
		
		this.pContenido.add(pVolver, BorderLayout.SOUTH);
        
		//AÑADIR ACTION LISTENERS
		this.btVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				volver();
			}
		});
		
		this.btGenerarHistorial.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				generarHistorial();
			}
		});
    }

	protected void generarHistorial() {
		this.textArea.setText(control.generarHistorialGrupo(this.tfId.getText()));
		
	}

	protected void volver() {
		this.setVisible(false);
		FMenuAdministrador menu = new FMenuAdministrador(this.control);
		menu.setVisible(true);
		this.dispose();
		
	}

}
