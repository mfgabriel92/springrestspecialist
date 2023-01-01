package com.gabriel.springrestspecialist.domain.models;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@EqualsAndHashCode
public class Address {
    private String zipCode;
    private String streetName;
    private String streetNumber;
    private String apartmentNumber;
    private String neighborhood;

    @ManyToOne(fetch = FetchType.LAZY)
    private City city;
}