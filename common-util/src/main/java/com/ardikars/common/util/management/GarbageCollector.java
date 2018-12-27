package com.ardikars.common.util.management;

/**
 * Garbage collector statistics for the JVM.
 */
public interface GarbageCollector {

    long getMajorCollectionCount();

    long getMajorCollectionTime();

    long getMinorCollectionCount();

    long getMinorCollectionTime();

    long getUnknownCollectionCount();

    long getUnknownCollectionTime();

}
