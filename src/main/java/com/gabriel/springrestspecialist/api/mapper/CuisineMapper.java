package com.gabriel.springrestspecialist.api.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gabriel.springrestspecialist.api.model.request.CuisineRequestModel;
import com.gabriel.springrestspecialist.api.model.response.CuisineResponseModel;
import com.gabriel.springrestspecialist.domain.models.Cuisine;

@Component
public class CuisineMapper {
    @Autowired
    private ModelMapper mapper;

    public CuisineResponseModel toModel(Cuisine cuisine) {
        return mapper.map(cuisine, CuisineResponseModel.class);
    }

    public List<CuisineResponseModel> toCollectionModel(List<Cuisine> cuisines) {
        return cuisines.stream()
                .map(cuisine -> toModel(cuisine))
                .collect(Collectors.toList());
    }

    public Cuisine toDomainObject(CuisineRequestModel request) {
        return mapper.map(request, Cuisine.class);
    }
}
