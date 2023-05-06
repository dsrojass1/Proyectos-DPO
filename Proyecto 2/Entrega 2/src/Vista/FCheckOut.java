package Vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import Controlador.Control;

public class FCheckOut extends JFrame{
	
	private Control control;
	private JPanel JInnerBox;
	private JPanel JOuterBox;
	private JPanel JTitulo;
	private JTextArea JHistorialTextA;
	
	public FCheckOut(Control control) 
	{
		this.control=control;
		
		//Inicializar pantalla----------
		this.setSize(900,900);
		this.setVisible(true);
		this.setMinimumSize(new Dimension(750,600));
		this.setTitle("Hacer check out");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Colores, labels, etc--------
		Color outLine = Color.decode("#5AB7FA");
		Color darkblue=Color.decode("#1846be");
		Color innerColor = Color.decode("#9DB7F0");
		
		Font Ftitulo=new Font("Arial", Font.BOLD, 20);
		Font Fforms = new Font("Arial Black", Font.BOLD, 10);
		
		JLabel Ltitulo = new JLabel("Hacer checkout");
		Ltitulo.setFont(Ftitulo);
		Ltitulo.setForeground(Color.WHITE);
		Ltitulo.setHorizontalAlignment(JLabel.CENTER);
		//-----------------------
		
		JPanel content = finalPanel(Fforms);
		
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
		this.add(JOuterBox);
	}
	
	private JPanel finalPanel(Font font) 
	{
		JPanel principal = new JPanel();
		principal.setLayout(new BorderLayout());
		principal.setOpaque(false);
		
		JPanel controles = new JPanel();
		controles.setLayout(new BorderLayout());
		controles.setOpaque(false);
		
		this.JHistorialTextA =  new JTextArea("Historial+\n");
		JHistorialTextA.setEditable(false);
		
		JPanel Jhistorial =  new JPanel();
		Jhistorial.setLayout(new BorderLayout());
		JPanel JhistTitle = new JPanel();
		JhistTitle.setBackground(Color.DARK_GRAY);
		
		JLabel hisTitleText = new JLabel("Historial de la reserva");
		hisTitleText.setForeground(Color.WHITE);
		
		JPanel formText = new JPanel();
		formText.setLayout(new GridLayout(2, 1));
		formText.setOpaque(false);
		formText.setPreferredSize(new Dimension(250,100));
		
		JLabel pregunta = new JLabel("Numero de documento del huesped titular");
		pregunta.setFont(font);
		
		JTextField Rpregunta = new JTextField();
		
		JButton hacerCheck = new JButton("Aceptar");
		JButton volver = new JButton("Volver");
		
		JPanel b1 = new JPanel(new GridLayout(2,1));
		b1.setOpaque(false);
		JPanel botones = new JPanel();
		botones.setLayout(new FlowLayout());
		
		botones.setOpaque(false);
		botones.add(hacerCheck);
		botones.add(volver);
		botones.setPreferredSize(new Dimension(200,10));
		
		b1.add(new JLabel(" "));
		b1.add(botones);
		
		formText.add(pregunta);
		formText.add(Rpregunta);
		
		JhistTitle.add(hisTitleText);
		Jhistorial.add(JhistTitle, BorderLayout.NORTH);
		Jhistorial.add(JHistorialTextA, BorderLayout.CENTER);
		controles.add(formText, BorderLayout.WEST);
		controles.add(b1, BorderLayout.EAST);
		
		
		principal.add(controles, BorderLayout.NORTH);
		principal.add(Jhistorial, BorderLayout.CENTER);
		
		//botones
		volver.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				volverAtras();
			}
		});
		
		hacerCheck.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String r = hacerCheckout(Rpregunta.getText());
				JHistorialTextA.setText(r);
			}
		});
		
		return principal;
	}
	
	private void volverAtras() 
	{
		this.setVisible(false);
		FMenuEmpleado menu= new FMenuEmpleado(this.control);
		menu.setVisible(true);
		this.dispose();
	}
	
	private String hacerCheckout(String id) 
	{
		String result = control.HacerCheckout(id);
		return result;
	}
	
}
