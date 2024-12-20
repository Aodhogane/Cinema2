package com.example.OnlineSinema.controller;

import com.example.OnlineSinema.dto.filmDTO.FilmCardDTO;
import com.example.OnlineSinema.service.FilmService;
import com.example.OnlineSinema.service.impl.ElasticsearchFilmService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/main")
public class MainPageController {

    private final FilmService filmService;
    private final ElasticsearchFilmService elasticsearchFilmService;

    public MainPageController(FilmService filmService, ElasticsearchFilmService elasticsearchFilmService) {
        this.filmService = filmService;
        this.elasticsearchFilmService = elasticsearchFilmService;
    }

    @GetMapping
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
        } else if (genre != null && !genre.isEmpty()) {
            films = filmService.findByGenre(genre, page, size);
        } else {
            films = filmService.findAll(page, size);
        }

        model.addAttribute("films", films);
        model.addAttribute("currentPage", page + 1);
        model.addAttribute("totalPages", films.getTotalPages());
        model.addAttribute("query", query);
        model.addAttribute("genres", genres);
        model.addAttribute("selectedGenre", genre);


        if (principal != null) {
            model.addAttribute("username", principal.getName());
        }

        return "main";
    }
}