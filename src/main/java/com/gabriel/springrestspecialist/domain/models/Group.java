package com.gabriel.springrestspecialist.domain.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "groups")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class Group extends BaseEntity {
    private String name;

    @ManyToMany
    @JoinTable(name = "groups_permissions", joinColumns = @JoinColumn(name = "group_id"), inverseJoinColumns = @JoinColumn(name = "permission_id"))
    private List<Permission> permissions = new ArrayList<>();
}
