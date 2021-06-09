package com.epam.liadov.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * JpaConfig - configuration for storing objects in database
 *
 * @author Aleksandr Liadov
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.epam.liadov.repository")
@RequiredArgsConstructor
@Profile("!local")
public class JpaConfig {

    private final DataSource dataSource;

    /**
     * Bean for EntityManagerFactory
     *
     * @return entityManagerFactory
     */
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        var entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactory.setDataSource(dataSource);
        entityManagerFactory.setPackagesToScan("com.epam.liadov");
        entityManagerFactory.setJpaVendorAdapter(jpaVendorAdapter());
        entityManagerFactory.setJpaDialect(new HibernateJpaDialect());
        entityManagerFactory.setJpaProperties(additionalProperties());
        Map<String, Object> propertyMap = new HashMap<>();
        propertyMap.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQL82Dialect");
        entityManagerFactory.setJpaPropertyMap(propertyMap);
        return entityManagerFactory;
    }

    /**
     * Bean for TransactionManager
     *
     * @param entityManagerFactory target entityManagerFactory
     * @return JpaTransactionManager
     */
    @Bean
    public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        var transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }

    /**
     * Method intend to specify JPA properties, to be passed into Persistence.createEntityManagerFactory
     *
     * @return Properties
     */
    public Properties additionalProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.show_sql", "true");
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQL82Dialect");
        return properties;
    }

    private JpaVendorAdapter jpaVendorAdapter() {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setDatabasePlatform(additionalProperties().getProperty("jdbc.dialect"));
        return vendorAdapter;
    }
}