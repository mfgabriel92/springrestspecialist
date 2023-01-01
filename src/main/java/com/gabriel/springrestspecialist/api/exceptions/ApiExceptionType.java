package com.gabriel.springrestspecialist.api.exceptions;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public enum ApiExceptionType {
    BAD_REQUEST("Bad request", HttpStatus.BAD_REQUEST), //
    ENTITY_NOT_FOUND("Entity not found", HttpStatus.NOT_FOUND), //
    ENTITY_IN_USE("Entity in use", HttpStatus.CONFLICT);

    private String title;
    private HttpStatus statusCode;
    private String uri;

    ApiExceptionType(String title, HttpStatus statusCode) {
        this.title = title;
        this.statusCode = statusCode;
        this.uri = String.format("https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/%s", statusCode.value());
    }
}
