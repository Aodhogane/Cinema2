package com.example.SinemaContract.controllers.domeinController;

import com.example.SinemaContract.VM.domain.film.ReviewPageFormModel;
import com.example.SinemaContract.VM.form.actor.ActorPageFM;
import com.example.SinemaContract.VM.form.director.DirectorPageFM;
import com.example.SinemaContract.controllers.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/films")
public interface FilmControllerMain extends BaseController {

    @GetMapping("/{id}")
    String filmPage(@ModelAttribute("reviewForm") ReviewPageFormModel reviewForm,
                    @ModelAttribute("actorForm") ActorPageFM actorForm,
                    @ModelAttribute("directorForm") DirectorPageFM directorForm,
                    @PathVariable int id, Model model);
}
