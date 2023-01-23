package com.gabriel.springrestspecialist.domain.models;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.gabriel.springrestspecialist.core.validation.annotations.FreeShipping;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "restaurants")
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@FreeShipping(field = "shippingRate", description = "name", requiredDescription = "[Free Shipping] - ")
public class Restaurant extends BaseEntity {
    private String name;

    private BigDecimal shippingRate;
    private Boolean isActive = false;
    private Boolean isOpen = false;

    @ManyToOne
    private Cuisine cuisine;

    @Embedded
    private Address address;

    @ManyToMany
    @JoinTable(name = "restaurants_payment_methods", joinColumns = @JoinColumn(name = "restaurant_id"), inverseJoinColumns = @JoinColumn(name = "payment_method_id"))
    private List<PaymentMethod> paymentMethods = new ArrayList<>();

    @OneToMany(mappedBy = "restaurant")
    private List<Product> products = new ArrayList<>();
}
