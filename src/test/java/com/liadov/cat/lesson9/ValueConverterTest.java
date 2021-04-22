package com.liadov.cat.lesson9;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * ValueConverterTest
 *
 * @author Aleksandr Liadov on 4/22/2021
 */
public class ValueConverterTest {

    @Test
    public void getConvertedValueReturnSameValueIfTargetTypeNotSupported() {
        ValueConverter converter = new ValueConverter();
        Object expectedResult = "2";

        Object actualResult = converter.getConvertedValue(String.class, "2");

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void getConvertedValueReturnInt() {
        ValueConverter converter = new ValueConverter();
        int expectedResult = 17;

        Object actualResult = converter.getConvertedValue(int.class, 17);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void getConvertedValueReturnBoolean() {
        ValueConverter converter = new ValueConverter();
        boolean expectedResult = true;

        Object actualResult = converter.getConvertedValue(boolean.class, true);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void isSupportedTypeReturnTrue() {
        ValueConverter converter = new ValueConverter();
        boolean expectedResult = true;

        boolean actualResult = converter.isSupportedType(int.class);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void isSupportedTypeReturnFalse() {
        ValueConverter converter = new ValueConverter();
        boolean expectedResult = false;

        boolean actualResult = converter.isSupportedType(String.class);

        assertEquals(expectedResult, actualResult);
    }
}