package com.gabriel.springrestspecialist.domain.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gabriel.springrestspecialist.domain.exceptions.EntityInUseException;
import com.gabriel.springrestspecialist.domain.exceptions.EntityNotFoundException;
import com.gabriel.springrestspecialist.domain.models.Cuisine;
import com.gabriel.springrestspecialist.domain.models.Restaurant;
import com.gabriel.springrestspecialist.domain.repositories.RestaurantRepository;

@Service
public class RestaurantService {
    private static final String RESTAURANT_NOT_FOUND = "The restaurant '%s' cannot be found";
    private static final String RESTAURANT_IN_USE = "The restaurant '%s' cannot be removed because it is in use";

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private CuisineService cuisineService;

    public List<Restaurant> findAll() {
        return restaurantRepository.findAll();
    }

    public List<Restaurant> findAllWithNoShippingRates() {
        return restaurantRepository.findAllWithNoShippingRates();
    }

    public List<Restaurant> findByNameAndShippingRates(String name, BigDecimal lowestShippingRate,
        BigDecimal highestShippingRate) {
        return restaurantRepository.findByNameAndShippingRates(name, lowestShippingRate, highestShippingRate);
    }

    public List<Restaurant> findByCuisineName(String cuisineName) {
        return restaurantRepository.findByCuisineName(cuisineName);
    }

    public List<Restaurant> findAllWithShippingRates() {
        return restaurantRepository.findAllWithNoShippingRates();
    }

    public Restaurant findById(UUID id) {
        return restaurantRepository.findById(id).orElseThrow(() -> {
            String message = String.format(RESTAURANT_NOT_FOUND, id);
            throw new EntityNotFoundException(message);
        });
    }

    public Restaurant findLatest() {
        return restaurantRepository.findLatest().get();
    }

    @Transactional
    public Restaurant save(Restaurant restaurant) {
        try {
            UUID cuisineId = restaurant.getCuisine().getId();
            Cuisine cuisine = cuisineService.findById(cuisineId);

            restaurant.setCuisine(cuisine);

            return restaurantRepository.save(restaurant);
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException(e.getMessage());
        }
    }

    @Transactional
    public void deleteById(UUID id) {
        try {
            restaurantRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            String message = String.format(RESTAURANT_NOT_FOUND, id);
            throw new EntityNotFoundException(message);
        } catch (DataIntegrityViolationException e) {
            String message = String.format(RESTAURANT_IN_USE, id);
            throw new EntityInUseException(message);
        }
    }
}
