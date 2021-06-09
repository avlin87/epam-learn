package com.epam.liadov.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

/**
 * DataSourceConfig - configuration for connection to database
 *
 * @author Aleksandr Liadov
 */
@Configuration
@Profile("!local")
public class DataSourceConfig {

    /**
     * Bean for dataSource configuration
     *
     * @return dataSource
     */
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl("jdbc:postgresql://localhost:5432/epamcourses?currentSchema=liadov");
        dataSource.setUsername("usr");
        dataSource.setPassword("pass");
        dataSource.setDriverClassName("org.postgresql.Driver");
        return dataSource;
    }
}
