package com.ardikars.common.net;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.net.*;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;

@RunWith(JUnit4.class)
public class BaseTest {

    @Test
    public void init() throws SocketException, UnknownHostException {
        // do nothing.
        DatagramSocket socket = new DatagramSocket();
        socket.connect(InetAddress.getByAddress(new byte[] {1,1,1,1}), 0);
        System.out.println(NetworkInterface.getByInetAddress(socket.getLocalAddress()));
    }

}
