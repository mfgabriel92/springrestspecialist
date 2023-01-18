package com.gabriel.springrestspecialist.api.exceptions;

import java.time.LocalDateTime;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonInclude(value = Include.NON_NULL)
public class ApiException {
    private LocalDateTime timestamp;
    private Integer status;
    private String type;
    private String title;
    private String detail;
    private Set<Obj> objs;

    @Getter
    @Builder
    public static class Obj {
        private String name;
        private String detail;
    }
}