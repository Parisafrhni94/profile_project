package org.launchcode.artgallery.data;

import org.launchcode.artgallery.models.Review;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ReviewRepository extends CrudRepository<Review, Integer> {
    List<Review> findTop8ByOrderByRateDesc();
}
