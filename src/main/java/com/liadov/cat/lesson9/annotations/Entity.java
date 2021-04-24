package com.liadov.cat.lesson9.annotations;

import java.lang.annotation.*;

/**
 * Entity - annotation for identification of POJO classes
 *
 * @author Aleksandr Liadov on 4/19/2021
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Entity {

}
