package Fuente;
import java.awt.EventQueue;

import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Node;

import Interfaz.Menu;


public class main {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Menu frame = new Menu();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
