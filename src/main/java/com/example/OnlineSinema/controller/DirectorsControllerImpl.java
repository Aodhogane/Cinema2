package com.example.OnlineSinema.controller;

import com.example.OnlineSinema.DTO.DirectorDTO;
import com.example.OnlineSinema.DTO.FilmDTO;
import com.example.OnlineSinema.service.DirectorService;
import com.example.OnlineSinema.service.FilmService;
import com.example.SinemaContract.viewModel.BaseViewModel;
import com.example.SinemaContract.viewModel.DirectorViewModel;
import com.example.SinemaContract.viewModel.card.FilmCardViewModel;
import com.example.SinemaContract.controllers.DirectorController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@Controller
@RequestMapping("/director")
public class DirectorsControllerImpl implements DirectorController {

    private final DirectorService directorService;
    private final FilmService filmService;
    private static final Logger LOG = LogManager.getLogger(Controller.class);

    @Autowired
    public DirectorsControllerImpl(DirectorService directorService, FilmService filmService) {
        this.directorService = directorService;
        this.filmService = filmService;
    }

    @Override
    @GetMapping("/{directorId}")
    public String findDirectorById(@PathVariable int directorId,
                                   Principal principal,
                                   Model model){

        LOG.log(Level.INFO, "Shows the actor's profile with directorId = " + directorId + " user with email = " + principal.getName());

        DirectorDTO director = directorService.findById(directorId);
        List<FilmDTO> films = filmService.findFilmsByDirectorsId(directorId);

        List<FilmCardViewModel> film = new ArrayList<>();
        for (FilmDTO filmDTO : films){
            FilmCardViewModel filmCardViewModel = new FilmCardViewModel(filmDTO.getId(), filmDTO.getTitle(), filmDTO.getExitDate(), filmDTO.getGenres().toString());
            film.add(filmCardViewModel);
        }

        DirectorViewModel viewModel = new DirectorViewModel(
                director.getName(), director.getSurname(), director.getMidlName(), film, createBaseVieModel(principal)
        );

        model.addAttribute("model", viewModel);
        return "directorDetails";
    }

    @Override
    public BaseViewModel createBaseVieModel(Principal principal) {
        if (principal != null){
            return new BaseViewModel(principal.getName());
        }
        return new BaseViewModel(null);
    }
}
