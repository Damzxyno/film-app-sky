package utilities;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;
import com.google.gson.Gson;

import apiObjects.FilmArrayListObjects;
import apiObjects.FilmObject;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import models.Film;

public class ContentSerializeUtility {
	public <Type> String serializeObject(String acceptValue, Type value) {
		if (acceptValue.equals("application/json")) {
			return toJson(value);
		} else if (acceptValue.equals("application/xml")) {
			return toXml(value);
		} else if (acceptValue.equals("text/plain")) {
			return toPlainText(value);
		} else {
			return toJson(value);
		}
	}
	
	public <Type> Type deserializeObject(String contentType, Class<Type> type, String body) {
		if (contentType.equals("application/json")) {
			return fromJson(body, type);
		} else if (contentType.equals("application/xml")) {
			return fromXml(body, type);
		} else if (contentType.equals("text/plain")) {
			return fromPlainText(body, type);
		} else {
			return fromJson(body, type);
		}
	}
	
	private <Type> Type fromJson(String body, Class<Type> type) {
		Gson gson = new Gson();
        return gson.fromJson(body, type);
	}
	
	@SuppressWarnings("unchecked")
	private <Type> Type fromXml(String body, Class<Type> type) {
		 try {
			 JAXBContext context = JAXBContext.newInstance(type);
			 Unmarshaller unmarshaller = context.createUnmarshaller();
	         return (Type) unmarshaller.unmarshal(new StringReader(body));
	        } catch (JAXBException e) {
	            e.printStackTrace();
	            return null; 
	        }
	}
	
	@SuppressWarnings("unchecked")
	private <Type> Type fromPlainText(String body, Class<Type> type) {
		if (type.equals(FilmObject.class)) {
			return (Type) fromPlainSingleFilm(body);
		} else {
			return (Type) fromPlainListOfFilms(body);
		}
	}
		
	
	private String toJson(Object object) {
		Gson gson = new Gson();
		String json = gson.toJson(object);
		return json;
	}
	
	private<Type> String toXml(Type value) {
	    try {
	    	JAXBContext jaxbContext = JAXBContext.newInstance(value.getClass());
		    Marshaller marshaller = jaxbContext.createMarshaller();
		    marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		    StringWriter stringWriter = new StringWriter();
		    marshaller.marshal(value, stringWriter);
		    return stringWriter.toString();
	    } catch (JAXBException jaxbException) {
	    	jaxbException.printStackTrace();
	    	return "";
	    }
	}
	
	@SuppressWarnings("unchecked")
	private<Type> String toPlainText (Type value) {
		if (value instanceof FilmObject) {
			FilmObject film = (FilmObject) value;
			return toPlainSingleFilm(film);
		} else {
			var films = (FilmArrayListObjects) value;
			return toPlainListOfFilms(films.getFilms());
		}
	}
	
	private String toPlainListOfFilms(ArrayList<FilmObject> value) {
		return value.stream()
	            .map(this::toPlainSingleFilm)
	            .collect(Collectors.joining("\\^"));
	}

	private String toPlainSingleFilm(FilmObject film) {
		return film.getId() + "#" + film.getTitle() + "#" + film.getYear() + "#" + film.getDirector() + "#" + film.getStars()  + "#" + film.getReview();
	}
	private FilmObject fromPlainSingleFilm(String body) {
		String [] arr = body.split("#");
		if (arr.length == 6) {
			return new FilmObject(Integer.parseInt(arr[0]), arr[1], Integer.parseInt(arr[2]), arr[3], arr[4], arr[5]);
		} else {
			return new FilmObject(arr[0], Integer.parseInt(arr[1]), arr[2], arr[3], arr[4]);
		}
		
	}
	private FilmArrayListObjects fromPlainListOfFilms(String body) {
		String [] arr = body.split("\\^");
		return new FilmArrayListObjects (Arrays.stream(arr)
	            .map(this::fromPlainSingleFilm)
	            .collect(Collectors.toCollection(ArrayList::new)));
	}
	
}
