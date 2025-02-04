package com.example.OnlineSinema.controller.admin;

import com.example.OnlineSinema.DTO.ActorDTO;
import com.example.OnlineSinema.DTO.UserDTO;
import com.example.OnlineSinema.DTO.inputDTO.ActorInputDTO;
import com.example.OnlineSinema.service.ActorService;
import com.example.OnlineSinema.service.UserService;
import com.example.SinemaContract.viewModel.BaseViewModel;
import com.example.SinemaContract.viewModel.admin.AdminActorViewModel;
import com.example.SinemaContract.viewModel.admin.AdminViewModel;
import com.example.SinemaContract.viewModel.form.PageForm;
import com.example.SinemaContract.controllers.admin.AdminActorController;
import com.example.SinemaContract.viewModel.form.admin.actor.ActorCreateForm;
import com.example.SinemaContract.viewModel.form.admin.actor.ActorUpdateForm;
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
@RequestMapping("/admin/actor")
public class AdminActorControllerImpl implements AdminActorController {

    private final ActorService actorService;
    private final UserService userService;

    @Autowired
    public AdminActorControllerImpl(ActorService actorService, UserService userService) {
        this.actorService = actorService;
        this.userService = userService;
    }

    @Override
    @GetMapping()
    public String PageAdminActor(@ModelAttribute("form") PageForm form,
                                 Principal principal,
                                 Model model){
        int page = form.page() != null ? form.page() : 1;
        int size = form.size() != null ? form.size() : 10;
        form = new PageForm(page, size);

       Page<ActorDTO> pageActors = actorService.findAllPage(page, size);

       List<AdminActorViewModel> list = new ArrayList<>();
       for (ActorDTO actorDTO : pageActors){

           UserDTO userDTO = userService.findById(actorDTO.getUserId());

           AdminActorViewModel adminActor = new AdminActorViewModel( actorDTO.getId(),
                   actorDTO.getName(), actorDTO.getSurname(), actorDTO.getMidlName(), userDTO.getEmail());

           list.add(adminActor);
       }

        AdminViewModel<AdminActorViewModel> viewModel = new AdminViewModel<>(createBaseVieModel(principal),
                pageActors.getTotalPages(), list);

       model.addAttribute("model", viewModel);
       model.addAttribute("form", form);

       return "admin/actor/adminActor";
    }

    @Override
    @GetMapping("/create")
    public String adminCreate(
            Principal principal, Model model){

        model.addAttribute("form", new ActorCreateForm("", "", "", "", ""));

        return "admin/actor/adminActorCreate";
    }

    @Override
    @PostMapping("/create")
    public String adminCreate(@Valid @ModelAttribute("form") ActorCreateForm form,
                              BindingResult bindingResult, Principal principal, Model model){

        if(bindingResult.hasErrors()){
            model.addAttribute("form", form);
            return "admin/actor/adminActorCreate";
        }

        ActorInputDTO actorInputDTO = new ActorInputDTO(form.name(), form.surname(),
                form.midlName(), form.email(), form.password());

        actorService.create(actorInputDTO);

        return "redirect:/admin/actor";
    }

    @Override
    @GetMapping("/update/{actorId}")
    public String adminUpdate(@PathVariable int actorId,
                              Principal principal,
                              Model model){

        ActorDTO actorDTO = actorService.findActorById(actorId);

        model.addAttribute("form", new ActorUpdateForm(actorId,
                actorDTO.getName(), actorDTO.getSurname(), actorDTO.getMidlName()));

        return "admin/actor/adminActorUpdate";
    }

    @Override
    @PostMapping("/update/{actorId}")
    public String adminUpdate(@PathVariable int actorId,
                              @Valid @ModelAttribute("form") ActorUpdateForm form,
                              BindingResult bindingResult, Principal principal, Model model){
        if (bindingResult.hasErrors()){
            model.addAttribute("form", form);
            return "admin/actor/adminActorUpdate";
        }

        ActorDTO actorDTO = new ActorDTO(form.name(), form.surname(), form.midlName());
        actorService.update(actorDTO, actorId);

        return "redirect:/admin/actor";
    }

    @Override
    @GetMapping("/delete/{actorId}")
    public String adminDelete(@PathVariable int actorId,
                              Principal principal, Model model){
        actorService.delete(actorId);

        return "redirect:/admin/actor";
    }

    @Override
    public BaseViewModel createBaseVieModel(Principal principal) {
        if (principal != null){
            return new BaseViewModel(principal.getName());
        }
        return new BaseViewModel(null);
    }
}
