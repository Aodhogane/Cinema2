package com.example.OnlineSinema.controller.admin;

import com.example.OnlineSinema.DTO.FilmDTO;
import com.example.OnlineSinema.DTO.inputDTO.FilmInputDTO;
import com.example.OnlineSinema.service.ActorService;
import com.example.OnlineSinema.service.DirectorService;
import com.example.OnlineSinema.service.FilmService;
import com.example.SinemaContract.controllers.admin.AdminFilmController;
import com.example.SinemaContract.viewModel.BaseViewModel;
import com.example.SinemaContract.viewModel.admin.AdminFilmViewModel;
import com.example.SinemaContract.viewModel.admin.AdminViewModel;
import com.example.SinemaContract.viewModel.form.PageForm;
import com.example.SinemaContract.viewModel.form.admin.film.FilmCreateForm;
import com.example.SinemaContract.viewModel.form.admin.film.FilmUpdateForm;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin/film")
public class AdminFilmControllerImpl implements AdminFilmController {

    private final FilmService filmService;

    @Autowired
    public AdminFilmControllerImpl(FilmService filmService) {
        this.filmService = filmService;
    }

    @Override
    @GetMapping()
    public String PageAdminFilm(@ModelAttribute("form") PageForm form,
                                Principal principal,
                                Model model){
        int page = form.page() != null ? form.page() : 1;
        int size = form.size() != null ? form.size() : 10;
        form = new PageForm(page, size);

        Page<FilmDTO> pageFilm = filmService.findAllPage(page, size);

        List<AdminFilmViewModel> list = new ArrayList<>();
        for (FilmDTO filmDTO : pageFilm){
            AdminFilmViewModel adminFilm = new AdminFilmViewModel(filmDTO.getId(), filmDTO.getTitle(), filmDTO.getExitDate(),
                    filmDTO.getRating(), filmDTO.getGenres());
            list.add(adminFilm);
        }

        AdminViewModel<AdminFilmViewModel> viewModel = new AdminViewModel<>(createBaseVieModel(principal),
                pageFilm.getTotalPages(), list);

        model.addAttribute("model", viewModel);
        model.addAttribute("form", form);

        return "admin/film/adminFilm";
    }

    @Override
    @GetMapping("/create")
    public String adminCreate(
            Principal principal, Model model){

        model.addAttribute("form", new FilmCreateForm("", null, 0.0, "", 0));

        return "admin/film/adminFilmCreate";
    }

    @Override
    @PostMapping("/create")
    public String adminCreate(@Valid @ModelAttribute("form") FilmCreateForm form,
                              BindingResult bindingResult, Principal principal, Model model){

        if(bindingResult.hasErrors()){
            model.addAttribute("form", form);
            return "admin/film/adminFilmCreate";
        }

        FilmInputDTO filmInputDTO = new FilmInputDTO(form.title(), form.exitDate(),
                form.rating(), form.genres(), form.directorId());

        filmService.create(filmInputDTO);

        return "redirect:/admin/film";
    }

    @Override
    @GetMapping("/update/{filmId}")
    public String adminUpdate(@PathVariable int filmId,
                              Principal principal,
                              Model model){

        FilmDTO filmDTO = filmService.findFilmById(filmId);

        model.addAttribute("form", new FilmUpdateForm(filmId,
                filmDTO.getTitle(), filmDTO.getExitDate(), filmDTO.getRating(), filmDTO.getGenres(), filmDTO.getDirectorsId()));

        return "admin/film/adminFilmUpdate";
    }

    @Override
    @PostMapping("/update/{filmId}")
    public String adminUpdate(@PathVariable int filmId,
                              @Valid @ModelAttribute("form") FilmUpdateForm form,
                              BindingResult bindingResult, Principal principal, Model model){
        if (bindingResult.hasErrors()){
            model.addAttribute("form", form);
            return "admin/actor/adminFilmUpdate";
        }

        FilmDTO filmDTO = new FilmDTO(form.title(),
                form.exitDate(), form.rating(), form.genres(), form.directorId());
        filmService.update(filmDTO, filmId);

        return "redirect:/admin/film";
    }

    @Override
    @GetMapping("/delete/{filmId}")
    public String adminDelete(@PathVariable int filmId,
                              Principal principal, Model model){
        filmService.delete(filmId);

        return "redirect:/admin/film";
    }

    @Override
    public BaseViewModel createBaseVieModel(Principal principal) {
        if (principal != null){
            return new BaseViewModel(principal.getName());
        }
        return new BaseViewModel(null);
    }
}
