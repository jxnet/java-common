package com.ardikars.common.util;

import com.ardikars.common.annotation.Helper;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Static convenience methods that help a method or constructor check whether it was invoked correctly.
 * If validation is fail, the {@code ValidateNumber} method throws an unchecked exception
 * of a specified type, which helps the method in which the exception was thrown communicate that
 * its caller has made a mistake.
 *
 * @author <a href="mailto:contact@ardikars.com">Ardika Rommy Sanjaya</a>
 * @since 1.2.2
 */
@Helper
public class ValidateNumber {

    /**
     * Ensures that given parameter is not contains non numeric character.
     * @param text text.
     * @throws IllegalArgumentException illegal argument exception.
     */
    public static void notNumeric(String text) throws IllegalArgumentException {
        Validate.notIllegalArgument(text != null, new IllegalArgumentException("Text should be not null."));
        Validate.notIllegalArgument(text.length() > 0, new IllegalArgumentException("Text should be not empty."));
        int length = text.length();
        for (int i = 0; i < length; i++) {
            if (!Character.isDigit(text.charAt(i))) {
                throw new IllegalArgumentException("Text should not contains non numeric character.");
            }
        }
    }

    /**
     * Ensures that given parameter is not contains non numeric and comma separator.
     * @param text text.
     * @throws IllegalArgumentException illegal argument exception.
     */
    public static void notNumericWithCommaSeparator(String text) throws IllegalArgumentException {
        Validate.notIllegalArgument(text != null, new IllegalArgumentException("Text should be not null."));
        Validate.notIllegalArgument(text.length() > 0, new IllegalArgumentException("Text should be not empty."));
        int length = text.length();
        for (int i = 0; i < length; i++) {
            char character = text.charAt(i);
            if (!Character.isDigit(character)) {
                if (character != ',') {
                    throw new IllegalArgumentException("Text should not contains non numeric and comma separator.");
                }
            }
        }
    }

    /**
     * Ensures that given parameter is not contains non numeric, dot and comma separator.
     * @param text text.
     * @throws IllegalArgumentException illegal argument exception.
     */
    public static void notNumericWithDotAndCommaSeparator(String text) throws IllegalArgumentException {
        Validate.notIllegalArgument(text != null, new IllegalArgumentException("Text should be not null."));
        Validate.notIllegalArgument(text.length() > 0, new IllegalArgumentException("Text should be not empty."));
        int length = text.length();
        for (int i = 0; i < length; i++) {
            char character = text.charAt(i);
            if (!Character.isDigit(character)) {
                if (character != ',' && character != '.') {
                    throw new IllegalArgumentException("Text should not contains non numeric, dot and comma separator.");
                }
            }
        }
    }

    /**
     * Ensures that given parameter is not contains non numeric character and dot separator.
     * @param text text.
     * @throws IllegalArgumentException illegal argument exception.
     */
    public static void notNumericWithDotSeparator(String text) throws IllegalArgumentException {
        Validate.notIllegalArgument(text != null, new IllegalArgumentException("Text should be not null."));
        Validate.notIllegalArgument(text.length() > 0, new IllegalArgumentException("Text should be not empty."));
        int length = text.length();
        for (int i = 0; i < length; i++) {
            if (!Character.isDigit(text.charAt(i))) {
                if (text.charAt(i) != '.') {
                    throw new IllegalArgumentException("Text should not contains non numeric and dot separator.");
                }
            }
        }
    }

    /**
     * Ensures that given parameter is not contains non numeric character.
     * @param text test.
     * @param exception exception.
     * @throws IllegalArgumentException illegal argument exception.
     */
    public static void notNumeric(String text, IllegalArgumentException exception) throws IllegalArgumentException {
        Validate.notIllegalArgument(text != null, new IllegalArgumentException("Text should be not null."));
        Validate.notIllegalArgument(text.length() > 0, new IllegalArgumentException("Text should be not empty."));
        int length = text.length();
        for (int i = 0; i < length; i++) {
            if (!Character.isDigit(text.charAt(i))) {
                throw exception;
            }
        }
    }

    /**
     * Ensures that given parameter is zero.
     * @param number number.
     * @param <T> type.
     * @throws IllegalArgumentException exception.
     */
    public static <T extends Number> void notZero(T number) throws IllegalArgumentException {
        notZero(number, new IllegalArgumentException("Number \'" + number + "\'" + " is not zero."));
    }

    /**
     * Ensures that given parameter is zero.
     * @param number number.
     * @param exception exception.
     * @param <T> type.
     * @throws IllegalArgumentException illegal argument exception.
     */
    public static <T extends Number> void notZero(T number, IllegalArgumentException exception) throws IllegalArgumentException {
        if (compare(number) != 0) {
            throw exception;
        }
    }

    /**
     * Ensures that given parameter is greateer then zero.
     * @param number number.
     * @param <T> type.
     * @throws IllegalArgumentException exception.
     */
    public static <T extends Number> void notGreaterThenZero(T number) throws IllegalArgumentException {
        notGreaterThenZero(number, new IllegalArgumentException(number + " is less then or equal to zero."));
    }

    /**
     * Ensures that given parameter is greateer then zero.
     * @param number number.
     * @param exception illegal argument exception.
     * @param <T> type.
     * @throws IllegalArgumentException exception.
     */
    public static <T extends Number> void notGreaterThenZero(T number, IllegalArgumentException exception) throws IllegalArgumentException {
        int result = compare(number);
        if (result <= 0) {
            throw exception;
        }
    }

    /**
     * Ensures that given parameter is less then zero.
     * @param number number.
     * @param <T> type.
     * @throws IllegalArgumentException exception.
     */
    public static <T extends Number> void notLessThenZero(T number) throws IllegalArgumentException {
        notGreaterThenZero(number, new IllegalArgumentException(number + " is greater then or equal to zero."));
    }

    /**
     * Ensures that given parameter is less then zero.
     * @param number number.
     * @param exception illegal argument exception.
     * @param <T> type.
     * @throws IllegalArgumentException exception.
     */
    public static <T extends Number> void notLessThenZero(T number, IllegalArgumentException exception) throws IllegalArgumentException {
        int result = compare(number);
        if (result >= 0) {
            throw exception;
        }
    }
    
    /**
     * Ensures that given parameter is greateer then zero.
     * @param number number.
     * @param <T> type.
     * @throws IllegalArgumentException exception.
     */
    public static <T extends Number> void notGreaterThenOrEqualZero(T number) throws IllegalArgumentException {
        notGreaterThenZero(number, new IllegalArgumentException(number + " is less then or equal to zero."));
    }

    /**
     * Ensures that given parameter is greateer then zero.
     * @param number number.
     * @param exception illegal argument exception.
     * @param <T> type.
     * @throws IllegalArgumentException exception.
     */
    public static <T extends Number> void notGreaterThenOrEqualZero(T number, IllegalArgumentException exception) throws IllegalArgumentException {
        int result = compare(number);
        if (result < 0) {
            throw exception;
        }
    }

    /**
     * Ensures that given parameter is less then zero.
     * @param number number.
     * @param <T> type.
     * @throws IllegalArgumentException exception.
     */
    public static <T extends Number> void notLessThenOrEqualZero(T number) throws IllegalArgumentException {
        notGreaterThenZero(number, new IllegalArgumentException(number + " is greater then or equal to zero."));
    }

    /**
     * Ensures that given parameter is less then zero.
     * @param number number.
     * @param exception illegal argument exception.
     * @param <T> type.
     * @throws IllegalArgumentException exception.
     */
    public static <T extends Number> void notLessThenOrEqualZero(T number, IllegalArgumentException exception) throws IllegalArgumentException {
        int result = compare(number);
        if (result > 0) {
            throw exception;
        }
    }

    public static <T extends Number> int compare(T number) throws IllegalArgumentException {
        if (number instanceof BigDecimal) {
            return ((BigDecimal) number).compareTo(BigDecimal.ZERO);
        } else if (number instanceof BigInteger) {
            return ((BigInteger) number).compareTo(BigInteger.ZERO);
        } else {
            long result = number.longValue() & 0xffffffffffffffffL;
            if (result < 0) {
                return -1;
            } else if (result > 0) {
                return 1;
            } else {
                return 0;
            }
        }
    }

}
