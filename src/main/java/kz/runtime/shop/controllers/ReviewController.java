package kz.runtime.shop.controllers;

import kz.runtime.shop.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ReviewController {
    @Autowired
    private ReviewService reviewService;
    @PostMapping("/products/{id}/details")
    public String addReview(@PathVariable(value = "id") Long id, String text, String score) {

        if (score==null || score.isEmpty() || text.isEmpty()) {
            return "redirect:/products/{id}/details?emptyParam=true";
        }

        long scoreLong = Long.parseLong(score);
        reviewService.addReview(id, text, scoreLong);

        return "redirect:/products/{id}/details";
    }
}

