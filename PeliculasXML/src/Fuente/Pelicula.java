package Fuente;

public class Pelicula {

	String titulo;
	String director;
	String categoria;
	String[] actores;
	int anyo;
	
	public Pelicula(String titulo, String director, String categoria, String[] actores, int anyo) {
		super();
		this.titulo = titulo;
		this.director = director;
		this.actores = actores;
		this.anyo = anyo;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public String[] getActores() {
		return actores;
	}

	public void setActores(String[] actores) {
		this.actores = actores;
	}

	public int getAnyo() {
		return anyo;
	}

	public void setAnyo(int anyo) {
		this.anyo = anyo;
	}
}
