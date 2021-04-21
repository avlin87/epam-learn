package com.liadov.cat.lesson9;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * FileOperations
 *
 * @author Aleksandr Liadov on 4/21/2021
 */
public class FileOperations {
    private final Logger log = LoggerFactory.getLogger(FileOperations.class);

    /**
     * Method select appropriate value from target file according to Method/Filed name
     *
     * @param file             target file
     * @param accessibleObject Method/Filed
     * @return String value
     */
    public String getValueFromFile(File file, AccessibleObject accessibleObject) {
        Map<String, String> map = new HashMap<>();
        try (BufferedReader reader = getReader(file)) {
            map = readValuesFromFile(reader);
        } catch (IOException e) {
            log.error("error", e);
        }
        log.trace("Map from file = {}", map);
        String fieldName = parsFiledName(accessibleObject);
        String fieldValue = map.get(fieldName);
        log.debug("field name = {}, value = {}", fieldName, fieldValue);

        return fieldValue;
    }

    /**
     * Method returns <Field,Value> map from file by splitting by "="
     *
     * @param reader BufferedReader
     * @return map<String, String>
     */
    public Map<String, String> readValuesFromFile(BufferedReader reader) {
        Map<String, String> map = new HashMap<>();
        if (Objects.nonNull(reader)) {
            try {
                String[] temp;
                while (reader.ready()) {
                    temp = reader.readLine().split("=");
                    if (temp.length > 1) {
                        String field = temp[0].toUpperCase();
                        String value = temp[1];
                        map.put(field, value);
                    }
                }
                log.info("Values from file: {}", map);
            } catch (FileNotFoundException e) {
                log.error("File not found", e);
            } catch (IOException e) {
                log.error("error", e);
            }
        }
        return map;
    }

    private BufferedReader getReader(File file) {
        BufferedReader reader = new BufferedReader(new StringReader(""));
        try {
            reader = new BufferedReader(new FileReader(file));
            log.debug("Reader initiated successfully");
        } catch (FileNotFoundException e) {
            log.error("Reader initialization failed", e);
        }
        return reader;
    }

    private String parsFiledName(AccessibleObject accessibleObject) {
        StringBuilder stringBuilder = new StringBuilder();
        if (accessibleObject instanceof Method) {
            Method method = (Method) accessibleObject;
            stringBuilder.append(method.getName());
            stringBuilder = stringBuilder.delete(0, 3);
        } else if (accessibleObject instanceof Field) {
            Field field = (Field) accessibleObject;
            stringBuilder.append(field.getName());
        }
        return stringBuilder.toString().toUpperCase();
    }

}
