package com.example.OnlineSinema.controller;

import com.example.OnlineSinema.dto.filmDTO.FilmCardDTO;
import com.example.OnlineSinema.service.FilmService;
import com.example.SinemaContract.VM.cards.BaseViewModel;
import com.example.SinemaContract.controllers.MainController;
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

import java.util.List;

@Controller
public class MainPageController implements MainController {

    private final FilmService filmService;
    private static final Logger LOG = LogManager.getLogger(MainPageController.class);

    @Autowired
    public MainPageController(FilmService filmService) {
        this.filmService = filmService;
    }

    @Override
    @GetMapping("/main")
    public String getMainPage(
            @RequestParam(required = false) String genre,
            @RequestParam(required = false) String pageParam,
            @RequestParam(required = false) String sizeParam,
            Model model,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        int page = parseIntegerOrDefault(pageParam, 1, 1);
        int size = parseIntegerOrDefault(sizeParam, 5, 1);

        String GenreWork = genre == null ? "" : genre;

        List<String> genres = filmService.getAllGenres();

        Page<FilmCardDTO> films;

        if (genre != null && !genre.isEmpty()) {
            films = filmService.findByGenre(GenreWork, page, size);
            LOG.info("Searching films by genre '{}', page {}, size {}", GenreWork, page, size);
        } else {
            films = filmService.findAll(page, size);
            LOG.info("Fetching all films, page {}, size {}", page, size);
        }

        String username = (userDetails != null) ? userDetails.getUsername() : "Guest";
        BaseViewModel baseViewModel = createBaseVieModel("Main Page", username);

        LOG.info("BaseViewModel being passed to template: {}", baseViewModel);

        model.addAttribute("baseViewModel", baseViewModel);
        model.addAttribute("films", films);
        model.addAttribute("currentPage", films.getNumber());
        model.addAttribute("totalPages", films.getTotalPages());
        model.addAttribute("genres", genres);
        model.addAttribute("size", size);
        model.addAttribute("selectedGenre", GenreWork);
        model.addAttribute("totalElements", films.getTotalElements());

        return "main";
    }


    private int parseIntegerOrDefault(String param, int defaultValue, int minValue) {
        try {
            return Math.max(Integer.parseInt(param), minValue);
        } catch (NumberFormatException | NullPointerException e) {
            return defaultValue;
        }
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        LOG.info("User out!");

        return "redirect:/login";
    }

    @Override
    public BaseViewModel createBaseVieModel(String title, String username) {
        LOG.info("Creating BaseViewModel with title: {} and username: {}", title, username);
        return new BaseViewModel(title, -1, username);
    }
}