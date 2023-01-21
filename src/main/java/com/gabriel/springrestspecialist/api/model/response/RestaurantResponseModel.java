package com.gabriel.springrestspecialist.api.model.response;

import java.math.BigDecimal;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestaurantResponseModel {
    private UUID id;
    private String name;
    private BigDecimal shippingRate;
    private CuisineResponseModel cuisine;
}
