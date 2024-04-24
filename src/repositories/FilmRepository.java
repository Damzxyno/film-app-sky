package repositories;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import models.Film;

import java.io.IOException;
import java.sql.*;


public class FilmRepository {
	
	private Connection conn = null;
    private Statement stmt = null;
    private Properties properties;
	private String user;
    private String password;
    private String url; 

	public FilmRepository() {
		loadProperties();
		initializeDBSettings();
	}

	private Properties loadProperties() {
		properties = new Properties();
		try {
			properties.load(getClass().getClassLoader().getResourceAsStream("settings.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return properties;
	}
	
	private void initializeDBSettings() {
		if (properties ==  null) {
			return;
		}
		user = properties.getProperty("database-user");
		password = properties.getProperty("database-password");
		url = properties.getProperty("database-url") + '/' + properties.getProperty("database-name");	
	}
	private void openConnection(){
		try{
		    Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch(Exception e) { System.out.println(e); }
		try{
 			conn = DriverManager.getConnection(url, user, password);
		    stmt = conn.createStatement();
		} catch(SQLException se) { System.out.println(se); }	   
    }
	private void closeConnection(){
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private Film getNextFilm(ResultSet rs){
    	Film thisFilm=null;
		try {
			thisFilm = new Film(
					rs.getInt("id"),
					rs.getString("title"),
					rs.getInt("year"),
					rs.getString("director"),
					rs.getString("stars"),
					rs.getString("review"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return thisFilm;		
	}
	
	
	
   public ArrayList<Film> getAllFilms(){
		openConnection();
		var films = new ArrayList<Film>();
		try{
		    String sql = "SELECT * FROM films";
		    ResultSet rs1 = stmt.executeQuery(sql);
		    while(rs1.next()){
		    	films.add(getNextFilm(rs1));
		   }
		    stmt.close();
		    closeConnection();
		} catch(SQLException se) { System.out.println(se); }
	   return films;
   }

   public Film getFilmById(int id){
		openConnection();
		Film oneFilm=null;
		try{
		    String sql = "SELECT * FROM films WHERE id= "+ id;
		    ResultSet rs1 = stmt.executeQuery(sql);
		    while(rs1.next()){
		    	oneFilm = getNextFilm(rs1);
		    }

		    stmt.close();
		    closeConnection();
		} catch(SQLException se) { System.out.println(se); }

	   return oneFilm;
   }
   
   public void insertFilm (Film film){
		openConnection();
		try{ 
		    String sql = "INSERT INTO films (title, year, director, stars, review) VALUES("  
		    		+ "'" + film.getTitle() + "', "
		    		+ "'" + film.getYear() + "', "
		    		+ "'" + film.getDirector() + "', "
		    		+ "'" + film.getStars() + "', "
		    		+ "'" + film.getReview() + "')";
		    stmt.execute(sql);
		    stmt.close();
		    closeConnection();
		} catch(SQLException se) { System.out.println(se); }
  }
   
   public void updateFilm (Film film){
		openConnection();
		try{
		    String sql = "UPDATE films SET title='" + film.getTitle() 
		    					+ "', year='" + film.getYear()
		    					+ "', director='" + film.getDirector()
		    					+ "', stars='" + film.getStars()
		    					+ "', review='" + film.getReview()
		    					+ "' WHERE id =" + film.getId();
		    stmt.execute(sql);
		    
		    stmt.close();
		    closeConnection();
		} catch(SQLException se) { System.out.println(se); }
   }
   public void deleteFilm (int id){
		openConnection();
		try{
		    String sql = "DELETE FROM films WHERE id=" + id;
		    stmt.execute(sql);
		    stmt.close();
		    closeConnection();
		} catch(SQLException se) { System.out.println(se); }
  }
  
   public ArrayList<Film> searchFilm (String searchStr){
		openConnection();
		var films = new ArrayList<Film>();
		try{
		    String sql = "SELECT * FROM films WHERE title LIKE '%" + searchStr +  "%' OR review LIKE '%" + searchStr +  "%'";
		    ResultSet rs1 = stmt.executeQuery(sql);
		    while(rs1.next()){
		    	films.add(getNextFilm(rs1));
		   }
		    stmt.close();
		    closeConnection();
		} catch(SQLException se) { System.out.println(se); }
	   return films;
  }
}
