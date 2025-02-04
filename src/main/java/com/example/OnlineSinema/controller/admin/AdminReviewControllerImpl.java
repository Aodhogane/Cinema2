package com.example.OnlineSinema.controller.admin;

import com.example.OnlineSinema.DTO.ClientDTO;
import com.example.OnlineSinema.DTO.FilmDTO;
import com.example.OnlineSinema.DTO.ReviewDTO;
import com.example.OnlineSinema.DTO.UserDTO;
import com.example.OnlineSinema.domain.User;
import com.example.OnlineSinema.service.ClientService;
import com.example.OnlineSinema.service.FilmService;
import com.example.OnlineSinema.service.ReviewService;
import com.example.OnlineSinema.service.UserService;
import com.example.SinemaContract.controllers.admin.AdminReviewController;
import com.example.SinemaContract.viewModel.BaseViewModel;
import com.example.SinemaContract.viewModel.admin.AdminReviewViewModel;
import com.example.SinemaContract.viewModel.admin.AdminViewModel;
import com.example.SinemaContract.viewModel.form.PageForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("admin/review")
public class AdminReviewControllerImpl implements AdminReviewController {

    private final ReviewService reviewService;
    private final FilmService filmService;
    private final ClientService clientService;


    @Autowired
    public AdminReviewControllerImpl(ReviewService reviewService,
                                     FilmService filmService, ClientService clientService) {
        this.reviewService = reviewService;
        this.filmService = filmService;
        this.clientService = clientService;
    }

    @Override
    @GetMapping()
    public String PageAdminReview(@ModelAttribute("form") PageForm form,
                                  Principal principal,
                                  Model model){
        int page = form.page() != null ? form.page() : 1;
        int size = form.size() != null ? form.size() : 10;
        form = new PageForm(page, size);

        Page<ReviewDTO> pageReview = reviewService.findAllPage(page, size);

        List<AdminReviewViewModel> list = new ArrayList<>();
        for (ReviewDTO reviewDTO : pageReview){

            ClientDTO clientDTO = clientService.findClientById(reviewDTO.getClientId());
            FilmDTO filmDTO = filmService.findFilmById(reviewDTO.getFilmId());

            AdminReviewViewModel adminReview = new AdminReviewViewModel(reviewDTO.getId(), reviewDTO.getComment(),
                    reviewDTO.getEstimation(), reviewDTO.getDateTime(), reviewDTO.getClientId(), clientDTO.getName(), reviewDTO.getFilmId(), filmDTO.getTitle());

            list.add(adminReview);
        }

        AdminViewModel<AdminReviewViewModel> viewModel = new AdminViewModel<>(createBaseVieModel(principal),
                pageReview.getTotalPages(), list);

        model.addAttribute("model", viewModel);
        model.addAttribute("form", form);

        return "admin/review/adminReview";
    }


    @Override
    public BaseViewModel createBaseVieModel(Principal principal) {
        if (principal != null){
            return new BaseViewModel(principal.getName());
        }
        return new BaseViewModel(null);
    }
}
