package com.example.OnlineSinema.controller;

import com.example.OnlineSinema.DTO.UserRegistrationDTO;
import com.example.OnlineSinema.service.AuthService;
import com.example.SinemaContract.controllers.AuthController;
import com.example.SinemaContract.viewModel.form.ClientRegisterCreateForm;
import com.example.SinemaContract.viewModel.form.MediaRegisterCreateForm;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/users")
public class AuthControllerImpl implements AuthController {

    private AuthService authService;

    @Autowired
    public AuthControllerImpl(AuthService authService) {
        this.authService = authService;
    }

    @Override
    @GetMapping("/login")
    public String login(Model model){
        return "login";
    }

    @Override
    @GetMapping("/register")
    public String registerClient(Model model){

        model.addAttribute("form", new ClientRegisterCreateForm("", "",
                "", "", ""));

        return "register";
    }

    @Override
    @PostMapping("/register")
    public String registerClient(@Valid @ModelAttribute("form") ClientRegisterCreateForm form,
                                 BindingResult bindingResult,
                                 Model model){

        if (bindingResult.hasErrors()){
            model.addAttribute("form", form);
            return "register";
        }

        UserRegistrationDTO regDTO = new UserRegistrationDTO(form.email(),
                form.password(), form.confirmPassword(), "CLIENT", form.name());
        authService.register(regDTO);

        return "redirect:/users/login";
    }

    @Override
    @GetMapping("/mediaRegister")
    public String mediaRegister(Model model){

        model.addAttribute("form", new MediaRegisterCreateForm("", "", "",
                "", "", "", ""));

        return "mediaRegister";
    }

    @Override
    @PostMapping("/mediaRegister")
    public String mediaRegister(@Valid @ModelAttribute("form") MediaRegisterCreateForm form,
                                BindingResult bindingResult,
                                Model model){

        if (bindingResult.hasErrors()){
            model.addAttribute("form", form);
            return "mediaRegister";
        }

        UserRegistrationDTO regDTO = new UserRegistrationDTO(form.email(), form.password(), form.confirmPassword(),
                form.role(), form.name(), form.surname(), form.midName());

        authService.register(regDTO);

        return "redirect:/users/login";
    }

    @Override
    @PostMapping("/login-error")
    public String onFailedLogin(
            @ModelAttribute(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY) String username,
            RedirectAttributes redirectAttributes) {

//        LOG.log(Level.INFO, "login-error method called with POST");

        redirectAttributes.addFlashAttribute(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY, username);
        redirectAttributes.addFlashAttribute("badCredentials", true);

        return "redirect:/users/login";
    }
}

