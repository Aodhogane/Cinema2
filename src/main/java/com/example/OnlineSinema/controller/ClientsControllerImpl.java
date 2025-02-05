package com.example.OnlineSinema.controller;

import com.example.OnlineSinema.DTO.BaseUserDTO;
import com.example.OnlineSinema.DTO.ClientDTO;
import com.example.OnlineSinema.DTO.ReviewDTO;
import com.example.OnlineSinema.service.AuthService;
import com.example.OnlineSinema.service.ClientService;
import com.example.OnlineSinema.service.FilmService;
import com.example.OnlineSinema.service.ReviewService;
import com.example.SinemaContract.viewModel.BaseViewModel;
import com.example.SinemaContract.viewModel.ClientsDeteilsViewModel;
import com.example.SinemaContract.viewModel.card.ReviewCardViewModel;
import com.example.SinemaContract.controllers.ClientContriller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/client")
public class ClientsControllerImpl implements ClientContriller {

    private final ClientService clientService;
    private final ReviewService reviewService;
    private final FilmService filmService;
    private final AuthService authService;

    @Autowired
    public ClientsControllerImpl(ClientService clientService,
                                 ReviewService reviewService,
                                 FilmService filmService, AuthService authService) {
        this.clientService = clientService;
        this.reviewService = reviewService;
        this.filmService = filmService;
        this.authService = authService;
    }

    @Override
    @GetMapping
    public String findVlientById(Principal principal,
                                 Model model){

        BaseUserDTO baseUserDTO = authService.getUser(principal.getName());
        ClientDTO client = clientService.findClientById(baseUserDTO.getId());
        List<ReviewDTO> review = reviewService.findReviewByClientId(baseUserDTO.getId());

        List<ReviewCardViewModel> reviews = new ArrayList<>();
        for (ReviewDTO reviewDTO : review){

            String filmName = filmService.findFilmById(reviewDTO.getFilmId()).getTitle();

            ReviewCardViewModel reviewCardViewModel = new ReviewCardViewModel(filmName, reviewDTO.getComment(),
                    reviewDTO.getEstimation(), reviewDTO.getDateTime());
            reviews.add(reviewCardViewModel);
        }

        ClientsDeteilsViewModel viewModel = new ClientsDeteilsViewModel(
                client.getName(), reviews, createBaseVieModel(principal));

        model.addAttribute("model", viewModel);
        return "profile";
    }


    @Override
    public BaseViewModel createBaseVieModel(Principal principal) {
        if (principal != null){
            return new BaseViewModel(principal.getName());
        }
        return new BaseViewModel(null);
    }
}
