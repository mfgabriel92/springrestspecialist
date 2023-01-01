package com.gabriel.springrestspecialist.infrastructure.specifications;

import java.math.BigDecimal;

import org.springframework.data.jpa.domain.Specification;

import com.gabriel.springrestspecialist.domain.models.Restaurant;

public class RestaurantSpecs {
    public static Specification<Restaurant> withShippingRates() {
        return (root, query, builder) -> builder.notEqual(root.get("shippingRate"), BigDecimal.ZERO);
    }
}
