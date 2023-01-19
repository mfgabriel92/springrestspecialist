package com.gabriel.springrestspecialist.api.core.validation.jackson;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.gabriel.springrestspecialist.domain.models.Restaurant;
import com.gabriel.springrestspecialist.domain.models.mixins.RestaurantMixin;

@Component
public class JacksonMixinModule extends SimpleModule {
    public JacksonMixinModule() {
        setMixInAnnotation(Restaurant.class, RestaurantMixin.class);
    }
}
