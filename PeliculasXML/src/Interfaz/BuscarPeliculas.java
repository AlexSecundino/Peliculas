package Interfaz;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.*;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import Fuente.Categoria;
import Fuente.OpcionesBusqueda;
import Fuente.Pelicula;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class BuscarPeliculas extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	private JComboBox cbOpcionesBusqueda;
	private JComboBox cbBuscar;
	
	private OpcionesBusqueda[] opcionesBusqueda = OpcionesBusqueda.values();
	private Categoria[] opcionesCategoria = Categoria.values();
	private ArrayList<Pelicula> peliculasEncontradas;
	
	private DocumentBuilderFactory dbf;
	private DocumentBuilder db;
	private Document doc;

	public BuscarPeliculas() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblBuscarPor = new JLabel("Buscar por:");
		lblBuscarPor.setBounds(16, 45, 82, 16);
		contentPane.add(lblBuscarPor);
		
		cbOpcionesBusqueda = new JComboBox();
		cbOpcionesBusqueda.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				cbBuscar.setEditable(true);
				cbBuscar.removeAllItems();
				if(cbOpcionesBusqueda.getSelectedItem().toString().equals("categoria")){
					cbBuscar.setEditable(false);
					addCategorias();
				}
			}
		});
		cbOpcionesBusqueda.setBounds(110, 41, 215, 27);
		contentPane.add(cbOpcionesBusqueda);
		
		JLabel lblPalabras = new JLabel("Palabra(s): ");
		lblPalabras.setBounds(16, 124, 82, 16);
		contentPane.add(lblPalabras);
		
		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				peliculasEncontradas = buscarPeliculas();
			}
		});
		btnBuscar.setBounds(309, 230, 117, 29);
		contentPane.add(btnBuscar);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		btnCancelar.setBounds(6, 230, 117, 29);
		contentPane.add(btnCancelar);
		
		cbBuscar = new JComboBox();
		cbBuscar.setEditable(true);
		cbBuscar.setBounds(110, 120, 215, 27);
		contentPane.add(cbBuscar);
		
		addBuscarPor();
	}

	protected ArrayList<Pelicula> buscarPeliculas() {
		
		ArrayList<Pelicula> encontradas = new ArrayList<Pelicula>();
		
		try {
			crearDocumento();
			XPathFactory xpf = XPathFactory.newInstance();
			XPath xp = xpf.newXPath();
			String expresion = null;
			
			if(cbOpcionesBusqueda.getSelectedItem().equals(OpcionesBusqueda.categoria)){
				expresion = "/peliculas/pelicula[@" + cbOpcionesBusqueda.getSelectedItem().toString() + "='" + cbBuscar.getSelectedItem().toString() + "']";
			}
			else{
				expresion = "/peliculas/pelicula[" + cbOpcionesBusqueda.getSelectedItem().toString() + "='" + cbBuscar.getSelectedItem().toString() + "']";
			}	
			
			NodeList peliculas = (NodeList) xp.compile(expresion).evaluate(doc, XPathConstants.NODESET);
				
			for(int i = 0; i <= peliculas.getLength() - 1; i++){
				NodeList camposPelicula = peliculas.item(i).getChildNodes();
					
				String categoria = camposPelicula.item(1).getParentNode().getAttributes().getNamedItem("categoria").getNodeValue();
				String src = camposPelicula.item(1).getParentNode().getAttributes().getNamedItem("src").getNodeValue();
					
				String titulo = camposPelicula.item(1).getFirstChild().getNodeValue();
				String director = camposPelicula.item(3).getFirstChild().getNodeValue();
				String anyo = camposPelicula.item(5).getFirstChild().getNodeValue();
				Pelicula pelicula = new Pelicula(titulo, director, categoria, src, new String[0], Integer.parseInt(anyo));
				encontradas.add(pelicula);
				System.out.println(pelicula);
			}
			
			if(encontradas.size() <= 0)
				JOptionPane.showMessageDialog(contentPane, "No se ha encontrado ningœn resultado");
			
		} catch (XPathExpressionException e) {
			JOptionPane.showMessageDialog(contentPane, "Error en la bœsqueda.");
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(contentPane, "No se ha encontrado el archivo origen.");
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return encontradas;
	}
	
	private void crearDocumento() throws ParserConfigurationException, FileNotFoundException, SAXException, IOException {

		dbf = DocumentBuilderFactory.newInstance();
		db = dbf.newDocumentBuilder();
		doc = db.parse(new InputSource(new FileInputStream("peliculas.xml")));
	}

	private void addBuscarPor() {
		for(int i = 0; i <= opcionesBusqueda.length - 1; i++){
			cbOpcionesBusqueda.addItem(opcionesBusqueda[i]);
		}
	}
	
	private void addCategorias() {
		for(int i = 0; i <= opcionesCategoria.length - 1; i++){
			cbBuscar.addItem(opcionesCategoria[i]);
		}
	}
	
	public JComboBox getCbOpcionesBusqueda() {
		return cbOpcionesBusqueda;
	}
	public JComboBox getCbBuscar() {
		return cbBuscar;
	}
}
