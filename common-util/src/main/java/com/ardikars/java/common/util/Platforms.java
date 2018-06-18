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

package com.ardikars.java.common.util;

import com.ardikars.java.common.annotation.Helper;

/**
 * Platform information.
 * @author <a href="mailto:contact@ardikars.com">Ardika Rommy Sanjaya</a>
 * @since 1.0.0
 */
@Helper
public final class Platforms {

    private static final String OS_ARCH = "os.arch";
    private static final String OS_NAME = "os.name";
    private static final String JAVA_VM_NAME = "java.vm.name";

    public enum Name {
        WINDOWS, LINUX, ANDROID, FREEBSD, DARWIN, UNKNOWN;
    }

    public enum Architecture {
        _32_BIT, _64_BIT;
    }

    private Platforms() {

    }

    private static Name name;
    private static Architecture architecture;

    /**
     * Get platform name.
     * @return returns platform name.
     */
    public static Name getName() {
        return name;
    }

    /**
     * Get platform architecture.
     * @return returns platform architecture.
     */
    public static Architecture getArchitecture() {
        return architecture;
    }

    /**
     * Returns true if Windows platform, false otherwise.
     * @return returns true if windows platform, false otherwise.
     */
    public static boolean isWindows() {
        return name == Name.WINDOWS;
    }

    /**
     * Returns true if Linux platform, false otherwise.
     * @return returns true if linux platform, false otherwise.
     */
    public static final boolean isLinux() {
        return name == Name.LINUX;
    }

    /**
     * Returns true if Android platform, false otherwise.
     * @return returns true if android platform, false otherwise.
     */
    public static final boolean isAndroid() {
        return name == Name.ANDROID;
    }

    /**
     * Returns true if FreeBsd platform, false otherwise.
     * @return returns true if FreeBsd platform, false otherwise.
     */
    public static final boolean isFreeBsd() {
        return name == Name.FREEBSD;
    }

    /**
     * Returns true if Darwin (MacOs) platform, false otherwise.
     * @return returns true if Darwin (MacOs) platform, false otherwise.
     */
    public static final boolean isDarwin() {
        return name == Name.DARWIN;
    }

    /**
     * Returns true if 32-bit architecture, false otherwise.
     * @return returns true if 32-bit architecture, false otherwise.
     */
    public static final boolean is32Bit() {
        return architecture == Architecture._32_BIT;
    }

    /**
     * Returns true if 64-bit architecture, false otherwise.
     * @return returns true if 64-bit architecture, false otherwise.
     */
    public static final boolean is64Bit() {
        return architecture == Architecture._64_BIT;
    }

    /**
     * Returns true if Arm architecture, false otherwise.
     * @return returns true if Arm architecture, false otherwise.
     */
    public static final boolean isArm() {
        return System.getProperty(OS_ARCH).toLowerCase().trim().startsWith("arm");
    }

    /**
     * Returns true if Intel platform, false otherwise.
     * @return returns true if Intel platform, false otherwise.
     */
    public static final boolean isIntel() {
        final String arch = System.getProperty(OS_ARCH).toLowerCase().trim();
        return arch.startsWith("x86") || arch.startsWith("x64");
    }

    /**
     * Returns true if Amd platform, false otherwise.
     * @return returns true if Amd platform, false otherwise.
     */
    public static final boolean isAmd() {
        return System.getProperty(OS_ARCH).toLowerCase().trim().startsWith("amd");
    }

    /**
     * Get Cpu Version.
     * @return returns Cpu version.
     */
    public static final String getVersion() {
        final String version = System.getProperty("os.version");
        if (Character.isDigit(version.charAt(version.indexOf('v') + 1))) {
            return String.valueOf("v" + version.charAt(version.indexOf('v') + 1));
        }
        return "";
    }

    static {
        final String osName = System.getProperty(OS_NAME).toUpperCase().trim();
        final String osArch = System.getProperty(OS_ARCH).toLowerCase().trim();
        if (osName.startsWith("LINUX")) {
            if ("DALVIK".equals(System.getProperty(JAVA_VM_NAME).toUpperCase().trim())) {
                name = Name.ANDROID;
            } else {
                name = Name.LINUX;
            }
        } else if (osName.startsWith("WINDOWS")) {
            name = Name.WINDOWS;
        } else if (osName.startsWith("FREEBSD")) {
            name = Name.FREEBSD;
        } else if (osName.startsWith("MAC OS")) {
            name = Name.DARWIN;
        } else {
            name = Name.UNKNOWN;
        }

        if ("i386".equals(osArch) || "i686".equals(osArch) || "i586".equals(osArch)) {
            architecture = Architecture._32_BIT;
        } else if ("x86_64".equals(osArch) || "amd64".equals(osArch) || "x64".equals(osArch)) {
            architecture = Architecture._64_BIT;
        }

    }

}
