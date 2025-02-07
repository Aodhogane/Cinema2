package com.example.OnlineSinema.controller.admin;

import com.example.OnlineSinema.DTO.ActorDTO;
import com.example.OnlineSinema.DTO.FilmActorDTO;
import com.example.OnlineSinema.DTO.FilmDTO;
import com.example.OnlineSinema.service.ActorService;
import com.example.OnlineSinema.service.FilmActorService;
import com.example.OnlineSinema.service.FilmService;
import com.example.SinemaContract.controllers.admin.AdminFilmActorController;
import com.example.SinemaContract.viewModel.BaseViewModel;
import com.example.SinemaContract.viewModel.admin.AdminViewModel;
import com.example.SinemaContract.viewModel.card.ActorIdViewModel;
import com.example.SinemaContract.viewModel.card.FilmActorCardViewModel;
import com.example.SinemaContract.viewModel.form.PageForm;
import com.example.SinemaContract.viewModel.form.admin.filmActor.FilmActorCreateForm;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin/filmActor")
public class AdminFilmActorControllerImpl implements AdminFilmActorController {

    private final FilmActorService filmActorService;
    private final ActorService actorService;
    private final FilmService filmService;

    @Autowired
    public AdminFilmActorControllerImpl(FilmActorService filmActorService, ActorService actorService, FilmService filmService) {
        this.filmActorService = filmActorService;
        this.actorService = actorService;
        this.filmService = filmService;
    }

    @Override
    @GetMapping()
    public String adminFilmActorController(@ModelAttribute("form") PageForm form,
                                           Principal principal,
                                           Model model){

        int page = form.page() != null ? form.page() : 1;
        int size = form.size() != null ? form.size() : 10;
        form = new PageForm(page, size);

        Page<FilmDTO> filmDTOPage = filmService.findAllPage(page, size);

        List<FilmActorCardViewModel> listFA = new ArrayList<>();

        for (FilmDTO filmDTO : filmDTOPage){

            List<ActorIdViewModel> listActor = new ArrayList<>();
            List<ActorDTO> actorDTOS = actorService.findActorsByFilmId(filmDTO.getId());

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

        AdminViewModel<FilmActorCardViewModel> viewModel = new AdminViewModel<>(createBaseVieModel(principal), filmDTOPage.getTotalPages(), listFA);

        model.addAttribute("model", viewModel);
        model.addAttribute("form", form);

        return "admin/filmActor/adminFilmActor";
    }

    @Override
    @GetMapping("/create")
    public String filmActorCreate(Principal principal, Model model){
        model.addAttribute("form", new FilmActorCreateForm(0, 0));
        return "admin/filmActor/adminFilmActorCreate";
    }

    @Override
    @PostMapping("/create")
    public String filmActorCreate(@Valid @ModelAttribute("form") FilmActorCreateForm form,
                                  BindingResult bindingResult, Principal principal, Model model) {
        if(bindingResult.hasErrors()){

            model.addAttribute("form", form);
            return "admin/filmActor/adminFilmActorCreate";
        }
        FilmActorDTO filmActorDTO = new FilmActorDTO(form.filmId(), form.actorId());
        filmActorService.create(filmActorDTO);
        return "redirect:/admin/filmActor";
    }

    @GetMapping("/delete")
    public String filmActorDelete(@RequestParam("filmId") int filmId, @RequestParam("actorId") int actorId,
                                  Principal principal, Model model) {
        filmActorService.delete(filmId, actorId);
        return "redirect:/admin/filmActor";
    }

    @Override
    public BaseViewModel createBaseVieModel(Principal principal) {
        if (principal != null){
            return new BaseViewModel(principal.getName());
        }
        return new BaseViewModel(null);
    }
}
