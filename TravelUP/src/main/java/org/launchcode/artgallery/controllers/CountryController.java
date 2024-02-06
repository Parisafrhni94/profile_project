package org.launchcode.artgallery.controllers;

import org.launchcode.artgallery.service.CountryInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/country")
public class CountryController {

    private final CountryInfoService countryInfoService;

    @Autowired
    public CountryController(CountryInfoService countryInfoService) {
        this.countryInfoService = countryInfoService;
    }

    @GetMapping("/save")
    public void saveCountryInfo() {
        // Fetch data from the external API
        // Note: You might want to move this logic to a separate method in CountryInfoService
        // and call that method here.
        String apiUrl = "https://restcountries.com/v3.1/all"; // Replace with your API URL
        countryInfoService.fetchDataAndSaveToDatabase(apiUrl);
    }
}