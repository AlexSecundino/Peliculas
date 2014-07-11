package Interfaz;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.*;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import Fuente.Categoria;
import Fuente.Formato;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class AddPelicula extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	private JTextField txtAnyo;
	private JComboBox cbCategoria;
	private JTextField txtTitulo;
	private JTextField txtDirector;
	
	private DocumentBuilderFactory dbf;
	private DocumentBuilder db;
	private Document doc;
	private JTextField txtSrc;
	private JComboBox cbFormato;
	
	private File imagen;

	public AddPelicula() {
		setTitle("Nueva Pel\u00EDcula");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
		lblActores.setBounds(22, 166, 61, 16);
		contentPane.add(lblActores);
		
		txtTitulo = new JTextField();
		txtTitulo.setBounds(118, 20, 326, 28);
		contentPane.add(txtTitulo);
		txtTitulo.setColumns(10);
		
		txtDirector = new JTextField();
		txtDirector.setBounds(118, 48, 326, 28);
		contentPane.add(txtDirector);
		txtDirector.setColumns(10);
		
		JButton btnAceptar = new JButton("Aceptar");
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(crearPelicula())
					JOptionPane.showMessageDialog(contentPane, "Se ha a–adido correctamente");
				else
					JOptionPane.showMessageDialog(contentPane, "Ha habido un error");
			}
		});
		btnAceptar.setBounds(375, 343, 117, 29);
		contentPane.add(btnAceptar);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnCancelar.setBounds(247, 343, 117, 29);
		contentPane.add(btnCancelar);
		
		JLabel lblFormato = new JLabel("Formato: ");
		lblFormato.setBounds(22, 110, 84, 16);
		contentPane.add(lblFormato);
		
		cbFormato = new JComboBox();
		cbFormato.setBounds(118, 106, 173, 27);
		contentPane.add(cbFormato);
		
		JLabel lblImagen = new JLabel("Imagen:");
		lblImagen.setBounds(22, 138, 84, 16);
		contentPane.add(lblImagen);
		
		txtSrc = new JTextField();
		txtSrc.setBounds(118, 132, 211, 28);
		contentPane.add(txtSrc);
		txtSrc.setColumns(10);
		
		JButton btnExplorar = new JButton("Explorar");
		btnExplorar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser jfc = new JFileChooser();
				jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
				int sel = jfc.showOpenDialog(contentPane);
				if(sel == JFileChooser.APPROVE_OPTION)
				{
					imagen = jfc.getSelectedFile();
					txtSrc.setText(imagen.getName());
				}
			}
		});
		btnExplorar.setBounds(327, 133, 117, 29);
		contentPane.add(btnExplorar);
		
		addCategorias();
		addFormato();
	}

	private void addFormato() {
		for(Formato formato: Formato.values()){
			cbFormato.addItem(formato);
		}
	}

	private void addCategorias() {
		for(Categoria cat: Categoria.values()){
			cbCategoria.addItem(cat);
		}
	}

	protected boolean crearPelicula() {
	
		boolean correcto = true;
		
		try {
			
			crearDocumento();
		
			Element root = doc.getDocumentElement();
			
			//elementos
			Element pelicula = doc.createElement("pelicula");
			root.appendChild(pelicula);
				
			Attr categoria = doc.createAttribute("categoria");
			categoria.setValue(cbCategoria.getSelectedItem().toString());
			pelicula.setAttributeNode(categoria);
			
			Attr src = doc.createAttribute("src");
			src.setValue(txtSrc.getText());
			pelicula.setAttributeNode(src);
			
			Attr formato = doc.createAttribute("formato");
			formato.setValue(cbFormato.getSelectedItem().toString());
			pelicula.setAttributeNode(formato);
				
			Element titulo = doc.createElement("titulo");
			titulo.appendChild(doc.createTextNode(txtTitulo.getText()));
			pelicula.appendChild(titulo);
			
			Element director = doc.createElement("director");
			director.appendChild(doc.createTextNode(txtDirector.getText()));
			pelicula.appendChild(director);
			
			Element anyo = doc.createElement("a–o");
			anyo.appendChild(doc.createTextNode(txtAnyo.getText()));
			pelicula.appendChild(anyo);
				
			copiarImagen();
			
			try {
				TransformerFactory transformerFactory = TransformerFactory.newInstance();
				
				transformerFactory.setAttribute("indent-number", new Integer(3));
				
				Transformer transformer = transformerFactory.newTransformer();
				DOMSource source = new DOMSource(doc);
				
				transformer = transformerFactory.newTransformer();
			    transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			    transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
				
				File file = new File("../../../Desktop/PeliculasXML/peliculas.xml");
				
				StreamResult result = new StreamResult(file);
				
				transformer.transform(source, result);
				
			} catch (TransformerException e) {
				correcto = false;
			}
		} catch (FileNotFoundException e1) {
			JOptionPane.showMessageDialog(contentPane, "No se ha encontrado el archivo de origen");
		} catch (ParserConfigurationException e1) {
			e1.printStackTrace();
		} catch (SAXException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		return correcto;
	}

	private void copiarImagen() {
		
		DataInputStream dis = null;
		DataOutputStream dos = null;
		
		try{
			dis = new DataInputStream(new FileInputStream(imagen));
			dos = new DataOutputStream(new FileOutputStream("../../../Desktop/PeliculasXML/Imagenes/" + imagen.getName()));
			
			boolean copiar = true;
			
			while(copiar){
				
				try {
					byte b = new Byte(dis.readByte());
					dos.write(b);
				}
				catch(EOFException e){
					copiar = false;
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		catch(FileNotFoundException e){}
		finally{
			if(dis != null){
				try {
					dis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(dos != null){
				try {
					dos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
	}

	private void crearDocumento() throws ParserConfigurationException, FileNotFoundException, SAXException, IOException {

		dbf = DocumentBuilderFactory.newInstance();
		db = dbf.newDocumentBuilder();
		doc = db.parse(new InputSource(new FileInputStream("../../../Desktop/PeliculasXML/peliculas.xml")));
	}
	
	public JComboBox getCbCategoria() {
		return cbCategoria;
	}
	public JTextField getTxtDirector() {
		return txtDirector;
	}
	public JTextField getTxtAnyo() {
		return txtAnyo;
	}
	public JTextField getTxtTitulo() {
		return txtTitulo;
	}
	public JComboBox getCbFormato() {
		return cbFormato;
	}
	public JTextField getTxtSrc() {
		return txtSrc;
	}
}
