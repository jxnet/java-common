package com.ardikars.common.util.management;

import com.ardikars.common.util.BaseTest;
import org.junit.Test;

public class GarbageCollectorStatTest extends BaseTest {

    @Test
    public void t() {
        Jvm jvm = Jvms.getJvm();
        System.out.println(jvm.isThreadCpuTimeEnabled());
    }

}
