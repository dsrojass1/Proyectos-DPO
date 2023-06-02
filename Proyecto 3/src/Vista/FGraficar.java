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
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.jfree.chart.ChartPanel;

import Controlador.Control;

public class FGraficar extends JFrame{
	private Control control;
	private JPanel JInnerBox;
	private JPanel JOuterBox;
	private JPanel JTitulo;
	private JPanel JGrafica;
	
	
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
		
		JButton volver = new JButton("Volver");
		
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
		
		this.JGrafica = Graficar();
		
		
		//Añadir al frame
		JOuterBox.add(new JLabel("     "), BorderLayout.NORTH);
		JOuterBox.add(new JLabel("     "), BorderLayout.SOUTH);
		JOuterBox.add(new JLabel("     "), BorderLayout.EAST);
		JOuterBox.add(new JLabel("     "), BorderLayout.WEST);
		JOuterBox.add(JInnerBox, BorderLayout.CENTER);
		
		JInnerBox.add(JTitulo, BorderLayout.NORTH);
		JInnerBox.add(JGrafica, BorderLayout.CENTER);
		JInnerBox.add(volver, BorderLayout.SOUTH);
		
		
		this.add(JOuterBox);
		this.setVisible(true);
		
		
		//botons actions----------------------
		
		volver.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				volverAtras();
			}
		});
	}
	
	private JPanel Graficar() 
	{
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(3,2));
		//-----------------------------------
		ChartPanel Cpanel1 = this.control.graficarConsumosGlobales();
		Cpanel1.setMouseWheelEnabled(true);
		panel.add(Cpanel1);
		//------------------------------------
		ChartPanel Cpanel2 = this.control.graficarValorConsumosPorFecha();
		Cpanel2.setMouseWheelEnabled(true);
		panel.add(Cpanel2);
		
		//------------------------------------
		ChartPanel Cpanel3 = this.control.graficarConsumosPorSemana();
		Cpanel3.setMouseWheelEnabled(true);
		panel.add(Cpanel3);
		
		//------------------------------------
		ChartPanel Cpanel4 = this.control.graficarConsumosPorPrecio();
		Cpanel4.setMouseWheelEnabled(true);
		panel.add(Cpanel4);
		
		return panel;
	}
	
	private void volverAtras() 
	{
		this.setVisible(false);
		FMenuAdministrador menu= new FMenuAdministrador(this.control);
		menu.setVisible(true);
		this.dispose();
	}

}
