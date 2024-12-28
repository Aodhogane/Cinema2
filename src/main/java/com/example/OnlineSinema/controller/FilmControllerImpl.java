package com.example.OnlineSinema.controller;

import com.example.OnlineSinema.domain.Film;
import com.example.OnlineSinema.dto.filmDTO.FilmCardDTO;
import com.example.OnlineSinema.dto.filmDTO.FilmOutputDTO;
import com.example.OnlineSinema.dto.filmDTO.FilmSalesDTO;
import com.example.OnlineSinema.dto.reviewDTO.ReviewOutputDTO;
import com.example.OnlineSinema.exceptions.FilmNotFounf;
import com.example.OnlineSinema.service.FilmService;
import com.example.OnlineSinema.service.ReviewsService;
import com.example.OnlineSinema.service.UserService;
import com.example.OnlineSinema.service.impl.UserDetailsServiceImpl;
import com.example.SinemaContract.VM.cards.BaseViewModel;
import com.example.SinemaContract.VM.domain.film.ReviewPageFormModel;
import com.example.SinemaContract.VM.form.actor.ActorPageFM;
import com.example.SinemaContract.VM.form.director.DirectorPageFM;
import com.example.SinemaContract.VM.form.review.ReviewFormModel;
import com.example.SinemaContract.controllers.domeinController.FilmControllerMain;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
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


    @GetMapping("/top-sales")
    public String getTopFilmsBySales(Model model) {
        List<FilmSalesDTO> topFilms = filmService.getTop5FilmsBySales();
        model.addAttribute("topFilms", topFilms);
        return "top-sales";
    }


    @GetMapping("/search")
    public String searchFilms(@RequestParam String query, Model model) {
        List<FilmCardDTO> searchResults = filmService.findByNameContaining(query);
        model.addAttribute("films", searchResults);
        model.addAttribute("query", query);
        return "search-results";
    }

    @Override
    public BaseViewModel createBaseVieModel(String title, UserDetails userDetails) {
        if (userDetails == null) {
            return new BaseViewModel(title, -1, null);
        } else {
            UserDetailsServiceImpl.CustomUser customUser = (UserDetailsServiceImpl.CustomUser) userDetails;
            return new BaseViewModel(title, customUser.getId(), customUser.getName());
        }
    }

    @Override
    @GetMapping("/details/{id}")
    public String filmPage(ReviewPageFormModel reviewForm,
                           ActorPageFM actorForm,
                           DirectorPageFM directorForm,
                           @PathVariable int id, Model model) {
        try {
            Film film = filmService.findFilmWithDetails(id);
            List<ReviewOutputDTO> reviews = reviewsService.findByFilmId(film.getId())
                    .stream()
                    .sorted(Comparator.comparing(ReviewOutputDTO::getDateTime).reversed())
                    .collect(Collectors.toList());

            double averageRating = reviews.stream()
                    .mapToDouble(ReviewOutputDTO::getEstimation)
                    .average()
                    .orElse(0.0);

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
    @PostMapping("/films/{id}/review")
    public String addReview(
            @PathVariable("id") int filmId,
            @Valid @ModelAttribute("reviewForm") ReviewFormModel reviewFM,
            BindingResult bindingResult,
            Model model,
            Principal principal){

        if (bindingResult.hasErrors()){
            model.addAttribute("error", "Validation failed");
            return "film-detail";
        }

        try {
            String username = principal.getName();
            int userId = userService.findByUsername(username).getId();

            ReviewFormModel validForm = new ReviewFormModel(
                    userId,
                    username,
                    filmId,
                    reviewFM.nameFilm(),
                    reviewFM.filmId(),
                    reviewFM.text()
            );

            reviewsService.save(validForm);
            filmService.updateRatingFilm(filmId);

            return "redirect:/films/" + filmId;
        } catch (Exception e){
            model.addAttribute("error", "Failed to add review: " + e.getMessage());
            return "errorPage";
        }
    }
}