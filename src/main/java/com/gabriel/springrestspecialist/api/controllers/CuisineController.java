package com.gabriel.springrestspecialist.api.controllers;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.gabriel.springrestspecialist.api.mapper.CuisineMapper;
import com.gabriel.springrestspecialist.api.model.response.CuisineResponseModel;
import com.gabriel.springrestspecialist.domain.models.Cuisine;
import com.gabriel.springrestspecialist.domain.services.CuisineService;

@RestController
@RequestMapping("/api/v1/cuisines")
public class CuisineController {
    @Autowired
    private CuisineService service;

    @Autowired
    private CuisineMapper mapper;

    @GetMapping
    public ResponseEntity<List<CuisineResponseModel>> findAll() {
        List<Cuisine> cuisines = service.findAll();
        return ResponseEntity.ok(mapper.toCollectionModel(cuisines));
    }

    @GetMapping("{id}")
    public ResponseEntity<CuisineResponseModel> findById(@PathVariable UUID id) {
        Cuisine cuisine = service.findById(id);
        return ResponseEntity.ok(mapper.toModel(cuisine));
    }

    @GetMapping("by-name")
    public ResponseEntity<CuisineResponseModel> findByName(@RequestParam String name) {
        Cuisine cuisine = service.findOneByName(name);
        return ResponseEntity.ok(mapper.toModel(cuisine));
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public Cuisine save(@RequestBody @Valid Cuisine cuisine) {
        return service.save(cuisine);
    }

    @PutMapping("{id}")
    public ResponseEntity<CuisineResponseModel> save(@PathVariable UUID id, @RequestBody Cuisine cuisine) {
        Cuisine updatedCuisine = service.save(id, cuisine);
        return ResponseEntity.ok(mapper.toModel(updatedCuisine));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<CuisineResponseModel> deleteById(@PathVariable UUID id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
