package com.gabriel.springrestspecialist.domain.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "cuisines")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class Cuisine extends BaseEntity {
    private String name;

    @OneToMany(mappedBy = "cuisine")
    private List<Restaurant> restaurants = new ArrayList<>();
}
