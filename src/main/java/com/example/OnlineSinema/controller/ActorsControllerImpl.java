package com.example.OnlineSinema.controller;

import com.example.OnlineSinema.DTO.ActorDTO;
import com.example.OnlineSinema.DTO.FilmDTO;
import com.example.OnlineSinema.service.ActorService;
import com.example.OnlineSinema.service.FilmService;
import com.example.SinemaContract.viewModel.ActorViewModel;
import com.example.SinemaContract.viewModel.BaseViewModel;
import com.example.SinemaContract.viewModel.card.FilmCardViewModel;
import com.example.SinemaContract.controllers.ActorController;
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
@RequestMapping("/actor")
public class ActorsControllerImpl implements ActorController {

    private final ActorService actorService;
    private final FilmService filmService;

    @Autowired
    public ActorsControllerImpl(ActorService actorService, FilmService filmService) {
        this.actorService = actorService;
        this.filmService = filmService;
    }

    @Override
    @GetMapping("/{actorId}")
    public String findActorById(@PathVariable int actorId,
                                Principal principal,
                                Model model){
        ActorDTO actor = actorService.findActorById(actorId);
        List<FilmDTO> films = filmService.findFilmsByActorsId(actorId);

        List<FilmCardViewModel> film = new ArrayList<>();
        for (FilmDTO filmDTO : films){
            FilmCardViewModel filmCardViewModel = new FilmCardViewModel(filmDTO.getTitle(), filmDTO.getExitDate(), filmDTO.getGenres().toString());
            film.add(filmCardViewModel);
        }

        ActorViewModel viewModel = new ActorViewModel(
                actor.getName(), actor.getSurname(), actor.getMidlName(), film, createBaseVieModel(principal));

        model.addAttribute("model", viewModel);
        return "actorDetails";
    }

    @Override
    public BaseViewModel createBaseVieModel(Principal principal) {
        if (principal != null){
            return new BaseViewModel(principal.getName());
        }
        return new BaseViewModel(null);
    }
}
