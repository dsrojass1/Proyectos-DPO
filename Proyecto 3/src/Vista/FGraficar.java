package Vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import Controlador.Control;

public class FGraficar extends JFrame{
	private Control control;
	private JPanel JInnerBox;
	private JPanel JOuterBox;
	private JPanel JTitulo;
	
	
	public FGraficar(Control control) {
		this.control=control;
			
		//Inicializar pantalla----------
		this.setSize(900,900);
		this.setVisible(true);
		this.setMinimumSize(new Dimension(750,600));
		this.setTitle("Realizar una nueva reserva");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Colores, labels, etc--------
		Color outLine = Color.decode("#5AB7FA");
		Color darkblue=Color.decode("#1846be");
		Color innerColor = Color.decode("#9DB7F0");
		
		Font Ftitulo=new Font("Arial", Font.BOLD, 20);
		//Font Fforms = new Font("Arial Black", Font.BOLD, 10);
		
		//Labels
		JLabel Ltitulo = new JLabel("Graficar");
		Ltitulo.setFont(Ftitulo);
		Ltitulo.setForeground(Color.WHITE);
		Ltitulo.setHorizontalAlignment(JLabel.CENTER);
		
		
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
		
		this.add(JOuterBox);
		this.setVisible(true);
	}

}
