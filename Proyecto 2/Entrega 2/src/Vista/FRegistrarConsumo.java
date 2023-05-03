package Vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

import Controlador.Control;

public class FRegistrarConsumo extends JFrame{

	private Control control;
	
	private JPanel JInnerBox;
	private JPanel JOuterBox;
	private JPanel JTitulo;
	private JPanel JFormulario;
	private JPanel JListaUsuarios;
	private JPanel JConfirmBotons;
	
	public FRegistrarConsumo(Control control) {
		
		this.control = control;
		
		//Inicializar pantalla----------
		this.setSize(900,900);
		this.setVisible(true);
		this.setMinimumSize(new Dimension(500,500));
		this.setTitle("Registrar consumo");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Colores, labels, etc--------
		Color outLine = Color.decode("#5AB7FA");
		Color darkblue=Color.decode("#1846be");
		Color innerColor = Color.decode("#9DB7F0");
		
		Font Ftitulo=new Font("Arial", Font.BOLD, 20);
		Font Fforms = new Font("Arial Black", Font.BOLD, 29);
		
		JLabel Ltitulo = new JLabel("Registrar consumo");
		Ltitulo.setFont(Ftitulo);
		Ltitulo.setForeground(Color.WHITE);
		Ltitulo.setHorizontalAlignment(JLabel.CENTER);
		
		//Modificar los jpanels
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
		
		this.JFormulario = new JPanel();
		JFormulario.setBackground(Color.RED);
		JFormulario.setPreferredSize(new Dimension(this.getBounds().width/2,500));
		
		this.JConfirmBotons = new JPanel();
		JConfirmBotons.setBackground(Color.GREEN);
		JConfirmBotons.setPreferredSize(new Dimension(this.getBounds().width/2,500));
		
		this.JListaUsuarios = new JPanel();
		JListaUsuarios.setBackground(Color.YELLOW);
		
		//Añiadir los jpanels, frames, etc
		JOuterBox.add(new JLabel("     "), BorderLayout.NORTH);
		JOuterBox.add(new JLabel("     "), BorderLayout.SOUTH);
		JOuterBox.add(new JLabel("     "), BorderLayout.EAST);
		JOuterBox.add(new JLabel("     "), BorderLayout.WEST);
		JOuterBox.add(JInnerBox, BorderLayout.CENTER);
		
		JInnerBox.add(JTitulo, BorderLayout.NORTH);
		JInnerBox.add(JFormulario, BorderLayout.WEST);
		JInnerBox.add(JConfirmBotons, BorderLayout.EAST);
		JInnerBox.add(JListaUsuarios, BorderLayout.SOUTH);
		
		
		this.add(JOuterBox);
	}
}
