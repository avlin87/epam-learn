package com.liadov.cat.lesson9;

import com.liadov.cat.lesson9.interfaces.Converter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * ValueConverter - class to identify value type and return in correct form
 *
 * @author Aleksandr Liadov on 4/22/2021
 */
public class ValueConverter {

    private final Logger log = LoggerFactory.getLogger(ValueConverter.class);
    private final Map<Class<?>, Converter> converterMap = new HashMap<>();

    /**
     * Constructor initiate population of ConversionMap
     */
    public ValueConverter() {
        fillConversionMap();
    }

    /**
     * Method convert target value to target type
     *
     * @param expectedType target field type
     * @param rawValue     target field value
     * @return value converted to filed type
     */
    @SuppressWarnings("unchecked")
    public Object getConvertedValue(Class<?> expectedType, Object rawValue) {
        Object tempObject = rawValue;
        Converter converter = converterMap.get(expectedType);
        if (Objects.nonNull(converter)){
            tempObject = converter.convert(String.valueOf(rawValue));
            log.trace("value {} converted to expected type {}", rawValue, expectedType);
            return tempObject;
        }
        log.trace("value {} NOT converted to expected type {}", rawValue, expectedType);
        return tempObject;
    }

    /**
     * Method checks if filed type is supported by ValueConverter
     *
     * @param expectedType target filed type
     * @return boolean
     */
    public boolean isSupportedType(Class<?> expectedType) {
        boolean typeInMap = converterMap.containsKey(expectedType);
        log.trace("Expected [{}] type is supported = {}", expectedType, typeInMap);
        return typeInMap;
    }

    private void fillConversionMap() {
        Converter<String, Integer> integerConverter = Integer::valueOf;
        converterMap.put(int.class, integerConverter);
        converterMap.put(Integer.class, integerConverter);
        Converter<String, Double> doubleConverter = Double::valueOf;
        converterMap.put(double.class, doubleConverter);
        converterMap.put(Double.class, doubleConverter);
        Converter<String, Short> shortConverter = Short::valueOf;
        converterMap.put(short.class, shortConverter);
        converterMap.put(Short.class, shortConverter);
        Converter<String, Long> longConverter = Long::valueOf;
        converterMap.put(long.class, longConverter);
        converterMap.put(Long.class, longConverter);
        Converter<String, Byte> byteConverter = Byte::valueOf;
        converterMap.put(byte.class, byteConverter);
        converterMap.put(Byte.class, byteConverter);
        Converter<String, Float> floatConverter = Float::valueOf;
        converterMap.put(float.class, floatConverter);
        converterMap.put(Float.class, floatConverter);
        Converter<String, Boolean> booleanConverter = Boolean::valueOf;
        converterMap.put(boolean.class, booleanConverter);
        converterMap.put(Boolean.class, booleanConverter);
        log.trace("converter map is populated {}", converterMap.keySet());
    }
}
