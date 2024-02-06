package org.launchcode.artgallery.controllers;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.launchcode.artgallery.data.CountryInfoRepository;
import org.launchcode.artgallery.models.CountryInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/countries")
public class CountriesController {

    @Autowired
    private CountryInfoRepository countryInfoRepository;

    // Corresponds to http://localhost:8080/artists
    @GetMapping
    public String displayArtistsPage(@RequestParam(name = "search", required = false) String search,
                                     Model model, HttpSession session) {
        List<CountryInfo> countries;

        if (search != null && !search.isEmpty()) {
            // Perform search if the 'search' parameter is present
            countries = countryInfoRepository.findByCommonNameContainingIgnoreCase(search);
        } else {
            // Otherwise, fetch all countries
            countries = (List<CountryInfo>) countryInfoRepository.findAll();
        }

        model.addAttribute("countries", countries);
        model.addAttribute("loggedIn", session.getAttribute("user") != null);
        return "/countries/index";
    }

    // Corresponds to http://localhost:8080/artists/add
    @GetMapping("/add")
    public String displayAddArtistForm(Model model, HttpSession session) {
        model.addAttribute("loggedIn", session.getAttribute("user") != null);
        model.addAttribute("country", new CountryInfo());
        return "countries/add";
    }

    @PostMapping("/add")
    public String processAddArtistForm(@ModelAttribute @Valid CountryInfo artist, Errors errors) {
        if (errors.hasErrors()) {
            return "countries/add";
        } else {
            countryInfoRepository.save(artist);
            return "redirect:/countries";
        }
    }

    //search methods

}
