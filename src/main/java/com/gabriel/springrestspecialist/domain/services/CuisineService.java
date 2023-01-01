package com.gabriel.springrestspecialist.domain.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.gabriel.springrestspecialist.domain.exceptions.EntityInUseException;
import com.gabriel.springrestspecialist.domain.exceptions.EntityNotFoundException;
import com.gabriel.springrestspecialist.domain.models.Cuisine;
import com.gabriel.springrestspecialist.domain.repositories.CuisineRepository;

@Service
public class CuisineService {
    private static final String CUISINE_NOT_FOUND = "The cuisine '%s' cannot be found";
    private static final String CUISINE_IN_USE = "The cuisine '%s' cannot be removed because it is in use";

    @Autowired
    private CuisineRepository cuisineRepository;

    public List<Cuisine> findAll() {
        return cuisineRepository.findAll();
    }

    public Cuisine findById(UUID id) {
        return cuisineRepository.findById(id).orElseThrow(() -> {
            String message = String.format(CUISINE_NOT_FOUND, id);
            throw new EntityNotFoundException(message);
        });
    }

    public Cuisine findOneByName(String name) {
        return cuisineRepository.findOneByName(name).orElseThrow(() -> {
            String message = String.format(CUISINE_NOT_FOUND, name);
            throw new EntityNotFoundException(message);
        });
    }

    public Cuisine save(Cuisine cuisine) {
        return cuisineRepository.save(cuisine);
    }

    public Cuisine save(UUID id, Cuisine cuisine) {
        Cuisine currentCuisine = findById(id);
        BeanUtils.copyProperties(cuisine, currentCuisine, "id");
        return save(currentCuisine);
    }

    public void deleteById(UUID id) {
        try {
            cuisineRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            String message = String.format(CUISINE_NOT_FOUND, id);
            throw new EntityNotFoundException(message);
        } catch (DataIntegrityViolationException e) {
            String message = String.format(CUISINE_IN_USE, id);
            throw new EntityInUseException(message);
        }
    }
}
