package com.gabriel.springrestspecialist.domain.models;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "order_items")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class OrderItem extends BaseEntity {
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;
    private String observation;

    @ManyToOne
    private Order order;

    @ManyToOne
    private Product product;
}
