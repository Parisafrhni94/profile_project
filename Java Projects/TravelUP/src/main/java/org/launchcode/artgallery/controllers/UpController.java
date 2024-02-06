package org.launchcode.artgallery.controllers;

import jakarta.servlet.http.HttpSession;
import org.launchcode.artgallery.data.ReviewRepository;
import org.launchcode.artgallery.models.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.nio.file.FileStore;
import java.util.List;

@Controller
public class UpController {

    @Autowired
    private ReviewRepository reviewRepository;

    // Corresponds to http://localhost:8080
    @GetMapping("/")
    public String redirectToHomePage(Model model, HttpSession session) {
        List<Review> topReviews = reviewRepository.findTop8ByOrderByRateDesc();
        model.addAttribute("headingText", "Welcome to TravelUp");

        model.addAttribute("loggedIn", session.getAttribute("user") != null);
        model.addAttribute("topReviews", topReviews);

        return "index";
    }

    @GetMapping("/welcome")
    public String displayHomePage(Model model, HttpSession session) {
        List<Review> topReviews = reviewRepository.findTop8ByOrderByRateDesc();

        model.addAttribute("headingText", "Welcome");
        model.addAttribute("loggedIn", session.getAttribute("user") != null);
        model.addAttribute("topReviews", topReviews);

        return "index";
    }
}
