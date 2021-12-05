package com.skilldistillery.film.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.skilldistillery.film.data.FilmDAO;
import com.skilldistillery.film.entities.Actor;
import com.skilldistillery.film.entities.Film;


@Controller
public class FilmController {

	@Autowired
	private FilmDAO filmDao;
	
	@RequestMapping(path = {"/", "home.do"})
	public String goHome() {
		return "home";
	}

	@RequestMapping(path = "searchFilmByIDForm.do")
	public ModelAndView searchFilmByID() {
		ModelAndView mv = new ModelAndView("searchFilmByIDForm");
		return mv;
	}

	@RequestMapping(path = "searchFilmByID.do", params = "filmID", method = RequestMethod.GET)
	public ModelAndView doSearch(int filmID) {
		ModelAndView mv = new ModelAndView();

		Film foundFilm = filmDao.findFilmById(filmID);
		List<Actor> actors = filmDao.findActorsByFilmId(filmID);

		mv.addObject("film", foundFilm);
		mv.addObject("actors", actors);
		mv.setViewName("searchFilmByID");
		return mv;
	}

	@RequestMapping(path = "addFilmForm.do", method = RequestMethod.GET)
	public ModelAndView newFilmForm(Film film) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("addFilmForm");
		return mv;
	}

	@RequestMapping(path = "addFilmNew.do", method = RequestMethod.POST)
	public ModelAndView newFilm(Film film) {
		ModelAndView mv = new ModelAndView();
		Film newFilm = filmDao.createFilm(film);
		
		
		mv.addObject("film", newFilm);
		mv.setViewName("searchFilmByID");
		return mv;
	}

	@RequestMapping(path = "deleteFilm.do", params = "filmID", method = RequestMethod.POST)
	public ModelAndView deleteFilmFromSearch(@RequestParam("filmID") int filmID) {

		boolean delete = filmDao.deleteFilm(filmID);

		ModelAndView mv = new ModelAndView();
		Film foundFilm = filmDao.findFilmById(filmID);
		mv.addObject("film", foundFilm);
		mv.setViewName("deleteResult");
		return mv;
	}

	@RequestMapping(path = "getFilmFields.do", params = "filmID", method = RequestMethod.GET)
	public ModelAndView getFilmFields(@RequestParam("filmID") int filmID) {
		ModelAndView mv = new ModelAndView();

		Film foundFilm = filmDao.findFilmById(filmID);

		mv.addObject("film", foundFilm);
		mv.setViewName("update");
		return mv;
	}

	@RequestMapping(path = "saveFilmFields.do", params = "filmID", method = RequestMethod.POST)
	public ModelAndView saveFilmFields(@RequestParam("filmID") int filmID, Film film) {
		ModelAndView mv = new ModelAndView();

		Film updatedFilm = filmDao.saveFilmAllFields(filmID, film);
		Film display = filmDao.findFilmById(filmID);
		mv.addObject("film", display);
		mv.setViewName("searchFilmByID");
		return mv;
	}

	@RequestMapping(path = "searchKeyword.do", params = "keyword", method = RequestMethod.GET)
	public ModelAndView doKeywordSearch(@RequestParam("keyword") String keyword) {
		ModelAndView mv = new ModelAndView();

		List<Film> foundFilms = filmDao.findFilmByKeyword(keyword);

		mv.addObject("films", foundFilms);
		mv.setViewName("keywordSearch");
		return mv;
	}

}
