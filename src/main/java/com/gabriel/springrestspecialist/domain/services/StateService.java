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
import com.gabriel.springrestspecialist.domain.models.State;
import com.gabriel.springrestspecialist.domain.repositories.StateRepository;

@Service
public class StateService {
    private static final String STATE_NOT_FOUND = "The state '%s' cannot be found";
    private static final String STATE_IN_USE = "The state '%s' cannot be removed because it is in use";

    @Autowired
    private StateRepository stateRepository;

    public List<State> findAll() {
        return stateRepository.findAll();
    }

    public State findById(UUID id) {
        return stateRepository.findById(id).orElseThrow(() -> {
            String message = String.format(STATE_NOT_FOUND, id);
            throw new EntityNotFoundException(message);
        });
    }

    public State save(State state) {
        return stateRepository.save(state);
    }

    public State save(UUID id, State state) {
        State currentState = findById(id);
        BeanUtils.copyProperties(state, currentState, "id");
        return save(currentState);
    }

    public void deleteById(UUID id) {
        try {
            stateRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            String message = String.format(STATE_NOT_FOUND, id);
            throw new EntityNotFoundException(message);
        } catch (DataIntegrityViolationException e) {
            String message = String.format(STATE_IN_USE, id);
            throw new EntityInUseException(message);
        }
    }
}
