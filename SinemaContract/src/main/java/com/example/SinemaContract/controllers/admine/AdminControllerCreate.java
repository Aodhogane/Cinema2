package com.example.SinemaContract.controllers.admine;

import com.example.SinemaContract.VM.form.actor.ActorFM;
import com.example.SinemaContract.VM.form.director.DirectorFM;
import com.example.SinemaContract.VM.form.film.FilmFM;
import com.example.SinemaContract.VM.form.genre.GenreFM;
import com.example.SinemaContract.VM.form.user.UserFM;
import com.example.SinemaContract.controllers.BaseController;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/create")
public interface AdminControllerCreate extends BaseController {

    @GetMapping("/user")
    String createClient(Model model);

    @GetMapping("/genre")
    String createGenre(Model model);

    @GetMapping("/film")
    String createMedia(Model model);

    @GetMapping("/actor")
    String createActor(Model model);

    @GetMapping("/director")
    String crateDirector(Model model);

    @PostMapping("/user")
    String createClient(@Valid @ModelAttribute("clientForm") UserFM userFM,
                        BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes);

    @PostMapping("/genre")
    String createGenre(@Valid @ModelAttribute("genreForm") GenreFM genreFM,
                       BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes);

    @PostMapping("/film")
    String createMedia(@Valid @ModelAttribute("mediaForm") FilmFM filmFM,
                       BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes);

    @PostMapping("/actor")
    String createActor(@Valid @ModelAttribute("actorForm")ActorFM actorFM,
                       BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes);

    @PostMapping("/director")
    String createDirector(@Valid @ModelAttribute("directorForm") DirectorFM directorFM,
                       BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes);

}