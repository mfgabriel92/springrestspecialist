package com.gabriel.springrestspecialist.api.model.request;

import java.math.BigDecimal;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.gabriel.springrestspecialist.core.validation.annotations.FreeShipping;
import com.gabriel.springrestspecialist.core.validation.annotations.ShippingRate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@FreeShipping(field = "shippingRate", description = "name", requiredDescription = "[Free Shipping] - ")
public class RestaurantRequestModel {
    @NotBlank
    private String name;

    @ShippingRate
    private BigDecimal shippingRate;

    @Valid
    @NotNull
    private CuisineRequestModel cuisine;
}
