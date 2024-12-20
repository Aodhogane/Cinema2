package com.example.OnlineSinema.controller;

import com.example.OnlineSinema.dto.filmDTO.FilmOutputDTO;
import com.example.OnlineSinema.dto.filmDTO.FilmSalesDTO;
import com.example.OnlineSinema.dto.reviewDTO.ReviewOutputDTO;
import com.example.OnlineSinema.exceptions.FilmNotFounf;
import com.example.OnlineSinema.service.FilmService;
import com.example.OnlineSinema.service.ReviewsService;
import com.example.SinemaContract.VM.cards.BaseViewModel;
import com.example.SinemaContract.VM.domain.film.ReviewPageFormModel;
import com.example.SinemaContract.VM.form.actor.ActorPageFM;
import com.example.SinemaContract.VM.form.director.DirectorPageFM;
import com.example.SinemaContract.controllers.domeinController.FilmControllerMain;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/films")
public class FilmControllerImpl implements FilmControllerMain {

    private final FilmService filmService;
    private final ReviewsService reviewsService;

    public FilmControllerImpl(FilmService filmService, ReviewsService reviewsService) {
        this.filmService = filmService;
        this.reviewsService = reviewsService;
    }

    @Override
    public String filmPage(ReviewPageFormModel reviewForm, ActorPageFM actorForm, DirectorPageFM directorForm, int id, Model model) {
        try {
            FilmOutputDTO film = filmService.findById(id);

            List<ReviewOutputDTO> reviews = reviewsService.findByFilmId(id)
                    .stream()
                    .sorted(Comparator.comparing(ReviewOutputDTO::getDateTime).reversed())
                    .limit(10)
                    .collect(Collectors.toList());

            double averageRating = reviews.stream()
                    .mapToDouble(ReviewOutputDTO::getEstimation)
                    .average()
                    .orElse(0.0);

            List<String> actors = film.getActors();
            List<String> directors = film.getDirectors();

            model.addAttribute("film", film);
            model.addAttribute("reviews", reviews);
            model.addAttribute("actors", actors);
            model.addAttribute("directors", directors);
            model.addAttribute("averageRating", averageRating);

            return "details";
        } catch (FilmNotFounf e) {
            model.addAttribute("error", "Film not found: " + e.getMessage());
            return "error/404";
        } catch (Exception e) {
            model.addAttribute("error", "An unexpected error occurred: " + e.getMessage());
            return "error/500";
        }
    }

    @GetMapping("/top-sales")
    public String getTopFilmsBySales(Model model) {
        List<FilmSalesDTO> topFilms = filmService.getTop5FilmsBySales();
        model.addAttribute("topFilms", topFilms);
        return "films/top-sales";
    }

    @GetMapping("/most-discussed")
    public String getTopFilmsByReviewCount(@RequestParam(name = "isTop", defaultValue = "true") boolean isTop, Model model) {
        List<FilmOutputDTO> films = filmService.findTopFilmsByReviewCount(isTop);
        model.addAttribute("films", films);
        model.addAttribute("isTop", isTop);
        return "films/most-discussed";
    }

    @Override
    public BaseViewModel createBaseVieModel(String title, int filmId) {
        return new BaseViewModel(title, filmId);
    }
}