package Fuente;

import java.util.Arrays;

public class Pelicula {

	String titulo;
	String director;
	String categoria;
	String formato;
	String src;
	String[] actores;
	int anyo;
	
	public Pelicula(String titulo, String director, String categoria, String formato, String src, String[] actores, int anyo) {
		super();
		this.titulo = titulo;
		this.director = director;
		this.categoria = categoria;
		this.formato = formato;
		this.src = src;
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
	
	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getFormato() {
		return formato;
	}

	public void setFormato(String formato) {
		this.formato = formato;
	}
	
	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
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
	
	

	@Override
	public String toString() {
		return "Pelicula [titulo=" + titulo + ", director=" + director
				+ ", categoria=" + categoria + ", formato=" + formato + ", src=" + src + ", actores="
				+ Arrays.toString(actores) + ", anyo=" + anyo + "]";
	}

}
