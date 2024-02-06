package org.launchcode.artgallery.controllers;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.launchcode.artgallery.data.WeatherRepository;
import org.launchcode.artgallery.models.Weather;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/weathers")
public class WeatherController {

    @Autowired
    private WeatherRepository weathereRepository;

    // Corresponds to http://localhost:8080/styles
    @GetMapping
    public String displayStylesPage(Model model, HttpSession session) {
        model.addAttribute("weathers", weathereRepository.findAll());
        model.addAttribute("loggedIn", session.getAttribute("user") != null);
        return "/weathers/index";
    }

    // Corresponds to http://localhost:8080/styles/add
    @GetMapping("/add")
    public String displayAddStyleForm(Model model, HttpSession session) {
        model.addAttribute("weather", new Weather());
        model.addAttribute("loggedIn", session.getAttribute("user") != null);
        return "weathers/add";
    }

    @PostMapping("/add")
    public String processAddStyleForm(@ModelAttribute @Valid Weather weather, Errors errors) {
        if (errors.hasErrors()) {
            return "weathers/add";
        } else {
            weathereRepository.save(weather);
            return "redirect:/weathers";
        }
    }
}
