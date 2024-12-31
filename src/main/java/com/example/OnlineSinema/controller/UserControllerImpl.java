package com.example.OnlineSinema.controller;


import com.example.OnlineSinema.dto.userDTO.UserOutputDTO;
import com.example.OnlineSinema.service.UserService;
import com.example.OnlineSinema.service.impl.UserDetailsServiceImpl;
import com.example.SinemaContract.VM.cards.BaseViewModel;
import com.example.SinemaContract.VM.form.user.UserLoginFM;
import com.example.SinemaContract.controllers.domeinController.UserController;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UserControllerImpl implements UserController {

    private final UserService userService;
    private static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(UserControllerImpl.class);

    @Autowired
    public UserControllerImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    @GetMapping("/login")
    public String pageLogin(@RequestParam(value = "error", required = false) String error,
                            Model model, @AuthenticationPrincipal UserDetails userDetails) {
        String username = (userDetails instanceof UserDetailsServiceImpl.CustomUser) ? ((UserDetailsServiceImpl.CustomUser) userDetails).getName() : "anonymous";
        LOG.info("Client '{}' requested the login page.", username);

        var baseView = createBaseVieModel("Authorization",userDetails);
        if (baseView.filmId() != -1){
            LOG.info("User '{}' is already authenticated. Redirecting to the home page.", username);
            return "redirect:/";
        }
        model.addAttribute("model", baseView);

        model.addAttribute("loginForm", new UserLoginFM(null, null));
        LOG.info("Returning the login page for client '{}'.", username);
        return "login";
    }

    @GetMapping("/register")
    public String showRegisterPage(Model model) {
        model.addAttribute("user", new UserOutputDTO());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") @Valid UserOutputDTO userDto,
                               @RequestParam("accessId") int accessId,
                               BindingResult bindingResult,
                               Model model) {
        if (bindingResult.hasErrors()) {
            return "register";
        }

        try {
            userService.register(userDto.getName(), userDto.getEmail(), userDto.getPassword(), accessId);
        } catch (IllegalArgumentException ex){
            model.addAttribute("error", ex.getMessage());
            return "register";
        }

        return "redirect:/user/login";
    }

    @Override
    public BaseViewModel createBaseVieModel(String title, UserDetails userDetails) {
        if (userDetails instanceof UserDetailsServiceImpl.CustomUser customUser) {
            return new BaseViewModel(title, customUser.getId(), customUser.getName());
        }
        return new BaseViewModel(title, -1, null);
    }
}