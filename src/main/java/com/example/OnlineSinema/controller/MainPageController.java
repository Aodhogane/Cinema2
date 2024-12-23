package com.example.OnlineSinema.controller;

import com.example.OnlineSinema.dto.filmDTO.FilmCardDTO;
import com.example.OnlineSinema.service.FilmService;
import com.example.OnlineSinema.service.impl.ElasticsearchFilmService;
import com.example.OnlineSinema.service.impl.UserDetailsServiceImpl;
import com.example.SinemaContract.VM.cards.BaseViewModel;
import com.example.SinemaContract.controllers.MainController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.security.Principal;
import java.util.Collections;
import java.util.List;

@Controller
public class MainPageController implements MainController {

    private final FilmService filmService;
    private final ElasticsearchFilmService elasticsearchFilmService;
    private static final Logger LOG = LogManager.getLogger(MainPageController.class);

    @Autowired
    public MainPageController(FilmService filmService, ElasticsearchFilmService elasticsearchFilmService) {
        this.filmService = filmService;
        this.elasticsearchFilmService = elasticsearchFilmService;
    }

    @GetMapping("/main")
    public String getMainPage(
            @RequestParam(required = false, defaultValue = "") String query,
            @RequestParam(required = false) String genre,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model,
            Principal principal
    ) throws IOException {
        page = Math.max(page, 0);
        size = Math.max(size, 1);

        List<String> genres = filmService.getAllGenres();

        Page<FilmCardDTO> films;
        if (query != null && !query.isEmpty()) {
            films = elasticsearchFilmService.searchFilms(query, page, size);
            LOG.info("Searching films by query: '{}', page: {}, size: {}", query, page, size);
        } else if (genre != null && !genre.isEmpty()) {
            films = filmService.findByGenre(genre, page, size);
            LOG.info("Searching films by genre: '{}', page: {}, size: {}", genre, page, size);
        } else {
            films = filmService.findAll(page, size);
            LOG.info("Fetching all films, page: {}, size: {}", page, size);
        }

        UserDetails userDetails = null;
        if (principal != null) {
            if (principal instanceof Authentication) {
                Authentication authentication = (Authentication) principal;
                userDetails = (UserDetails) authentication.getPrincipal();
            }
        }

        BaseViewModel baseViewModel = createBaseVieModel("Main Page", userDetails);

        model.addAttribute("baseViewModel", baseViewModel);
        model.addAttribute("films", films);
        model.addAttribute("currentPage", page + 1);
        model.addAttribute("totalPages", films.getTotalPages());
        model.addAttribute("query", query);
        model.addAttribute("genres", genres);
        model.addAttribute("selectedGenre", genre);

        if (userDetails != null) {
            String username = userDetails.getUsername();
            LOG.info("Main page model constructed successfully for user '{}'.", username);
        } else {
            LOG.info("Main page model constructed successfully for anonymous user.");
        }

        return "main";
    }

    @Override
    public BaseViewModel createBaseVieModel(String title, UserDetails userDetails) {
        if (userDetails == null){
            return new BaseViewModel(title, -1, null);
        } else if (userDetails instanceof UserDetailsServiceImpl.CustomUser) {
            UserDetailsServiceImpl.CustomUser customUser = (UserDetailsServiceImpl.CustomUser) userDetails;
            return new BaseViewModel(title, customUser.getId(), customUser.getName());
        } else {
            return new BaseViewModel(title, -1, null);
        }
    }
}