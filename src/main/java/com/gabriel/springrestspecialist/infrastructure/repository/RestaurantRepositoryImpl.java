package com.gabriel.springrestspecialist.infrastructure.repository;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;

import com.gabriel.springrestspecialist.domain.models.Cuisine;
import com.gabriel.springrestspecialist.domain.models.Restaurant;
import com.gabriel.springrestspecialist.domain.repositories.RestaurantRepository;
import com.gabriel.springrestspecialist.domain.repositories.RestaurantRepositoryQuery;
import com.gabriel.springrestspecialist.infrastructure.specifications.RestaurantSpecs;

@Repository
public class RestaurantRepositoryImpl implements RestaurantRepositoryQuery {
    @PersistenceContext
    private EntityManager manager;

    @Autowired
    @Lazy
    private RestaurantRepository repository;

    @Override
    public List<Restaurant> findByNameAndShippingRates(String name, BigDecimal lowestShippingRate,
        BigDecimal highestShippingRate) {
        String jpql = """
                FROM Restaurant
                WHERE name LIKE :name
                AND shippingRate BETWEEN :lowestShippingRate AND :highestShippingRate
            """;

        TypedQuery<Restaurant> query = manager.createQuery(jpql, Restaurant.class)
            .setParameter("name", "%" + name + "%")
            .setParameter("lowestShippingRate", lowestShippingRate)
            .setParameter("highestShippingRate", highestShippingRate);

        return query.getResultList();
    }

    @Override
    public List<Restaurant> findByCuisineName(String cuisineName) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Restaurant> criteria = builder.createQuery(Restaurant.class);
        Root<Restaurant> restaurant = criteria.from(Restaurant.class);
        Join<Restaurant, Cuisine> cuisine = restaurant.join("cuisine");

        Predicate cuisineNamePredicate = builder.like(cuisine.get("name"), "%" + cuisineName + "%");
        criteria.where(cuisineNamePredicate);

        TypedQuery<Restaurant> query = manager.createQuery(criteria);
        return query.getResultList();
    }

    @Override
    public List<Restaurant> findAllWithShippingRates() {
        return repository.findAll(RestaurantSpecs.withShippingRates());
    }
}
