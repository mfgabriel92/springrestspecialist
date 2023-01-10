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
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "restaurants")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class Restaurant extends BaseEntity {
    @NotBlank
    private String name;

    @NotNull
    @PositiveOrZero
    private BigDecimal shippingRate;

    private Boolean isActive = false;

    private Boolean isOpen = false;

    @Valid
    @NotNull
    @ManyToOne
    private Cuisine cuisine;

    @JsonIgnore
    @Embedded
    private Address address;

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "restaurants_payment_methods", joinColumns = @JoinColumn(name = "restaurant_id"), inverseJoinColumns = @JoinColumn(name = "payment_method_id"))
    private List<PaymentMethod> paymentMethods = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "restaurant")
    private List<Product> products = new ArrayList<>();
}
