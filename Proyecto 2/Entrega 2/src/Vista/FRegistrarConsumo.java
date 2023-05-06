package Vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import Controlador.Control;

public class FRegistrarConsumo extends JFrame{

	private Control control;
	
	private JPanel JInnerBox;
	private JPanel JOuterBox;
	private JPanel JTitulo;
	private JPanel JFormulario;
	private JPanel JListaUsuarios;
	
	public FRegistrarConsumo(Control control) {
		
		this.control = control;
		
		//Inicializar pantalla----------
		this.setSize(900,900);
		this.setVisible(true);
		this.setMinimumSize(new Dimension(750,600));
		this.setTitle("Registrar consumo");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Colores, labels, etc--------
		Color outLine = Color.decode("#5AB7FA");
		Color darkblue=Color.decode("#1846be");
		Color innerColor = Color.decode("#9DB7F0");
		
		Font Ftitulo=new Font("Arial", Font.BOLD, 20);
		Font Fforms = new Font("Arial Black", Font.BOLD, 10);
		
		//Labels
		JLabel Ltitulo = new JLabel("Registrar consumo");
		Ltitulo.setFont(Ftitulo);
		Ltitulo.setForeground(Color.WHITE);
		Ltitulo.setHorizontalAlignment(JLabel.CENTER);
		
		JLabel Llistatitulo = new JLabel("Lista de servicios disponibles");
		Llistatitulo.setForeground(Color.WHITE);
		
		//Crear un panel con scroll que muestre cada servicio
		JPanel InventarioServicios = mostrarInventarioS();
		JScrollPane scroll = new JScrollPane(InventarioServicios);
		
		//Botones
		JButton Registrar = new JButton("Registrar");
		JButton Volver = new JButton("Volver");
		
		Volver.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				volverAtras();
			}
		});
		
		//otros
		Dimension DForms = new Dimension(this.getBounds().width/3*2,this.getBounds().height/4);
		
		
		JLabel LHuespedTId = new JLabel("Documento del huesped titular");
		LHuespedTId.setFont(Fforms);
		LHuespedTId.setForeground(Color.BLACK);
		LHuespedTId.setHorizontalAlignment(JLabel.CENTER);
		JTextField Thid = new JTextField();
		
		
		JLabel LServicioId = new JLabel("Id del servicio");
		LServicioId.setFont(Fforms);
		LServicioId.setForeground(Color.BLACK);
		LServicioId.setHorizontalAlignment(JLabel.CENTER);
		JTextField Lsid = new JTextField();
		
		JLabel LHuespedUsaraServicio = new JLabel("El huesped titular usara el servicio");
		LHuespedUsaraServicio.setFont(Fforms);
		LHuespedUsaraServicio.setForeground(Color.BLACK);
		LHuespedUsaraServicio.setHorizontalAlignment(JLabel.CENTER);
		JCheckBox Lhus = new JCheckBox();
		Lhus.setOpaque(false);
		
		JLabel LNAcompanantes = new JLabel("Documentos de los acompañantes (separados por ,)");
		LNAcompanantes.setFont(Fforms);
		LNAcompanantes.setForeground(Color.BLACK);
		LNAcompanantes.setHorizontalAlignment(JLabel.CENTER);
		JTextField Lna = new JTextField();
		
		JLabel PagoInmediato = new JLabel("Pago Inmediato");
		PagoInmediato.setFont(Fforms);
		PagoInmediato.setForeground(Color.BLACK);
		JCheckBox Pi = new JCheckBox();
		Pi.setOpaque(false);
		
		JPanel JpagoInmediato = new JPanel();
		JpagoInmediato.setOpaque(false);
		JpagoInmediato.add(PagoInmediato);
		JpagoInmediato.add(Pi);
		
		JLabel AsignarAH = new JLabel("Asignar a habitación");
		AsignarAH.setFont(Fforms);
		AsignarAH.setForeground(Color.BLACK);
		JCheckBox Aah = new JCheckBox();
		Aah.setOpaque(false);
		
		JPanel JAsignarAH = new JPanel();
		JAsignarAH.setOpaque(false);
		JAsignarAH.add(AsignarAH);
		JAsignarAH.add(Aah);
		
		//Modificar los jpanels
		JPanel preguntas = new JPanel();
		preguntas.setOpaque(false);
		//preguntas.setPreferredSize(DForms);
		preguntas.setLayout(new GridLayout(5,5,10,10));
		preguntas.add(LHuespedTId);
		preguntas.add(Thid);
		preguntas.add(LServicioId);
		preguntas.add(Lsid);
		preguntas.add(LHuespedUsaraServicio);
		preguntas.add(Lhus);
		preguntas.add(LNAcompanantes);
		preguntas.add(Lna);
		preguntas.add(JpagoInmediato);
		preguntas.add(JAsignarAH);
		
		JPanel BotonesConfirmacion = new JPanel();
		BotonesConfirmacion.setOpaque(false);
		//BotonesConfirmacion.setPreferredSize(DForms);
		BotonesConfirmacion.setLayout(new FlowLayout(FlowLayout.CENTER));
		BotonesConfirmacion.add(Registrar);
		BotonesConfirmacion.add(Volver);
		
		
		//registrar consumo
		Registrar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				RegistrarConsumo(Lsid.getText(),Pi.isSelected(), Aah.isSelected() ,Thid.getText(), Lna.getText(), Lhus.isSelected());
			}
		});
		
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
		JFormulario.setOpaque(false);
		JFormulario.setLayout(new BorderLayout());
		
		this.JListaUsuarios = new JPanel();
		JListaUsuarios.setBackground(Color.YELLOW);
		JListaUsuarios.setPreferredSize(DForms);
		JListaUsuarios.setLayout(new BorderLayout());
		
		JPanel ListaTitulo = new JPanel();
		ListaTitulo.setBackground(Color.DARK_GRAY);
		
		JPanel ElementosLista = new JPanel();
		ElementosLista.setBackground(Color.GREEN);
		
		
		
		ListaTitulo.add(Llistatitulo);
		
		//Añiadir los jpanels, frames, etc
		JOuterBox.add(new JLabel("     "), BorderLayout.NORTH);
		JOuterBox.add(new JLabel("     "), BorderLayout.SOUTH);
		JOuterBox.add(new JLabel("     "), BorderLayout.EAST);
		JOuterBox.add(new JLabel("     "), BorderLayout.WEST);
		JOuterBox.add(JInnerBox, BorderLayout.CENTER);
		
		JInnerBox.add(JTitulo, BorderLayout.NORTH);
		JInnerBox.add(JFormulario, BorderLayout.CENTER);
		JInnerBox.add(JListaUsuarios, BorderLayout.SOUTH);
		
		JFormulario.add(preguntas, BorderLayout.WEST);
		JFormulario.add(BotonesConfirmacion, BorderLayout.CENTER);
		
		JListaUsuarios.add(ListaTitulo, BorderLayout.NORTH);
		JListaUsuarios.add(scroll, BorderLayout.CENTER);
		//ElementosLista.add(InventarioServicios);
		
		//Falta crear los actionListener para los botones
		
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
	
	private void RegistrarConsumo(String idServicio, Boolean pagoInmediato, Boolean AsignaraHabitacion,
			String DocumentoT, String DocumentosClientes, Boolean UsarTitular) 
	{
		ArrayList<String> dAcom= new ArrayList<String>();
		if(DocumentosClientes != "") 
		{
			String[] dAcompanantes = DocumentosClientes.split(",");
			for (String data: dAcompanantes) 
			{
				dAcom.add(data);
			}
		}
		boolean done = control.registrarConsumo(idServicio, pagoInmediato, AsignaraHabitacion, DocumentoT, dAcom, UsarTitular);
		if(done) 
		{
			JOptionPane.showMessageDialog(this, "Se registro con exito");
		}else 
		{
			JOptionPane.showMessageDialog(this, "Hubo un error");
		}
	}
}
