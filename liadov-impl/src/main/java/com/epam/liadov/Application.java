package com.epam.liadov;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

/**
 * Application - class initialization of Spring Context
 *
 * @author Aleksandr Liadov
 */
@Slf4j
public class Application implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext) {
        var rootContext = new AnnotationConfigWebApplicationContext();
        rootContext.scan("com.epam.liadov");

        servletContext.addListener(new ContextLoaderListener(rootContext));

        var dispatcherContext = new AnnotationConfigWebApplicationContext();
        dispatcherContext.scan("com.epam.liadov");

        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher", new DispatcherServlet(dispatcherContext));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/*");

        log.info("Application is running");
    }

}