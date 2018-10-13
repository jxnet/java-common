package com.ardikars.common.util;

import com.ardikars.common.annotation.Helper;

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
     * @param text test.
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

}
