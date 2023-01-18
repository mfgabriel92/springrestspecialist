package com.gabriel.springrestspecialist.api.exceptions;

import org.springframework.validation.BindingResult;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ValidationException extends RuntimeException {
    private BindingResult bindingResult;
}
