package com.example.OnlineSinema.controller.admin;

import com.example.OnlineSinema.DTO.ActorDTO;
import com.example.OnlineSinema.DTO.DirectorDTO;
import com.example.OnlineSinema.DTO.FilmDTO;
import com.example.OnlineSinema.service.ActorService;
import com.example.OnlineSinema.service.DirectorService;
import com.example.OnlineSinema.service.FilmService;
import com.example.SinemaContract.controllers.admin.AdminFilmController;
import com.example.SinemaContract.viewModel.BaseViewModel;
import com.example.SinemaContract.viewModel.admin.AdminFilmViewModel;
import com.example.SinemaContract.viewModel.admin.AdminViewModel;
import com.example.SinemaContract.viewModel.form.PageForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin/film")
public class AdminFilmControllerImpl implements AdminFilmController {

    private final FilmService filmService;
    private final DirectorService directorService;
    private final ActorService actorService;


    @Autowired
    public AdminFilmControllerImpl(FilmService filmService, DirectorService directorService, ActorService actorService) {
        this.filmService = filmService;
        this.directorService = directorService;
        this.actorService = actorService;
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
                    filmDTO.getRating(), filmDTO.getGenres().toString());

            list.add(adminFilm);
        }

        AdminViewModel<AdminFilmViewModel> viewModel = new AdminViewModel<>(createBaseVieModel(principal),
                pageFilm.getTotalPages(), list);

        model.addAttribute("model", viewModel);
        model.addAttribute("form", form);

        return "admin/film/adminFilm";
    }

    @Override
    public BaseViewModel createBaseVieModel(Principal principal) {
        if (principal != null){
            return new BaseViewModel(principal.getName());
        }
        return new BaseViewModel(null);
    }
}
