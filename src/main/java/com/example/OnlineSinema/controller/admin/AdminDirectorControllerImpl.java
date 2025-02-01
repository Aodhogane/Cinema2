package com.example.OnlineSinema.controller.admin;

import com.example.OnlineSinema.DTO.DirectorDTO;
import com.example.OnlineSinema.DTO.UserDTO;
import com.example.OnlineSinema.domain.User;
import com.example.OnlineSinema.service.DirectorService;
import com.example.OnlineSinema.service.UserService;
import com.example.SinemaContract.controllers.admin.AdminDirectorController;
import com.example.SinemaContract.viewModel.BaseViewModel;
import com.example.SinemaContract.viewModel.admin.AdminDirectorViewModel;
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
@RequestMapping("/admin/director")
public class AdminDirectorControllerImpl implements AdminDirectorController {

    private final DirectorService directorService;
    private final UserService userService;

    @Autowired
    public AdminDirectorControllerImpl(DirectorService directorService, UserService userService) {
        this.directorService = directorService;
        this.userService = userService;
    }

    @Override
    @GetMapping()
    public String PageAdminController(
            @ModelAttribute("form") PageForm form,
            Principal principal,
            Model model){
         int page = form.page() != null ? form.page() : 1;
         int size = form.size() != null ? form.size() : 10;
         form = new PageForm(page, size);

        Page<DirectorDTO> pageDirectoe = directorService.findAllPage(page, size);


        List<AdminDirectorViewModel> list = new ArrayList<>();
        for (DirectorDTO directorDTO : pageDirectoe){

            UserDTO userDTO = userService.findById(directorDTO.getUserId());

            AdminDirectorViewModel adminDirector = new AdminDirectorViewModel(directorDTO.getId(),
                    directorDTO.getName(), directorDTO.getSurname(), directorDTO.getMidlName(),
                    userDTO.getEmail());
            list.add(adminDirector);
        }

        AdminViewModel<AdminDirectorViewModel> viewModel = new AdminViewModel<>(
                createBaseVieModel(principal), pageDirectoe.getTotalPages(), list);

        model.addAttribute("model", viewModel);
        model.addAttribute("form", form);

        return "admin/director/adminDirector";

    }


    @Override
    public BaseViewModel createBaseVieModel(Principal principal) {
        if (principal != null){
            return new BaseViewModel(principal.getName());
        }
        return new BaseViewModel(null);
    }
}
