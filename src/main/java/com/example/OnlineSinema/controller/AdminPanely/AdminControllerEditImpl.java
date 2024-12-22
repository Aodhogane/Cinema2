package com.example.OnlineSinema.controller.AdminPanely;

import com.example.OnlineSinema.dto.filmDTO.FilmOutputDTO;
import com.example.OnlineSinema.dto.genresDTO.GenresOutputDTO;
import com.example.OnlineSinema.dto.userDTO.UserInfoDTO;
import com.example.OnlineSinema.dto.userDTO.UserOutputDTO;
import com.example.OnlineSinema.exceptions.FilmNotFounf;
import com.example.OnlineSinema.exceptions.GenreNotFoundException;
import com.example.OnlineSinema.exceptions.UserNotFound;
import com.example.OnlineSinema.repository.AccessRepository;
import com.example.OnlineSinema.service.*;
import com.example.OnlineSinema.service.impl.UserDetailsServiceImpl;
import com.example.SinemaContract.VM.admin.AdminViewModelEntityEdit;
import com.example.SinemaContract.VM.cards.BaseViewModel;
import com.example.SinemaContract.VM.domain.user.UserEditForm;
import com.example.SinemaContract.VM.form.actor.ActorFM;
import com.example.SinemaContract.VM.form.director.DirectorFM;
import com.example.SinemaContract.VM.form.film.FilmFM;
import com.example.SinemaContract.VM.form.genre.GenreFM;
import com.example.SinemaContract.controllers.admine.AdminControllerEdit;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.stream.Collectors;

public class AdminControllerEditImpl implements AdminControllerEdit {

    private final UserService userService;
    private final GenresService genresService;
    private final FilmService filmService;
    private final AccessRepository accessRepository;
    private final ActorsServis actorsServis;
    private final DirectorsService directorsService;

    @Autowired
    public AdminControllerEditImpl(UserService userService, GenresService genresService, FilmService filmService, AccessRepository accessRepository, ActorsServis actorsServis, DirectorsService directorsService) {
        this.userService = userService;
        this.genresService = genresService;
        this.filmService = filmService;
        this.accessRepository = accessRepository;
        this.actorsServis = actorsServis;
        this.directorsService = directorsService;
    }

    @Override
    @GetMapping("/{id}/user")
    public String editUser(@PathVariable int id, Model model, RedirectAttributes redirectAttributes, @AuthenticationPrincipal UserDetails userDetails) {
        UserInfoDTO user;
        try {
            user = userService.findById(id);
        } catch (UserNotFound e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/admin";
        }

        var viewModel = new AdminViewModelEntityEdit(createBaseVieModel("Client edit page", userDetails), "client", id);

        model.addAttribute("model", viewModel);

        UserEditForm userForm = new UserEditForm(
                user.getName(),
                user.getId(),
                "",
                "",
                ""
        );

        model.addAttribute("userForm", userForm);
        model.addAttribute("roles", accessRepository.findAll());
        return "admin-edit";
    }

    @Override
    @GetMapping("/{id}/genre")
    public String editGenre(@PathVariable int id, Model model, RedirectAttributes redirectAttributes, @AuthenticationPrincipal UserDetails userDetails) {
        GenresOutputDTO genre;
        try {
            genre = genresService.findById(id);
        }catch (GenreNotFoundException e){
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/admin";
        }

        var viewModel = new AdminViewModelEntityEdit(createBaseVieModel("Genre edit page", userDetails),
                "genre", id);

        model.addAttribute("model", viewModel);
        model.addAttribute("genreForm", new GenreFM(genre.getGenres()));
        return "admin-edit";
    }

    @Override
    @GetMapping("/{id}/film")
    public String editFilm(@PathVariable int id, Model model, RedirectAttributes redirectAttributes, @AuthenticationPrincipal UserDetails userDetails) {
        FilmOutputDTO film;
        try {
            film = filmService.findById(id);
        }catch (FilmNotFounf e){
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/admin";
        }

        var viewModel = new AdminViewModelEntityEdit(createBaseVieModel("Film edit page", userDetails),
                "media", id);

        model.addAttribute("model", viewModel);
        model.addAttribute("filmForm", new FilmFM(film.getTitle(), film.getActors(),film.getGenres(), film.getDirectors(), film.getExitData(), 0.0));
        model.addAttribute("genres", genresService.findAll().stream().map(GenresOutputDTO::getId).collect(Collectors.toList()));

        return "admin-edit";
    }

    @Override
    public String editActor(int id, Model model, RedirectAttributes redirectAttributes) {
        return "";
    }

    @Override
    public String editDirector(int id, Model model, RedirectAttributes redirectAttributes) {
        return "";
    }

    @Override
    @PostMapping("/{id}/user")
    public String createClient(@PathVariable int id, @Valid @ModelAttribute("clientForm") UserEditForm userEditForm,
                               BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("error", "Error in filling out the form");
            redirectAttributes.addFlashAttribute("form", userEditForm);
            return "redirect:/admin/edit/" + id + "/user";
        }
        try {
            UserOutputDTO userOutputDTO = new UserOutputDTO(
                    id,
                    userEditForm.name(),
                    userEditForm.email(),
                    userEditForm.password(),
                    userEditForm.access()
            );


            userService.update(userOutputDTO);
        }catch (UserNotFound e){
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/admin/edit/" + id + "/user";
        }

        redirectAttributes.addFlashAttribute("successMessage", "Client successfully updated!");
        return "redirect:/admin/user";
    }

    @Override
    @PostMapping("/{id}/genre")
    public String createGenre(@PathVariable int id, @Valid @ModelAttribute("genreForm") GenreFM genreFormModel,
                              BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("error", "Error in filling out the form");
            redirectAttributes.addFlashAttribute("form", genreFormModel);
            return "redirect:/admin/edit/" + id + "/genre";
        }
        try {
            genresService.update(id, genreFormModel.genres());
        }catch (GenreNotFoundException e){
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/admin/edit/" + id + "/genre";
        }

        redirectAttributes.addFlashAttribute("successMessage", "Genre successfully updated!");
        return "redirect:/admin/genre";
    }

    @Override
    @PostMapping("/{id}/film")
    public String createFilm(@PathVariable int id, @Valid @ModelAttribute("mediaForm") FilmFM filmFM,
                              BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("error", "Error in filling out the form");
            redirectAttributes.addFlashAttribute("form", filmFM);
            return "redirect:/admin/edit/" + id + "/film";
        }
        try {
            filmService.update(id, filmFM.name(), filmFM.genres());
        }catch (FilmNotFounf | GenreNotFoundException e){
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/admin/edit/" + id + "/film";
        }

        redirectAttributes.addFlashAttribute("successMessage", "Media successfully updated!");
        return "redirect:/admin/media";
    }

    @Override
    public String createActor(int id, ActorFM actorFM, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        return "";
    }

    @Override
    public String createDirector(int id, DirectorFM directorFM, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        return "";
    }

    @Override
    public BaseViewModel createBaseVieModel(String title, UserDetails userDetails) {
        if (userDetails == null){
            return new BaseViewModel(title, -1, null);
        }
        else{
            UserDetailsServiceImpl.CustomUser customUser = (UserDetailsServiceImpl.CustomUser) userDetails;
            return new BaseViewModel(title, customUser.getId(), customUser.getName());
        }
    }
}
