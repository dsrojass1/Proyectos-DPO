package Vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Controlador.Control;

public class FMenuEmpleado extends JFrame{
	private Control control;
	
	private JPanel Titulo;
	private JPanel Botones;
	private JPanel OuterBox;
	private JPanel InnerBox;
	
	public FMenuEmpleado(Control control) {
		this.control = control;
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(900, 900);
		this.setMinimumSize(new Dimension(500,500));
		this.setTitle("Menu de empleado");
		
		//Textos, botones ,etc
		Color darkblue=Color.decode("#1846be");
		Font f1=new Font("Arial", Font.BOLD, 20);
		
		JLabel LMenuEmpleado = new JLabel("Menu Empleado");
		LMenuEmpleado.setHorizontalAlignment(JLabel.CENTER);
		LMenuEmpleado.setVerticalAlignment(JLabel.CENTER);
		LMenuEmpleado.setFont(f1);
		LMenuEmpleado.setForeground(Color.WHITE);
		
		//Botones
		JButton BCerrarSesion = new JButton("Cerrar Sesion");
		BCerrarSesion.setBackground(darkblue);
		BCerrarSesion.setForeground(Color.WHITE);
		
		JButton BGuardarCambios = new JButton("Guardar cambios");
		BGuardarCambios.setBackground(darkblue);
		BGuardarCambios.setForeground(Color.WHITE);
		
		JButton BConsultarInventarioH = new JButton("Consultar inventario de habitaciones");
		BConsultarInventarioH.setBackground(darkblue);
		BConsultarInventarioH.setForeground(Color.WHITE);
		
		JButton BConsultarInventarioS = new JButton("Consultar inventario de servicios");
		BConsultarInventarioS.setBackground(darkblue);
		BConsultarInventarioS.setForeground(Color.WHITE);
		
		JButton BRegistrarConsumo = new JButton("Registrar consumo");
		BRegistrarConsumo.setBackground(darkblue);
		BRegistrarConsumo.setForeground(Color.WHITE);
		
		JButton BCheckout = new JButton("Hacer check out");
		BCheckout.setBackground(darkblue);
		BCheckout.setForeground(Color.WHITE);
		
		JButton BReservar = new JButton("Realizar reserva");
		BReservar.setBackground(darkblue);
		BReservar.setForeground(Color.WHITE);
		
		JButton BCancelarReserva = new JButton("Cancelar reserva");
		BCancelarReserva.setBackground(darkblue);
		BCancelarReserva.setForeground(Color.WHITE);
		
		
		//Modificar los Jpanels
		this.OuterBox = new JPanel();
		this.OuterBox.setBackground(Color.decode("#5AB7FA"));
		this.OuterBox.setLayout(new BorderLayout());
		
		this.InnerBox = new JPanel();
		this.InnerBox.setBackground(Color.decode("#9DB7F0"));
		this.InnerBox.setLayout(new BorderLayout());
		
		
		this.Titulo= new JPanel();
		this.Titulo.setBackground(darkblue);
		this.Titulo.setLayout(new GridLayout(3, 0, 2, 2));
		this.Titulo.add(new JLabel("   "));
		this.Titulo.add(LMenuEmpleado);
		
		this.Botones = new JPanel();
		this.Botones.setOpaque(false);
		this.Botones.setLayout(new GridLayout(8,1,10,10));
		this.Botones.setPreferredSize(new Dimension(this.getBounds().width/2,500));
		
		
		this.Botones.add(BConsultarInventarioH);
		this.Botones.add(BConsultarInventarioS);
		this.Botones.add(BRegistrarConsumo);
		this.Botones.add(BCheckout);
		this.Botones.add(BReservar);
		this.Botones.add(BCancelarReserva);
		this.Botones.add(BGuardarCambios);
		this.Botones.add(BCerrarSesion);
		
		//Asignar elementos a los panels y al frame-----------------------------------------------
		
		//espaciadores de los extremos----------------------------
		this.OuterBox.add(InnerBox, BorderLayout.CENTER);
		this.OuterBox.add(new JLabel("     \n  "), BorderLayout.NORTH);
		this.OuterBox.add(new JLabel("       "), BorderLayout.WEST);
		this.OuterBox.add(new JLabel("       "), BorderLayout.EAST);
		this.OuterBox.add(new JLabel("     \n  "), BorderLayout.SOUTH);
		//--------------------------------------------------------
		
		this.InnerBox.add(Titulo, BorderLayout.NORTH);
		this.InnerBox.add(Botones, BorderLayout.WEST);
		this.InnerBox.add(new JLabel("    "), BorderLayout.EAST);
		
		this.add(this.OuterBox);
		this.repaint();
		
		//Añadir los listeners a para los botones----------------------------------------------
		BConsultarInventarioH.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ConsultarHabitaciones();
			}
		});
		
		BConsultarInventarioS.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ConsultarServicios();
			}
		});
		
		BRegistrarConsumo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RegistrarConsumo();
			}
		});
		
		BCheckout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CheckOut();
			}
		});
		
		BReservar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				HacerReserva();
			}
		});
		
		BCancelarReserva.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CancelarReserva();
			}
		});
		
		BGuardarCambios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GuardarCambios();
			}
		});
		
		BCerrarSesion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CerrarSesion();
			}
		});
	}
	
	protected void ConsultarHabitaciones() {
		// TODO Auto-generated method stub
		this.setVisible(false);
		new FInventarioHabitaciones(this.control, false);
		this.dispose();
	}

	protected void ConsultarServicios() {
		// TODO Auto-generated method stub
		this.setVisible(false);
		new FInventarioServicio(this.control);
		this.dispose();
	}

	protected void RegistrarConsumo() {
		this.setVisible(false);
		new FRegistrarConsumo(this.control);
		this.dispose();
	}

	protected void CancelarReserva() {
		// TODO Auto-generated method stub
		this.setVisible(false);
		new cancelarReserva(this.control);
		this.dispose();
	}

	protected void GuardarCambios() {
		this.control.guardarCambios();
		JOptionPane.showMessageDialog(this, "Se han guardado los cambios de manera exitosa", "Guardar", JOptionPane.INFORMATION_MESSAGE);
	}

	protected void CerrarSesion() {
		JOptionPane.showMessageDialog(this, "Recuerde guardar antes de salir", "Recordatorio", JOptionPane.INFORMATION_MESSAGE);
		if(JOptionPane.showConfirmDialog(this, "¿Desea cerrar sesión?") == 0) 
		{
			this.setVisible(false);
			new FPrincipal();
			this.dispose();
		}
		
	}

	protected void CheckOut() {
		// TODO Auto-generated method stub
		this.setVisible(false);
		new FCheckOut(this.control);
		this.dispose();
	}

	protected void HacerReserva() {
		this.setVisible(false);
		new FRealizarReserva(this.control);
		this.dispose();
	}

}
