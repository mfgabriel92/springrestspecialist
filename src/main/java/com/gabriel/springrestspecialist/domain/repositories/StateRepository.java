package com.gabriel.springrestspecialist.domain.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gabriel.springrestspecialist.domain.models.State;

public interface StateRepository extends JpaRepository<State, UUID> {
}
