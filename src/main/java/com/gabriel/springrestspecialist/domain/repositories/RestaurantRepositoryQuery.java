package com.gabriel.springrestspecialist.domain.repositories;

import java.math.BigDecimal;
import java.util.List;

import com.gabriel.springrestspecialist.domain.models.Restaurant;

public interface RestaurantRepositoryQuery {
    List<Restaurant> findByNameAndShippingRates(String name, BigDecimal lowestShippingRate,
        BigDecimal highestShippingRate);

    List<Restaurant> findByCuisineName(String cuisineName);

    List<Restaurant> findAllWithShippingRates();
}
