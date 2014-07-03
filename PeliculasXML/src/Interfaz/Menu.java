package Interfaz;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Menu extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;

	public Menu() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnNuevaPelcula = new JButton("Nueva Pel\u00EDcula");
		btnNuevaPelcula.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Pelicula nuevaPelicula = new Pelicula();
				nuevaPelicula.setVisible(true);
			}
		});
		btnNuevaPelcula.setBounds(66, 88, 170, 29);
		contentPane.add(btnNuevaPelcula);
		
		JButton btnVerPelculas = new JButton("Ver Pel\u00EDculas");
		btnVerPelculas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				VerPelicula verPelicula = new VerPelicula();
				verPelicula.setVisible(true);
			}
		});
		btnVerPelculas.setBounds(66, 129, 170, 29);
		contentPane.add(btnVerPelculas);
		
		JButton btnBuscarPelculas = new JButton("Buscar Pel\u00EDculas");
		btnBuscarPelculas.setBounds(66, 170, 170, 29);
		contentPane.add(btnBuscarPelculas);
		
		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(-1);
			}
		});
		btnExit.setBounds(291, 170, 117, 29);
		contentPane.add(btnExit);
	}
}
