package com.gabriel.springrestspecialist.api.exceptions;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.lang.Nullable;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.databind.exc.IgnoredPropertyException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.gabriel.springrestspecialist.domain.exceptions.DomainException;
import com.gabriel.springrestspecialist.domain.exceptions.EntityInUseException;
import com.gabriel.springrestspecialist.domain.exceptions.EntityNotFoundException;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {
    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleUncaughtException(Exception ex, WebRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        ApiException exception = apiExceptionBuilder(
                ApiExceptionType.SYSTEM_EXCEPTION,
                status,
                "An internal error has occurred. Try again, and if the problem persists, please contact the system admin")
                .build();

        return handleExceptionInternal(ex, exception, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(DomainException.class)
    public ResponseEntity<Object> handleDomainException(DomainException ex, WebRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ApiException exception = apiExceptionBuilder(
                ApiExceptionType.BAD_REQUEST,
                status,
                ex.getMessage()).build();

        return handleExceptionInternal(ex, exception, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException ex, WebRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        ApiException exception = apiExceptionBuilder(
                ApiExceptionType.RESOURCE_NOT_FOUND,
                status,
                ex.getMessage()).build();

        return handleExceptionInternal(ex, exception, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(EntityInUseException.class)
    public ResponseEntity<Object> handleEntityInUseException(EntityInUseException ex, WebRequest request) {
        HttpStatus status = HttpStatus.CONFLICT;
        ApiException exception = apiExceptionBuilder(
                ApiExceptionType.ENTITY_IN_USE,
                status,
                ex.getMessage()).build();

        return handleExceptionInternal(ex, exception, new HttpHeaders(), status, request);
    }

    @SuppressWarnings(value = "null")
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Object> handleMethodArgumentTypeMismatchException(
            MethodArgumentTypeMismatchException ex,
            WebRequest request) {
        HttpStatus status = HttpStatus.CONFLICT;
        String message = "The URL parameter '%s' received the value '%s', which is invalid. Please, enter a compatible value of type '%s'";
        String detail = String.format(message, ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName());

        ApiException exception = apiExceptionBuilder(
                ApiExceptionType.INVALID_PARAMETER,
                status,
                detail).build();

        return handleExceptionInternal(ex, exception, new HttpHeaders(), status, request);
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
        } else if (rootCause instanceof UnrecognizedPropertyException e) {
            return handlePropertyBindingException(e, headers, status, request);
        } else if (rootCause instanceof IgnoredPropertyException e) {
            return handlePropertyBindingException(e, headers, status, request);
        }

        ApiException exception = apiExceptionBuilder(
                ApiExceptionType.MESSAGE_NOT_READABLE,
                status,
                "The body of the request has not been understood. Check for any syntax error and try again.").build();

        return handleExceptionInternal(ex, exception, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(
            NoHandlerFoundException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        String message = "The URL resource '%s' does not exist";
        String detail = String.format(message, ex.getRequestURL());

        ApiException exception = apiExceptionBuilder(
                ApiExceptionType.RESOURCE_NOT_FOUND,
                status,
                detail).build();

        return handleExceptionInternal(ex, exception, new HttpHeaders(), status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {

        String detail = String.format("One or more fields are invalid. Fill them up correctly and try again.");

        Set<ApiException.Obj> objs = ex.getBindingResult()
                .getAllErrors()
                .stream()
                .map(objError -> {
                    String message = messageSource.getMessage(objError, LocaleContextHolder.getLocale());
                    String name = objError.getObjectName();

                    if (objError instanceof FieldError f) {
                        name = ((FieldError) objError).getField();
                    }

                    return ApiException.Obj.builder()
                            .name(name)
                            .detail(message)
                            .build();
                })
                .collect(Collectors.toSet());

        ApiException exception = apiExceptionBuilder(
                ApiExceptionType.RESOURCE_NOT_FOUND,
                status,
                detail).objs(objs).build();

        return handleExceptionInternal(ex, exception, new HttpHeaders(), status, request);
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

    private ResponseEntity<Object> handlePropertyBindingException(
            PropertyBindingException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        String message = "The property '%s' does not exist in '%s'.";
        String detail = String.format(message, ex.getPropertyName(), ex.getReferringClass().getSimpleName());

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
                .timestamp(LocalDateTime.now())
                .title(type.getTitle())
                .type(type.getUri())
                .status(status.value())
                .detail(detail);
    }
}
