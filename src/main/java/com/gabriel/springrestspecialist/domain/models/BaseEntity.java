package com.gabriel.springrestspecialist.domain.models;

import java.time.OffsetDateTime;

import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
@EqualsAndHashCode
public abstract class BaseEntity {
    @CreationTimestamp
    public OffsetDateTime createdAt;

    @UpdateTimestamp
    public OffsetDateTime updatedAt;
}
