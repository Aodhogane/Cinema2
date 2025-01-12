package com.example.OnlineSinema.controller;

import com.example.OnlineSinema.dto.reviewDTO.ReviewInputDTO;
import com.example.OnlineSinema.dto.reviewDTO.ReviewOutputDTO;
import com.example.OnlineSinema.service.ReviewsService;
import com.example.SinemaContract.VM.cards.BaseViewModel;
import com.example.SinemaContract.VM.form.review.ReviewFormModel;
import com.example.SinemaContract.controllers.domeinController.ReviewsController;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/review")
public class ReviewControllerImpl implements ReviewsController {

    private final ReviewsService reviewsService;

    @Autowired
    public ReviewControllerImpl(ReviewsService reviewsService) {
        this.reviewsService = reviewsService;
    }

    @Override
    @GetMapping("/{id}")
    public String review(@PathVariable int id, Model model) {
        ReviewOutputDTO review = reviewsService.findById(id);
        model.addAttribute("review", review);
        return "review-detail";
    }

    @Override
    @PostMapping
    public String createReview(@Valid @ModelAttribute("review") ReviewFormModel reviewFormModel,
                               BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Validation failed.");
            return "redirect:/film/details/" + reviewFormModel.filmId();
        }

        try {
            ReviewInputDTO reviewInputDTO = mapToInputDTO(reviewFormModel);
            reviewsService.save(reviewInputDTO);
            redirectAttributes.addFlashAttribute("successMessage", "Review created successfully.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "An error occurred while creating the review.");
        }
        return "redirect:/film/details/" + reviewFormModel.filmId();
    }

    private ReviewInputDTO mapToInputDTO(ReviewFormModel reviewFormModel) {
        return new ReviewInputDTO(
                reviewFormModel.filmId(),
                reviewFormModel.userId(),
                reviewFormModel.text(),
                reviewFormModel.rating()
        );
    }

    @Override
    @PostMapping("/delete")
    public String deleteReview(@RequestParam("reviewId") int reviewId, RedirectAttributes redirectAttributes) {
        try {
            reviewsService.deleteById(reviewId);
            redirectAttributes.addFlashAttribute("successMessage", "Review deleted successfully.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "An error occurred while deleting the review.");
        }
        return "redirect:/film/details/";
    }

    @Override
    @GetMapping("/edit/{id}")
    public String editReview(@PathVariable int id, Model model) {
        ReviewOutputDTO review = reviewsService.findById(id);
        model.addAttribute("review", review);
        return "edit-review";
    }

    @Override
    @PostMapping("/update")
    public String updateReview(@RequestParam("id") int id,
                               @Valid @ModelAttribute("updateReview") ReviewFormModel reviewFormModel,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Validation failed.");
            return "redirect:/film/details/" + reviewFormModel.filmId();
        }

        try {
            ReviewInputDTO reviewInputDTO = mapToInputDTO(reviewFormModel);
            redirectAttributes.addFlashAttribute("successMessage", "Review updated successfully.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "An error occurred while updating the review.");
        }
        return "redirect:/film/details/" + reviewFormModel.filmId();
    }

    @Override
    public BaseViewModel createBaseVieModel(String title, UserDetails userDetails) {
        return null;
    }
}
