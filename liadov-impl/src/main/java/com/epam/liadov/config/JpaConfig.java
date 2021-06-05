package com.epam.liadov.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * JpaConfig - configuration for storing objects in database
 *
 * @author Aleksandr Liadov
 */
@Configuration
@RequiredArgsConstructor
@Profile("!local")
public class JpaConfig {

    @Bean(destroyMethod = "close")
    @Scope("prototype")
    public EntityManager entityManager() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("EntityPU");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        return entityManager;
    }
}