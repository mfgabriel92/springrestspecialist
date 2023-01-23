package com.gabriel.springrestspecialist.api.controllers;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.SmartValidator;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gabriel.springrestspecialist.api.exceptions.ValidationException;
import com.gabriel.springrestspecialist.api.mapper.RestaurantMapper;
import com.gabriel.springrestspecialist.api.model.request.RestaurantRequestModel;
import com.gabriel.springrestspecialist.api.model.response.RestaurantResponseModel;
import com.gabriel.springrestspecialist.domain.exceptions.DomainException;
import com.gabriel.springrestspecialist.domain.exceptions.EntityNotFoundException;
import com.gabriel.springrestspecialist.domain.models.Restaurant;
import com.gabriel.springrestspecialist.domain.services.RestaurantService;

@RestController
@RequestMapping("/api/v1/restaurants")
public class RestaurantController {
    @Autowired
    private RestaurantService service;

    @Autowired
    private SmartValidator validator;

    @Autowired
    private RestaurantMapper mapper;

    @GetMapping
    public ResponseEntity<List<RestaurantResponseModel>> findAll() {
        List<Restaurant> restaurants = service.findAll();
        return ResponseEntity.ok(toCollectionModel(restaurants));
    }

    @GetMapping("by-no-shipping-rates")
    public ResponseEntity<List<RestaurantResponseModel>> findAllWithNoShippingRate() {
        List<Restaurant> restaurants = service.findAllWithNoShippingRates();
        return ResponseEntity.ok(toCollectionModel(restaurants));
    }

    @GetMapping("by-name-and-shipping-rates")
    public ResponseEntity<List<RestaurantResponseModel>> findByNameAndShippingRates(
            @RequestParam String name,
            @RequestParam BigDecimal lowestShippingRate,
            @RequestParam BigDecimal highestShippingRate) {
        List<Restaurant> restaurants = service.findByNameAndShippingRates(
                name,
                lowestShippingRate,
                highestShippingRate);
        return ResponseEntity.ok(toCollectionModel(restaurants));
    }

    @GetMapping("by-cuisine")
    public ResponseEntity<List<RestaurantResponseModel>> findByCuisineName(@RequestParam String cuisineName) {
        List<Restaurant> restaurants = service.findByCuisineName(cuisineName);
        return ResponseEntity.ok(toCollectionModel(restaurants));
    }

    @GetMapping("by-with-shipping-rates")
    public ResponseEntity<List<RestaurantResponseModel>> findAllWithShippingRates() {
        List<Restaurant> restaurants = service.findAllWithShippingRates();
        return ResponseEntity.ok(toCollectionModel(restaurants));
    }

    @GetMapping("latest")
    public ResponseEntity<RestaurantResponseModel> findLatest() {
        Restaurant restaurant = service.findLatest();
        return ResponseEntity.ok(mapper.toModel(restaurant));
    }

    @GetMapping("{id}")
    public ResponseEntity<RestaurantResponseModel> findById(@PathVariable UUID id) {
        Restaurant restaurant = service.findById(id);
        return ResponseEntity.ok(mapper.toModel(restaurant));
    }

    @PostMapping
    public ResponseEntity<RestaurantResponseModel> save(@RequestBody @Valid RestaurantRequestModel request) {
        try {
            Restaurant restaurant = service.save(mapper.toDomainObject(request));
            return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toModel(restaurant));
        } catch (EntityNotFoundException e) {
            throw new DomainException(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<RestaurantResponseModel> save(@PathVariable UUID id,
            @RequestBody RestaurantRequestModel request) {
        Restaurant updatedRestaurant = service.findById(id);

        validate(updatedRestaurant);

        BeanUtils.copyProperties(request, updatedRestaurant, "id", "paymentMethods", "address", "products",
                "createdAt");

        try {
            service.save(mapper.toDomainObject(request));
            return ResponseEntity.ok(mapper.toModel(updatedRestaurant));
        } catch (EntityNotFoundException e) {
            throw new DomainException(e.getMessage());
        } catch (ValidationException e) {
            throw new DomainException(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteById(@PathVariable UUID id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private void validate(Restaurant restaurant) {
        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(restaurant, "restaurants");
        validator.validate(restaurant, bindingResult);

        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult);
        }
    }

    private List<RestaurantResponseModel> toCollectionModel(List<Restaurant> restaurants) {
        List<RestaurantResponseModel> response = mapper.toCollectionModel(restaurants);
        return response;
    }
}
