/**
 * Copyright 2017-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ardikars.common.util;

import com.ardikars.common.annotation.Helper;

/**
 * Stirngs utility.
 * @author <a href="mailto:contact@ardikars.com">Ardika Rommy Sanjaya</a>
 * @since 1.0.0
 */
@Helper
public final class Strings {

    private Strings() { }

    /**
     * Get length.
     * @param dataLength data length.
     * @param offset offset.
     * @param length length.
     * @return length.
     */
    private static int length(final int dataLength, final int offset, final int length) {
        int l;
        if (dataLength != length && offset != 0) {
            l = (offset + length);
        } else {
            l = length;
        }
        return l;
    }

    /**
     * Byte to hex value.
     * @param value value.
     * @return hex format.
     * @since 1.0.0
     */
    public static String toHexString(final byte value) {
        String srt = Integer.toHexString((value) & 0xff);
        if ((srt.length() & 1) == 1) {
            return ("0" + srt);
        }
        return (srt);
    }

    /**
     * Byte array to hex stream.
     * @param data byte array.
     * @return hex stream.
     * @since 1.0.0
     */
    public static String toHexString(final byte[] data) {
        return toHexString(data, 0, data.length);
    }

    /**
     * Byte array to hex stream.
     * @param data byte array.
     * @param offset offset.
     * @param length length.
     * @return hex stream.
     * @since 1.0.0
     */
    public static String toHexString(final byte[] data, final int offset, final int length) {
        Validate.notInBounds(data, offset, length);
        StringBuilder sb = new StringBuilder(length);
        int l = length(data.length, offset, length);
        for (int i = offset; i < l; i++) {
            sb.append(toHexString(data[i]));
        }
        return sb.toString();
    }

    /**
     * Short to hex stream.
     * @param value short array.
     * @return hex stream.
     * @since 1.0.0
     */
    public static String toHexString(final short value) {
        String srt = Integer.toHexString((value) & 0xFFFF);
        if ((srt.length() & 1) == 1) {
            return ("0" + srt);
        }
        return (srt);
    }

    /**
     * Short array to hex stream.
     * @param values short array.
     * @return hex stream.
     * @since 1.0.0
     */
    public static String toHexString(final short[] values) {
        return toHexString(values, 0, values.length);
    }

    /**
     * Short array to hex stream.
     * @param data short array.
     * @param offset offset.
     * @param length length.
     * @return hex stream.
     * @since 1.0.0
     */
    public static String toHexString(final short[] data, final int offset, final int length) {
        Validate.notInBounds(data, offset, length);
        StringBuilder sb = new StringBuilder(length);
        int l = length(data.length, offset, length);
        for (int i = offset; i < l; i++) {
            sb.append(toHexString(data[i]));
        }
        return sb.toString();
    }

    /**
     * Int to hex stream.
     * @param value integer value.
     * @return hex stream.
     * @since 1.0.0
     */
    public static String toHexString(final int value) {
        String srt = Integer.toHexString(value);
        if ((srt.length() & 1) == 1) {
            return ("0" + srt);
        }
        return (srt);
    }

    /**
     * Int array to hex stream.
     * @param values int array.
     * @return hex stream.
     * @since 1.0.0
     */
    public static String toHexString(final int[] values) {
        return toHexString(values, 0, values.length);
    }

    /**
     * Int array to hex stream.
     * @param data int array.
     * @param offset offset.
     * @param length length.
     * @return hex stream.
     * @since 1.0.0
     */
    public static String toHexString(final int[] data, final int offset, final int length) {
        Validate.notInBounds(data, offset, length);
        StringBuilder sb = new StringBuilder(length);
        int l = length(data.length, offset, length);
        for (int i = offset; i < l; i++) {
            sb.append(toHexString(data[i]));
        }
        return sb.toString();
    }

}
