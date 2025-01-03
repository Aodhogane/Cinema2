package com.example.OnlineSinema.controller;


import com.example.OnlineSinema.domain.User;
import com.example.OnlineSinema.dto.filmDTO.FilmCardDTO;
import com.example.OnlineSinema.dto.userDTO.UserInfoDTO;
import com.example.OnlineSinema.dto.userDTO.UserOutputDTO;
import com.example.OnlineSinema.service.ReviewsService;
import com.example.OnlineSinema.service.UserService;
import com.example.OnlineSinema.config.UserDetailsServiceImpl;
import com.example.SinemaContract.VM.cards.BaseViewModel;
import com.example.SinemaContract.VM.form.user.UserLoginFM;
import com.example.SinemaContract.controllers.domeinController.UserController;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/user")
public class UserControllerImpl implements UserController {

    private final UserService userService;
    private final ReviewsService reviewsService;
    private static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(UserControllerImpl.class);

    @Autowired
    public UserControllerImpl(UserService userService, ReviewsService reviewsService) {
        this.userService = userService;
        this.reviewsService = reviewsService;
    }

    @Override
    @GetMapping("/login")
    public String pageLogin(@RequestParam(value = "error", required = false) String error,
                            Model model, @AuthenticationPrincipal UserDetails userDetails) {
        String username = (userDetails instanceof UserDetailsServiceImpl.CustomUser) ? ((UserDetailsServiceImpl.CustomUser) userDetails).getUsername() : "anonymous";
        LOG.info("User '{}' requested the login page.", username);

        var baseView = createBaseVieModel("Authorization",userDetails);
        if (baseView.filmId() != -1){
            LOG.info("User '{}' is already authenticated. Redirecting to the home page.", username);
            return "redirect:/";
        }
        model.addAttribute("baseViewModel", baseView);

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
    @GetMapping("/profile")
    public String showProfile(Model model, @AuthenticationPrincipal UserDetails userDetails){
        LOG.info("Accessing profile page");

        if (userDetails == null) {
            LOG.error("User is not authenticated");
            return "redirect:/user/login";
        }

        String username = userDetails.getUsername();
        LOG.info("Authenticated user: {}", username);

        UserInfoDTO user = userService.findByUsername(username);
        if (user == null){
            LOG.error("User with username {} not found in database", username);
            throw new IllegalArgumentException("User with username " + username + " not found!");
        }

        LOG.info("Fetching reviews for user: {}", username);
        Set<FilmCardDTO> reviewedFilms = reviewsService.getReviewsByUserId(user.getId());

        model.addAttribute("userName", user.getName());
        model.addAttribute("review", user.getReviews());
        model.addAttribute("reviewedFilms", reviewedFilms);
        model.addAttribute("baseViewModel", createBaseVieModel("Profile", userDetails));

        LOG.info("Profile page loaded successfully for user: {}", username);
        return "profile";
    }

    @GetMapping("/by_role")
    public ResponseEntity<List<UserOutputDTO>> getUsersByRole(@RequestParam String role){
        int accessId = getAccessIdByRole(role);
        List<User> users = userService.findUsersByAccessId(accessId);
        List<UserOutputDTO> userDTOs = users.stream()
                .map(user -> new UserOutputDTO(
                        user.getId(),
                        user.getUsername(),
                        user.getPassword(),
                        user.getPassword(),
                        user.getAccess().getRegistered()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(userDTOs);
    }

    private int getAccessIdByRole (String role){
        switch (role.toUpperCase()){
            case "ADMIN":
                return 1;
            case "USER":
                return 2;
            default:
                throw new IllegalArgumentException("Invalid role: " + role);
        }
    }

    @Override
    public BaseViewModel createBaseVieModel(String title, String username) {
        LOG.info("Creating BaseViewModel with title: {} and username: {}", title, username);
        return new BaseViewModel(title, -1, username);
    }

    @Override
    public BaseViewModel createBaseVieModel(String title, UserDetails userDetails) {
        if (userDetails == null) {
            return new BaseViewModel(title, -1, null);
        }

        if (userDetails instanceof UserDetailsServiceImpl.CustomUser customUser) {
            return new BaseViewModel(
                    title,
                    customUser.getId(),
                    customUser.getUsername()
            );
        }

        return new BaseViewModel(title, -1, null);
    }
}