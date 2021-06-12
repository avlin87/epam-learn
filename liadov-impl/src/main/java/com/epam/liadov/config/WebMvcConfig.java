package com.epam.liadov.config;

import com.epam.liadov.converter.OrderDtoToOrderConverter;
import com.epam.liadov.converter.OrderToOrderDtoConverter;
import com.epam.liadov.converter.ProductDtoToProductConverter;
import com.epam.liadov.converter.ProductToProductDtoConverter;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * WebMvcConfig
 *
 * @author Aleksandr Liadov
 */
@EnableWebMvc
@Configuration
@ComponentScan(basePackages = {"com.epam.liadov"})
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new OrderToOrderDtoConverter());
        registry.addConverter(new OrderDtoToOrderConverter());
        registry.addConverter(new ProductToProductDtoConverter());
        registry.addConverter(new ProductDtoToProductConverter());
    }
}
