package com.example.OnlineSinema.controller.AdminPanely;

import com.example.OnlineSinema.exceptions.FilmNotFounf;
import com.example.OnlineSinema.exceptions.GenreNotFoundException;
import com.example.OnlineSinema.exceptions.ReviewNotFound;
import com.example.OnlineSinema.service.FilmService;
import com.example.OnlineSinema.service.GenresService;
import com.example.OnlineSinema.service.ReviewsService;
import com.example.OnlineSinema.service.impl.UserDetailsServiceImpl;
import com.example.SinemaContract.VM.cards.BaseViewModel;
import com.example.SinemaContract.controllers.admine.AdminControllerDelete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public class AdminControllerDeleteImpl implements AdminControllerDelete {

    private final GenresService genresService;
    private final FilmService filmService;
    private final ReviewsService reviewsService;

    @Autowired
    public AdminControllerDeleteImpl(GenresService genresService, FilmService filmService, ReviewsService reviewsService) {
        this.genresService = genresService;
        this.filmService = filmService;
        this.reviewsService = reviewsService;
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
    public BaseViewModel createBaseViewModel(String title, UserDetails userDetails) {
        if (userDetails == null){
            return new BaseViewModel(title, -1, null);
        }
        else{
            UserDetailsServiceImpl.CustomUser customUser = (UserDetailsServiceImpl.CustomUser) userDetails;
            return new BaseViewModel(title, customUser.getId(), customUser.getName());
        }
    }
}


