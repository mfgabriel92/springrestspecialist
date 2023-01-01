package com.gabriel.springrestspecialist.domain.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gabriel.springrestspecialist.domain.models.City;

public interface CityRepository extends JpaRepository<City, UUID> {
    List<City> findAllByState(String stateName);
}
