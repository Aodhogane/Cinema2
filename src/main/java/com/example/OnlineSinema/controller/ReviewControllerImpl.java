package com.example.OnlineSinema.controller;

import com.example.OnlineSinema.dto.reviewDTO.ReviewOutputDTO;
import com.example.OnlineSinema.exceptions.FilmNotFounf;
import com.example.OnlineSinema.exceptions.UserNotFound;
import com.example.OnlineSinema.service.FilmService;
import com.example.OnlineSinema.service.ReviewsService;
import com.example.OnlineSinema.service.UserService;
import com.example.SinemaContract.VM.form.review.ReviewFormModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/review")
public class ReviewControllerImpl {

    private final ReviewsService reviewsService;
    private final UserService userService;
    private final FilmService filmService;

    @Autowired
    public ReviewControllerImpl(ReviewsService reviewsService,
                                UserService userService, FilmService filmService) {
        this.reviewsService = reviewsService;
        this.userService = userService;
        this.filmService = filmService;
    }


    @GetMapping("/{id}")
    public String viewReview(@PathVariable("id") int id, Model model) {
        try {
            ReviewOutputDTO review = reviewsService.findById(id);
            model.addAttribute("review", review);
            return "review/details";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error/404";
        }
    }

    @PostMapping("/create")
    @Transactional
    public String createReview(@ModelAttribute ReviewFormModel reviewFormModel, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("error", "Validation errors occurred. Please correct them.");
            return "redirect:/review/create?filmId=" + reviewFormModel.filmId();
        }

        try {
            String userName = userService.findUserNameById(reviewFormModel.userId());

            String filmName = filmService.findFilmNameById((long) reviewFormModel.filmId());
            ReviewOutputDTO reviewInputDto = new ReviewOutputDTO(
                    0,
                    reviewFormModel.userId(),
                    Long.valueOf(reviewFormModel.filmId()),
                    reviewFormModel.rating(),
                    reviewFormModel.text(),
                    "UserNamePlaceholder",
                    "FilmNamePlaceholder",
                    LocalDateTime.now()
            );
            reviewsService.save(reviewInputDto);

            redirectAttributes.addFlashAttribute("success", "Review created successfully!");
            return "redirect:/review?filmId=" + reviewFormModel.filmId();
        } catch (UserNotFound | FilmNotFounf e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/review/create?filmId=" + reviewFormModel.filmId();
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "An unexpected error occurred. Please try again.");
            return "redirect:/review/create?filmId=" + reviewFormModel.filmId();
        }
    }

    @DeleteMapping("/delete/{reviewId}")
    public String deleteReview(@PathVariable int reviewId, RedirectAttributes redirectAttributes) {
        try {
            reviewsService.deleteById(reviewId);
            redirectAttributes.addFlashAttribute("success", "Review deleted successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/review";
    }

    @PostMapping("/update/{id}")
    @Transactional
    public String updateReview(@PathVariable int id, @ModelAttribute ReviewFormModel reviewFormModel, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("error", "Validation errors occurred. Please correct them.");
            return "redirect:/review/edit/" + id;
        }

        try {
            ReviewOutputDTO existingReview = reviewsService.findById(id);
            if (existingReview == null) {
                redirectAttributes.addFlashAttribute("error", "Review not found.");
                return "redirect:/review";
            }

            ReviewOutputDTO updatedReview = new ReviewOutputDTO(
                    id,
                    reviewFormModel.userId(),
                    Long.valueOf(reviewFormModel.filmId()),
                    reviewFormModel.rating(),
                    reviewFormModel.text(),
                    userService.findUserNameById(reviewFormModel.userId()),
                    filmService.findFilmNameById(Long.valueOf(reviewFormModel.filmId())),
                    LocalDateTime.now()
            );

            reviewsService.update(id, updatedReview);
            redirectAttributes.addFlashAttribute("success", "Review updated successfully!");
            return "redirect:/review?filmId=" + reviewFormModel.filmId();
        } catch (UserNotFound | FilmNotFounf e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/review/edit/" + id;
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "An unexpected error occurred. Please try again.");
            return "redirect:/review/edit/" + id;
        }
    }

    @GetMapping("/create")
    public String showCreateForm(@RequestParam int filmId, Model model) {
        model.addAttribute("reviewFormModel", new ReviewFormModel(0, "", filmId, "", 0, ""));
        return "review/create";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable int id, Model model) {
        try {
            ReviewOutputDTO review = reviewsService.findById(id);
            model.addAttribute("reviewFormModel", new ReviewFormModel(
                    review.getUserId(),
                    review.getUserName(),
                    review.getFilmId().intValue(),
                    review.getFilmName(),
                    review.getEstimation(),
                    review.getComment()
            ));
            return "review/edit";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error/404";
        }
    }

    @GetMapping
    public String listReviews(@RequestParam(required = false) Integer filmId, Model model) {
        if (filmId != null) {
            List<ReviewOutputDTO> reviews = reviewsService.findByFilmId(filmId);
            model.addAttribute("reviews", reviews);
            model.addAttribute("filmId", filmId);
        } else {
            List<ReviewOutputDTO> reviews = reviewsService.findAll();
            model.addAttribute("reviews", reviews);
        }
        return "review/list";
    }
}