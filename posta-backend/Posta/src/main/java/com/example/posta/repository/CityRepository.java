package com.example.posta.repository;

import com.example.posta.model.City;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<City, Long> {
    City findByPostalCode(String postalCode);
}
