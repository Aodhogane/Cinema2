package com.example.OnlineSinema.controller;

import com.example.OnlineSinema.domain.Film;
import com.example.OnlineSinema.dto.filmDTO.FilmCardDTO;
import com.example.OnlineSinema.dto.filmDTO.FilmSalesDTO;
import com.example.OnlineSinema.dto.reviewDTO.ReviewOutputDTO;
import com.example.OnlineSinema.dto.userDTO.UserInfoDTO;
import com.example.OnlineSinema.exceptions.FilmNotFounf;
import com.example.OnlineSinema.service.FilmService;
import com.example.OnlineSinema.service.ReviewsService;
import com.example.OnlineSinema.service.UserService;
import com.example.SinemaContract.VM.cards.BaseViewModel;
import com.example.SinemaContract.VM.domain.film.ReviewPageFormModel;
import com.example.SinemaContract.VM.form.actor.ActorPageFM;
import com.example.SinemaContract.VM.form.director.DirectorPageFM;
import com.example.SinemaContract.VM.form.review.ReviewFormModel;
import com.example.SinemaContract.controllers.domeinController.FilmControllerMain;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/film")
public class FilmControllerImpl implements FilmControllerMain {
    private static final Logger LOG = LogManager.getLogger(FilmControllerImpl.class);
    private final FilmService filmService;
    private final ReviewsService reviewsService;
    private final UserService userService;

    public FilmControllerImpl(FilmService filmService,
                              ReviewsService reviewsService,
                              UserService userService) {
        this.filmService = filmService;
        this.reviewsService = reviewsService;
        this.userService = userService;
    }

    @Override
    @GetMapping("/top-sales")
    public String getTopFilmsBySales(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        String username = (userDetails != null) ? userDetails.getUsername() : "Guest";
        BaseViewModel baseViewModel = createBaseVieModel("Top Sales", username);

        List<FilmSalesDTO> topFilms = filmService.getTop5FilmsBySales();
        model.addAttribute("baseViewModel", baseViewModel);
        model.addAttribute("topFilms", topFilms);

        return "top-sales";
    }

    @Override
    @GetMapping("/search")
    public String searchFilms(@RequestParam String query, Model model, @AuthenticationPrincipal UserDetails userDetails) {
        String username = (userDetails != null) ? userDetails.getUsername() : "Guest";
        BaseViewModel baseViewModel = createBaseVieModel("Search Results", username);

        List<FilmCardDTO> searchResults = filmService.findByNameContaining(query);
        model.addAttribute("baseViewModel", baseViewModel);
        model.addAttribute("films", searchResults);
        model.addAttribute("query", query);

        return "search-results";
    }

    @Override
    @GetMapping("/details/{id}")
    public String filmPage(ReviewPageFormModel reviewForm,
                           ActorPageFM actorForm,
                           DirectorPageFM directorForm,
                           @PathVariable int id,
                           Model model,
                           @AuthenticationPrincipal UserDetails userDetails) {
        try {
            String username = (userDetails != null) ? userDetails.getUsername() : "Guest";
            BaseViewModel baseViewModel = createBaseVieModel("Film Details", username);

            Film film = filmService.findFilmWithDetails(id);
            List<ReviewOutputDTO> reviews = reviewsService.findByFilmId(film.getId())
                    .stream()
                    .sorted(Comparator.comparing(ReviewOutputDTO::getDateTime).reversed())
                    .toList();

            double averageRating = reviews.stream()
                    .mapToDouble(ReviewOutputDTO::getEstimation)
                    .average()
                    .orElse(0.0);

            model.addAttribute("baseViewModel", baseViewModel);
            model.addAttribute("film", film);
            model.addAttribute("reviews", reviews);
            model.addAttribute("averageRating", averageRating);

            return "film-detail";

        } catch (FilmNotFounf e) {
            model.addAttribute("error", "Film not found: " + e.getMessage());
            return "errorPage";
        }
    }

    @Override
    public BaseViewModel createBaseVieModel(String title, String username) {
        LOG.info("Creating BaseViewModel with title: {} and username: {}", title, username);

        int userId = -1;
        try {
            UserInfoDTO user = userService.findByUsername(username);
            if (user != null) {
                userId = user.getId();
            }
        } catch (Exception e) {
            LOG.warn("Failed to fetch user ID for username: {}", username, e);
        }

        return new BaseViewModel(title, userId, username);
    }
}