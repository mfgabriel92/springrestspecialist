package com.gabriel.springrestspecialist.domain.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "order_items")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class Order extends BaseEntity {
    private BigDecimal subtotal;
    private BigDecimal shippingFee;
    private BigDecimal grandtotal;

    @Embedded
    private Address address;
    private OrderStatus status;

    @ManyToOne
    private Restaurant restaurant;

    @ManyToOne
    private PaymentMethod paymentMethod;

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> items = new ArrayList<>();
    private LocalDateTime confirmedAt;
    private LocalDateTime deliveredAt;
    private LocalDateTime canceledAt;
}
