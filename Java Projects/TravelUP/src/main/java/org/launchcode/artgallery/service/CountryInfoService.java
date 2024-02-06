package org.launchcode.artgallery.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.launchcode.artgallery.data.CountryInfoRepository;
import org.launchcode.artgallery.models.CountryInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Service
public class CountryInfoService {

    @Autowired
    private CountryInfoRepository countryInfoRepository;

    public void fetchDataAndSaveToDatabase(String apiUrl) {
        // Fetch data from the external API using RestTemplate
        String jsonString = new RestTemplate().getForObject(apiUrl, String.class);

        // Parse JSON and save to the database
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode rootNode = objectMapper.readTree(jsonString);

            // Check if the root node is an array
            if (rootNode.isArray()) {
                for (JsonNode countryNode : rootNode) {
                    // Process each country entry
                    if (countryNode.has("name")) {
                        processCountryNode(countryNode);
                    } else {
                        // Handle the case where "name" node is not present
                        System.err.println("Name node not found in JSON response for a country entry.");
                    }
                }
            } else {
                // Process a single country entry
                if (rootNode.has("name")) {
                    processCountryNode(rootNode);
                } else {
                    // Handle the case where "name" node is not present
                    System.err.println("Name node not found in JSON response for a country entry.");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception appropriately
        }
    }

    private void processCountryNode(JsonNode countryNode) {
        // Access the "name" node and extract the common name
        JsonNode nameNode = countryNode.path("name");
        String commonName = nameNode.path("common").asText();

        // Create a CountryInfo object with the common name and save it to the database
        CountryInfo countryInfo = new CountryInfo(commonName);
        countryInfoRepository.save(countryInfo);
    }
}
