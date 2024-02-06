package org.launchcode.artgallery.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Weather extends AbstractEntity{

    @NotBlank(message = "Name of style is required.")
    private String name;

    @ManyToMany(mappedBy = "weather")
    @JsonBackReference
    private final List<Review> artworks = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Review> getArtworks() {
        return artworks;
    }
}
