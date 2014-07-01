package Interfaz;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;

import Fuente.Categoria;

import javax.swing.JButton;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.*;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;

public class Pelicula extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	private JTextField txtAnyo;
	private JComboBox cbCategoria;
	private JTextField txtTitulo;
	private JTextField txtDirector;
	private JTextArea txtActores;

	public Pelicula() {
		setTitle("Nueva Pel\u00EDcula");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 498, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblTtulo = new JLabel("T\u00EDtulo:");
		lblTtulo.setBounds(22, 26, 61, 16);
		contentPane.add(lblTtulo);
		
		JLabel lblAo = new JLabel("A\u00F1o:");
		lblAo.setBounds(303, 82, 61, 16);
		contentPane.add(lblAo);
		
		JLabel lblDirector = new JLabel("Director:");
		lblDirector.setBounds(22, 54, 61, 16);
		contentPane.add(lblDirector);
		
		JLabel lblCategora = new JLabel("Categor\u00EDa: ");
		lblCategora.setBounds(22, 82, 84, 16);
		contentPane.add(lblCategora);
		
		cbCategoria = new JComboBox();
		cbCategoria.setBounds(118, 78, 173, 27);
		contentPane.add(cbCategoria);
		
		txtAnyo = new JTextField();
		txtAnyo.setBounds(376, 76, 68, 28);
		contentPane.add(txtAnyo);
		txtAnyo.setColumns(10);
		
		JLabel lblActores = new JLabel("Actores:");
		lblActores.setBounds(22, 110, 61, 16);
		contentPane.add(lblActores);
		
		txtTitulo = new JTextField();
		txtTitulo.setBounds(118, 20, 326, 28);
		contentPane.add(txtTitulo);
		txtTitulo.setColumns(10);
		
		txtDirector = new JTextField();
		txtDirector.setBounds(118, 48, 326, 28);
		contentPane.add(txtDirector);
		txtDirector.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(128, 117, 314, 133);
		contentPane.add(scrollPane);
		
		txtActores = new JTextArea();
		scrollPane.setViewportView(txtActores);
		
		JButton btnAceptar = new JButton("Aceptar");
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				a�adirPelicula();
			}
		});
		btnAceptar.setBounds(327, 300, 117, 29);
		contentPane.add(btnAceptar);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnCancelar.setBounds(193, 300, 117, 29);
		contentPane.add(btnCancelar);
		
		addCategorias();
	}

	protected void a�adirPelicula() {
	
		try {
			//fabrica
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			
			//nuevo documento
			Document doc = db.newDocument();
			
			//raiz
			Element rootElement = doc.createElement("peliculas");
			doc.appendChild(rootElement);
			
			//elementos
			Element pelicula = doc.createElement("pelicula");
			rootElement.appendChild(pelicula);
			
			Attr categoria = doc.createAttribute("categoria");
			categoria.setValue(cbCategoria.getSelectedItem().toString());
			pelicula.setAttributeNode(categoria);
			
			Element titulo = doc.createElement("titulo");
			titulo.appendChild(doc.createTextNode(txtTitulo.getText()));
			pelicula.appendChild(titulo);
			
			Element director = doc.createElement("director");
			director.appendChild(doc.createTextNode(txtDirector.getText()));
			pelicula.appendChild(director);
			
			Element anyo = doc.createElement("a�o");
			anyo.appendChild(doc.createTextNode(txtAnyo.getText()));
			pelicula.appendChild(anyo);
			
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			
			File file = new File("peliculas.xml");
			
			StreamResult result = new StreamResult(file);
			
			transformer.transform(source, result);
			
			
		} catch (ParserConfigurationException e) {
			JOptionPane.showMessageDialog(contentPane, "Error");
		} catch (TransformerConfigurationException e) {
			JOptionPane.showMessageDialog(contentPane, "Error");
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void addCategorias() {
		for(Categoria cat: Categoria.values()){
			cbCategoria.addItem(cat);
		}
	}
	public JComboBox getCbCategoria() {
		return cbCategoria;
	}
	public JTextField getTextField() {
		return txtTitulo;
	}
	public JTextField getTxtDirector() {
		return txtDirector;
	}
	public JTextField getTxtAnyo() {
		return txtAnyo;
	}
	public JTextArea getTextArea() {
		return txtActores;
	}
}
