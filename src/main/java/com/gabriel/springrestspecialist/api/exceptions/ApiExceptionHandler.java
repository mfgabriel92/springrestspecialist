package com.gabriel.springrestspecialist.api.exceptions;

import java.util.stream.Collectors;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.gabriel.springrestspecialist.domain.exceptions.DomainException;
import com.gabriel.springrestspecialist.domain.exceptions.EntityInUseException;
import com.gabriel.springrestspecialist.domain.exceptions.EntityNotFoundException;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(DomainException.class)
    public ResponseEntity<?> handleDomainException(DomainException e, WebRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ApiException exception = apiExceptionBuilder(
            ApiExceptionType.BAD_REQUEST,
            status,
            e.getMessage()).build();

        return handleExceptionInternal(e, exception, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handleEntityNotFoundException(EntityNotFoundException e, WebRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        ApiException exception = apiExceptionBuilder(
            ApiExceptionType.ENTITY_NOT_FOUND,
            status,
            e.getMessage()).build();

        return handleExceptionInternal(e, exception, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(EntityInUseException.class)
    public ResponseEntity<?> handleEntityInUseException(EntityInUseException e, WebRequest request) {
        HttpStatus status = HttpStatus.CONFLICT;
        ApiException exception = apiExceptionBuilder(
            ApiExceptionType.ENTITY_IN_USE,
            status,
            e.getMessage()).build();

        return handleExceptionInternal(e, exception, new HttpHeaders(), status, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
        HttpMessageNotReadableException ex,
        HttpHeaders headers,
        HttpStatus status,
        WebRequest request) {
        Throwable rootCause = ExceptionUtils.getRootCause(ex);

        if (rootCause instanceof InvalidFormatException e) {
            return handleInvalidFormatException(e, headers, status, request);
        }

        ApiException exception = apiExceptionBuilder(
            ApiExceptionType.MESSAGE_NOT_READABLE,
            status,
            "The body of the request has not been understood. Check for any syntax error and try again.").build();

        return handleExceptionInternal(ex, exception, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(
        Exception ex,
        @Nullable Object body,
        HttpHeaders headers,
        HttpStatus status,
        WebRequest request) {
        if (body == null) {
            body = ApiException.builder()
                .title(status.getReasonPhrase())
                .status(status.value())
                .build();
        } else if (body instanceof String title) {
            body = ApiException.builder()
                .title(title)
                .status(status.value())
                .build();
        }

        return super.handleExceptionInternal(ex, body, headers, status, request);
    }

    private ResponseEntity<Object> handleInvalidFormatException(
        InvalidFormatException ex,
        HttpHeaders headers,
        HttpStatus status,
        WebRequest request) {
        String path = ex.getPath().stream()
            .map(ref -> ref.getFieldName())
            .collect(Collectors.joining("."));
        String message = "The property '%s' received the value '%s', but they are incompatible. Please, choose a compatible value for '%s'.";
        String detail = String.format(message, path, ex.getValue(), ex.getTargetType().getSimpleName());

        ApiException exception = apiExceptionBuilder(
            ApiExceptionType.MESSAGE_NOT_READABLE,
            status,
            detail).build();

        return handleExceptionInternal(ex, exception, headers, status, request);
    }

    private ApiException.ApiExceptionBuilder apiExceptionBuilder(
        ApiExceptionType type,
        HttpStatus status,
        String detail) {
        return ApiException.builder()
            .title(type.getTitle())
            .type(type.getUri())
            .status(status.value())
            .detail(detail);
    }
}
