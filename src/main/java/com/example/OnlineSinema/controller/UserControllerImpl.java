package com.example.OnlineSinema.controller;


import com.example.OnlineSinema.dto.userDTO.UserOutputDTO;
import com.example.OnlineSinema.exceptions.UserNotFound;
import com.example.OnlineSinema.service.UserService;
import com.example.OnlineSinema.service.impl.UserDetailsServiceImpl;
import com.example.SinemaContract.VM.cards.BaseViewModel;
import com.example.SinemaContract.VM.form.user.UserFM;
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

//    @Override
//    @GetMapping("/{identifier}")
//    public String userProfile(@PathVariable String identifier, Model model, Principal principal) {
//        try {
//            UserInfoDTO userInfo;
//            if (identifier.matches("\\d+")) {
//                userInfo = userService.findById(Integer.parseInt(identifier));
//            } else {
//                userInfo = userService.findByUsername(identifier);
//            }
//
//            String loggedUser = principal.getName();
//
//            model.addAttribute("loggedUser", loggedUser);
//            model.addAttribute("name", userInfo.getName());
//            model.addAttribute("reviews", userInfo.getReviews());
//
//            return "user/profile";
//        } catch (UserNotFound e) {
//            model.addAttribute("error", e.getMessage());
//            return "404";
//        }
//    }

    @Override
    @GetMapping("/login")
    public String pageLogin(@RequestParam(value = "error", required = false) String error, Model model, @AuthenticationPrincipal UserDetails userDetails) {
        String username = (userDetails != null) ? userDetails.getUsername() : "anonymous";
        LOG.info("Client '{}' requested the login page.", username);

        var baseView = createBaseVieModel("Authorization",userDetails);
        if (baseView.filmId() != -1){
            LOG.info("User '{}' is already authenticated. Redirecting to the home page.", username);
            return "redirect:/";
        }
        model.addAttribute("model", baseView);
        if (error != null) {
            LOG.warn("Failed login attempt by '{}'. Reason: Incorrect login or password.", username);
            model.addAttribute("error", "Неверный логин или пароль");
        }
        model.addAttribute("loginForm", new UserLoginFM(null, null));
        LOG.info("Returning the login page for client '{}'.", username);
        return "login";
    }

    @Override
    @GetMapping("/register")
    public String pageRegistration(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        String username = (userDetails != null) ? userDetails.getUsername() : "anonymous";
        LOG.info("User '{}' requested the register page.", username);

        var baseView = createBaseVieModel("Registration",  userDetails);
        if (baseView.filmId() != -1){
            LOG.info("Client '{}' is already authenticated. Redirecting to the home page.", username);
            return "redirect:/";
        }

        model.addAttribute("model", baseView);
        model.addAttribute("newUser", new UserFM(null, null, null, null));
        LOG.info("Returning the register page for client '{}'.", username);
        return "auth-reg";
    }

    @Override
    @PostMapping("/register")
    public String requestRegister(@Valid @ModelAttribute("newUser") UserFM userFM,
                                  BindingResult bindingResult, Model model) {
        LOG.info("Client 'anonymous' attempting to register with email: {}.", userFM.email());

        if (bindingResult.hasErrors()){
            LOG.warn("Registration form validation failed for email: {}.", userFM.email());
            model.addAttribute("error", "Error in filling out the form");
            return "auth-reg";
        }

        try {
            userService.save(new UserOutputDTO(0, userFM.name(),
                    userFM.email(),
                    userFM.password(),
                    userFM.acces()));
            LOG.info("Client successfully registered with email: {}.", userFM.email());
            return "redirect:/auth/login";
        }catch (UserNotFound e){
            LOG.error("Registration failed for email: {}. Reason: {}", userFM.email(), e.getMessage());
            model.addAttribute("error", e.getMessage());
            return "auth-reg";
        }
    }

    @Override
    public BaseViewModel createBaseVieModel(String title, UserDetails userDetails) {
        if (userDetails == null){
            return new BaseViewModel(title, -1, null);
        }else{
            UserDetailsServiceImpl.CustomUser customUser = (UserDetailsServiceImpl.CustomUser) userDetails;
            return new BaseViewModel(title, customUser.getId(), customUser.getName());
        }
    }
}