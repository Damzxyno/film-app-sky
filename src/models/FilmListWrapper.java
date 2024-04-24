package models;

import java.util.List;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Films")
public class FilmListWrapper {
	@XmlElement
	private List<Film> films;
	
	public FilmListWrapper() {}
	public FilmListWrapper(List<Film> films) {
		this.films = films;
	}
	public List<Film> getFilm(){
		return this.films;
	}
}
