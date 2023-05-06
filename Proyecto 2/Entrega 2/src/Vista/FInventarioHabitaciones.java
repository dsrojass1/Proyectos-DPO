package Vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import Controlador.Control;
import Modelo.Habitacion;

public class FInventarioHabitaciones extends JFrame{
	
	private Control control;
	private JPanel JInnerBox;
	private JPanel JOuterBox;
	private JPanel JTitulo;
	private Boolean askforIDs;
	private JTextArea inventariotext;
	
	public FInventarioHabitaciones(Control control, Boolean askIDs) 
	{
		this.control=control;
		this.askforIDs=askIDs;
		
		//Inicializar pantalla----------
		this.setSize(900,900);
		this.setVisible(true);
		this.setMinimumSize(new Dimension(750,600));
		this.setTitle("Inventario de habitaciones");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Colores, labels, etc--------
		Color outLine = Color.decode("#5AB7FA");
		Color darkblue=Color.decode("#1846be");
		Color innerColor = Color.decode("#9DB7F0");
		
		Font Ftitulo=new Font("Arial", Font.BOLD, 20);
		Font Fforms = new Font("Arial", Font.ROMAN_BASELINE, 10);
		
		//Panels
		JPanel Inventario = GetInventarioH(Fforms);
		JPanel controles = controls(this.askforIDs);
		
		//Labels
		JLabel Ltitulo = new JLabel("Inventario de habitaciones");
		Ltitulo.setFont(Ftitulo);
		Ltitulo.setForeground(Color.WHITE);
		Ltitulo.setHorizontalAlignment(JLabel.CENTER);
		
		
		this.JOuterBox = new JPanel();
		JOuterBox.setBackground(outLine);
		JOuterBox.setLayout(new BorderLayout());
		
		this.JInnerBox = new JPanel();
		JInnerBox.setBackground(innerColor);
		JInnerBox.setLayout(new BorderLayout());
		
		this.JTitulo = new JPanel();
		JTitulo.setBackground(darkblue);
		JTitulo.setLayout(new GridLayout(3,0,2,2));
		JTitulo.add(new JLabel("   "));
		JTitulo.add(Ltitulo);
		
		//Añadir al frame
		JOuterBox.add(new JLabel("     "), BorderLayout.NORTH);
		JOuterBox.add(new JLabel("     "), BorderLayout.SOUTH);
		JOuterBox.add(new JLabel("     "), BorderLayout.EAST);
		JOuterBox.add(new JLabel("     "), BorderLayout.WEST);
		JOuterBox.add(JInnerBox, BorderLayout.CENTER);
		
		
		JInnerBox.add(this.JTitulo, BorderLayout.NORTH);
		JInnerBox.add(Inventario, BorderLayout.CENTER);
		JInnerBox.add(controles, BorderLayout.SOUTH);
		
		
		this.add(JOuterBox);
	}
	
	private JPanel GetInventarioH(Font fuente) 
	{
		JPanel Inventario = new JPanel();
		Inventario.setLayout(new BorderLayout());
		inventariotext = new JTextArea();
		inventariotext.setForeground(Color.BLACK);
		inventariotext.setEditable(false);
		JScrollPane Listascroll = new JScrollPane(inventariotext);
		
		this.inventariotext.setText(this.control.getInventariohabitaciones(null));
		inventariotext.setFont(fuente);
		
		
		Inventario.add(Listascroll);
		return Inventario;
	}
	
	private JPanel controls(Boolean ask) 
	{
		
		JPanel controles = new JPanel();
		controles.setLayout(new FlowLayout(FlowLayout.RIGHT));
		if(!ask)  
		{
			JButton volverb;
			
			//botones
			volverb=new JButton("regresar");
			volverb.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				volverAtras();
			}
			});
			controles.add(volverb);
		}
		return controles;
	}
	
	private void volverAtras() 
	{
		this.setVisible(false);
		FMenuEmpleado menu= new FMenuEmpleado(this.control);
		menu.setVisible(true);
		this.dispose();
	}
	
	public void actualizarInventario(String newInventario)
	{
		this.inventariotext.setText(newInventario);
		this.repaint();
	}
	
	public String getnewId(String name) 
	{
		String id = JOptionPane.showInputDialog(this, "Digite el id que de la habitacion que se le asignara a "+name);
		return id;
	}
}
