package com.gabriel.springrestspecialist.domain.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gabriel.springrestspecialist.domain.models.Cuisine;

public interface CuisineRepository extends JpaRepository<Cuisine, UUID> {
    Optional<Cuisine> findOneByName(String name);
}
