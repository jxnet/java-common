package com.ardikars.common.util.management;

public final class Jvms {

    private static final Jvm JVM;

    public static Jvm getJvm() {
        return JVM;
    }

    static {
        JVM = new DefaultJvm();
    }

}
