package com.gabriel.springrestspecialist.domain.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.gabriel.springrestspecialist.domain.exceptions.EntityInUseException;
import com.gabriel.springrestspecialist.domain.exceptions.EntityNotFoundException;
import com.gabriel.springrestspecialist.domain.models.City;
import com.gabriel.springrestspecialist.domain.models.State;
import com.gabriel.springrestspecialist.domain.repositories.CityRepository;

@Service
public class CityService {
    private static final String CITY_NOT_FOUND = "The city '%s' cannot be found";
    private static final String CITY_IN_USE = "The city '%s' cannot be removed because it is in use";

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private StateService stateService;

    public List<City> findAll() {
        return cityRepository.findAll();
    }

    public List<City> findAllByState(String stateName) {
        return cityRepository.findAllByState(stateName);
    }

    public City findById(UUID id) {
        return cityRepository.findById(id).orElseThrow(() -> {
            String message = String.format(CITY_NOT_FOUND, id);
            throw new EntityNotFoundException(message);
        });
    }

    public City save(City city) {
        try {
            UUID stateId = city.getState().getId();
            State state = stateService.findById(stateId);

            city.setState(state);

            return cityRepository.save(city);
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException(e.getMessage());
        }
    }

    public void remove(UUID id) {
        try {
            cityRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            String message = String.format(CITY_NOT_FOUND, id);
            throw new EntityNotFoundException(message);
        } catch (DataIntegrityViolationException e) {
            String message = String.format(CITY_IN_USE, id);
            throw new EntityInUseException(message);
        }
    }
}
