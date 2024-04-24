package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import apiObjects.FilmArrayListObjects;
import apiObjects.FilmObject;
import models.Film;
import repositories.FilmRepository;
import utilities.ContentSerializeUtility;
import utilities.ContentTypeUtility;
import utilities.Mapper;

@WebServlet("/films-api")
public class FilmServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final FilmRepository repository = new FilmRepository();
	private final ContentTypeUtility contentTypeUtility = new ContentTypeUtility();
	private final ContentSerializeUtility contentSerializeUtility = new ContentSerializeUtility();
    private final Mapper mapper = new Mapper();   
    public FilmServlet() {
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String filmId = request.getParameter("filmId");
		String acceptValue = contentTypeUtility.getAccept(request);
		if (filmId != null) {
			int id = Integer.parseInt(filmId);
			Film film = repository.getFilmById(id);
			FilmObject filmObject = mapper.filmToFilmObjectToFilm(film);
			String objectString = contentSerializeUtility.serializeObject(acceptValue, filmObject);
			response.getWriter().append(objectString);
		} else if (request.getParameter("searchStr") != null){
			ArrayList<Film> films = repository.searchFilm(request.getParameter("searchStr"));
			FilmArrayListObjects filmObjects = mapper.filmsToFilmArrayListObject(films);
			String objectString = contentSerializeUtility.serializeObject(acceptValue, filmObjects);
			response.getWriter().append(objectString);
		} else {
			ArrayList<Film> films = repository.getAllFilms();
			FilmArrayListObjects filmObjects = mapper.filmsToFilmArrayListObject(films);
			String objectString = contentSerializeUtility.serializeObject(acceptValue, filmObjects);
			response.getWriter().append(objectString);
		}
		response.setContentType(acceptValue);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 String body = request.getReader().lines().collect(Collectors.joining());
		 String contentType = contentTypeUtility.getContentTypeValue(request);
		 String acceptValue = contentTypeUtility.getAccept(request);
		 FilmObject filmObject = contentSerializeUtility.deserializeObject(contentType, FilmObject.class, body);
		 Film film = mapper.filmObjectToFilm(filmObject);
		 repository.insertFilm(film);
		 response.setContentType(acceptValue);
	}
	
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String body = request.getReader().lines().collect(Collectors.joining());
		 String contentType = contentTypeUtility.getContentTypeValue(request);
		 String acceptValue = contentTypeUtility.getAccept(request);
		 FilmObject filmObject = contentSerializeUtility.deserializeObject(contentType, FilmObject.class, body);
		 Film film = mapper.filmObjectToFilm(filmObject);
		 repository.updateFilm(film);
		 response.setContentType(acceptValue);
	}
	
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("filmId"));
		repository.deleteFilm(id);
		response.setContentType(contentTypeUtility.getAccept(request));
	}
}
