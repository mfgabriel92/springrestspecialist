package com.gabriel.springrestspecialist.api.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.gabriel.springrestspecialist.domain.models.State;
import com.gabriel.springrestspecialist.domain.services.StateService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/states")
@RequiredArgsConstructor
public class StateController {
    @Autowired
    private StateService service;

    @GetMapping
    public ResponseEntity<List<State>> findAll() {
        List<State> states = service.findAll();
        return ResponseEntity.ok(states);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> findById(@PathVariable UUID id) {
        State state = service.findById(id);
        return ResponseEntity.ok(state);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public State save(@RequestBody State state) {
        return service.save(state);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> save(@PathVariable UUID id, @RequestBody State state) {
        State updatedState = service.save(id, state);
        return ResponseEntity.ok(updatedState);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteById(@PathVariable UUID id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
