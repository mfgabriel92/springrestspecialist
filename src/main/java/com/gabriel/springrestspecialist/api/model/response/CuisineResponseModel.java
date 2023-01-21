package com.gabriel.springrestspecialist.api.model.response;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CuisineResponseModel {
    private UUID id;
    private String name;
}
