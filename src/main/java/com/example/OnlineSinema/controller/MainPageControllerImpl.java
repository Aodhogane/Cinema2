package com.example.OnlineSinema.controller;

import com.example.OnlineSinema.DTO.FilmDTO;
import com.example.OnlineSinema.service.AuthService;
import com.example.OnlineSinema.service.FilmService;
import com.example.SinemaContract.viewModel.BaseViewModel;
import com.example.SinemaContract.viewModel.MainViewModel;
import com.example.SinemaContract.viewModel.card.FilmCardViewModel;
import com.example.SinemaContract.viewModel.form.PageForm;
import com.example.SinemaContract.viewModel.form.SortForm;
import com.example.SinemaContract.controllers.MainController;
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
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Controller
@RequestMapping("/main")
public class MainPageControllerImpl implements MainController {

    private final FilmService filmService;
    private final AuthService authService;
    private static final Logger LOG = LogManager.getLogger(Controller.class);

    @Autowired
    public MainPageControllerImpl(FilmService filmService, AuthService authService) {
        this.filmService = filmService;
        this.authService = authService;
    }

    @Override
    @GetMapping
    public String getMainPage(@ModelAttribute("sort") SortForm sort,
                              @ModelAttribute("pageSize") PageForm pageForm,
                              Principal principal,
                              Model model) {

        int page = pageForm.page() != null ? pageForm.page() : 1;
        int size = pageForm.size() != null ? pageForm.size() : 3;
        pageForm = new PageForm(page, size);
        Page<FilmDTO> filmDTOPage = filmService.chuzSort(sort.title(), sort.genre(), page, size);

        if (principal == null){
            LOG.log(Level.INFO, "No authoraze user clicks on the main page");
        }
        else {
            LOG.log(Level.INFO, "The user clicks on the main page " + principal.getName());
        }

        List<FilmCardViewModel> filmCard = new ArrayList<>();
        for (FilmDTO filmDTO : filmDTOPage){
            FilmCardViewModel filmCardWork = new FilmCardViewModel(filmDTO.getId(), filmDTO.getTitle(), filmDTO.getExitDate(), filmDTO.getGenres());
            filmCard.add(filmCardWork);
        }

        MainViewModel viewModel = new MainViewModel(filmCard, createBaseVieModel(principal));
        model.addAttribute("model", viewModel);
        model.addAttribute("sort", sort);
        model.addAttribute("pageSize", pageForm);
        return "main";
    }

    @Override
    public BaseViewModel createBaseVieModel(Principal principal) {
        if (principal != null){
            return new BaseViewModel(authService.getUser(principal.getName()).getName());
        }
        return new BaseViewModel(null);
    }
}
