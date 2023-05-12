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
import javax.swing.JTextField;

import Controlador.Control;

public class FRealizarReserva extends JFrame{
	private Control control;
	private JPanel JInnerBox;
	private JPanel JOuterBox;
	private JPanel JTitulo;
	
	private JTextField nombreT;
	private JTextField diT;
	private JTextField emailT;
	private JTextField telT;
	
	
	private JTextField Fecha1;
	private JTextField Fecha2;
	
	private JTextArea ListaAcom;
	
	private int acompananteN=0;
	private int currentAnumber=0;
	private ArrayList<String> datosAcompanantes= new ArrayList<String>();
	private Boolean canRegist = false;
	
	public FRealizarReserva(Control control) 
	{
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
		JLabel Ltitulo = new JLabel("Realizar una nueva reserva");
		Ltitulo.setFont(Ftitulo);
		Ltitulo.setForeground(Color.WHITE);
		Ltitulo.setHorizontalAlignment(JLabel.CENTER);
		
		JLabel LDatosBtitulo= new JLabel("Datos Base de la reserva");
		LDatosBtitulo.setForeground(Color.WHITE);
		
		JLabel LDatosAtitulo= new JLabel("Datos de los acompañantes");
		LDatosAtitulo.setForeground(Color.WHITE);
		
		//panels
		JPanel Jform = new JPanel();
		Jform.setLayout(new GridLayout(2,1));
		Jform.setOpaque(false);
		
		//-----------------------------------
		JPanel DatosBaseTitulo = new JPanel();
		DatosBaseTitulo.setBackground(Color.DARK_GRAY);
		DatosBaseTitulo.add(LDatosBtitulo);
		
		JPanel JDatosBase = new JPanel();
		JDatosBase.setLayout(new BorderLayout());
		JDatosBase.setOpaque(false);
		
		JPanel JTitular = FormTitular();
		JTitular.setOpaque(false);
		
		JPanel JFechas = FechasForm();
		JFechas.setOpaque(false);
		JFechas.setPreferredSize(new Dimension(300,300));
		
		JDatosBase.add(JTitular, BorderLayout.CENTER);
		JDatosBase.add(JFechas, BorderLayout.EAST);
		JDatosBase.add(DatosBaseTitulo, BorderLayout.NORTH);
		
		//-----------------------------------------------
		JPanel CuadroDatosA=new JPanel();
		CuadroDatosA.setLayout(new BorderLayout());
		CuadroDatosA.setOpaque(false);
		
		JPanel DatosAcompTitulo = new JPanel();
		DatosAcompTitulo.setBackground(Color.DARK_GRAY);
		DatosAcompTitulo.add(LDatosAtitulo);
		
		JPanel JDatosAcompanantes = new JPanel();
		JDatosAcompanantes.setLayout(new GridLayout(1,2));
		JDatosAcompanantes.setOpaque(false);
		
		this.ListaAcom = new JTextArea("Nombre  |   documento  |  email  |   telefono");
		JScrollPane ScrollLista = new JScrollPane(ListaAcom);
		JPanel JFormAcompanantes = FormAcompanante();
		JFormAcompanantes.setOpaque(false);
		
		JDatosAcompanantes.add(JFormAcompanantes);
		JDatosAcompanantes.add(ScrollLista);
		
		CuadroDatosA.add(JDatosAcompanantes, BorderLayout.CENTER);
		CuadroDatosA.add(DatosAcompTitulo, BorderLayout.NORTH);
		//----------------------------------
		Jform.add(JDatosBase);
		Jform.add(CuadroDatosA);
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
		
		//envio de formulario 
		JPanel enviarVolver = FormEnviarVolver();
		
		
		//Añadir al frame
		JOuterBox.add(new JLabel("     "), BorderLayout.NORTH);
		JOuterBox.add(new JLabel("     "), BorderLayout.SOUTH);
		JOuterBox.add(new JLabel("     "), BorderLayout.EAST);
		JOuterBox.add(new JLabel("     "), BorderLayout.WEST);
		JOuterBox.add(JInnerBox, BorderLayout.CENTER);
		
		JInnerBox.add(this.JTitulo, BorderLayout.NORTH);
		JInnerBox.add(Jform, BorderLayout.CENTER);
		JInnerBox.add(enviarVolver, BorderLayout.SOUTH);
		this.add(JOuterBox);
	}
	
	private JPanel FormTitular() 
	{
		//panels
		JPanel form = new JPanel();
		form.setOpaque(false);
		form.setLayout(new BorderLayout());
		
		JPanel p1 = new JPanel();
		p1.setLayout(new GridLayout(4,2));
		p1.setOpaque(false);
		
		JPanel p2 = new JPanel();
		p2.setLayout(new GridLayout(1, 2));
		p2.setOpaque(false);
		
		JPanel JNacompanantes = new JPanel();
		JNacompanantes.setLayout(new FlowLayout(FlowLayout.LEFT));
		JNacompanantes.setOpaque(false);
		
		//labels
		JLabel nombres = new JLabel("Nombre y apellido");
		JLabel documento = new JLabel("Numero de documento");
		JLabel email = new JLabel("E-mail");
		JLabel tel = new JLabel("Numero de telefono");
		JLabel nacompanantes = new JLabel("Numero de acompañantes");
		
		//Inputs
		JTextField Rnombres = new JTextField("");
		JTextField Rdocumento = new JTextField("");
		JTextField Remail = new JTextField();
		JTextField Rtel = new JTextField();
		JTextField Rnacompanantes = new JTextField("0");
		Rnacompanantes.setPreferredSize(new Dimension(30,30));
		
		JButton setacompanantes = new JButton("Guardar datos base");
		
		//add elements
		p1.add(nombres);
		p1.add(Rnombres);
		p1.add(documento);
		p1.add(Rdocumento);
		p1.add(email);
		p1.add(Remail);
		p1.add(tel);
		p1.add(Rtel);
		
		JNacompanantes.add(nacompanantes);
		JNacompanantes.add(Rnacompanantes);
		
		p2.add(JNacompanantes);
		p2.add(setacompanantes);
		
		form.add(p1, BorderLayout.CENTER);
		form.add(p2, BorderLayout.SOUTH);
		
		//btones
		setacompanantes.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				acompananteN = Integer.parseInt(Rnacompanantes.getText());
				if(Rnombres.getText() =="" && (Rdocumento.getText()== ""))
				{
					JOptionPane.showMessageDialog(p1, "Los datos no estan completos");
				}else 
				{
					nombreT = Rnombres;
					diT = Rdocumento;
					emailT = Remail;
					telT = Rtel;
					JOptionPane.showMessageDialog(form, "Se añadieron los datos de "+nombreT.getText());
					canRegist=true;
				}
				//JOptionPane.showMessageDialog(form, nombreT.getText());
			}
		});
		return form;
	}
	
	private JPanel FechasForm() 
	{
		GridLayout fecha= new GridLayout(2,1);
		
		JPanel form = new JPanel();
		form.setLayout(fecha);
		
		JPanel f1 = new JPanel();
		JPanel f2= new JPanel();
		f1.setLayout(fecha);
		f2.setLayout(fecha);
		
		JLabel Lf1=new JLabel("Fecha de entrada");
		JLabel Lf2=new JLabel("Fecha de salida");
		
		this.Fecha1 = new JTextField("dd-mm-aaaa");
		this.Fecha2 = new JTextField("dd-mm-aaaa");
		
		
		//añadir
		f1.add(Lf1);
		f1.add(Fecha1);
		f2.add(Lf2);
		f2.add(Fecha2);
		
		form.add(f1);
		form.add(f2);
		
		form.setOpaque(false);
		f1.setOpaque(false);
		f2.setOpaque(false);
		return form;
	}

	private JPanel FormAcompanante() 
	{
		//panels
		JPanel form = new JPanel();
		form.setLayout(new BorderLayout());
		
		JPanel p1 = new JPanel();
		p1.setLayout(new GridLayout(4,2));
		
		JPanel p2 = new JPanel();
		
		
		//labels
		JLabel nombres = new JLabel("Nombre y apellido");
		JLabel documento = new JLabel("Numero de documento");
		JLabel email = new JLabel("E-mail");
		JLabel tel = new JLabel("Numero de telefono");
		
		//inpust del usuaroi
		JTextField Rnombres = new JTextField();
		JTextField Rdocumento = new JTextField();
		JTextField Remail = new JTextField();
		JTextField Rtel = new JTextField();
		
		JButton addacompanantes = new JButton("Añadir acompañante");
		
		//add elements
		p1.add(nombres);
		p1.add(Rnombres);
		p1.add(documento);
		p1.add(Rdocumento);
		p1.add(email);
		p1.add(Remail);
		p1.add(tel);
		p1.add(Rtel);
		
		p2.add(addacompanantes);
		
		form.add(p1, BorderLayout.CENTER);
		form.add(p2, BorderLayout.SOUTH);
		
		form.setOpaque(false);
		p1.setOpaque(false);
		p2.setOpaque(false);
		
		//botones
		addacompanantes.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(currentAnumber < acompananteN)
				{
					if(Rnombres.getText() != "" && Rdocumento.getText() !="")
						{
							String datos = Rnombres.getText()+";"+Rdocumento.getText()+";"+Remail.getText()+";"+Rtel.getText();
							AddAcompananteData(datos);
							ListaAcom.setText(ListaAcom.getText()+"\n"+"Acompañante "+(currentAnumber+1)
									+ ": "+datos.replace(";", "   |   "));
							Rnombres.setText("");
							Rdocumento.setText("");
							Remail.setText("");
							Rtel.setText("");
							currentAnumber++;
						}
					}else 
				{
					JOptionPane.showMessageDialog(form, "Se ha excedido el numero de acompañantes establecido");
				}
			}
		});
		
		
		return form;
	}
	
	private JPanel FormEnviarVolver() 
	{
		JPanel enviarp = new JPanel();
		enviarp.setOpaque(false);
		enviarp.setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		JButton hacerRegistro = new JButton("Realizar reserva");
		JButton volverb = new JButton("Cancelar y volver");
		
		//botones funcinamiento
		hacerRegistro.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(JOptionPane.showConfirmDialog(JOuterBox, "Desea registrar este usuario")==0)
				{
					if(canRegist) 
					{
						FInventarioHabitaciones pedirIds = new FInventarioHabitaciones(control, true);
						String result = control.realizarReserva(nombreT.getText(), diT.getText(), emailT.getText(), telT.getText(),
								currentAnumber, datosAcompanantes, Fecha1.getText(), Fecha2.getText(), pedirIds);
						JOptionPane.showMessageDialog(JOuterBox, result);
					}else 
					{
						JOptionPane.showMessageDialog(JOuterBox, "No has completado los datos necesarios para hacer este registro");
					}
				}
			}
		});
		
		volverb.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				volverAtras();
			}
		});
		
		//añadir
		enviarp.add(hacerRegistro);
		enviarp.add(volverb);
		return enviarp;
	}
	
	private void AddAcompananteData(String datos) 
	{
		this.datosAcompanantes.add(datos);
	}
	
	private void volverAtras() 
	{
		this.setVisible(false);
		FMenuEmpleado menu= new FMenuEmpleado(this.control);
		menu.setVisible(true);
		this.dispose();
	}
}
