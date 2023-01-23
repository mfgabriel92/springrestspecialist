package com.gabriel.springrestspecialist.core.modelmapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.gabriel.springrestspecialist.api.model.response.RestaurantResponseModel;
import com.gabriel.springrestspecialist.domain.models.Restaurant;

@Configuration
public class ModelMapperConfig {
    @Bean
    ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();

        mapper.createTypeMap(Restaurant.class, RestaurantResponseModel.class)
                .addMapping(Restaurant::getShippingRate, RestaurantResponseModel::setFee);

        return mapper;
    }
}
