package com.example.OnlineSinema.controller.admin;

import com.example.OnlineSinema.DTO.ClientDTO;
import com.example.OnlineSinema.DTO.UserDTO;
import com.example.OnlineSinema.service.ClientService;
import com.example.OnlineSinema.service.UserService;
import com.example.SinemaContract.controllers.admin.AdminClientController;
import com.example.SinemaContract.viewModel.BaseViewModel;
import com.example.SinemaContract.viewModel.admin.AdminClientViewModel;
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
@RequestMapping("/admin/client")
public class AdminClientControllerImpl implements AdminClientController {

    private final ClientService clientService;
    private final UserService userServices;

    @Autowired
    public AdminClientControllerImpl(ClientService clientService, UserService userServices) {
        this.clientService = clientService;
        this.userServices = userServices;
    }

    @Override
    @GetMapping()
    public String PageAdminClientController(
            @ModelAttribute("form") PageForm form,
            Principal principal,
            Model model){

        int page = form.page() != null ? form.page() : 1;
        int size = form.size() != null ? form.size() : 10;
        form = new PageForm(page, size);

        Page<ClientDTO> pageClient = clientService.findAllPage(page, size);

        List<AdminClientViewModel> list = new ArrayList<>();
        for (ClientDTO clientDTO : pageClient){

            UserDTO userDTO = userServices.findById(clientDTO.getUserId());

            AdminClientViewModel adminClient = new AdminClientViewModel(
                    clientDTO.getId(), clientDTO.getName(), userDTO.getEmail());

            list.add(adminClient);
        }

        AdminViewModel<AdminClientViewModel> viewModel = new AdminViewModel<>(
                createBaseVieModel(principal), pageClient.getTotalPages(), list);

        model.addAttribute("model", viewModel);
        model.addAttribute("form", form);

        return "admin/client/adminClient";
    }

    @Override
    public BaseViewModel createBaseVieModel(Principal principal) {
        if (principal != null){
            return new BaseViewModel(principal.getName());
        }
        return new BaseViewModel(null);
    }
}
