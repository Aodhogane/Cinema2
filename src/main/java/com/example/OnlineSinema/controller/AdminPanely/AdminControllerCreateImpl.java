//package com.example.OnlineSinema.controller.AdminPanely;
//
//import com.example.OnlineSinema.dto.filmDTO.FilmOutputDTO;
//import com.example.OnlineSinema.dto.genresDTO.GenresOutputDTO;
//import com.example.OnlineSinema.dto.userDTO.UserOutputDTO;
//import com.example.OnlineSinema.exceptions.FilmNotFounf;
//import com.example.OnlineSinema.exceptions.GenreAlreadyExistsException;
//import com.example.OnlineSinema.exceptions.GenreNotFoundException;
//import com.example.OnlineSinema.exceptions.UserNotFound;
//import com.example.OnlineSinema.service.*;
//import com.example.OnlineSinema.config.UserDetailsServiceImpl;
//import com.example.SinemaContract.VM.cards.BaseViewModel;
//import com.example.SinemaContract.VM.form.actor.ActorFM;
//import com.example.SinemaContract.VM.form.director.DirectorFM;
//import com.example.SinemaContract.VM.form.film.FilmFM;
//import com.example.SinemaContract.VM.form.genre.GenreFM;
//import com.example.SinemaContract.VM.form.user.UserFM;
//import com.example.SinemaContract.controllers.admine.AdminControllerCreate;
//import jakarta.validation.Valid;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//
//import java.util.ArrayList;
//
//@PreAuthorize("hasRole('ADMIN')")
//@Controller
//@RequestMapping("/admin/create")
//public class AdminControllerCreateImpl implements AdminControllerCreate {
//
//    private final UserService userService;
//    private final FilmService filmService;
//    private final ActorsServis actorsServis;
//    private final DirectorsService directorsService;
//    private final GenresService genresService;
//
//    @Autowired
//    public AdminControllerCreateImpl(UserService userService, FilmService filmService,
//                                     ActorsServis actorsServis, DirectorsService directorsService,
//                                     GenresService genresService) {
//        this.userService = userService;
//        this.filmService = filmService;
//        this.actorsServis = actorsServis;
//        this.directorsService = directorsService;
//        this.genresService = genresService;
//    }
//
//
//
//    @Override
//    @GetMapping("/user")
//    public String createClient(Model model, @AuthenticationPrincipal UserDetails userDetails) {
//        var base = createBaseVieModel("User create page", userDetails);
//
//        model.addAttribute("model", base);
//        model.addAttribute("entity", "user");
//
//        if (!model.containsAttribute("form")){
//            model.addAttribute("form", new UserFM(null, null, null, null));
//        }
//
//        return "admin-create";
//    }
//
//    @Override
//    @GetMapping("/genre")
//    public String createGenre(Model model, @AuthenticationPrincipal UserDetails userDetails) {
//        var base = createBaseVieModel("Genre create page", userDetails);
//        model.addAttribute("model", base);
//        model.addAttribute("entity", "genre");
//
//        if (!model.containsAttribute("form")){
//            model.addAttribute("form", new GenreFM(null));
//
//        }
//        return "admin-create";
//    }
//
//    @Override
//    @GetMapping("/film")
//    public String createFilm(Model model, @AuthenticationPrincipal UserDetails userDetails) {
//        var base = createBaseVieModel("Film create page", userDetails);
//        model.addAttribute("model", base);
//        model.addAttribute("entity", "film");
//        model.addAttribute("genres", genresService.findAll().stream().map(GenresOutputDTO::getGenres).toList());
//        model.addAttribute("actors",
//                actorsServis.findAll().stream()
//                        .map(actor -> actor.getMidlName() + " " + actor.getName() + " " + actor.getSurname())
//                        .toList());
//        model.addAttribute("director",
//                directorsService.findAll().stream()
//                .map(director -> director.getMidlName()+ " " + director.getName() + " " + director.getSurname())
//                        .toList());
//        if (!model.containsAttribute("form")){
//            model.addAttribute("form", new FilmFM(null, null, null, null, null, 0.0));
//        }
//        return "admin-create";
//    }
//
//    @Override
//    @GetMapping("/actor")
//    public String createActor(Model model,  @AuthenticationPrincipal UserDetails userDetails) {
//        var base = createBaseVieModel("Actor create page", userDetails);
//        model.addAttribute("model", base);
//        model.addAttribute("entity", "actor");
//
//        if (!model.containsAttribute("form")){
//            model.addAttribute("form", new ActorFM(null, null,null));
//
//        }
//        return "admin-create";
//    }
//
//    @Override
//    @GetMapping("/director")
//    public String crateDirector(Model model,  @AuthenticationPrincipal UserDetails userDetails) {
//        var base = createBaseVieModel("Actor create page", userDetails);
//        model.addAttribute("model", base);
//        model.addAttribute("entity", "director");
//
//        if (!model.containsAttribute("form")){
//            model.addAttribute("form", new DirectorFM(null, null,null));
//
//        }
//
//        return "admin-create";
//    }
//
//    @Override
//    @PostMapping("/user")
//    public String createUser(@Valid @ModelAttribute("userFM") UserFM userFM,
//                             BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
//        if (bindingResult.hasErrors()){
//            redirectAttributes.addFlashAttribute("error", "Error in filing out form");
//            redirectAttributes.addFlashAttribute("form", userFM);
//            return "redirect:/admin/crete/user";
//        }
//        try {
//            userService.save(new UserOutputDTO(0, userFM.name(), userFM.email(), userFM.password(), userFM.acces()));
//        }catch (UserNotFound e){
//            redirectAttributes.addFlashAttribute("error", e.getMessage());
//            redirectAttributes.addFlashAttribute("form", userFM);
//            return "redirect:/admin/create/user";
//        }
//        redirectAttributes.addFlashAttribute("successMessage", "User created ERROR!");
//        return "redirect:/admin/user";
//    }
//
//    @Override
//    @PostMapping("/genre")
//    public String createGenre(GenreFM genreFM, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
//        if (bindingResult.hasErrors()){
//            redirectAttributes.addFlashAttribute("error", "Error in filling out the form");
//            redirectAttributes.addFlashAttribute("form", genreFM);
//            return "redirect:/admin/create/genre";
//        }
//        try {
//            genresService.save(new GenresOutputDTO(genreFM.genres(), 0));
//        }catch (GenreAlreadyExistsException e){
//            redirectAttributes.addFlashAttribute("error", e.getMessage());
//            redirectAttributes.addFlashAttribute("form", genreFM);
//            return "redirect:/admin/create/genre";
//        }
//
//        redirectAttributes.addFlashAttribute("successMessage", "Genre created successfully!");
//        return "redirect:/admin/genre";
//    }
//
//    @Override
//    @PostMapping("/film")
//    public String createFilm(FilmFM filmFM, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
//        if (bindingResult.hasErrors()){
//            redirectAttributes.addFlashAttribute("error", "Error in filling out the form");
//            redirectAttributes.addFlashAttribute("form", filmFM);
//            return "redirect:/admin/create/genre";
//        }
//        try {
//            filmService.save(new FilmOutputDTO(
//                    0,
//                    new ArrayList<>(filmFM.actors()),
//                    new ArrayList<>(filmFM.genres()),
//                    new ArrayList<>(filmFM.directors()),
//                    filmFM.dateTime(),
//                    filmFM.name(),
//                    0,
//                    filmFM.reating()
//            ));
//        }catch (FilmNotFounf | GenreNotFoundException e){
//            redirectAttributes.addFlashAttribute("error", e.getMessage());
//            redirectAttributes.addFlashAttribute("form", filmFM);
//            return "redirect:/admin/create/film";
//        }
//
//        redirectAttributes.addFlashAttribute("successMessage", "Film created successfully!");
//        return "redirect:/admin/film";
//    }
//
//    @Override
//    @PostMapping("/actor")
//    public String createActor(ActorFM actorFM, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
//        if (bindingResult.hasErrors()){
//            redirectAttributes.addFlashAttribute("error", "Error in filing out form");
//            redirectAttributes.addFlashAttribute("form", actorFM);
//            return "redirect:/admin/crete/actor";
//        }
//        redirectAttributes.addFlashAttribute("successMessage", "Actor created ERROR!");
//        return "redirect:/admin/actor";
//    }
//
//    @Override
//    @PostMapping("/director")
//    public String createDirector(DirectorFM directorFM, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
//        if (bindingResult.hasErrors()){
//            redirectAttributes.addFlashAttribute("error", "Error in filing out form");
//            redirectAttributes.addFlashAttribute("form", directorFM);
//            return "redirect:/admin/crete/director";
//        }
//        redirectAttributes.addFlashAttribute("successMessage", "Director created ERROR!");
//        return "redirect:/admin/director";
//    }
//
//    @Override
//    public BaseViewModel createBaseVieModel(String title, UserDetails userDetails) {
//        if (userDetails == null){
//            return new BaseViewModel(title, -1, null);
//        }
//        else{
//            UserDetailsServiceImpl.CustomUser customUser = (UserDetailsServiceImpl.CustomUser) userDetails;
//            return new BaseViewModel(title, customUser.getId(), customUser.getUsername());
//        }
//    }
//}
