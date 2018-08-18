package com.ardikars.common.net;

import org.junit.Test;

import java.net.SocketException;

public class NetworkInterfaceTest extends BaseTest {

    @Test
    public void test01() throws SocketException {
        for (NetworkInterface networkInterface : NetworkInterface.getNetworkInterfaces()) {
            System.out.println(networkInterface);
        }
    }

}
