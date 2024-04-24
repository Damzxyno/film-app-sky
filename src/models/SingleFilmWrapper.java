package models;

import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Film")
public class SingleFilmWrapper {
	private int id;
	private String title;
	private int year;
	private String director;
	private String stars;
	private String review;
	
	public SingleFilmWrapper() {}
	public static SingleFilmWrapper getSingleFilmWrapper(Film film) {
		SingleFilmWrapper sfw = new SingleFilmWrapper();
		if (film != null) {
			sfw.id = film.getId();
			sfw.title = film.getTitle();
			sfw.year = film.getYear();
			sfw.director = film.getDirector();
			sfw.stars = film.getStars();
			sfw.review = film.getReview();
		}
		return sfw;
	}
	
	public Film getFilm() {
		Film film = new Film();
		film.setId(this.id);
		film.setTitle(this.title);
		film.setDirector(this.director);
		film.setYear(this.year);
		film.setStars(this.stars);
		film.setReview(this.review);
		return film;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public String getDirector() {
		return director;
	}
	public void setDirector(String director) {
		this.director = director;
	}
	public String getStars() {
		return stars;
	}
	public void setStars(String stars) {
		this.stars = stars;
	}
	public String getReview() {
		return review;
	}
	public void setReview(String review) {
		this.review = review;
	}
}
