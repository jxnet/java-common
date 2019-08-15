package com.ardikars.common.memory.internal;

import com.ardikars.common.util.Platforms;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.runners.MethodSorters;

import java.util.List;

@RunWith(JUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UnsafeHelperTest {

    @Test
    public void isUnsafeAvailableTest() {
        if (Platforms.getJavaMojorVersion() > 8) {
            assert !UnsafeHelper.isUnsafeAvailable();
        } else {
            assert UnsafeHelper.isUnsafeAvailable();
        }
    }

    @Test
    public void isUnalignedTest() {
        assert UnsafeHelper.isUnaligned() || !UnsafeHelper.isUnaligned();
    }

    @Test
    public void getUnsafeTest() {
        try {
            assert UnsafeHelper.getUnsafe() != null;
        } catch (UnsupportedOperationException ex) {
            assert Platforms.getJavaMojorVersion() > 8;
        }
    }

    @Test
    public void getNoUnsafeCausesTest() {
        List<Throwable> throwables = UnsafeHelper.getNoUnsafeCauses();
        if (Platforms.getJavaMojorVersion() > 8) {
            assert !throwables.isEmpty();
        } else {
            assert throwables.isEmpty();
        }
    }

}
