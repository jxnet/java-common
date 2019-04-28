package com.ardikars.common.memory.internal;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.runners.MethodSorters;

@RunWith(JUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UnsafeHelperTest {

    @Test
    public void isUnsafeAvailableTest() {
        assert UnsafeHelper.isUnsafeAvailable();
    }

    @Test
    public void isUnalignedTest() {
        assert UnsafeHelper.isUnaligned() || !UnsafeHelper.isUnaligned();
    }

    @Test
    public void getUnsafeTest() {
        assert UnsafeHelper.getUnsafe() != null;
    }

    @Test
    public void getNoUnsafeCausesTest() {
        assert UnsafeHelper.getNoUnsafeCauses().isEmpty();
    }

}
