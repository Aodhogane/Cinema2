package com.example.OnlineSinema.controller.admin;

import com.example.OnlineSinema.DTO.ClientDTO;
import com.example.OnlineSinema.DTO.UserDTO;
import com.example.OnlineSinema.DTO.inputDTO.ClientInputDTO;
import com.example.OnlineSinema.service.ClientService;
import com.example.OnlineSinema.service.UserService;
import com.example.SinemaContract.controllers.admin.AdminClientController;
import com.example.SinemaContract.viewModel.BaseViewModel;
import com.example.SinemaContract.viewModel.admin.AdminClientViewModel;
import com.example.SinemaContract.viewModel.admin.AdminViewModel;
import com.example.SinemaContract.viewModel.form.PageForm;
import com.example.SinemaContract.viewModel.form.admin.client.ClientCreateForm;
import com.example.SinemaContract.viewModel.form.admin.client.ClientUpdateForm;
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
    @GetMapping("/create")
    public String adminCreate(
            Principal principal, Model model){

        model.addAttribute("form", new ClientCreateForm("", "", ""));
        return "admin/client/adminClientCreate";
    }

    @Override
    @PostMapping("/create")
    public String adminCreate(@Valid @ModelAttribute("form") ClientCreateForm form,
                              BindingResult bindingResult, Principal principal, Model model){
        if(bindingResult.hasErrors()){
            model.addAttribute("form", form);
            return "admin/client/adminClientCreate";
        }

        ClientInputDTO clientInputDTO = new ClientInputDTO(form.name(), form.email(), form.password());
        clientService.create(clientInputDTO);
        return "redirect:/admin/client";
    }

    @Override
    @GetMapping("/update/{clientId}")
    public String adminUpdate(@PathVariable int clientId,
                              Principal principal,
                              Model model){

        ClientDTO clientDTO = clientService.findClientById(clientId);
        UserDTO userDTO = userServices.findById(clientDTO.getUserId());

        model.addAttribute("form", new ClientUpdateForm(clientId,
                clientDTO.getName(), userDTO.getEmail(), userDTO.getPassword()));

        return "admin/client/adminClientUpdate";
    }

    @Override
    @PostMapping("/update/{clientId}")
    public String adminUpdate(@PathVariable int clientId,
                              @Valid @ModelAttribute("form") ClientUpdateForm form,
                              BindingResult bindingResult, Principal principal, Model model){
        if (bindingResult.hasErrors()){
            model.addAttribute("form", form);
            return "admin/client/adminClientUpdate";
        }

        ClientDTO clientDTO = new ClientDTO(form.name());
        clientService.update(clientDTO, clientId);
        return "redirect:/admin/client";
    }

    @Override
    @GetMapping("/delete/{clientId}")
    public String adminDelete(@PathVariable int clientId,
                              Principal principal, Model model){
        clientService.delete(clientId);
        return "redirect:/admin/client";
    }

    @Override
    public BaseViewModel createBaseVieModel(Principal principal) {
        if (principal != null){
            return new BaseViewModel(principal.getName());
        }
        return new BaseViewModel(null);
    }
}
