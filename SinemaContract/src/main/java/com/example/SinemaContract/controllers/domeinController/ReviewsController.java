package com.example.SinemaContract.controllers.domeinController;

import com.example.SinemaContract.VM.form.review.ReviewFormModel;
import com.example.SinemaContract.controllers.BaseController;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/review")
public interface ReviewsController extends BaseController {

    @GetMapping("/{id}")
    String review(@PathVariable int id, Model model);

    @PostMapping
    String createReview(@Valid @ModelAttribute("review") ReviewFormModel reviewFormModel,
                        BindingResult bindingResult, RedirectAttributes redirectAttributes);

    @PostMapping("/delete")
    String deleteReview(@RequestParam("reviewId") int reviewId, RedirectAttributes redirectAttributes);

    @PostMapping
    String updateReview(@ModelAttribute("updateReview") int id, ReviewFormModel reviewFormModel, BindingResult bindingResult,
                        RedirectAttributes redirectAttributes);

}
