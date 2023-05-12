package Vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import Controlador.Control;

public class FInventarioServicio extends JFrame{

	private Control control;
	private JPanel JInnerBox;
	private JPanel JOuterBox;
	private JPanel JTitulo;
	
	public FInventarioServicio(Control control) 
	{
		this.control=control;
		
		//Inicializar pantalla----------
		this.setSize(900,900);
		this.setVisible(true);
		this.setMinimumSize(new Dimension(750,600));
		this.setTitle("Inventario de servicios");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Colores, labels, etc--------
		Color outLine = Color.decode("#5AB7FA");
		Color darkblue=Color.decode("#1846be");
		Color innerColor = Color.decode("#9DB7F0");
		
		Font Ftitulo=new Font("Arial", Font.BOLD, 20);
		Font Fforms = new Font("Arial Black", Font.BOLD, 10);
		
		JLabel Ltitulo = new JLabel("Inventario de servicios");
		Ltitulo.setFont(Ftitulo);
		Ltitulo.setForeground(Color.WHITE);
		Ltitulo.setHorizontalAlignment(JLabel.CENTER);
		//-----------------------
		
		JButton volver = new JButton("Volver");
		
		volver.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				volverAtras();
			}
		});
		
		JPanel content = mostrarInventarioS();
		
		//--------------------------------------------
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
		JInnerBox.add(content, BorderLayout.CENTER);
		JInnerBox.add(volver, BorderLayout.SOUTH);
		this.add(JOuterBox);
	}
	
	private JPanel mostrarInventarioS() 
	{	
		ArrayList<String[]> Data = this.control.mostrarInventarioServicios();
		int dtSize = Data.size();
		JPanel Inventario = new JPanel();
		Inventario.setLayout(new GridLayout(dtSize+1,1));
		
		JPanel headers = new JPanel();
		headers.setLayout(new GridLayout(1,3));
		headers.setBackground(Color.LIGHT_GRAY);
		
		JLabel id= new JLabel("ID");
		JLabel precio =  new JLabel("Precio");
		JLabel descripcion = new JLabel("Descripcion");
		
		headers.add(id);
		headers.add(precio);
		headers.add(descripcion);
		
		Inventario.add(headers);
		
		for(String[] servicio: Data) 
		{
			JPanel PListItem = new JPanel();
			PListItem.setLayout(new GridLayout(1,3));
			JLabel nombreS = new JLabel(servicio[0]);
			JLabel precioS = new JLabel(servicio[1]);
			JLabel descripcionS = new JLabel(servicio[2]);
			
			PListItem.add(nombreS);
			PListItem.add(precioS);
			PListItem.add(descripcionS);
			Inventario.add(PListItem);
		}
		
		
		return Inventario;
	}
	
	private void volverAtras() 
	{
		this.setVisible(false);
		FMenuEmpleado menu= new FMenuEmpleado(this.control);
		menu.setVisible(true);
		this.dispose();
	}
}
