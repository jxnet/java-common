package com.ardikars.common.util;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.runners.MethodSorters;

@RunWith(JUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PlatformsTest {

    @Test
    public void getNameTest() {
        assert Platforms.getName().name() != null;
    }

    @Test
    public void getArchitectureTest() {
        assert Platforms.getArchitecture() != null;
    }

    @Test
    public void isWindowsTest() {
        assert Platforms.isWindows() || !Platforms.isWindows();
    }

    @Test
    public void isLinuxTest() {
        assert Platforms.isLinux() || !Platforms.isLinux();
    }

    @Test
    public void isAndroidTest() {
        assert Platforms.isAndroid() || !Platforms.isAndroid();
    }

    @Test
    public void isFreeBsdTest() {
        assert Platforms.isFreeBsd() || !Platforms.isFreeBsd();
    }

    @Test
    public void isDarwinTest() {
        assert Platforms.isDarwin() || !Platforms.isDarwin();
    }

    @Test
    public void is32BitTest() {
        assert Platforms.is32Bit() || !Platforms.is32Bit();
    }

    @Test
    public void is64BitTest() {
        assert Platforms.is64Bit() || !Platforms.is64Bit();
    }

    @Test
    public void isArmTest() {
        assert Platforms.isArm() || !Platforms.isArm();
    }

    @Test
    public void isIntelTest() {
        assert Platforms.isIntel() || !Platforms.isIntel();
    }

    @Test
    public void isAmdTest() {
        assert Platforms.isAmd() || !Platforms.isAmd();
    }

    @Test
    public void getCpuVersionTest() {
        assert Platforms.getCpuVersion() != null;
    }

    @Test
    public void getJavaMajorVersionTest() {
        assert Platforms.getJavaMojorVersion() != 0;
    }

}
