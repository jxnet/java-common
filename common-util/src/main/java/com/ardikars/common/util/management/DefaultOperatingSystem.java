package com.ardikars.common.util.management;

import com.ardikars.common.logging.Logger;
import com.ardikars.common.logging.LoggerFactory;
import com.ardikars.common.util.Reflections;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Operating system info.
 * @since 1.2.3
 */
final class DefaultOperatingSystem implements OperatingSystem {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultOperatingSystem.class);

    private static final long DEFAULT_LONG_ERROR_VALUE = Long.MIN_VALUE;

    private static final double DEFAULT_DOUBLE_ERROR_VALUE = Double.MIN_VALUE;

    private static final double PERCENTAGE_MULTIPLIER = 100d;

    private static final OperatingSystemMXBean OPERATING_SYSTEM_MX_BEAN;

    private static final Method COMMITED_VIRTUAL_MEMORY_SIZE_METHOD;
    private static final Method TOTAL_SWAP_SPACE_SIZE_METHOD;
    private static final Method FREE_SWAP_SPACE_SIZE_METHOD;
    private static final Method PROCESS_CPU_TIME_METHOD;
    private static final Method FREE_PHYSICAL_MEMORY_SIZE_METHOD;
    private static final Method TOTAL_PHYSICAL_MEMORY_SIZE_METHOD;
    private static final Method SYSTEM_CPU_LOAD_METHOD;
    private static final Method PROCESS_CPU_LOAD_METHOD;

    private static final boolean ACCESSIBLE;

    @Override
    public boolean isAccessible() {
        return ACCESSIBLE;
    }

    @Override
    public long getCommittedVirtualMemorySize() {
        return parseLong(COMMITED_VIRTUAL_MEMORY_SIZE_METHOD);
    }

    @Override
    public long getTotalSwapSpaceSize() {
        return parseLong(TOTAL_SWAP_SPACE_SIZE_METHOD);
    }

    @Override
    public long getFreeSwapSpaceSize() {
        return parseLong(FREE_SWAP_SPACE_SIZE_METHOD);
    }

    @Override
    public long getProcessCpuTime() {
        return parseLong(PROCESS_CPU_TIME_METHOD);
    }

    @Override
    public long getFreePhysicalMemorySize() {
        return parseLong(FREE_PHYSICAL_MEMORY_SIZE_METHOD);
    }

    @Override
    public long getTotalPhysicalMemorySize() {
        return parseLong(TOTAL_PHYSICAL_MEMORY_SIZE_METHOD);
    }

    @Override
    public double getSystemCpuLoad() {
        return parseDouble(SYSTEM_CPU_LOAD_METHOD);
    }

    @Override
    public double getProcessCpuLoad() {
        return parseDouble(PROCESS_CPU_LOAD_METHOD);
    }

    @Override
    public String getVersion() {
        return OPERATING_SYSTEM_MX_BEAN.getVersion();
    }

    @Override
    public String getArch() {
        return OPERATING_SYSTEM_MX_BEAN.getArch();
    }

    @Override
    public String getName() {
        return OPERATING_SYSTEM_MX_BEAN.getName();
    }

    @Override
    public double getSystemLoadAverage() {
        return OPERATING_SYSTEM_MX_BEAN.getSystemLoadAverage();
    }

    private static long parseLong(Method method) {
        try {
            Object object = method.invoke(OPERATING_SYSTEM_MX_BEAN);
            if (object == null) {
                return DEFAULT_LONG_ERROR_VALUE;
            }
            return (Long) object;
        } catch (IllegalAccessException | InvocationTargetException e) {
            LOGGER.warn(e.getMessage());
            return DEFAULT_LONG_ERROR_VALUE;
        }
    }

    private static double parseDouble(Method method) {
        try {
            Object object = method.invoke(OPERATING_SYSTEM_MX_BEAN);
            if (object == null) {
                return DEFAULT_DOUBLE_ERROR_VALUE;
            }
            double value = (Double) object;
            return Math.round(value * PERCENTAGE_MULTIPLIER);
        } catch (IllegalAccessException | InvocationTargetException e) {
            LOGGER.warn(e.getMessage());
            return DEFAULT_DOUBLE_ERROR_VALUE;
        }
    }

    private static void trySetAccessible(Method method) throws Throwable {
        Throwable throwable = Reflections.trySetAccessible(method, true);
        if (throwable != null) {
            throw throwable;
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("DefaultOperatingSystem{");
        sb.append("commitedVirtualMemorySize=").append(getCommittedVirtualMemorySize());
        sb.append(", totalSwapSpaceSize=").append(getTotalSwapSpaceSize());
        sb.append(", freeSwapSpaceSize=").append(getFreeSwapSpaceSize());
        sb.append(", processCpuTime=").append(getProcessCpuTime());
        sb.append(", freePhysicalMemorySize=").append(getFreePhysicalMemorySize());
        sb.append(", totalPhysicalMemorySize=").append(getTotalPhysicalMemorySize());
        sb.append(", systemCpuLoad=").append(getSystemCpuLoad());
        sb.append(", processCpuLoad=").append(getProcessCpuLoad());
        sb.append(", name=").append(getName());
        sb.append(", arch=").append(getArch());
        sb.append(", version=").append(getVersion());
        sb.append(", systemLoadAverage=").append(getSystemLoadAverage());
        sb.append('}');
        return sb.toString();
    }

    static {
        OPERATING_SYSTEM_MX_BEAN = ManagementFactory.getOperatingSystemMXBean();
        Class clazz = OPERATING_SYSTEM_MX_BEAN.getClass();
        Method committedVirtualMemorySizeMethod;
        Method totalSwapSpaceSizeMethod;
        Method freeSwapSpaceSizeMethod;
        Method processCpuTime;
        Method freePhysicalMemorySize;
        Method totalPhysicalMemorySize;
        Method systemCpuLoad;
        Method processCpuLoad;
        boolean accessible;
        try {
            committedVirtualMemorySizeMethod = clazz.getMethod("getCommittedVirtualMemorySize");
            totalSwapSpaceSizeMethod = clazz.getMethod("getTotalSwapSpaceSize");
            freePhysicalMemorySize = clazz.getMethod("getFreePhysicalMemorySize");
            freeSwapSpaceSizeMethod = clazz.getMethod("getFreeSwapSpaceSize");
            processCpuTime = clazz.getMethod("getProcessCpuTime");
            totalPhysicalMemorySize = clazz.getMethod("getTotalPhysicalMemorySize");
            systemCpuLoad = clazz.getMethod("getSystemCpuLoad");
            processCpuLoad = clazz.getMethod("getProcessCpuLoad");
            trySetAccessible(committedVirtualMemorySizeMethod);
            trySetAccessible(totalSwapSpaceSizeMethod);
            trySetAccessible(freeSwapSpaceSizeMethod);
            trySetAccessible(processCpuTime);
            trySetAccessible(freePhysicalMemorySize);
            trySetAccessible(totalPhysicalMemorySize);
            trySetAccessible(systemCpuLoad);
            trySetAccessible(processCpuLoad);
            accessible = true;
        } catch (Throwable e) {
            committedVirtualMemorySizeMethod = null;
            totalSwapSpaceSizeMethod = null;
            freeSwapSpaceSizeMethod = null;
            processCpuTime = null;
            freePhysicalMemorySize = null;
            totalPhysicalMemorySize = null;
            systemCpuLoad = null;
            processCpuLoad = null;
            accessible = false;
            LOGGER.error(e.getMessage());
        }
        COMMITED_VIRTUAL_MEMORY_SIZE_METHOD = committedVirtualMemorySizeMethod;
        TOTAL_SWAP_SPACE_SIZE_METHOD = totalSwapSpaceSizeMethod;
        FREE_SWAP_SPACE_SIZE_METHOD = freeSwapSpaceSizeMethod;
        PROCESS_CPU_TIME_METHOD = processCpuTime;
        FREE_PHYSICAL_MEMORY_SIZE_METHOD = freePhysicalMemorySize;
        TOTAL_PHYSICAL_MEMORY_SIZE_METHOD = totalPhysicalMemorySize;
        SYSTEM_CPU_LOAD_METHOD = systemCpuLoad;
        PROCESS_CPU_LOAD_METHOD = processCpuLoad;
        ACCESSIBLE = accessible;
    }

}
