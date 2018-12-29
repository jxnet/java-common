package com.ardikars.common.util.management;

import com.ardikars.common.util.Reflections;
import sun.management.VMManagement;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.lang.reflect.Field;
import java.util.List;

/**
 * Jvm info
 * @since 1.2.3
 */
final class DefaultJvm implements Jvm {

    private static final DefaultOperatingSystem DEFAULT_OPERATING_SYSTEM_INFO
            = new DefaultOperatingSystem();

    private static final VMManagement VM_MANAGEMENT;

    @Override
    public GarbageCollector getGarbageCollector() {
        DefaultGarbageCollector stats = new DefaultGarbageCollector.Builder()
                .build(ManagementFactory.getGarbageCollectorMXBeans());
        return stats;
    }

    @Override
    public OperatingSystem getOperatingSystem() {
        return DEFAULT_OPERATING_SYSTEM_INFO;
    }

    @Override
    public boolean isCompilationTimeMonitoringSupported() {
        return VM_MANAGEMENT.isCompilationTimeMonitoringSupported();
    }

    @Override
    public boolean isThreadContentionMonitoringSupported() {
        return VM_MANAGEMENT.isThreadContentionMonitoringSupported();
    }

    @Override
    public boolean isThreadContentionMonitoringEnabled() {
        return VM_MANAGEMENT.isThreadContentionMonitoringEnabled();
    }

    @Override
    public boolean isCurrentThreadCpuTimeSupported() {
        return VM_MANAGEMENT.isCurrentThreadCpuTimeSupported();
    }

    @Override
    public boolean isOtherThreadCpuTimeSupported() {
        return VM_MANAGEMENT.isOtherThreadCpuTimeSupported();
    }

    @Override
    public boolean isThreadCpuTimeEnabled() {
        return VM_MANAGEMENT.isThreadCpuTimeEnabled();
    }

    @Override
    public boolean isBootClassPathSupported() {
        return VM_MANAGEMENT.isBootClassPathSupported();
    }

    @Override
    public boolean isObjectMonitorUsageSupported() {
        return VM_MANAGEMENT.isObjectMonitorUsageSupported();
    }

    @Override
    public boolean isSynchronizerUsageSupported() {
        return VM_MANAGEMENT.isSynchronizerUsageSupported();
    }

    @Override
    public boolean isThreadAllocatedMemorySupported() {
        return VM_MANAGEMENT.isThreadAllocatedMemorySupported();
    }

    @Override
    public boolean isThreadAllocatedMemoryEnabled() {
        return VM_MANAGEMENT.isThreadAllocatedMemoryEnabled();
    }

    @Override
    public boolean isGcNotificationSupported() {
        return VM_MANAGEMENT.isGcNotificationSupported();
    }

    @Override
    public boolean isRemoteDiagnosticCommandsSupported() {
        return VM_MANAGEMENT.isRemoteDiagnosticCommandsSupported();
    }

    @Override
    public long getTotalClassCount() {
        return VM_MANAGEMENT.getTotalClassCount();
    }

    @Override
    public int getLoadedClassCount() {
        return VM_MANAGEMENT.getLoadedClassCount();
    }

    @Override
    public long getUnloadedClassCount() {
        return VM_MANAGEMENT.getUnloadedClassCount();
    }

    @Override
    public boolean getVerboseClass() {
        return VM_MANAGEMENT.getVerboseClass();
    }

    @Override
    public boolean getVerboseGC() {
        return VM_MANAGEMENT.getVerboseGC();
    }

    @Override
    public String getManagementVersion() {
        return VM_MANAGEMENT.getManagementVersion();
    }

    @Override
    public String getVmId() {
        return VM_MANAGEMENT.getVmId();
    }

    @Override
    public String getVmName() {
        return VM_MANAGEMENT.getVmName();
    }

    @Override
    public String getVmVendor() {
        return VM_MANAGEMENT.getVmVendor();
    }

    @Override
    public String getVmVersion() {
        return VM_MANAGEMENT.getVmVersion();
    }

    @Override
    public String getVmSpecName() {
        return VM_MANAGEMENT.getVmSpecName();
    }

    @Override
    public String getVmSpecVendor() {
        return VM_MANAGEMENT.getVmSpecVendor();
    }

    @Override
    public String getVmSpecVersion() {
        return VM_MANAGEMENT.getVmSpecVersion();
    }

    @Override
    public String getClassPath() {
        return VM_MANAGEMENT.getClassPath();
    }

    @Override
    public String getLibraryPath() {
        return VM_MANAGEMENT.getLibraryPath();
    }

    @Override
    public String getBootClassPath() {
        return VM_MANAGEMENT.getBootClassPath();
    }

    @Override
    public List<String> getVmArguments() {
        return VM_MANAGEMENT.getVmArguments();
    }

    @Override
    public long getStartupTime() {
        return VM_MANAGEMENT.getStartupTime();
    }

    @Override
    public long getUptime() {
        return VM_MANAGEMENT.getUptime();
    }

    @Override
    public int getAvailableProcessors() {
        return VM_MANAGEMENT.getAvailableProcessors();
    }

    @Override
    public String getCompilerName() {
        return VM_MANAGEMENT.getCompilerName();
    }

    @Override
    public long getTotalCompileTime() {
        return VM_MANAGEMENT.getTotalCompileTime();
    }

    @Override
    public long getTotalThreadCount() {
        return VM_MANAGEMENT.getTotalThreadCount();
    }

    @Override
    public int getLiveThreadCount() {
        return VM_MANAGEMENT.getLiveThreadCount();
    }

    @Override
    public int getPeakThreadCount() {
        return VM_MANAGEMENT.getPeakThreadCount();
    }

    @Override
    public int getDaemonThreadCount() {
        return VM_MANAGEMENT.getDaemonThreadCount();
    }

    @Override
    public String getOsName() {
        return VM_MANAGEMENT.getOsName();
    }

    @Override
    public String getOsArch() {
        return VM_MANAGEMENT.getOsArch();
    }

    @Override
    public String getOsVersion() {
        return VM_MANAGEMENT.getOsVersion();
    }

    @Override
    public long getSafepointCount() {
        return VM_MANAGEMENT.getSafepointCount();
    }

    @Override
    public long getTotalSafepointTime() {
        return VM_MANAGEMENT.getTotalSafepointTime();
    }

    @Override
    public long getSafepointSyncTime() {
        return VM_MANAGEMENT.getSafepointSyncTime();
    }

    @Override
    public long getTotalApplicationNonStoppedTime() {
        return VM_MANAGEMENT.getTotalApplicationNonStoppedTime();
    }

    @Override
    public long getLoadedClassSize() {
        return VM_MANAGEMENT.getLoadedClassSize();
    }

    @Override
    public long getUnloadedClassSize() {
        return VM_MANAGEMENT.getUnloadedClassSize();
    }

    @Override
    public long getClassLoadingTime() {
        return VM_MANAGEMENT.getClassLoadingTime();
    }

    @Override
    public long getMethodDataSize() {
        return VM_MANAGEMENT.getMethodDataSize();
    }

    @Override
    public long getInitializedClassCount() {
        return VM_MANAGEMENT.getInitializedClassCount();
    }

    @Override
    public long getClassInitializationTime() {
        return VM_MANAGEMENT.getClassInitializationTime();
    }

    @Override
    public long getClassVerificationTime() {
        return VM_MANAGEMENT.getClassVerificationTime();
    }

    @Override
    public boolean hasJvm() {
        return VM_MANAGEMENT != null;
    }

    static {
        VMManagement vmManagement = null;
        try {
            OperatingSystemMXBean bean = ManagementFactory.getOperatingSystemMXBean();
            Field field = bean.getClass().getSuperclass().getDeclaredField("jvm");
            Throwable throwable = Reflections.trySetAccessible(field, true);
            if (throwable == null) {
                Object object = field.get(bean);
                if (object instanceof VMManagement) {
                    vmManagement = (VMManagement) object;
                }
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {

        }
        VM_MANAGEMENT = vmManagement;
    }

}
