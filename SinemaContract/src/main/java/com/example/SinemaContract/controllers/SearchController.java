package com.example.SinemaContract.controllers;
import com.example.SinemaContract.VM.domain.actor.ActorsSertchForm;
import com.example.SinemaContract.VM.domain.director.DirectorSertchForm;
import com.example.SinemaContract.VM.domain.film.FilmSertchForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/search")
public interface SearchController extends BaseController {

    @GetMapping
    String GenreSearch(@ModelAttribute("filter") FilmSertchForm Genrefilter, Model model);

    @GetMapping
    String ActorSearch(@ModelAttribute("filter") ActorsSertchForm Actorfilter, Model model);

    @GetMapping
    String DirectorSearch(@ModelAttribute("filter") DirectorSertchForm Directorfilter, Model model);
}
