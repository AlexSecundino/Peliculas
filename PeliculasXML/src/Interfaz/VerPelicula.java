package Interfaz;

import java.awt.Image;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import Fuente.Pelicula;

import javax.swing.JTextPane;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VerPelicula extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	
	private ArrayList<Pelicula> listadoPeliculas = new ArrayList<Pelicula>();
	private int peliculaActual = 0;
	private JLabel lblImage;
	private JTextPane txtTitulo;
	private JTextPane txtDirector;
	private JTextPane txtAnyo;
	private JTextPane txtCategoria;
	private JButton btnAnterior;
	private JButton btnSiguiente;

	public VerPelicula() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 453, 388);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblImage = new JLabel("");
		lblImage.setBounds(241, 27, 194, 276);
		contentPane.add(lblImage);
		
		txtTitulo = new JTextPane();
		txtTitulo.setBackground(Color.WHITE);
		txtTitulo.setForeground(Color.BLACK);
		txtTitulo.setEditable(false);
		txtTitulo.setBounds(6, 52, 194, 30);
		contentPane.add(txtTitulo);
		
		txtDirector = new JTextPane();
		txtDirector.setBackground(Color.WHITE);
		txtDirector.setForeground(Color.BLACK);
		txtDirector.setEditable(false);
		txtDirector.setBounds(6, 114, 194, 30);
		contentPane.add(txtDirector);
		
		txtAnyo = new JTextPane();
		txtAnyo.setEditable(false);
		txtAnyo.setBounds(6, 203, 101, 30);
		contentPane.add(txtAnyo);
		
		txtCategoria = new JTextPane();
		txtCategoria.setEditable(false);
		txtCategoria.setBounds(6, 156, 194, 30);
		contentPane.add(txtCategoria);
		
		btnAnterior = new JButton("Anterior");
		btnAnterior.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(peliculaActual > 0){
					peliculaActual--;
					mostrarPelicula(peliculaActual);
				}
			}
		});
		btnAnterior.setBounds(0, 331, 117, 29);
		contentPane.add(btnAnterior);
		
		btnSiguiente = new JButton("Siguiente");
		btnSiguiente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(peliculaActual < listadoPeliculas.size() - 1){
					peliculaActual++;
					mostrarPelicula(peliculaActual);
				}
			}
		});
		btnSiguiente.setBounds(318, 331, 117, 29);
		contentPane.add(btnSiguiente);
		
		cargarPeliculas();
		
		mostrarPelicula(peliculaActual);
	}

	private void mostrarPelicula(int peliculaActual) {
		Pelicula pelicula = listadoPeliculas.get(peliculaActual);
		
		txtTitulo.setText(pelicula.getTitulo());
		txtDirector.setText(pelicula.getDirector());
		txtCategoria.setText(pelicula.getCategoria());
		txtAnyo.setText(Integer.toString(pelicula.getAnyo()));
		
		ImageIcon image = new ImageIcon("Images/" + pelicula.getSrc());
		
		Icon icono = new ImageIcon(image.getImage().getScaledInstance(lblImage.getWidth(),  lblImage.getHeight(),  Image.SCALE_DEFAULT));
		lblImage.setIcon(icono);
	}

	private void cargarPeliculas() {
		
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db;
			db = dbf.newDocumentBuilder();
			Document doc = db.parse("peliculas.xml");
			
			Element raiz = doc.getDocumentElement();
			
			NodeList lista = raiz.getElementsByTagName("pelicula");
			
			for(int i = 0; i <= lista.getLength() - 1; i++){
				
				Pelicula pelicula;
				String titulo = null;
				String director = null;
				String categoria = null;
				String src = null;
				String anyo = "0";
				
				Node unaPelicula = lista.item(i);
				NodeList datosPelicula = unaPelicula.getChildNodes();
				
				categoria = unaPelicula.getAttributes().getNamedItem("categoria").getNodeValue();
				src = unaPelicula.getAttributes().getNamedItem("src").getNodeValue();
				
				Node nTitulo = datosPelicula.item(1);
				if(nTitulo.getNodeType() == Node.ELEMENT_NODE){
					Node texto = nTitulo.getFirstChild();
					if(texto != null && texto.getNodeType() == Node.TEXT_NODE){
						titulo = texto.getNodeValue();
					}
				}
				
				Node nDirector = datosPelicula.item(3);
				if(nDirector.getNodeType() == Node.ELEMENT_NODE){
					Node texto = nDirector.getFirstChild();
					if(texto != null && texto.getNodeType() == Node.TEXT_NODE){
						director = texto.getNodeValue();
					}
				}
				

				Node nAnyo = datosPelicula.item(5);
				if(nAnyo.getNodeType() == Node.ELEMENT_NODE){
					Node texto = nAnyo.getFirstChild();
					if(texto != null && texto.getNodeType() == Node.TEXT_NODE){
						anyo = texto.getNodeValue();
					}
				}
				
				pelicula = new Pelicula(titulo, director, categoria, src, new String[0], Integer.parseInt(anyo));
				listadoPeliculas.add(pelicula);
			}
			
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public JLabel getLblImage() {
		return lblImage;
	}
	public JTextPane getTextPane() {
		return txtTitulo;
	}
}
