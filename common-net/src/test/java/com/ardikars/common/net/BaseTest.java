package com.ardikars.common.net;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.net.*;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

@RunWith(JUnit4.class)
public class BaseTest {

    @Test
    public void init() throws SocketException, UnknownHostException {
        Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
        while (networkInterfaces.hasMoreElements()) {
            NetworkInterface networkInterface = networkInterfaces.nextElement();
            Enumeration<NetworkInterface> subNetworkInterfaces = networkInterface.getSubInterfaces();
            while (subNetworkInterfaces.hasMoreElements()) {
                NetworkInterface subNetworkInterface = subNetworkInterfaces.nextElement();
                byte[] hardwareAddress = subNetworkInterface.getHardwareAddress();
                if (hardwareAddress != null) {
                    System.out.println(hardwareAddress.length);
                }
            }
        }
    }

    @Test
    public void tes() {
        System.out.println(String.format("%016X", 100L));
    }

}
