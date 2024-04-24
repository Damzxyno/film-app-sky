package utilities;

import java.util.ArrayList;

import apiObjects.FilmArrayListObjects;
import apiObjects.FilmObject;
import models.Film;

public class Mapper {

	public Film filmObjectToFilm(FilmObject film) {
		if (film == null) {
			return new Film();
		}
		return new Film(film.getId(), film.getTitle(), film.getYear(), film.getDirector(), film.getStars(), film.getReview());
	}
	
	public FilmObject filmToFilmObjectToFilm(Film film) {
		if (film == null) {
			return new FilmObject();
		}
		return new FilmObject(film.getId(), film.getTitle(), film.getYear(), film.getDirector(), film.getStars(), film.getReview());
	}
	
	public FilmArrayListObjects filmsToFilmArrayListObject(ArrayList<Film> films) {
		ArrayList<FilmObject> filmsObjects = new ArrayList<>();
		for(Film film : films) {
			filmsObjects.add(filmToFilmObjectToFilm(film));
		}
		return new FilmArrayListObjects(filmsObjects);
	}
}
