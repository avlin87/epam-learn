package com.liadov.cat.lesson9;

import com.liadov.cat.lesson9.annotations.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.lang.reflect.AccessibleObject;
import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * FileOperations
 *
 * @author Aleksandr Liadov on 4/21/2021
 */
public class FileOperations {
    private final Logger log = LoggerFactory.getLogger(FileOperations.class);
    private final String fileName = "src/main/resources/sourceFile.txt";
    private final String multipleObjectsName = "src/main/resources/multipleSourceFile.txt";

    private Object tempObject;
    private Queue<Map<String, String>> queueOfMappedFieldsWithValues = new LinkedBlockingQueue<>();

    /**
     * Method checks whether target file exists.
     *
     * @param multipleFromFile boolean flag to select multiple entities in case of true or single entity in case of false
     * @param files File optional target file
     * @return boolean
     */
    public boolean isFileAvailable(boolean multipleFromFile, File... files) {
        File file = new File(multipleFromFile ? multipleObjectsName : fileName);
        if (files.length > 0) {
            file = files[0];
        }
        boolean isFileExist = file.exists();
        if (isFileExist) {
            log.debug("File identified: {}", true);
            return true;
        }
        log.debug("file not found");
        return false;
    }

    /**
     * Method select appropriate value according to Method/Filed name
     *
     * @param accessibleObject Method/Filed
     * @param object           target object
     * @param multipleFromFile boolean flag to select multiple entities in case of true or single entity in case of false
     * @return String value
     */
    public String getValueFromFile(AccessibleObject accessibleObject, Object object, boolean multipleFromFile) {
        Map<String, String> map = getMapFromSource(object, multipleFromFile);
        String fieldName = parsFiledName(accessibleObject);
        String valueFromField = map.get(fieldName);
        if (Objects.nonNull(valueFromField)) {
            log.debug("field name = {}, value = {}", fieldName, valueFromField);
            return valueFromField;
        } else {
            log.debug("field name = {}, value = null", fieldName);
            return "";
        }
    }

    /**
     * Method returns amount of sets of values extracted from multiple entities file
     *
     * @return int amount
     */
    public int getAmountOfObjectsToBeCreated() {
        queueOfMappedFieldsWithValues = getValuesFromFile(true);
        log.trace("Map received from file = {}, with size = {}", queueOfMappedFieldsWithValues, queueOfMappedFieldsWithValues.size());
        return queueOfMappedFieldsWithValues.size();
    }

    /**
     * Method returns Queue<Map<Field,Value>>. Map populated data from file by splitting by "=".
     *
     * @param reader BufferedReader
     * @return Queue<Map < String, String>>
     */
    public Queue<Map<String, String>> readValuesFromFile(BufferedReader reader) {
        Queue<Map<String, String>> mapQueue = new LinkedBlockingQueue<>();
        if (Objects.nonNull(reader)) {
            Map<String, String> map = new HashMap<>();
            log.debug("map initiated");
            try {
                String[] temp;
                while (reader.ready()) {
                    temp = reader.readLine().split("=");
                    log.trace("line from file = [{}], size = {}", Arrays.toString(temp), temp.length);
                    if (temp.length > 1) {
                        String field = temp[0].toUpperCase();
                        String value = temp[1];
                        map.put(field, value);
                        log.trace("added to map: field = {}, value = {}", field, value);
                    } else {
                        if (map.size() > 0) {
                            mapQueue.offer(map);
                            map = new HashMap<>();
                            log.debug("map initiated");
                        }
                    }
                }
                if (map.size() > 0) {
                    mapQueue.offer(map);
                }
                log.info("Values from file: {}", map);
            } catch (FileNotFoundException e) {
                log.error("File not found", e);
            } catch (IOException e) {
                log.error("error", e);
            }
        }
        return mapQueue;
    }

    private Map<String, String> getMapFromSource(Object object, boolean multipleFromFile) {
        Map<String, String> map;
        if (multipleFromFile) {
            log.debug("object = {}, temp = {}", object, tempObject);
            if (Objects.nonNull(tempObject) && !object.equals(tempObject)) {
                queueOfMappedFieldsWithValues.remove();
            }
            tempObject = object;
            log.trace("map size before peek {}", queueOfMappedFieldsWithValues.size());
            map = queueOfMappedFieldsWithValues.peek();
            log.trace("map peek from queue for multiple objects {}, objects in queue {}", map, queueOfMappedFieldsWithValues.size());
            return Objects.nonNull(map) ? map : new HashMap<>();
        }
        map = getValuesFromFile(false).poll();
        log.trace("map taken from single single source file: {}", map);
        return map;
    }

    private String parsFiledName(AccessibleObject accessibleObject) {
        String filedName = accessibleObject
                .getAnnotation(Value.class)
                .fileVariable()
                .toUpperCase();
        log.debug("filed name parsed as [{}]", filedName);
        return filedName;
    }

    private Queue<Map<String, String>> getValuesFromFile(boolean multipleFromFile) {
        Queue<Map<String, String>> mapQueue = new LinkedBlockingQueue<>();

        File file = new File(multipleFromFile ? multipleObjectsName : fileName);
        log.debug("Source file selected: {}", file);

        try (BufferedReader reader = getReader(file)) {
            mapQueue = readValuesFromFile(reader);
            log.trace("map from file: {}", mapQueue);
        } catch (IOException e) {
            log.error("error", e);
        }
        return mapQueue;
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
}
