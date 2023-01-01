package com.gabriel.springrestspecialist.domain.exceptions;

public class EntityInUseException extends DomainException {
    private static final long serialVersionUID = 1L;

    public EntityInUseException(String message) {
        super(message);
    }
}
