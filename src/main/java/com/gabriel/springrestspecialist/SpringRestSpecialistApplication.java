package com.gabriel.springrestspecialist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.gabriel.springrestspecialist.infrastructure.repository.CustomJpaRepositoryImpl;

@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = CustomJpaRepositoryImpl.class)
public class SpringRestSpecialistApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringRestSpecialistApplication.class, args);
    }
}
