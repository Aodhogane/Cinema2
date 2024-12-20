package com.example.SinemaContract.controllers.domeinController;

import com.example.SinemaContract.VM.form.user.UserFM;
import com.example.SinemaContract.controllers.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/user")
public interface UserController extends BaseController {

        @GetMapping("/{username}")
        String userProfile(@PathVariable String username, Model model, Principal principal);

        @PostMapping("/register")
        String register(@ModelAttribute UserFM userFM, BindingResult bindingResult, Model model);

        @PostMapping("/login")
        String login(@ModelAttribute UserFM userFM, BindingResult bindingResult, Model model);
}