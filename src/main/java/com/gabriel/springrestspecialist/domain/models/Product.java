package com.gabriel.springrestspecialist.domain.models;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "products")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class Product extends BaseEntity {
    private String name;
    private String description;
    private BigDecimal price;
    private Boolean isActive;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Restaurant restaurant;
}
