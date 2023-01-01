package com.gabriel.springrestspecialist.api.exceptions;

import java.util.Arrays;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.gabriel.springrestspecialist.domain.exceptions.DomainException;
import com.gabriel.springrestspecialist.domain.exceptions.EntityInUseException;
import com.gabriel.springrestspecialist.domain.exceptions.EntityNotFoundException;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(DomainException.class)
    public ResponseEntity<Object> handleDomainException(DomainException e, WebRequest request) {
        return handleExceptionInternal(e, e.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException e, WebRequest request) {
        return handleExceptionInternal(e, e.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(EntityInUseException.class)
    public ResponseEntity<Object> handleEntityInUseException(EntityInUseException e, WebRequest request) {
        return handleExceptionInternal(e, e.getMessage(), new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(
        Exception ex,
        @Nullable Object body,
        HttpHeaders headers,
        HttpStatus status,
        WebRequest request) {
        String detail = body != null ? ex.getMessage() : status.getReasonPhrase();
        body = apiExceptionBuilder(status, detail).build();

        return super.handleExceptionInternal(ex, body, headers, status, request);
    }

    private ApiException.ApiExceptionBuilder apiExceptionBuilder(
        HttpStatus status,
        String detail) {
        ApiExceptionType type = Arrays.stream(ApiExceptionType.values())
            .filter(e -> e.getStatusCode().equals(status))
            .findFirst()
            .orElseThrow(() -> new IllegalStateException(String.format("Unsupported code %s", status.value())));

        return ApiException.builder()
            .status(type.getStatusCode().value())
            .type(type.getUri())
            .title(type.getTitle())
            .detail(detail);
    }
}
