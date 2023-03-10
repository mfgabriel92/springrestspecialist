package com.gabriel.springrestspecialist.api.exceptions;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public enum ApiExceptionType {
    BAD_REQUEST("Bad request", HttpStatus.BAD_REQUEST), //
    MESSAGE_NOT_READABLE("Message not readable", HttpStatus.BAD_REQUEST), //
    RESOURCE_NOT_FOUND("Resource not found", HttpStatus.NOT_FOUND), //
    ENTITY_IN_USE("Entity in use", HttpStatus.CONFLICT), //
    INVALID_PARAMETER("Invalid parameter", HttpStatus.BAD_REQUEST), //
    SYSTEM_EXCEPTION("System exception", HttpStatus.INTERNAL_SERVER_ERROR);

    private String title;
    private String uri;

    ApiExceptionType(String title, HttpStatus statusCode) {
        this.title = title;
        this.uri = String.format("https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/%s", statusCode.value());
    }
}
