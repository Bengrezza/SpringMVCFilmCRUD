package com.skilldistillery.film.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.skilldistillery.film.data.FilmDAO;
import com.skilldistillery.film.entities.Actor;
import com.skilldistillery.film.entities.Category;
import com.skilldistillery.film.entities.Film;


@Controller
public class FilmController {

	@Autowired
	private FilmDAO filmDao;
	
	@RequestMapping(path = {"/", "home.do"})
	public String goHome() {
		return "home";
	}

	//Change Request
		@RequestMapping(path = { "GetFilmID.do" }, params = "filmID", method = RequestMethod.GET)
		public ModelAndView getFilmId(int filmID) {
			ModelAndView mv = new ModelAndView();
			Film f = filmDao.findFilmById(filmID);
			mv.addObject("film", f);
			mv.setViewName("SearchByFilmID");
			return mv;
		}

		
		@RequestMapping(path = { "GetFilmData.do" }, params = "keyword", method = RequestMethod.GET)
		public ModelAndView getFilmByKeyword(String keyword) {
			ModelAndView mv = new ModelAndView();
			List<Film> f = filmDao.findFilmByKeyword(keyword);
			mv.addObject("film", f);
			mv.setViewName("SearchByKeywordInFilm");
			return mv;
		}
		@RequestMapping(path = { "AddFilmData.do" }, params = "film", method = RequestMethod.GET)
		public ModelAndView createFilm(Film film) {
			ModelAndView mv = new ModelAndView();
			Film f = filmDao.createFilm(film);
			mv.addObject("film", f);
			mv.setViewName("AddFilm");
			return mv;
		}
	}


