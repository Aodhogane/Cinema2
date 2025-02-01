package com.example.OnlineSinema.controller;

import com.example.OnlineSinema.DTO.ActorDTO;
import com.example.OnlineSinema.DTO.DirectorDTO;
import com.example.OnlineSinema.DTO.FilmDTO;
import com.example.OnlineSinema.DTO.ReviewDTO;
import com.example.OnlineSinema.service.*;
import com.example.SinemaContract.viewModel.BaseViewModel;
import com.example.SinemaContract.viewModel.ReviewViewModel;
import com.example.SinemaContract.viewModel.card.ActorCardViewModel;
import com.example.SinemaContract.viewModel.form.ReviewCreateForm;
import com.example.SinemaContract.controllers.ReviewController;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/review")
public class ReviewsControllerImpl implements ReviewController {

    private final ReviewService reviewService;
    private final FilmService filmService;
    private final DirectorService directorService;
    private final ActorService actorService;
    private final ClientService clientService;

    @Autowired
    public ReviewsControllerImpl(ReviewService reviewService, FilmService filmService,
                             DirectorService directorService, ActorService actorService,
                             ClientService clientService) {
        this.reviewService = reviewService;
        this.filmService = filmService;
        this.directorService = directorService;
        this.actorService = actorService;
        this.clientService = clientService;
    }



    @Override
    @GetMapping("/{filmId}")
    public String pageReview(@PathVariable int filmId,
                             Principal principal,
                             Model model) {
        FilmDTO film = filmService.findFilmById(filmId);
        DirectorDTO director = directorService.findById(film.getDirectorsId());
        List<ActorDTO> actor = actorService.findActorsByFilmId(filmId);

        List<ActorCardViewModel> actors = new ArrayList<>();
        for (ActorDTO actorDTO : actor) {
            ActorCardViewModel var = new ActorCardViewModel(actorDTO.getName(), actorDTO.getSurname(), actorDTO.getMidlName());
            actors.add(var);
        }

        ReviewViewModel viewModel = new ReviewViewModel(film.getTitle(), film.getRating(), actors,
                director.getName(), director.getSurname(), director.getMidlName(), createBaseVieModel(principal), filmId);


        model.addAttribute("model", viewModel);
        model.addAttribute("form", new ReviewCreateForm(1, ""));

        return "addReview";
    }

    @Override
    @PostMapping("/create/{clientId}/{filmId}")
    public String createReview(@PathVariable int filmId,
                               @PathVariable int clientId,
                               @Valid @ModelAttribute("form") ReviewCreateForm form,
                               BindingResult bindingResult,
                               Principal principal,
                               Model model){

        if (bindingResult.hasErrors()){
            FilmDTO film = filmService.findFilmById(filmId);
            DirectorDTO director = directorService.findById(film.getDirectorsId());
            List<ActorDTO> actor = actorService.findActorsByFilmId(filmId);

            List<ActorCardViewModel> actors = new ArrayList<>();
            for (ActorDTO actorDTO : actor) {
                ActorCardViewModel var = new ActorCardViewModel(actorDTO.getName(), actorDTO.getSurname(), actorDTO.getMidlName());
                actors.add(var);
            }

            ReviewViewModel viewModel = new ReviewViewModel(film.getTitle(), film.getRating(), actors,
                    director.getName(), director.getSurname(), director.getMidlName(), createBaseVieModel(principal), filmId);


            model.addAttribute("model", viewModel);
            model.addAttribute("form", form);

            return "addReview";
        }

        ReviewDTO reviewDTO = new ReviewDTO();
        reviewDTO.setComment(form.comment());
        reviewDTO.setEstimation(form.estimation());
        reviewDTO.setFilmId(filmId);
        reviewDTO.setClientId(clientId);

        reviewService.addReview(reviewDTO);

        return "redirect:/film/" + filmId;
    }

    @Override
    public BaseViewModel createBaseVieModel(Principal principal) {
        if (principal != null){
            return new BaseViewModel(principal.getName());
        }
        return new BaseViewModel(null);
    }
}
