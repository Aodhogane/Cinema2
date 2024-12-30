package com.example.OnlineSinema.controller;


import com.example.OnlineSinema.component.Cooky;
import com.example.OnlineSinema.dto.userDTO.UserOutputDTO;
import com.example.OnlineSinema.service.UserService;
import com.example.OnlineSinema.service.impl.UserDetailsServiceImpl;
import com.example.SinemaContract.VM.cards.BaseViewModel;
import com.example.SinemaContract.VM.form.user.UserLoginFM;
import com.example.SinemaContract.controllers.domeinController.UserController;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
                            Model model,
                            @AuthenticationPrincipal UserDetails userDetails,
                            HttpServletResponse response,
                            HttpServletRequest request) {
        String usernameFromCookie = null;
        String username = (userDetails != null) ? userDetails.getUsername() : "anonymous";
        LOG.info("Client '{}' requested the login page.", username);
        var baseView = createBaseVieModel("Authorization",userDetails);
        if (baseView.filmId() != -1){
            LOG.info("User '{}' is already authenticated. Redirecting to the home page.", username);

            Cookie userCookie = new Cookie("username", username);
            userCookie.setMaxAge(5 * 60);
            userCookie.setPath("/");
            response.addCookie(userCookie);

            return "redirect:/";
        }

        Cookie[] cookies = request.getCookies();
        if (cookies != null){
            for(Cookie cookie : cookies){
                if ("username".equals(cookie.getName())){
                    usernameFromCookie = cookie.getValue();
                    break;
                }
            }
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
                               Model model,
                               HttpServletResponse response) {
        if (bindingResult.hasErrors()) {
            return "register";
        }

        try {
            userService.register(userDto.getName(), userDto.getEmail(), userDto.getPassword(), accessId);

            Cookie userCookie = new Cookie("username", userDto.getName());
            userCookie.setMaxAge(5 * 60);
            userCookie.setPath("/");
            response.addCookie(userCookie);

        } catch (IllegalArgumentException ex){
            model.addAttribute("error", ex.getMessage());
            return "register";
        }
        userService.register(userDto.getName(), userDto.getEmail(), userDto.getPassword(), accessId);

        return "redirect:/user/login";
    }

    @GetMapping("/profile")
    public String getProfile(Model model, HttpServletRequest request){
        String username = null;

        Cookie[] cookies = request.getCookies();
        if (cookies != null){
            for (Cookie cookie : cookies){
                if("username".equals(cookie.getName())){
                    username = cookie.getName();
                    break;
                }
            }
        }

        model.addAttribute("username", username);
        return "profile";
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