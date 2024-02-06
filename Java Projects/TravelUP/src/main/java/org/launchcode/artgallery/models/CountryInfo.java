package org.launchcode.artgallery.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class CountryInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String commonName; // CX for Christmas Island



    @OneToMany(mappedBy = "country")
    @JsonBackReference
    private final List<Review> reviews = new ArrayList<>();



// Add other fields as needed

    public Long getId() {
        return id;
    }

    public CountryInfo(String commonName) {
        this.id = id;
        this.commonName = commonName;

    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    @Override
    public String toString() {
        return "CountryInfo{" +
                "id=" + id +
                ", commonName='" + commonName + '\'' +
                '}';
    }

    public CountryInfo() {
    }



    public String getCommonName() {
        return commonName;
    }

    public void setCommonName(String commonName) {
        this.commonName = commonName;
    }
}
