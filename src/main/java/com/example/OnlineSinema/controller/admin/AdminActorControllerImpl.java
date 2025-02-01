package com.example.OnlineSinema.controller.admin;

import com.example.OnlineSinema.DTO.ActorDTO;
import com.example.OnlineSinema.DTO.UserDTO;
import com.example.OnlineSinema.domain.User;
import com.example.OnlineSinema.service.ActorService;
import com.example.OnlineSinema.service.UserService;
import com.example.SinemaContract.viewModel.ActorViewModel;
import com.example.SinemaContract.viewModel.BaseViewModel;
import com.example.SinemaContract.viewModel.admin.AdminActorViewModel;
import com.example.SinemaContract.viewModel.admin.AdminViewModel;
import com.example.SinemaContract.viewModel.form.PageForm;
import com.example.SinemaContract.controllers.admin.AdminActorController;
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
                   actorDTO.getName(), actorDTO.getSurname(), actorDTO.getMidlName(), userDTO.getEmail(),
                   userDTO.getUserRoles().getValue());

           list.add(adminActor);
       }

        AdminViewModel<AdminActorViewModel> viewModel = new AdminViewModel<>(createBaseVieModel(principal),
                pageActors.getTotalPages(), list);

       model.addAttribute("model", viewModel);
       model.addAttribute("form", form);

       return "admin/actor/adminActor";
    }


    @Override
    public BaseViewModel createBaseVieModel(Principal principal) {
        if (principal != null){
            return new BaseViewModel(principal.getName());
        }
        return new BaseViewModel(null);
    }
}
