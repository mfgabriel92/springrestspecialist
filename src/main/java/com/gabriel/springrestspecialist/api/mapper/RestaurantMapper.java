package com.gabriel.springrestspecialist.api.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gabriel.springrestspecialist.api.model.request.RestaurantRequestModel;
import com.gabriel.springrestspecialist.api.model.response.RestaurantResponseModel;
import com.gabriel.springrestspecialist.domain.models.Restaurant;

@Component
public class RestaurantMapper {
    @Autowired
    private ModelMapper mapper;

    public RestaurantResponseModel toModel(Restaurant restaurant) {
        return mapper.map(restaurant, RestaurantResponseModel.class);
    }

    public List<RestaurantResponseModel> toCollectionModel(List<Restaurant> restaurants) {
        return restaurants.stream()
                .map(restaurant -> toModel(restaurant))
                .collect(Collectors.toList());
    }

    public Restaurant toDomainObject(RestaurantRequestModel request) {
        return mapper.map(request, Restaurant.class);
    }
}
