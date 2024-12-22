package com.example.OnlineSinema.controller.AdminPanely;

import com.example.OnlineSinema.dto.reviewDTO.ReviewOutputDTO;
import com.example.OnlineSinema.service.*;
import com.example.OnlineSinema.service.impl.UserDetailsServiceImpl;
import com.example.SinemaContract.VM.admin.AdminViewModelEntityList;
import com.example.SinemaContract.VM.cards.BaseViewModel;
import com.example.SinemaContract.VM.domain.film.ReviewPageFormModel;
import com.example.SinemaContract.VM.form.film.FilmPageFM;
import com.example.SinemaContract.VM.form.genre.GenrePageFM;
import com.example.SinemaContract.VM.form.user.UserPageFM;
import com.example.SinemaContract.controllers.admine.AdminController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminControllerImpl implements AdminController {

    private final UserService userService;
    private final GenresService genreService;
    private final FilmService filmService;
    private final ReviewsService reviewService;
    private final ActorsServis actorsServis;
    private final DirectorsService directorsService;

    @Autowired
    public AdminControllerImpl(UserService userService, GenresService genreService,
                               FilmService filmService, ReviewsService reviewService,
                               ActorsServis actorsServis, DirectorsService directorsService) {
        this.userService = userService;
        this.genreService = genreService;
        this.filmService = filmService;
        this.reviewService = reviewService;
        this.actorsServis = actorsServis;
        this.directorsService = directorsService;
    }

    @Override
    @GetMapping
    public String adminPanel(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        model.addAttribute("model", new AdminViewModelEntityList<>(createBaseVieModel("Admin panel", userDetails),
                null, null, null, 0));
        return "admin-main";
    }

    @Override
    @GetMapping("/user")
    public String adminPanelUser(@ModelAttribute("clientForm") UserPageFM userFM, Model model, @AuthenticationPrincipal UserDetails userDetails) {
        var page = userFM.page() != null ? userFM.page() : 1;
        var size = userFM.size() != null ? userFM.size() : 10;
        userFM = new UserPageFM(page, size);
        var clients = userService.findAll(page, size);

        var viewModel = new AdminViewModelEntityList<>(createBaseVieModel("Admin panel", userDetails),
                clients.stream().toList(), userFM, "client", clients.getTotalPages());

        model.addAttribute("model", viewModel);
        return "admin-main";
    }

    @Override
    @GetMapping("/genre")
    public String adminPanelGenre(@ModelAttribute("genreForm") GenrePageFM genreForm, Model model, @AuthenticationPrincipal UserDetails userDetails) {
        var page = genreForm.page() != null ? genreForm.page() : 1;
        var size = genreForm.size() != null ? genreForm.size() : 5;
        genreForm = new GenrePageFM(page, size);
        var genres = genreService.findAll(page, size);

        var viewModel = new AdminViewModelEntityList<>(createBaseVieModel("Admin panel", userDetails),
                genres.stream().toList(), genreForm, "genre", genres.getTotalPages());

        model.addAttribute("model", viewModel);
        return "admin-main";
    }

    @Override
    @GetMapping("/film")
    public String adminPanelMedia(@ModelAttribute("mediaForm") FilmPageFM filmFM, Model model, @AuthenticationPrincipal UserDetails userDetails) {
        var page = filmFM.page() != null ? filmFM.page() : 1;
        var size = filmFM.size() != null ? filmFM.size() : 10;
        filmFM = new FilmPageFM(page, size);
        var medias = filmService.findAll(page, size);

        var viewModel = new AdminViewModelEntityList<>(createBaseVieModel("Admin panel", userDetails),
                medias.stream().toList(), filmFM, "media", medias.getTotalPages());

        model.addAttribute("model", viewModel);
        return "admin-main";
    }

    @Override
    @GetMapping("/review")
    public String adminPanelReview(@ModelAttribute("reviewForm") ReviewPageFormModel reviewForm, Model model, @AuthenticationPrincipal UserDetails userDetails) {
        var page = reviewForm.page() != null ? reviewForm.page() : 1;
        var size = reviewForm.size() != null ? reviewForm.size() : 10;
        reviewForm = new ReviewPageFormModel(page, size);
        Page<ReviewOutputDTO> reviewsPage = reviewService.findAll(page - 1, size);
        List<ReviewOutputDTO> reviews = reviewsPage.getContent();

        var viewModel = new AdminViewModelEntityList<>(createBaseVieModel("Admin panel", userDetails),
                reviews.stream().toList(), reviewForm, "review", reviewsPage.getTotalPages());

        model.addAttribute("model", viewModel);
        return "admin-main";
    }

    @Override
    public BaseViewModel createBaseVieModel(String title, UserDetails userDetails) {
        if (userDetails == null){
            return new BaseViewModel(title, -1, null);
        }
        else{
            UserDetailsServiceImpl.CustomUser customUser = (UserDetailsServiceImpl.CustomUser) userDetails;
            return new BaseViewModel(title, customUser.getId(),customUser.getName());
        }
    }
}
