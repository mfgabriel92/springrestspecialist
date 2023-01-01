package com.gabriel.springrestspecialist.api.controllers;

import java.math.BigDecimal;
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
import com.gabriel.springrestspecialist.domain.models.Restaurant;
import com.gabriel.springrestspecialist.domain.services.RestaurantService;

@RestController
@RequestMapping("/api/v1/restaurants")
public class RestaurantController {
    @Autowired
    private RestaurantService service;

    @GetMapping
    public ResponseEntity<List<Restaurant>> findAll() {
        List<Restaurant> restaurants = service.findAll();
        return ResponseEntity.ok(restaurants);
    }

    @GetMapping("by-no-shipping-rates")
    public ResponseEntity<List<Restaurant>> findAllWithNoShippingRate() {
        List<Restaurant> restaurants = service.findAllWithNoShippingRates();
        return ResponseEntity.ok(restaurants);
    }

    @GetMapping("by-name-and-shipping-rates")
    public ResponseEntity<List<Restaurant>> findByNameAndShippingRates(
        @RequestParam String name,
        @RequestParam BigDecimal lowestShippingRate,
        @RequestParam BigDecimal highestShippingRate) {
        List<Restaurant> restaurants = service.findByNameAndShippingRates(name, lowestShippingRate,
            highestShippingRate);
        return ResponseEntity.ok(restaurants);
    }

    @GetMapping("by-cuisine")
    public ResponseEntity<?> findByCuisineName(@RequestParam String cuisineName) {
        List<Restaurant> restaurants = service.findByCuisineName(cuisineName);
        return ResponseEntity.ok(restaurants);
    }

    @GetMapping("by-with-shipping-rates")
    public ResponseEntity<List<Restaurant>> findAllWithShippingRates() {
        List<Restaurant> restaurants = service.findAllWithShippingRates();
        return ResponseEntity.ok(restaurants);
    }

    @GetMapping("latest")
    public ResponseEntity<Restaurant> findLatest() {
        Restaurant restaurant = service.findLatest();
        return ResponseEntity.ok(restaurant);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> findById(@PathVariable UUID id) {
        Restaurant restaurant = service.findById(id);
        return ResponseEntity.ok(restaurant);
    }

    @PostMapping
    public ResponseEntity<Restaurant> save(@RequestBody Restaurant restaurant) {
        try {
            Restaurant newCity = service.save(restaurant);
            return ResponseEntity.status(HttpStatus.CREATED).body(newCity);
        } catch (EntityNotFoundException e) {
            throw new DomainException(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<Restaurant> save(@PathVariable UUID id, @RequestBody Restaurant restaurant) {
        Restaurant updatedRestaurant = service.findById(id);
        BeanUtils.copyProperties(restaurant, updatedRestaurant, "id", "paymentMethods", "address", "products",
            "createdAt");

        try {
            service.save(updatedRestaurant);
            return ResponseEntity.ok(updatedRestaurant);
        } catch (EntityNotFoundException e) {
            throw new DomainException(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteById(@PathVariable UUID id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
