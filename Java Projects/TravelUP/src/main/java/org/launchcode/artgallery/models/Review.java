package org.launchcode.artgallery.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Entity
public class Review extends AbstractEntity{

    @NotBlank(message = "Title is required.")
    private String title;

    @NotBlank(message = "City name is required.")
    private String city;

    @NotBlank(message = "Comment is required.")
    private String comment;

    @NotNull(message = "Rate is required.")
    @Min(value = 1, message = "Rate must be at least 1")
    @Max(value = 5, message = "Rate must be at most 5")
    private int rate;


    @ManyToOne
    @NotNull(message = "Country name is required.")
    @JsonManagedReference
    private CountryInfo country;

    @ManyToMany
    @JsonManagedReference
    private List<Weather> weather;


    public Review() {
    }

    public Review(String title, String city, String comment, int rate, CountryInfo country, List<Weather> weather) {
        this.title = title;
        this.city = city;
        this.comment = comment;
        this.rate = rate;
        this.country = country;
        this.weather = weather;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public CountryInfo getCountry() {
        return country;
    }

    public void setCountry(CountryInfo country) {
        this.country = country;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public void setWeather(List<Weather> weather) {
        this.weather = weather;
    }

    @Override
    public String toString() {
        return "Review{" +
                "title='" + title + '\'' +
                ", city='" + city + '\'' +
                ", comment='" + comment + '\'' +
                ", rate=" + rate +
                ", country=" + country +
                ", weather=" + weather +
                '}';
    }

    @JsonIgnore
    public String getFormattedStyles() {
        StringBuilder styleNames = new StringBuilder("");
        for (int i=0; i < weather.size(); i++) {
            styleNames.append(weather.get(i).getName());
            if (i < weather.size() - 1) {
                styleNames.append(", ");
            }
        }
        return styleNames.toString();
    }
}
