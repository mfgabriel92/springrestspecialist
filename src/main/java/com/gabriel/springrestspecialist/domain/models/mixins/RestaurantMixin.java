package com.gabriel.springrestspecialist.domain.models.mixins;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.gabriel.springrestspecialist.domain.models.Address;
import com.gabriel.springrestspecialist.domain.models.Cuisine;
import com.gabriel.springrestspecialist.domain.models.PaymentMethod;
import com.gabriel.springrestspecialist.domain.models.Product;

public abstract class RestaurantMixin {
    @JsonIgnoreProperties(value = "name", allowGetters = true)
    private Cuisine cuisine;

    @JsonIgnore
    private Address address;

    @JsonIgnore
    private List<PaymentMethod> paymentMethods = new ArrayList<>();

    @JsonIgnore
    private List<Product> products = new ArrayList<>();
}
