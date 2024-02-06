package org.launchcode.artgallery.data;

import org.launchcode.artgallery.models.CountryInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CountryInfoRepository extends CrudRepository<CountryInfo, Long> {
    List<CountryInfo> findByCommonNameContainingIgnoreCase(String query);
    // Add custom queries if needed
    Optional<CountryInfo> findById(Long id);


}
