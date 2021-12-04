package com.skilldistillery.film.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.skilldistillery.film.data.FilmDAO;
import com.skilldistillery.film.entities.Film;

@Controller
public class FilmController {

	@Autowired
	private FilmDAO filmDao;
	
	//Change Request
	@RequestMapping(path = { "GetFilmData.do" }, params = "Id", method = RequestMethod.GET)
	public ModelAndView getFilmById(Integer Id) {
		ModelAndView mv = new ModelAndView();
		Film f = filmDao.findFilmById(Id);
		mv.addObject("film", f);
		mv.setViewName("WEB-INF/SearchByFilmID.jsp");
		return mv;
	}
	@RequestMapping(path = { "GetFilmData.do" }, params = "keyword", method = RequestMethod.GET)
	public ModelAndView getFilmByKeyword(String keyword) {
		ModelAndView mv = new ModelAndView();
		List<Film> f = filmDao.findFilmByKeyword(keyword);
		mv.addObject("film", f);
		mv.setViewName("WEB-INF/SearchByKeywordInFilm.jsp");
		return mv;
	}
	@RequestMapping(path = { "AddFilmData.do" }, params = "film", method = RequestMethod.GET)
	public ModelAndView createFilm(Film film) {
		ModelAndView mv = new ModelAndView();
		Film f = filmDao.createFilm(film);
		mv.addObject("film", f);
		mv.setViewName("WEB-INF/AddFilm.jsp");
		return mv;
	}
}
