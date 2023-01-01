package com.gabriel.springrestspecialist.api.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gabriel.springrestspecialist.domain.exceptions.DomainException;
import com.gabriel.springrestspecialist.domain.exceptions.EntityNotFoundException;
import com.gabriel.springrestspecialist.domain.models.City;
import com.gabriel.springrestspecialist.domain.services.CityService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/cities")
@RequiredArgsConstructor
public class CityController {
    @Autowired
    private CityService service;

    @GetMapping
    public ResponseEntity<List<City>> findAll() {
        List<City> cities = service.findAll();
        return ResponseEntity.ok(cities);
    }

    @GetMapping("by-state")
    public ResponseEntity<List<City>> findAllByState(@RequestParam String stateName) {
        List<City> cities = service.findAllByState(stateName);
        return ResponseEntity.ok(cities);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> findById(@PathVariable UUID id) {
        City city = service.findById(id);
        return ResponseEntity.ok(city);
    }

    @PostMapping
    public ResponseEntity<City> save(@RequestBody City city) {
        try {
            City newCity = service.save(city);
            return ResponseEntity.status(HttpStatus.CREATED).body(newCity);
        } catch (EntityNotFoundException e) {
            throw new DomainException(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<City> save(@PathVariable UUID id, @RequestBody City city) {
        City updatedCity = service.findById(id);
        BeanUtils.copyProperties(city, updatedCity, "id", "createdAt");

        try {
            service.save(updatedCity);
            return ResponseEntity.ok(updatedCity);
        } catch (EntityNotFoundException e) {
            throw new DomainException(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteById(@PathVariable UUID id) {
        service.remove(id);
        return ResponseEntity.noContent().build();
    }
}
