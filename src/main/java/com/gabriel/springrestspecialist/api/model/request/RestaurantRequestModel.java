package com.gabriel.springrestspecialist.api.model.request;

import java.math.BigDecimal;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestaurantRequestModel {
    @NotBlank
    private String name;

    @NotNull
    @PositiveOrZero
    private BigDecimal shippingRate;

    @Valid
    @NotBlank
    private CuisineRequestModel cuisine;
}
