package com.example.OnlineSinema.controller;

import com.example.OnlineSinema.domain.User;
import com.example.OnlineSinema.dto.userDTO.UserOutputDTO;
import com.example.OnlineSinema.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

public class RegistrationController {

    @Autowired
    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String registration(Model model){
        model.addAttribute("userForm", new User());

        return "registration";
    }

    @PostMapping("/register")
    public String addUser(@ModelAttribute("userForm") @Valid User userForm,
                          BindingResult bindingResult, Model model){

        if (bindingResult.hasErrors()){
            return "registration";
        }
        if (!userForm.getPassword().equals(userForm.getPassword())){
            model.addAttribute("passwordError", "Пароль не совпадает");
            return "registration";
        }
        if(!userService.register(userForm)){
            model.addAttribute("userEmailError", "Пользователь с такой почтной имеется");
            return "registration";
        }
        return "redirect:/";
    }
}
