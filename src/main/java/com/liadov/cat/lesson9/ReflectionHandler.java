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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * ReflectionHandler - class populates fields that have @Value annotation
 * on objects of Classes that have @Entity annotation
 *
 * @author Alexandr Liadov on 4/20/2021
 */
public class ReflectionHandler {
    private final Logger log = LoggerFactory.getLogger(ReflectionHandler.class);
    private final FileOperations fileOperations = new FileOperations();

    /**
     * Method populate object fields according to @Value annotation on Fields and Methods
     *
     * @param object           - to be populated with @Value
     * @param multipleFromFile - optional boolean
     */
    public void populateFieldsWithValues(Object object, boolean multipleFromFile) {
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

        executeValuePopulation(object, multipleFromFile);
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

    /**
     * Method initiate creation of objects from multiple entities File
     *
     * @param clazz Class of target objects
     * @return List<Object> of created objects
     */
    public List<Object> getMultipleObjectsFromFile(Class<?> clazz) {
        List<Object> objectList = new ArrayList<>();
        if (fileOperations.isFileAvailable(true)) {
            int amountOfObjectsToBeCreated = fileOperations.getAmountOfObjectsToBeCreated();
            log.debug("Amount of objects received from File = {}", amountOfObjectsToBeCreated);
            for (int i = 0; i < amountOfObjectsToBeCreated; i++) {
                try {
                    Object objectCreatedFromFile = clazz.getDeclaredConstructor().newInstance();
                    this.populateFieldsWithValues(objectCreatedFromFile, true);
                    objectList.add(objectCreatedFromFile);
                    log.info("Object #{} created from file: {}", i, objectCreatedFromFile);
                } catch (InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
                    log.error("creation of objects from file failed", e);
                }
            }
        }
        return objectList;
    }

    private void executeValuePopulation(Object object, boolean multipleFromFile) {
        Class<?> clazz = object.getClass();
        Field[] declaredFields = clazz.getDeclaredFields();
        log.trace("Declared fields: {}", Arrays.toString(declaredFields));
        if (checkValueAnnotation(declaredFields)) {
            populateValue(declaredFields, object, multipleFromFile);
        }

        Method[] declaredMethods = clazz.getDeclaredMethods();
        log.trace("Declared methods: {}", Arrays.toString(declaredMethods));
        if (checkValueAnnotation(declaredMethods)) {
            populateValue(declaredMethods, object, multipleFromFile);
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

    private void populateValue(AccessibleObject[] accessibleObjects, Object object, boolean multipleFromFile) {
        for (AccessibleObject accessibleObject : accessibleObjects) {
            Value valueAnnotation = accessibleObject.getAnnotation(Value.class);
            if (Objects.nonNull(valueAnnotation)) {
                Object plannedValue = valueAnnotation.value();
                log.info("Value from annotation: {}", plannedValue);

                if (fileOperations.isFileAvailable(multipleFromFile)) {
                    String temp = fileOperations.getValueFromFile(accessibleObject, object, multipleFromFile);
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
            Class<?> expectedType = field.getType();
            log.trace("Filed expecting type = {}", expectedType);
            valueAnnotation = tryToParseValue(expectedType, valueAnnotation);
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
            Class<?> expectedType = method.getParameterTypes()[0];
            log.trace("Method expecting type = {}", expectedType);
            valueAnnotation = tryToParseValue(expectedType, valueAnnotation);
            method.invoke(object, valueAnnotation);
            log.debug("value successfully populated");
        } catch (IllegalArgumentException e) {
            log.warn("Provided value {} is uncompilable. System trying to populate default value", valueAnnotation, e);
            method.invoke(object, Value.class.getDeclaredMethods()[0].getDefaultValue());
            log.info("Default Value populated successfully");
        }
    }


    private Object tryToParseValue(Class<?> expectedType, Object valueAnnotation) {
        ValueConverter converter = new ValueConverter();
        if (converter.isSupportedType(expectedType)) {
            try {
                valueAnnotation = converter.getConvertedValue(expectedType, valueAnnotation);
            } catch (NumberFormatException e) {
                log.trace("parseInt Failed");
            }
        }
        return valueAnnotation;
    }
}
