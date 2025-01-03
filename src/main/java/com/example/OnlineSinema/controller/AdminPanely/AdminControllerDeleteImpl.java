package com.example.OnlineSinema.controller.AdminPanely;

import com.example.OnlineSinema.exceptions.*;
import com.example.OnlineSinema.service.*;
import com.example.OnlineSinema.config.UserDetailsServiceImpl;
import com.example.SinemaContract.VM.cards.BaseViewModel;
import com.example.SinemaContract.controllers.admine.AdminControllerDelete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/delete")
public class AdminControllerDeleteImpl implements AdminControllerDelete {

    private final GenresService genresService;
    private final FilmService filmService;
    private final ReviewsService reviewsService;
    private final ActorsServis actorsServis;
    private final DirectorsService directorsService;

    @Autowired
    public AdminControllerDeleteImpl(GenresService genresService,
                                     FilmService filmService,
                                     ReviewsService reviewsService,
                                     ActorsServis actorsServis,
                                     DirectorsService directorsService) {
        this.genresService = genresService;
        this.filmService = filmService;
        this.reviewsService = reviewsService;
        this.actorsServis = actorsServis;
        this.directorsService = directorsService;
    }

    @Override
    @PostMapping("/{id}/genre")
    public String deleteGenre(@PathVariable int id, Model model, RedirectAttributes redirectAttributes) {
        try {
            genresService.deleteById(id);
        }catch (GenreNotFoundException e){
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/admin";
        }

        redirectAttributes.addFlashAttribute("successMessage", "Genre successfully deleted!");
        return "redirect:/admin/genre";
    }

    @Override
    @PostMapping("/{id}/film")
    public String deleteFilm(@PathVariable int id, Model model, RedirectAttributes redirectAttributes) {
        try {
            filmService.deleteById(id);
        }catch (FilmNotFounf e){
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/admin";
        }

        redirectAttributes.addFlashAttribute("successMessage", "Media successfully deleted!");
        return "redirect:/admin/media";
    }

    @Override
    @PostMapping("/{id}/review")
    public String deleteReview(@PathVariable int id, Model model, RedirectAttributes redirectAttributes) {
        try {
            reviewsService.deleteById(id);
        }catch (ReviewNotFound e){
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/admin";
        }

        redirectAttributes.addFlashAttribute("successMessage", "Review successfully deleted!");
        return "redirect:/admin/review";
    }

    @Override
    @PostMapping("/{id}/actor")
    public String deleteActor(@PathVariable int id, Model model, RedirectAttributes redirectAttributes){
        try {
            actorsServis.deleteById(id);
        } catch (ActorsNotFound e){
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/admin";
        }

        redirectAttributes.addFlashAttribute("successMessage", "Actor successfully deleted!");
        return "redirect:/admin/actors";
    }

    @Override
    @PostMapping("/{id}/director")
    public String deleteDirector(@PathVariable int id, Model model, RedirectAttributes redirectAttributes){
        try {
            directorsService.deleteById(id);
        } catch (DirectorsNotFound e){
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/admin";
        }

        redirectAttributes.addFlashAttribute("successMessage", "Director successfully deleted!");
        return "redirect:/admin/director";
    }

    @Override
    public BaseViewModel createBaseViewModel(String title, UserDetails userDetails) {
        if (userDetails == null){
            return new BaseViewModel(title, -1, null);
        }
        else{
            UserDetailsServiceImpl.CustomUser customUser = (UserDetailsServiceImpl.CustomUser) userDetails;
            return new BaseViewModel(title, customUser.getId(), customUser.getUsername());
        }
    }
}


