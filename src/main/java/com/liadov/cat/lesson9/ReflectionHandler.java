package com.liadov.cat.lesson9;

import com.liadov.cat.lesson9.annotations.Entity;
import com.liadov.cat.lesson9.annotations.Value;
import com.liadov.cat.lesson9.exceptions.NoValueAnnotationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
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

    /**
     * Method populate object fields according to @Value annotation on Fields and Methods
     *
     * @param object - to be populated with @Value
     */
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

    /**
     * Method checking if class of object has @Entity annotation
     *
     * @param object target object
     * @return boolean
     */
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

    private void populateValue(AccessibleObject[] accessibleObjects, Object object) {
        for (AccessibleObject accessibleObject : accessibleObjects) {
            Value valueAnnotation = accessibleObject.getAnnotation(Value.class);
            if (Objects.nonNull(valueAnnotation)) {
                Object plannedValue = valueAnnotation.value();
                log.info("Value from annotation: {}", plannedValue);

                if (isFileNameProvided(valueAnnotation)) {
                    File file = new File(valueAnnotation.fileName());
                    FileOperations fileOperations = new FileOperations();
                    String temp = fileOperations.getValueFromFile(file, accessibleObject);
                    if (temp.length() > 0) {
                        plannedValue = temp;
                    }
                    log.info("Value from file = {}", plannedValue);
                }

                accessibleObject.setAccessible(true);
                try {
                    selectInstanceToPopulate(object, accessibleObject, plannedValue);
                } catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
                    log.error("error. Value was not populated: ", e);
                }
            } else {
                log.debug("Value from annotation: is null");
            }
        }
    }

    private boolean isFileNameProvided(Value valueAnnotation) {
        String fileName = valueAnnotation.fileName();
        if (fileName.length() > 0) {
            File file = new File(fileName);
            boolean isFileExist = file.exists();
            log.debug("File identified: {}", isFileExist);
            return isFileExist;
        }
        log.debug("file name is not specified");
        return false;
    }


    private void selectInstanceToPopulate(Object object, AccessibleObject accessibleObject, Object valueAnnotation) throws IllegalAccessException, InvocationTargetException {
        if (accessibleObject instanceof Field) {
            log.debug("Instance identified as: {}", accessibleObject.getClass().getSimpleName());
            setFiledValue(object, accessibleObject, valueAnnotation);
        } else if (accessibleObject instanceof Method) {
            log.debug("Instance identified as: {}", accessibleObject.getClass().getSimpleName());
            invokeMethodValue(object, accessibleObject, valueAnnotation);
        } else {
            log.info("Instance Type Unidentified {}", accessibleObject.getClass());
        }
    }

    private void setFiledValue(Object object, AccessibleObject accessibleObject, Object valueAnnotation) throws IllegalAccessException {
        Field field = (Field) accessibleObject;
        try {
            log.debug("Field value population started for object: {}, and value: {}", object, valueAnnotation);
            valueAnnotation = tryToParseInt(valueAnnotation);
            field.set(object, valueAnnotation);
            log.debug("value successfully populated");
        } catch (IllegalArgumentException e) {
            log.warn("Provided value {} is uncompilable. System trying to populate default value", valueAnnotation, e);
            field.set(object, Value.class.getDeclaredMethods()[0].getDefaultValue());
            log.info("Default Value populated successfully");
        }
    }

    private void invokeMethodValue(Object object, AccessibleObject accessibleObject, Object valueAnnotation) throws InvocationTargetException, IllegalAccessException {
        Method method = (Method) accessibleObject;
        try {
            log.debug("Method {} value population started for object: {}, and value: {}", method.getName(), object, valueAnnotation);
            valueAnnotation = tryToParseInt(valueAnnotation);
            method.invoke(object, valueAnnotation);
            log.debug("value successfully populated");
        } catch (IllegalArgumentException e) {
            log.warn("Provided value {} is uncompilable. System trying to populate default value", valueAnnotation, e);
            method.invoke(object, Value.class.getDeclaredMethods()[0].getDefaultValue());
            log.info("Default Value populated successfully");
        }
    }

    private Object tryToParseInt(Object valueAnnotation) {
        try {
            valueAnnotation = Integer.parseInt(String.valueOf(valueAnnotation));
        } catch (NumberFormatException e) {
            log.trace("parseInt Failed");
        }
        return valueAnnotation;
    }

}
