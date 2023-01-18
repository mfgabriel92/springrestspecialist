package com.gabriel.springrestspecialist.domain.models;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gabriel.springrestspecialist.api.core.validation.annotations.FreeShipping;
import com.gabriel.springrestspecialist.api.core.validation.annotations.Multiply;
import com.gabriel.springrestspecialist.infrastructure.groups.ConstraintGroup;

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
    @NotNull
    @Id
    @GeneratedValue(generator = "UUID", strategy = GenerationType.AUTO)
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @EqualsAndHashCode.Include
    public UUID id = UUID.randomUUID();

    @NotBlank
    private String name;

    @NotNull
    // @ShippingFee
    @Multiply(number = 4)
    private BigDecimal shippingRate;

    private Boolean isActive = false;

    private Boolean isOpen = false;

    @ConvertGroup(from = Default.class, to = ConstraintGroup.CreateRestaurant.class)
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
