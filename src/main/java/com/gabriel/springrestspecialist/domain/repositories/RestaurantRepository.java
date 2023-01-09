package com.gabriel.springrestspecialist.domain.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.gabriel.springrestspecialist.domain.models.Restaurant;

public interface RestaurantRepository
    extends CustomJpaRepository<Restaurant, UUID>,
    RestaurantRepositoryQuery,
    JpaSpecificationExecutor<Restaurant> {

    @Query("FROM Restaurant WHERE shippingRate = 0")
    List<Restaurant> findAllWithNoShippingRates();

    @Query("SELECT DISTINCT r FROM Restaurant r JOIN FETCH r.cuisine LEFT JOIN FETCH r.paymentMethods")
    List<Restaurant> findAll();
}
