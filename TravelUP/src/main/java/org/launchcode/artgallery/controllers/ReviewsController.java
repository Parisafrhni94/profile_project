package org.launchcode.artgallery.controllers;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.launchcode.artgallery.data.CountryInfoRepository;
import org.launchcode.artgallery.data.ReviewRepository;
import org.launchcode.artgallery.data.WeatherRepository;
import org.launchcode.artgallery.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMethod;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/reviews")
public class ReviewsController {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private CountryInfoRepository countryRepository;

    @Autowired
    private WeatherRepository weatherRepository;

    private List<Integer> getSelectedWeatherIds(Review review) {
        List<Integer> selectedWeatherIds = new ArrayList<>();

        // Assuming that Review has a method getWeather() returning a List of Weather objects
        List<Weather> selectedWeather = review.getWeather();

        if (selectedWeather != null) {
            for (Weather weather : selectedWeather) {
                selectedWeatherIds.add(weather.getId());
            }
        }

        return selectedWeatherIds;
    }

    // Corresponds to http://localhost:8080/artworks
    @GetMapping("")
    public String displayArtworksPage(@RequestParam(required = false) Long countryId,
                                      @RequestParam(required = false) Integer weatherId,
                                      Model model,
                                      HttpSession session) {
        if (countryId != null) {
            Optional<CountryInfo> result = countryRepository.findById(countryId);
            if (result.isPresent()) {
                CountryInfo country = result.get();
                model.addAttribute("reviews", country.getReviews());
            }
        } else if (weatherId != null) {
            Optional<Weather> result;
            result = weatherRepository.findById(weatherId);
            if (result.isPresent()) {
                Weather style = result.get();
                model.addAttribute("reviews", style.getArtworks());
            }
        } else {
            model.addAttribute("reviews", reviewRepository.findAll());
        }
        model.addAttribute("loggedIn", session.getAttribute("user") != null);
        return "reviews/index";
    }

    // Corresponds to http://localhost:8080/artworks/details/1
    @GetMapping("/details/{reviewId}")
    public String displayArtworkDetailsPage(@PathVariable int reviewId, Model model, HttpSession session) {
        model.addAttribute("loggedIn", session.getAttribute("user") != null);
        Optional<Review> result = reviewRepository.findById(reviewId);
        if (result.isPresent()) {
            Review review = result.get();
            model.addAttribute("review", review);
            return "reviews/details";
        } else {
            return "reviews/index";
        }
    }

    // Corresponds to http://localhost:8080/artworks/add
    @GetMapping("/add")
    public String displayAddArtForm(Model model, HttpSession session) {
        List<CountryInfo> countries = (List<CountryInfo>) countryRepository.findAll();
        Collections.sort(countries, new CountryComparator());
        List<Weather> weather = (List<Weather>) weatherRepository.findAll();
        Collections.sort(weather, new WeatherComparator());
        model.addAttribute("review", new Review());
        model.addAttribute("countries", countries);
        model.addAttribute("weathers", weather);
        model.addAttribute("loggedIn", session.getAttribute("user") != null);
        return "reviews/add";
    }

    @PostMapping("/add")
    public String processAddArtForm(@ModelAttribute @Valid Review review,
                                    Errors errors,
                                    @RequestParam(required = false) List<Integer> weatherIds,
                                    Model model) {
        if (errors.hasErrors()) {
            System.out.println(errors.getAllErrors());
            List<CountryInfo> countries = (List<CountryInfo>) countryRepository.findAll();
            Collections.sort(countries, new CountryComparator());
            List<Weather> weather = (List<Weather>) weatherRepository.findAll();
            Collections.sort(weather, new WeatherComparator());
            model.addAttribute("review", review);
            model.addAttribute("countries", countries);
            model.addAttribute("weathers", weather);
            return "reviews/add";
        } else {
            if (weatherIds != null) {
                List<Weather> selectedweathers = (List<Weather>) weatherRepository.findAllById(weatherIds);
                review.setWeather(selectedweathers);
            }
            reviewRepository.save(review);
            return "redirect:/reviews";
        }
    }


    @GetMapping("/update/{reviewId}")
    public String displayEditArtForm(@PathVariable("reviewId") int reviewId, Model model, HttpSession session) {
        Optional<Review> result = reviewRepository.findById(reviewId);
        if (result.isPresent()) {
            Review review = result.get();
            List<CountryInfo> countries = (List<CountryInfo>) countryRepository.findAll();
            Collections.sort(countries, new CountryComparator());
            List<Weather> weather = (List<Weather>) weatherRepository.findAll();
            Collections.sort(weather, new WeatherComparator());

            // Retrieve selected weather IDs for the current review
            List<Integer> selectedWeatherIds = getSelectedWeatherIds(review);

            model.addAttribute("review", review);
            model.addAttribute("countries", countries);
            model.addAttribute("weathers", weather);
            model.addAttribute("selectedWeatherIds", selectedWeatherIds);
            model.addAttribute("loggedIn", session.getAttribute("user") != null);
            return "reviews/update";
        } else {
            return "redirect:/reviews";
        }
    }


    @PostMapping("/update")
    public String processEditArtForm(@RequestParam int id,
                                     @ModelAttribute @Valid Review review,
                                     Errors errors,
                                     @RequestParam(required = false) List<Integer> weatherIds,
                                     Model model) {
        if (errors.hasErrors()) {
            System.out.println(errors.getAllErrors());
            List<CountryInfo> countries = (List<CountryInfo>) countryRepository.findAll();
            Collections.sort(countries, new CountryComparator());
            List<Weather> weather = (List<Weather>) weatherRepository.findAll();
            Collections.sort(weather, new WeatherComparator());
            model.addAttribute("review", review);
            model.addAttribute("countries", countries);
            model.addAttribute("weathers", weather);
            return "reviews/update";
        } else {
            Optional<Review> existingReview = reviewRepository.findById(id);
            if (existingReview.isPresent()) {
                Review updatedReview = existingReview.get();
                // Update the fields of the existing review with the new values
                updatedReview.setTitle(review.getTitle());
                updatedReview.setCity(review.getCity());
                updatedReview.setRate(review.getRate());
                updatedReview.setComment(review.getComment());
                // Update other fields accordingly

                if (weatherIds != null) {
                    List<Weather> selectedWeathers = (List<Weather>) weatherRepository.findAllById(weatherIds);
                    updatedReview.setWeather(selectedWeathers);
                }

                reviewRepository.save(updatedReview);
                return "redirect:/reviews";
            } else {
                return "redirect:/reviews";
            }
        }
    }




    // Corresponds to http://localhost:8080/artworks/delete
    @GetMapping("/delete")
    public String displayDeleteArtForm(Model model, HttpSession session) {
        model.addAttribute("reviews", reviewRepository.findAll());
        model.addAttribute("loggedIn", session.getAttribute("user") != null);
        return "reviews/delete";
    }

    @PostMapping("/delete")
    public String processDeleteArtForm(@RequestParam(required = false) int[] reviewIds) {
        for (int id : reviewIds) {
            reviewRepository.deleteById(id);
        }
        return "redirect:/reviews";
    }

}


