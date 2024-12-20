package com.example.SinemaContract.controllers.domeinController;

import com.example.SinemaContract.VM.form.user.UserFM;
import com.example.SinemaContract.VM.form.user.UserLoginFM;
import com.example.SinemaContract.controllers.BaseController;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/authenticator")
public interface AuthenticatorController extends BaseController {

    @GetMapping("/login")
    String pageLogin(Model model);

    @GetMapping("/register")
    String pageRegistration(Model model);

    @PostMapping("/login")
    String requestLogin(@Valid @ModelAttribute("loginForm") UserLoginFM loginForm,
                        BindingResult bindingResult, Model model);

    @PostMapping("/register")
    String requestRegister(@Valid @ModelAttribute("newUser") UserFM clientFormModel,
                           BindingResult bindingResult, Model model);
}
