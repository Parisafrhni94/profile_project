package org.launchcode.artgallery.data;


import org.launchcode.artgallery.models.Weather;
import org.springframework.data.repository.CrudRepository;

public interface WeatherRepository extends CrudRepository<Weather, Integer> {
}
