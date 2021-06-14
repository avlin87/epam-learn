package com.epam.liadov;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Logging
 *
 * @author Aleksandr Liadov
 */
@Target(ElementType.METHOD)
@Retention(RUNTIME)
public @interface Logging {
}