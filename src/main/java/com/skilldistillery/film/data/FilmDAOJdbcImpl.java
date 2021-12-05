package com.skilldistillery.film.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.skilldistillery.film.entities.Actor;
import com.skilldistillery.film.entities.Category;
import com.skilldistillery.film.entities.Film;

@Service
public class FilmDAOJdbcImpl implements FilmDAO {

	{
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private static final String URL = "jdbc:mysql://localhost:3306/sdvid?useSSL=false&useLegacyDatetimeCode=false&serverTimezone=US/Mountain";
	private static final String USER = "student";
	private static final String PASS = "student";

	@Override
	public Film findFilmById(int filmId) {
		Film film = null;

		try {
			Connection conn = DriverManager.getConnection(URL, USER, PASS);
			String sql = "select film.id, film.title, film.description, film.release_year, lang.name, film.rental_duration, film.length, film.rental_rate, film.replacement_cost, film.rating, film.special_features\n"
					+ "from film\n" + "join language lang\n" + "on film.language_id = lang.id\n" + "where film.id = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, filmId);
			ResultSet filmResult = stmt.executeQuery();
			if (filmResult.next()) {
				film = new Film();

				film.setFilmId(filmResult.getInt("film.id"));
				film.setTitle(filmResult.getString("film.title"));
				film.setDescription(filmResult.getString("film.description"));
				film.setReleaseYear(filmResult.getInt("film.release_year"));
				film.setLanguage(filmResult.getString("lang.name"));
				film.setRentalDuration(filmResult.getInt("film.rental_duration"));
				film.setLength(filmResult.getInt("film.length"));
				film.setRate(filmResult.getDouble("film.rental_rate"));
				film.setReplacementCost(filmResult.getDouble("film.replacement_cost"));
				film.setRating(filmResult.getString("film.rating"));
				film.setSpecialFeatures(filmResult.getString("film.special_features"));
				film.setActors(findActorsByFilmId(film.getFilmId()));
				film.setCategoryFilm(findCategoryByFilmId(film.getFilmId()));
			}
			filmResult.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return film;
	}

	@Override
	public List<Film> findFilmByKeyword(String keyword) {
		List<Film> films = new ArrayList<>();

		try (Connection conn = DriverManager.getConnection(URL, USER, PASS);) {
			String sql = "select film.id, film.title, film.description, film.release_year, lang.name,\n"
					+ " film.rental_duration, film.length, film.rental_rate, film.replacement_cost,\n"
					+ "  film.rating, film.special_features\n" + "from film\n" + "join language lang\n"
					+ "on film.language_id = lang.id\n" + "where film.title LIKE ? or film.description LIKE ?;";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, "%" + keyword + "%");
			stmt.setString(2, "%" + keyword + "%");
			ResultSet filmResult = stmt.executeQuery();
			while (filmResult.next()) {
				Film film = new Film();

				film.setFilmId(filmResult.getInt("film.id"));
				film.setTitle(filmResult.getString("film.title"));
				film.setDescription(filmResult.getString("film.description"));
				film.setReleaseYear(filmResult.getInt("film.release_year"));
				film.setLanguage(filmResult.getString("lang.name"));
				film.setRentalDuration(filmResult.getInt("film.rental_duration"));
				film.setLength(filmResult.getInt("film.length"));
				film.setRate(filmResult.getDouble("film.rental_rate"));
				film.setReplacementCost(filmResult.getDouble("film.replacement_cost"));
				film.setRating(filmResult.getString("film.rating"));
				film.setSpecialFeatures(filmResult.getString("film.special_features"));
				film.setActors(findActorsByFilmId(film.getFilmId()));
				film.setCategoryFilm(findCategoryByFilmId(film.getFilmId()));

				films.add(film);
			}
			filmResult.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return films;
	}

	@Override
	public List<Actor> findActorsByFilmId(int filmId) {
		List<Actor> actors = new ArrayList<>();
		Actor actor = null;

		try (Connection conn = DriverManager.getConnection(URL, USER, PASS);) {
			String sql = "SELECT actor.id, actor.first_name, actor.last_name \n" + "FROM actor LEFT JOIN film_actor \n"
					+ "ON actor.id = film_actor.actor_id \n" + "LEFT JOIN film ON film_actor.film_id = film.id \n"
					+ "WHERE film.id = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, filmId);
			ResultSet actorResult = stmt.executeQuery();
			while (actorResult.next()) {
				actor = new Actor();

				actor.setId(actorResult.getInt("actor.id"));
				actor.setFirstName(actorResult.getString("actor.first_name"));
				actor.setLastName(actorResult.getString("actor.last_name"));

				actors.add(actor);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return actors;
	}

	@Override
	public Actor findActorById(int actorId) {
		Actor actor = null;

		try (Connection conn = DriverManager.getConnection(URL, USER, PASS);) {
			String sql = "SELECT id, first_name, last_name FROM actor WHERE id = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, actorId);
			ResultSet actorResult = stmt.executeQuery();
			if (actorResult.next()) {
				actor = new Actor();

				actor.setId(actorResult.getInt("id"));
				actor.setFirstName(actorResult.getString("first_name"));
				actor.setLastName(actorResult.getString("last_name"));
				actor.setFilms(findFilmsByActorId(actorId));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return actor;
	}

	@Override
	public List<Film> findFilmsByActorId(int actorId) {
		List<Film> films = new ArrayList<>();

		try {
			Connection conn = DriverManager.getConnection(URL, USER, PASS);
			String sql = "SELECT id, title, description, release_year, language_id, rental_duration, ";
			sql += " rental_rate, length, replacement_cost, rating, special_features "
					+ " FROM film JOIN film_actor ON film.id = film_actor.film_id " + " WHERE actor_id = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, actorId);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				int filmId = rs.getInt("id");
				String title = rs.getString("title");
				String desc = rs.getString("description");
				int releaseYear = rs.getShort("release_year");
				int langId = rs.getInt("language_id");
				int rentDur = rs.getInt("rental_duration");
				double rate = rs.getDouble("rental_rate");
				int length = rs.getInt("length");
				double repCost = rs.getDouble("replacement_cost");
				String rating = rs.getString("rating");
				String features = rs.getString("special_features");
				Film film = new Film(filmId, title, desc, releaseYear, langId, rentDur, rate, length, repCost, rating,
						features);
				films.add(film);
			}
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return films;
	}

	public boolean deleteFilmObj(Film film) {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(URL, USER, PASS);
			conn.setAutoCommit(false); // START TRANSACTION
			String sql = "DELETE FROM film WHERE film.id = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, film.getFilmId());
			int updateCount = stmt.executeUpdate();
			conn.commit(); // COMMIT TRANSACTION
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			if (conn != null) {
				try {
					conn.rollback();
				} catch (SQLException sqle2) {
					System.err.println("Error trying to rollback");
				}
			}
			return false;
		}
		return true;
	}

	@Override
	public boolean deleteFilm(int filmID) {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(URL, USER, PASS);
			conn.setAutoCommit(false); // START TRANSACTION
			String sql = "DELETE FROM film WHERE film.id = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, filmID);
			int updateCount = stmt.executeUpdate();
			conn.commit(); // COMMIT TRANSACTION
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			if (conn != null) {
				try {
					conn.rollback();
				} catch (SQLException sqle2) {
					System.err.println("Error trying to rollback");
				}
			}
			return false;
		}
		return true;
	}

	@Override
	public Film createFilm(Film film) {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(URL, USER, PASS);
			conn.setAutoCommit(false); // START TRANSACTION
			String sql = "INSERT INTO film (title, description, release_year, language_id, rental_duration, rental_rate, length, replacement_cost, rating, special_features)\n"
					+ "VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

			PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			stmt.setString(1, film.getTitle());
			stmt.setString(2, film.getDescription());
			stmt.setInt(3, film.getReleaseYear());
			stmt.setInt(4, film.getLanguageID());
			stmt.setInt(5, film.getRentalDuration());
			stmt.setDouble(6, film.getRate());
			stmt.setInt(7, film.getLength());
			stmt.setDouble(8, film.getReplacementCost());
			stmt.setString(9, film.getRating());
			stmt.setString(10, film.getSpecialFeatures());

			int uc = stmt.executeUpdate();

			System.out.println(uc + " film was created.");

			ResultSet keys = stmt.getGeneratedKeys();

			if (keys.next()) {
				int newFilmID = keys.getInt(1);
				film.setFilmId(newFilmID);
			}

			conn.commit(); // COMMIT TRANSACTION

		} catch (SQLException sqle) {
			sqle.printStackTrace();
			if (conn != null) {
				try {
					conn.rollback();
				} catch (SQLException sqle2) {
					System.err.println("Error trying to rollback");
				}
			}
			throw new RuntimeException("Error inserting film " + film);
		}

		return film;

	}

	@Override
	public Film saveFilmAllFields(int filmID, Film film) {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(URL, USER, PASS);
			conn.setAutoCommit(false); // START TRANSACTION

			String sql = "UPDATE film SET title = ?, description = ?, release_year = ?, language_id = ?,"
					+ "rental_duration = ?, rental_rate = ?, length = ?, replacement_cost = ?, rating = ?, special_features = ?\n"
					+ "WHERE film.id = ?;";

			PreparedStatement stmt = conn.prepareStatement(sql);

			stmt.setString(1, film.getTitle());
			stmt.setString(2, film.getDescription());
			stmt.setInt(3, film.getReleaseYear());
			stmt.setInt(4, film.getLanguageID());
			stmt.setInt(5, film.getRentalDuration());
			stmt.setDouble(6, film.getRate());
			stmt.setInt(7, film.getLength());
			stmt.setDouble(8, film.getReplacementCost());
			stmt.setString(9, film.getRating());
			stmt.setString(10, film.getSpecialFeatures());
			stmt.setInt(11, filmID);

			int updateCount = stmt.executeUpdate();
			if (updateCount == 1) {
				conn.commit(); // COMMIT TRANSACTION
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			if (conn != null) {
				try {
					conn.rollback(); // ROLLBACK TRANSACTION ON ERROR
				} catch (SQLException sqle2) {
					System.err.println("Error trying to rollback");
				}
			}
			return film;
		}
		return film;
	}

	@Override
	public String findCategoryByFilmId(int filmId) {
		Category cat = null;
		if(filmId > 1000) {
			return "No category found";
		}
		try (Connection conn = DriverManager.getConnection(URL, USER, PASS);) {
			String sql = "SELECT category.id, category.name \n" + "FROM category LEFT JOIN film_category \n"
					+ "ON category.id = film_category.category_id \n"
					+ "LEFT JOIN film ON film_category.film_id = film.id \n" + "WHERE film.id = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, filmId);

			ResultSet catResult = stmt.executeQuery();
			if (catResult.next()) {
				cat = new Category();
				cat.setId(catResult.getInt("category.id"));
				cat.setName(catResult.getString("category.name"));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return cat.getName();
	}

}