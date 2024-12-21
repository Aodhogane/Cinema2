package com.example.OnlineSinema.controller;


import com.example.OnlineSinema.dto.userDTO.UserInfoDTO;
import com.example.OnlineSinema.exceptions.UserNotFound;
import com.example.OnlineSinema.service.UserService;
import com.example.SinemaContract.VM.cards.BaseViewModel;
import com.example.SinemaContract.VM.form.user.UserFM;
import com.example.SinemaContract.controllers.domeinController.UserController;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/user")
public class UserControllerImpl implements UserController {

    private final UserService userService;
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    public UserControllerImpl(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public String userProfile(@PathVariable int id, Model model, Principal principal) {
        try {
            UserInfoDTO userInfo = userService.findById(id);

            String loggedUser = principal.getName();

            model.addAttribute("loggedUser", loggedUser);
            model.addAttribute("userName", userInfo.getName());
            model.addAttribute("reviews", userInfo.getReviews());

            return "user/profile";
        } catch (UserNotFound e) {
            model.addAttribute("error", e.getMessage());
            return "error/404";
        }
    }

    @Override
    @GetMapping("/{username}")
    public String userProfile(@PathVariable String username, Model model, Principal principal) {
        try {
            UserInfoDTO userInfo = userService.findByUsername(username);

            String loggedUser = principal.getName();

            model.addAttribute("loggedUser", loggedUser);
            model.addAttribute("userName", userInfo.getName());
            model.addAttribute("reviews", userInfo.getReviews());

            return "user/profile";
        } catch (UserNotFound e) {
            model.addAttribute("error", e.getMessage());
            return "error/404";
        }
    }

    @Override
    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("user") UserFM userFM, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "register";
        }

        try {
            userService.register(userFM.name(), userFM.email(), userFM.password());
            return "redirect:/user/login";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "register";
        }
    }

    @Override
    @PostMapping("/login")
    public String login(UserFM userFM, BindingResult bindingResult, Model model) {
        return "";
    }

    @PostMapping("/login-page")
    public String loginPage() {
        return "login";
    }

    @Override
    public BaseViewModel createBaseVieModel(String title, int filmId) {
        return new BaseViewModel(title, filmId);
    }
}
