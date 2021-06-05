package com.epam.liadov.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import java.util.Locale;

/**
 * DaoStubConfig - Stubbed configuration
 * spring.profiles.active=local
 *
 * @author Aleksandr Liadov
 */
@Configuration
@Profile("local")
public class DaoStubConfig {

    /**
     * Bean for getting messageSource
     *
     * @return MessageSource object
     */
    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource reloadableResourceBundleMessageSource = new ReloadableResourceBundleMessageSource();
        reloadableResourceBundleMessageSource.setDefaultEncoding("UTF-8");
        reloadableResourceBundleMessageSource.setBasename("classpath:messages/message");
        return reloadableResourceBundleMessageSource;
    }

    /**
     * Bean for getting locale
     *
     * @return Locale object
     */
    @Bean
    public Locale locale() {
        return new Locale("ru", "RU");
    }
}
