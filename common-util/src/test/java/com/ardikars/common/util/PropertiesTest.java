package com.ardikars.common.util;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.runners.MethodSorters;

@RunWith(JUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PropertiesTest {

    @Before
    public void before() {
        System.setProperty("jxnet", "jmalloc");
        System.setProperty("jxnet_bool_yes", "yEs");
        System.setProperty("jxnet_bool_1", "1");
        System.setProperty("jxnet_bool_true", "tRuE");
        System.setProperty("jxnet_bool_no", "NO");
        System.setProperty("jxnet_bool_0", "0");
        System.setProperty("jxnet_bool_false", "FALse");
        System.setProperty("jxnet_int_1", "1");
        System.setProperty("jxnet_long_1", "1");
    }

    @Test
    public void getPropertyTest() {
        assert Properties.getProperty("jxnet").equals("jmalloc");
    }

    @Test
    public void getPropertyWithDefaultValueTest() {
        assert Properties.getProperty("jmalloc", "jxnet").equals("jxnet");
    }

    @Test
    public void getBooleanTest() {
        assert Properties.getBoolean("jxnet_bool_yes", false) == true;
        assert Properties.getBoolean("jxnet_bool_1", false) == true;
        assert Properties.getBoolean("jxnet_bool_true", false) == true;
        assert Properties.getBoolean("jxnet_bool_no", true) == false;
        assert Properties.getBoolean("jxnet_bool_0", true) == false;
        assert Properties.getBoolean("jxnet_bool_false", true) == false;
        assert Properties.getBoolean("jxnet_bool_", false) == false;
    }

    @Test
    public void getIntTest() {
        assert Properties.getInt("jxnet_int_1", 0) == 1;
        assert Properties.getInt("jxnet_int", 0) == 0;
    }

    @Test
    public void getLongTest() {
        assert Properties.getLong("jxnet_long_1", 0) == 1;
        assert Properties.getLong("jxnet_long", 0) == 0;
    }

}
