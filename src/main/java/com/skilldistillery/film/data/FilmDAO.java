package com.skilldistillery.film.data;

import java.util.List;

import com.skilldistillery.film.entities.Actor;
import com.skilldistillery.film.entities.Film;

public interface FilmDAO {
	public Film findFilmById(int filmId);

	public List<Film> findFilmByKeyword(String keyword);

	public List<Actor> findActorsByFilmId(int filmId);

	public Actor findActorById(int actorId);

	public List<Film> findFilmsByActorId(int actorId);

	public Film createFilm(Film film);

	public String findCategoryByFilmId(int filmId);

	public boolean deleteFilm(int filmID);

	public Film saveFilmAllFields(int filmID, Film film);

}
