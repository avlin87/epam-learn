package com.liadov.cat.lesson9.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Value - annotation for setting value for Filed/Method for POJO classes
 * marked by @Entity annotation
 *
 * @author Aleksandr Liadov on 4/19/2021
 */
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Value {
    String value() default "Tomas";
    String fileVariable() default "";
}