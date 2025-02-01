package com.example.OnlineSinema.controller;

import com.example.OnlineSinema.DTO.ActorDTO;
import com.example.OnlineSinema.DTO.DirectorDTO;
import com.example.OnlineSinema.DTO.FilmDTO;
import com.example.OnlineSinema.DTO.ReviewDTO;
import com.example.OnlineSinema.service.*;
import com.example.SinemaContract.viewModel.BaseViewModel;
import com.example.SinemaContract.viewModel.FilmDeteilsViewModel;
import com.example.SinemaContract.viewModel.card.ActorCardViewModel;
import com.example.SinemaContract.viewModel.card.ReviewCardViewModel;
import com.example.SinemaContract.controllers.FilmController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/film")
public class FilmsControllerImpl implements FilmController {

    private final FilmService filmService;
    private final ReviewService reviewService;
    private final ClientService clientService;
    private final ActorService actorService;
    private final DirectorService directorService;

    @Autowired
    public FilmsControllerImpl(FilmService filmService, ReviewService reviewService,
                               ClientService clientService, ActorService actorService,
                               DirectorService directorService) {
        this.filmService = filmService;
        this.reviewService = reviewService;
        this.clientService = clientService;
        this.actorService = actorService;
        this.directorService = directorService;
    }

    @Override
    @GetMapping("/{filmId}")
    public String filmDeteilById(@PathVariable int filmId,
                                 Principal principal,
                                 Model model){
        FilmDTO films = filmService.findFilmById(filmId);
        List<ActorDTO> actor = actorService.findActorsByFilmId(filmId);
        DirectorDTO director = directorService.findById(films.getDirectorsId());
        List<ReviewDTO> review = reviewService.findReviewByFilmId(filmId);

        List<ActorCardViewModel> actors = new ArrayList<>();
        for (ActorDTO actorDTO : actor){
            ActorCardViewModel actorCardViewModel = new ActorCardViewModel(actorDTO.getName(), actorDTO.getSurname(), actorDTO.getMidlName());
            actors.add(actorCardViewModel);
        }

        List<ReviewCardViewModel> reviews = new ArrayList<>();
        for (ReviewDTO reviewDTO : review){
            String clientName = clientService.findClintNameByClintId(reviewDTO.getClientId());
            ReviewCardViewModel reviewCardViewModel = new ReviewCardViewModel(clientName, reviewDTO.getComment(), reviewDTO.getEstimation(), reviewDTO.getDateTime());
            reviews.add(reviewCardViewModel);
        }

        FilmDeteilsViewModel viewModel = new FilmDeteilsViewModel(
                films.getTitle(), films.getRating(), actors, director.getName(), director.getSurname(),director.getMidlName(),
                reviews, createBaseVieModel(principal));

        model.addAttribute("model", viewModel);
        return "film-detail";
    }

    @Override
    public BaseViewModel createBaseVieModel(Principal principal) {
        if (principal != null){
            return new BaseViewModel(principal.getName());
        }
        return new BaseViewModel(null);
    }
}
