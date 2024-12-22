package com.example.OnlineSinema.controller;

import com.example.OnlineSinema.dto.actorsDTO.ActorsOutputDTO;
import com.example.OnlineSinema.dto.directorDTO.DirectorOutputDTO;
import com.example.OnlineSinema.dto.filmDTO.FilmOutputDTO;
import com.example.OnlineSinema.exceptions.ActorsNotFound;
import com.example.OnlineSinema.exceptions.DirectorsNotFound;
import com.example.OnlineSinema.service.ActorsServis;
import com.example.OnlineSinema.service.DirectorsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/people")
public class PersonController {

    private final ActorsServis actorService;
    private final DirectorsService directorService;

    public PersonController(ActorsServis actorService, DirectorsService directorService) {
        this.actorService = actorService;
        this.directorService = directorService;
    }

    @GetMapping("/actor/{id}")
    public String getActorPage(@PathVariable("id") int actorId, Model model) {
        try {
            ActorsOutputDTO actor = actorService.findById(actorId);
            List<FilmOutputDTO> films = actorService.findFilmsByActorId(actorId);
            model.addAttribute("actor", actor);
            model.addAttribute("films", films);
            return "actorDetails";
        } catch (ActorsNotFound e) {
            model.addAttribute("error", "Actor not found: " + e.getMessage());
            return "404";
        }
    }

    @GetMapping("/director/{id}")
    public String getDirectorPage(@PathVariable("id") int directorId, Model model) {
        try {
            DirectorOutputDTO director = directorService.findById(directorId);
            List<FilmOutputDTO> films = directorService.findFilmsByDirectorId(directorId);
            model.addAttribute("director", director);
            model.addAttribute("films", films);
            return "directorDetails";
        } catch (DirectorsNotFound e) {
            model.addAttribute("error", "Director not found: " + e.getMessage());
            return "404";
        }
    }
}