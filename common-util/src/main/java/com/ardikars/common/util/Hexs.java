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

import java.nio.ByteBuffer;
import java.util.regex.Pattern;

/**
 * Hexs utility.
 * @author <a href="mailto:contact@ardikars.com">Ardika Rommy Sanjaya</a>
 * @since 1.0.0
 */
@Helper
public final class Hexs {

    private static final Pattern NO_SEPARATOR_HEX_STRING_PATTERN
            = Pattern.compile("\\A([0-9a-fA-F][0-9a-fA-F])+\\z");

    private static final String HEXDUMP_PRETTY_HEADER = ""
            + "         +-------------------------------------------------+\n"
            + "         |  0  1  2  3  4  5  6  7  8  9  a  b  c  d  e  f |\n"
            + "+--------+-------------------------------------------------+--------+\n";

    private static final String HEXDUMP_PRETTY_FOOTER = ""
            + "+--------+-------------------------------------------------+--------+";

    /**
     * Byte array to hex dump format.
     * @param data byte array.
     * @return hex dump format.
     * @since 1.0.0
     */
    public static String toPrettyHexDump(final byte[] data) {
        return toPrettyHexDump(data, 0, data.length);
    }

    /**
     * Byte array to hex dump format.
     * @param data byte array.
     * @param offset offset.
     * @param length length.
     * @return hex dump format.
     * @since 1.0.0
     */
    public static String toPrettyHexDump(final byte[] data, final int offset, final int length) {
        Validate.notInBounds(data, offset, length);
        StringBuilder result = new StringBuilder();
        StringBuilder builder = new StringBuilder();
        int pos = offset;
        int max = length;
        int lineNumber = 0;
        builder.append(HEXDUMP_PRETTY_HEADER);
        while (pos < max) {
            builder.append(String.format("%08d", lineNumber++) + " | ");
            int lineMax = Math.min(max - pos, 16);
            for (int i = 0; i < lineMax; i++) {
                builder.append(Strings.toHexString(data[pos + i]));
                builder.append(" ");
            }
            while (builder.length() < 48) {
                builder.append(" ");
            }
            builder.append("| ");
            for (int i = 0; i < lineMax; i++) {
                char c = (char) data[pos + i];
                if ((c < 32) || (c > 127)) {
                    c = '.';
                }
                builder.append(c);
            }
            builder.append("\n");
            result.append(builder);
            builder.setLength(0);
            pos += 16;
        }
        result.append(HEXDUMP_PRETTY_FOOTER);
        return result.toString();
    }

    /**
     * Byte buffer to hex dump format.
     * @param buffer byte buffer.
     * @param offset offset.
     * @param length length.
     * @return hex dump format.
     * @since 1.1.0
     */
    public static String toPrettyHexDump(ByteBuffer buffer, int offset, int length) {
        Validate.notInBounds(buffer.capacity(), offset, length);
        StringBuilder result = new StringBuilder();
        StringBuilder builder = new StringBuilder();
        int pos = offset;
        int max = length;
        int lineNumber = 0;
        builder.append(HEXDUMP_PRETTY_HEADER);
        while (pos < max) {
            builder.append(String.format("%08d", lineNumber++) + " | ");
            int lineMax = Math.min(max - pos, 16);
            for (int i = 0; i < lineMax; i++) {
                builder.append(Strings.toHexString(buffer.get(pos + i)));
                builder.append(" ");
            }
            while (builder.length() < 48) {
                builder.append(" ");
            }
            builder.append("| ");
            for (int i = 0; i < lineMax; i++) {
                char c = (char) buffer.getChar(pos + i);
                if ((c < 32) || (c > 127)) {
                    c = '.';
                }
                builder.append(c);
            }
            builder.append("\n");
            result.append(builder);
            builder.setLength(0);
            pos += 16;
        }
        result.append(HEXDUMP_PRETTY_FOOTER);
        return result.toString();
    }

    /**
     * Hex stream to byte array.
     * @param hexStream hex stream.
     * @return byte array.
     * @since 1.0.0
     */
    public static byte[] parseHex(String hexStream) {
        Validate.nullPointer(hexStream);
        if (hexStream.startsWith("0x")) {
            hexStream = hexStream.substring(2);
        }
        hexStream = hexStream.replaceAll("\\s+", "").trim();
        if (!NO_SEPARATOR_HEX_STRING_PATTERN.matcher(hexStream).matches()) {
            throw new IllegalArgumentException();
        }
        int len = hexStream.length();
        byte[] data = new byte[len >> 1];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hexStream.charAt(i), 16) << 4)
                    + Character.digit(hexStream.charAt(i + 1), 16));
        }
        return data;
    }

}
