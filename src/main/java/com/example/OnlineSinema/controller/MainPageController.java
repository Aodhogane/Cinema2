package com.example.OnlineSinema.controller;

import com.example.OnlineSinema.domain.User;
import com.example.OnlineSinema.dto.filmDTO.FilmCardDTO;
import com.example.OnlineSinema.service.FilmService;
import com.example.OnlineSinema.service.impl.ElasticsearchFilmService;
import com.example.OnlineSinema.service.impl.UserDetailsServiceImpl;
import com.example.SinemaContract.VM.cards.BaseViewModel;
import com.example.SinemaContract.controllers.MainController;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
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

    @Override
    @GetMapping("/main")
    public String getMainPage(
            @RequestParam(required = false, defaultValue = "") String query,
            @RequestParam(required = false) String genre,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            Model model,
            @AuthenticationPrincipal UserDetails userDetails,
            HttpServletRequest request) throws IOException {

        page = Math.max(page, 0);
        size = Math.max(size, 1);

        String usernameCookie = "Guest";

        if(userDetails == null){
            Cookie[] cookies = request.getCookies();
            if(cookies != null){
                for (Cookie cookie : cookies){
                    if ("username".equals(cookie.getName())){
                        usernameCookie = cookie.getValue();
                        break;
                    }
                }
            }
        }

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

        String username = (userDetails != null) ? ((UserDetailsServiceImpl.CustomUser) userDetails).getName() : "Guest";
        BaseViewModel baseViewModel = createBaseVieModel("Main Page", userDetails);

        model.addAttribute("baseViewModel", baseViewModel);
        model.addAttribute("films", films);
        model.addAttribute("currentPage", page + 1);
        model.addAttribute("totalPages", films.getTotalPages());
        model.addAttribute("query", query);
        model.addAttribute("genres", genres);
        model.addAttribute("selectedGenre", genre);

        return "main";
    }


    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        return "redirect:/main";
    }

    @Override
    public BaseViewModel createBaseVieModel(String title, UserDetails userDetails) {
        if (userDetails == null) {
            return new BaseViewModel(title, -1, "Guest");
        }

        if (userDetails instanceof UserDetailsServiceImpl.CustomUser) {
            UserDetailsServiceImpl.CustomUser customUser = (UserDetailsServiceImpl.CustomUser) userDetails;
            return new BaseViewModel(title, customUser.getId(), customUser.getName());
        } else if (userDetails instanceof User) {
            User user = (User) userDetails;
            return new BaseViewModel(title, -1, user.getUsername());
        }

        return new BaseViewModel(title, -1, "Guest");
    }
}