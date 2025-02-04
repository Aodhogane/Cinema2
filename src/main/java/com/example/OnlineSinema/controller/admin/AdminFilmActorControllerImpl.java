package com.example.OnlineSinema.controller.admin;

import com.example.OnlineSinema.DTO.ActorDTO;
import com.example.OnlineSinema.DTO.FilmDTO;
import com.example.OnlineSinema.service.ActorService;
import com.example.OnlineSinema.service.FilmService;
import com.example.SinemaContract.controllers.admin.AdminFilmActorController;
import com.example.SinemaContract.viewModel.BaseViewModel;
import com.example.SinemaContract.viewModel.admin.AdminViewModel;
import com.example.SinemaContract.viewModel.card.ActorIdViewModel;
import com.example.SinemaContract.viewModel.card.FilmActorCardViewModel;
import com.example.SinemaContract.viewModel.form.PageForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin/filmActor")
public class AdminFilmActorControllerImpl implements AdminFilmActorController {

    private final ActorService actorService;
    private final FilmService filmService;

    @Autowired
    public AdminFilmActorControllerImpl(ActorService actorService, FilmService filmService) {
        this.actorService = actorService;
        this.filmService = filmService;
    }

    @Override
    @GetMapping()
    public String adminFilmActorController(
                                           Principal principal,
                                           Model model){

//        int page = form.page() != null ? form.page() : 1;
//        int size = form.size() != null ? form.size() : 10;
//        form = new PageForm(page, size);

        int page = 1;
        int size = 10;

        Page<FilmDTO> filmDTOPage = filmService.findAllPage(page, size);

        List<FilmActorCardViewModel> listFA = new ArrayList<>();
        for (FilmDTO filmDTO : filmDTOPage){
            List<ActorIdViewModel> listActor = new ArrayList<>();
            List<ActorDTO> actorDTOS = actorService.findActorsByFilmId(filmDTO.getActorId());

            for (ActorDTO actorDTO : actorDTOS){
                ActorIdViewModel actor = new ActorIdViewModel(
                        actorDTO.getId(), actorDTO.getName(), actorDTO.getSurname(), actorDTO.getMidlName()
                );
                listActor.add(actor);
            }

            FilmActorCardViewModel filmActor = new FilmActorCardViewModel(
                    filmDTO.getId(), filmDTO.getTitle(), listActor);

            listFA.add(filmActor);
        }

        AdminViewModel<FilmActorCardViewModel> viewModel = new AdminViewModel<>(createBaseVieModel(principal),
                filmDTOPage.getTotalPages(), listFA);

        model.addAttribute("model", viewModel);
//        model.addAttribute("form", form);

        return "admin/filmActor/adminFilmActor";
    }

    @Override
    public BaseViewModel createBaseVieModel(Principal principal) {
        if (principal != null){
            return new BaseViewModel(principal.getName());
        }
        return new BaseViewModel(null);
    }
}
