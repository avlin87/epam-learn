package com.epam.liadov;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * LoggingConfiguration - class for configuration of logging module
 *
 * @author Aleksandr Liadov
 */

@Configuration
@ConditionalOnProperty(prefix = "logging", name = "enabled", havingValue = "true")
public class LoggingConfiguration {

    @Bean
    public LoggingBean loggingBean() {
        return new LoggingBean();
    }
}