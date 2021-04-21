package com.liadov.cat.lesson9.annotations;

import java.lang.annotation.*;

/**
 * Value
 *
 * @author Aleksandr Liadov on 4/19/2021
 */
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Value {
    String value() default "Tomas";
    String fileName() default "";
    int intValue() default 0;
}
