package apiObjects;

import java.util.ArrayList;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Films")
public class FilmArrayListObjects {
	@XmlElement
	private ArrayList<FilmObject> films;
	
	public FilmArrayListObjects() {}
	public FilmArrayListObjects(ArrayList<FilmObject> films) {
		this.films = films;
	}
	public  ArrayList<FilmObject> getFilms(){
		return this.films;
	}
}

