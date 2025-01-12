package com.example.OnlineSinema.controller.AdminPanely;

import com.example.OnlineSinema.config.Pagination;
import com.example.OnlineSinema.config.UserDetailsServiceImpl;
import com.example.OnlineSinema.controller.MainPageController;
import com.example.OnlineSinema.service.*;
import com.example.SinemaContract.VM.admin.AdminViewModelEntityList;
import com.example.SinemaContract.VM.cards.BaseViewModel;
import com.example.SinemaContract.VM.domain.film.ReviewPageFormModel;
import com.example.SinemaContract.VM.form.actor.ActorPageFM;
import com.example.SinemaContract.VM.form.director.DirectorPageFM;
import com.example.SinemaContract.VM.form.film.FilmPageFM;
import com.example.SinemaContract.VM.form.genre.GenrePageFM;
import com.example.SinemaContract.VM.form.user.UserPageFM;
import com.example.SinemaContract.controllers.admine.AdminController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminControllerImpl implements AdminController {

    private final UserService userService;
    private final GenresService genreService;
    private final FilmService filmService;
    private final ReviewsService reviewService;
    private final ActorsServis actorsServis;
    private final DirectorsService directorsService;
    private static final Logger LOG = LogManager.getLogger(MainPageController.class);

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
        LOG.info("Client '{}' accessed the admin panel.", userDetails.getUsername());

        Pagination pagination = new Pagination(1, 10, 100);

        model.addAttribute("pagination", pagination);

        model.addAttribute("model", new AdminViewModelEntityList<>(
                createBaseVieModel("Admin panel", userDetails),
                null, null, null, 0
        ));

        LOG.info("Admin panel view returned for client '{}'.", userDetails.getUsername());
        return "admin-main";
    }

    @Override
    @GetMapping("/user")
    public String adminPanelUser(@ModelAttribute("clientForm") UserPageFM userFM, Model model, @AuthenticationPrincipal UserDetails userDetails) {
        LOG.info("User '{}' accessed the admin panel for managing users.", userDetails.getUsername());
        var page = userFM.page() != null ? userFM.page() : 1;
        var size = userFM.size() != null ? userFM.size() : 5;

        var users = userService.findAll(page, size);

        if (users.getTotalElements() < size) {
            userFM = new UserPageFM(page, (int) users.getTotalElements());
        } else {
            userFM = new UserPageFM(page, size);
        }

        LOG.info("User '{}' requested user management list with pagination - Page: {}, Size: {}.", userDetails.getUsername(), page, size);

        model.addAttribute("model",
                new AdminViewModelEntityList<>(createBaseVieModel(
                        "User Management", userDetails),
                users.stream().toList(),
                        userFM,
                        "user",
                        users.getTotalPages()));

        LOG.info("Returned user management view to client '{}'.", userDetails.getUsername());
        return "admin-main";
    }

    @Override
    @GetMapping("/genre")
    public String adminPanelGenre(@ModelAttribute("genreForm") GenrePageFM genreForm, Model model, @AuthenticationPrincipal UserDetails userDetails) {
        LOG.info("User '{}' accessed the admin panel for managing genres.", userDetails.getUsername());
        var page = genreForm.page() != null ? genreForm.page() : 1;
        var size = genreForm.size() != null ? genreForm.size() : 5;

        var genres = genreService.findAll(page, size);

        if (genres.getTotalElements() < size) {
            genreForm = new GenrePageFM(page, (int) genres.getTotalElements());
        } else {
            genreForm = new GenrePageFM(page, size);
        }

        model.addAttribute("model", new AdminViewModelEntityList<>(createBaseVieModel("Genre Management", userDetails),
                genres.stream().toList(), genreForm, "genre", genres.getTotalPages()));

        LOG.info("Returned genre management view to client '{}'.", userDetails.getUsername());
        return "admin-main";
    }

    @Override
    @GetMapping("/film")
    public String adminPanelMedia(@ModelAttribute("mediaForm") FilmPageFM filmFM, Model model, @AuthenticationPrincipal UserDetails userDetails) {
        LOG.info("User '{}' accessed the admin panel for managing films.", userDetails.getUsername());
        var page = filmFM.page() != null ? filmFM.page() : 1;
        var size = filmFM.size() != null ? filmFM.size() : 5;

        var films = filmService.findAll(page, size);

        if (films.getTotalElements() < size) {
            filmFM = new FilmPageFM(page, (int) films.getTotalElements());
        } else {
            filmFM = new FilmPageFM(page, size);
        }

        model.addAttribute("model", new AdminViewModelEntityList<>(createBaseVieModel("Film Management", userDetails),
                films.stream().toList(), filmFM, "film", films.getTotalPages()));

        LOG.info("Returned film management view to client '{}'.", userDetails.getUsername());
        return "admin-main";
    }

    @Override
    @GetMapping("/review")
    public String adminPanelReview(@ModelAttribute("reviewForm") ReviewPageFormModel reviewForm, Model model, @AuthenticationPrincipal UserDetails userDetails) {
        LOG.info("User '{}' accessed the admin panel for managing reviews.", userDetails.getUsername());
        var page = reviewForm.page() != null ? reviewForm.page() : 1;
        var size = reviewForm.size() != null ? reviewForm.size() : 5;

        var reviews = reviewService.findAll(page, size);

        if (reviews.getTotalElements() < size) {
            reviewForm = new ReviewPageFormModel(page, (int) reviews.getTotalElements());
        } else {
            reviewForm = new ReviewPageFormModel(page, size);
        }

        model.addAttribute("model", new AdminViewModelEntityList<>(createBaseVieModel("Review Management", userDetails),
                reviews.stream().toList(), reviewForm, "review", reviews.getTotalPages()));

        LOG.info("Returned review management view to client '{}'.", userDetails.getUsername());
        return "admin-main";
    }

    @Override
    @GetMapping("/actors")
    public String adminPanelActor(@ModelAttribute("actorForm") ActorPageFM actorPageFM, Model model, @AuthenticationPrincipal UserDetails userDetails) {
        LOG.info("User '{}' accessed the admin panel for managing actors.", userDetails.getUsername());
        var page = actorPageFM.clientPage() != null ? actorPageFM.clientPage() : 1;
        var size = actorPageFM.clientSize() != null ? actorPageFM.clientSize() : 5;

        var actors = actorsServis.findAll(page, size);

        if (actors.getTotalElements() < size) {
            actorPageFM = new ActorPageFM(page, (int) actors.getTotalElements());
        } else {
            actorPageFM = new ActorPageFM(page, size);
        }

        model.addAttribute("model", new AdminViewModelEntityList<>(createBaseVieModel("Actor Management", userDetails),
                actors.stream().toList(), actorPageFM, "actor", actors.getTotalPages()));

        LOG.info("Returned actor management view to client '{}'.", userDetails.getUsername());
        return "admin-main";
    }

    @Override
    @GetMapping("/director")
    public String adminPanelDirector(@ModelAttribute("directorForm") DirectorPageFM directorPageFM, Model model, @AuthenticationPrincipal UserDetails userDetails) {
        LOG.info("User '{}' accessed the admin panel for managing directors.", userDetails.getUsername());
        var page = (directorPageFM.clientPage() != null) ? directorPageFM.clientPage() : 1;
        var size = (directorPageFM.clientSize() != null) ? directorPageFM.clientSize() : 5;

        var directors = directorsService.findAll(page, size);
        LOG.debug("Directors fetched: {}", directors.getContent());

        if (directors.getTotalElements() < size) {
            directorPageFM = new DirectorPageFM(page, (int) directors.getTotalElements());
        } else {
            directorPageFM = new DirectorPageFM(page, size);
        }

        model.addAttribute("model", new AdminViewModelEntityList<>(
                createBaseVieModel("Director Management", userDetails),
                directors.getContent(),
                directorPageFM,
                "director",
                directors.getTotalPages()));

        LOG.info("Returned director management view to client '{}'.", userDetails.getUsername());
        return "admin-main";
    }

    @Override
    public BaseViewModel createBaseVieModel(String title, UserDetails userDetails) {
        if (userDetails == null){
            return new BaseViewModel(title, -1, null);
        }
        else{
            UserDetailsServiceImpl.CustomUser customUser = (UserDetailsServiceImpl.CustomUser) userDetails;
            return new BaseViewModel(title, customUser.getId(), customUser.getUsername());
        }
    }
}
