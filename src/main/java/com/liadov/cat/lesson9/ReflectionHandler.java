package com.liadov.cat.lesson9;

import com.liadov.cat.lesson9.annotations.Entity;
import com.liadov.cat.lesson9.annotations.Value;
import com.liadov.cat.lesson9.exceptions.NoValueAnnotationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;

/**
 * ReflectionHandler
 *
 * @author Alexandr Liadov on 4/20/2021
 */
public class ReflectionHandler {
    private final Logger log = LoggerFactory.getLogger(ReflectionHandler.class);

    public void populateFieldsWithValues(Object object) {
        boolean annotatedWithEntity = false;

        try {
            annotatedWithEntity = isAnnotatedWithEntity(object);
            log.info("object of {} class annotated with @Entity = {}", object.getClass().getSimpleName(), annotatedWithEntity);
        } catch (NoValueAnnotationException e) {
            log.error("@Entity is present but @Value is not present:", e);
        } catch (IllegalStateException e) {
            log.error("@Entity is absent but @Value is present:", e);
        }

        if (!annotatedWithEntity) {
            log.info("No fields are available for population in {} class", object.getClass().getSimpleName());
            return;
        }

        executeValuePopulation(object);
    }

    public boolean isAnnotatedWithEntity(Object object) {
        Class<?> clazz = object.getClass();

        boolean isEntityPresent = checkEntity(clazz);
        log.trace("@Entity is present = {}", isEntityPresent);
        boolean isValuePresent = checkValueAnnotationOnFieldsAndMethods(clazz);
        log.trace("@Value is present = {}", isValuePresent);

        if (isEntityPresent) {
            if (isValuePresent) {
                log.info("@Entity and @Value are present");
                return true;
            } else {
                String validationText = "@Entity is present but @Value is absent";
                log.info(validationText);
                throw new NoValueAnnotationException(validationText);
            }
        } else {
            if (isValuePresent) {
                String validationText = "@Entity is absent but @Value is present";
                log.info(validationText);
                throw new IllegalStateException(validationText);
            }
        }

        log.info("@Entity and @Value are absent");
        return false;
    }


    private void executeValuePopulation(Object object) {
        Class<?> clazz = object.getClass();
        Field[] declaredFields = clazz.getDeclaredFields();
        log.trace("Declared fields: {}", Arrays.toString(declaredFields));
        if (checkValueAnnotation(declaredFields)) {
            populateValue(declaredFields, object);
        }

        Method[] declaredMethods = clazz.getDeclaredMethods();
        log.trace("Declared methods: {}", Arrays.toString(declaredMethods));
        if (checkValueAnnotation(declaredMethods)) {
            populateValue(declaredMethods, object);
        }
    }

    private void populateValue(AccessibleObject[] accessibleObjects, Object object) {
        for (AccessibleObject accessibleObject : accessibleObjects) {
            Value valueAnnotation = accessibleObject.getAnnotation(Value.class);
            if (Objects.nonNull(valueAnnotation)) {
                String value = String.valueOf(valueAnnotation.value());
                log.info("Value from annotation: {}", value);
                accessibleObject.setAccessible(true);
                Field field;
                Method method;
                try {
                    if (accessibleObject instanceof Field) {
                        field = (Field) accessibleObject;
                        field.set(object, valueAnnotation.value());
                    }
                    if (accessibleObject instanceof Method) {
                        method = (Method) accessibleObject;
                        method.invoke(object, valueAnnotation.value());
                    }
                } catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
                    log.error("error", e);
                }
            }
        }
    }

    private boolean checkEntity(Class<?> clazz) {
        Entity[] declaredAnnotationsByType = clazz.getDeclaredAnnotationsByType(Entity.class);
        log.trace("declaredAnnotationsByType = {}", Arrays.toString(declaredAnnotationsByType));

        if (declaredAnnotationsByType.length > 0) {
            log.info("@Entity annotation is present");
            return true;
        }

        log.info("There is NO @Entity annotation");
        return false;
    }

    private boolean checkValueAnnotationOnFieldsAndMethods(Class<?> clazz) {
        Field[] declaredFields = clazz.getDeclaredFields();
        log.trace("Declared fields: {}", Arrays.toString(declaredFields));
        boolean fieldHasValueAnnotation = checkValueAnnotation(declaredFields);

        Method[] declaredMethods = clazz.getDeclaredMethods();
        log.trace("Declared methods: {}", Arrays.toString(declaredMethods));
        boolean methodHasValueAnnotation = checkValueAnnotation(declaredMethods);

        log.info("Methods or Fields have @Value Annotation: {}", fieldHasValueAnnotation || methodHasValueAnnotation);
        return fieldHasValueAnnotation || methodHasValueAnnotation;
    }

    private boolean checkValueAnnotation(AccessibleObject[] accessibleObjects) {
        for (AccessibleObject accessibleObject : accessibleObjects) {
            Value[] declaredValueAnnotations = accessibleObject.getDeclaredAnnotationsByType(Value.class);
            log.trace("{} received. Declared Value Annotations: {}", accessibleObjects.getClass().getSimpleName(), Arrays.toString(declaredValueAnnotations));
            if (declaredValueAnnotations.length > 0) {
                log.info("@Value Annotation is preset");
                return true;
            }
        }
        log.info("There is no @Value Annotation");
        return false;
    }

}
