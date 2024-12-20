package com.example.SinemaContract.controllers.admine;

import com.example.SinemaContract.VM.domain.user.UserEditForm;
import com.example.SinemaContract.VM.form.actor.ActorFM;
import com.example.SinemaContract.VM.form.director.DirectorFM;
import com.example.SinemaContract.VM.form.film.FilmFM;
import com.example.SinemaContract.VM.form.genre.GenreFM;
import com.example.SinemaContract.controllers.BaseController;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admine/edit")
public interface AdminControllerEdit extends BaseController {

    @GetMapping("/{id}/user")
    String editClient(@PathVariable int id, Model model, RedirectAttributes redirectAttributes);

    @GetMapping("/{id}/genre")
    String editGenre(@PathVariable int id, Model model, RedirectAttributes redirectAttributes);

    @GetMapping("/{id}/film")
    String editFilm(@PathVariable int id, Model model, RedirectAttributes redirectAttributes);

    @GetMapping("/{id}/actor")
    String editActor(@PathVariable int id, Model model, RedirectAttributes redirectAttributes);

    @GetMapping("/{id}/director")
    String editDirector(@PathVariable int id, Model model, RedirectAttributes redirectAttributes);

    @PostMapping("/{id}/user")
    String createClient(@PathVariable int id, @Valid @ModelAttribute("clientForm") UserEditForm userEditForm,
                        BindingResult bindingResult, RedirectAttributes redirectAttributes);

    @PostMapping("/{id}/genre")
    String createGenre(@PathVariable int id, @Valid @ModelAttribute("genreForm") GenreFM genreFM,
                       BindingResult bindingResult, RedirectAttributes redirectAttributes);

    @PostMapping("/{id}/film")
    String createFilm(@PathVariable int id, @Valid @ModelAttribute("mediaForm") FilmFM filmFM,
                      BindingResult bindingResult, RedirectAttributes redirectAttributes);

    @PostMapping("/{id}/actor")
    String createActor(@PathVariable int id, @Valid @ModelAttribute("actorForm") ActorFM actorFM,
                      BindingResult bindingResult, RedirectAttributes redirectAttributes);

    @PostMapping("/{id}/director")
    String createDirector(@PathVariable int id, @Valid @ModelAttribute("directorForm") DirectorFM directorFM,
                      BindingResult bindingResult, RedirectAttributes redirectAttributes);

    @DeleteMapping("/{id}/genre")
    String deleteGenre(@PathVariable int id, Model model, RedirectAttributes redirectAttributes);

    @DeleteMapping("/{id}/film")
    String deleteFilm(@PathVariable int id, Model model, RedirectAttributes redirectAttributes);

    @DeleteMapping("/{id}/actor")
    String deleteActor(@PathVariable int id, Model model, RedirectAttributes redirectAttributes);

    @DeleteMapping("/{id}/directir")
    String deleteDirector(@PathVariable int id, Model model, RedirectAttributes redirectAttributes);

}