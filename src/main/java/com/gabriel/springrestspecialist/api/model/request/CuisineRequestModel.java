package com.gabriel.springrestspecialist.api.model.request;

import java.util.UUID;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CuisineRequestModel {
    @NotNull
    private UUID id;
}
