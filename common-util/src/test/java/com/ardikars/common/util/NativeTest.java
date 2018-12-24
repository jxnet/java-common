package com.ardikars.common.util;

import org.junit.Test;
import sun.misc.Unsafe;

/**
 * @author common 2018/12/12
 * @author <a href="mailto:contact@ardikars.com">Langkuy</a>
 */
public class NativeTest extends BaseTest{

    @Test
    public void isNativeAvailable() {
        boolean isNativeAvailable = Unsafes.isUnsafeAvailable();
        Unsafe unsafe = null;
        if (isNativeAvailable) {
            unsafe = Unsafes.getUnsafe();
            assert unsafe != null;
        } else {
            assert unsafe == null;
        }
    }


}
