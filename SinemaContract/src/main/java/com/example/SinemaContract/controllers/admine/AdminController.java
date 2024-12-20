package com.example.SinemaContract.controllers.admine;

import com.example.SinemaContract.VM.form.actor.ActorPageFM;
import com.example.SinemaContract.VM.form.director.DirectorPageFM;
import com.example.SinemaContract.VM.form.film.FilmPageFM;
import com.example.SinemaContract.VM.form.genre.GenrePageFM;
import com.example.SinemaContract.VM.form.user.UserPageFM;
import com.example.SinemaContract.controllers.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/admin")
public interface AdminController extends BaseController {

    @GetMapping
    String adminePanel(Model model);

    @GetMapping("/client")
    String adminPanelClient(@ModelAttribute("filter") UserPageFM userPageFM, Model model);

    @GetMapping("/genre")
    String adminPanelGenre(@ModelAttribute("filter") GenrePageFM genrePageFM, Model model);

    @GetMapping("/film")
    String adminPanelFilm(@ModelAttribute("filter") FilmPageFM filmPageFM, Model model);

    @GetMapping("/actor")
    String adminPanelActor(@ModelAttribute("actor")ActorPageFM actorPageFM, Model model);

    @GetMapping("/director")
    String adminPanelDirector(@ModelAttribute("director")DirectorPageFM directorPageFM, Model model);
}
