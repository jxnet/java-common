/**
 * Copyright 2017-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ardikars.common.net;

import com.ardikars.common.annotation.Immutable;
import com.ardikars.common.annotation.Incubating;
import com.ardikars.common.annotation.Mutable;
import com.ardikars.common.util.Address;

import java.net.InterfaceAddress;
import java.net.SocketException;
import java.util.*;

@Incubating
@Mutable(blocking = true)
public class NetworkInterface {

    private final int index;
    private final String name;
    private final String displayName;
    private final com.ardikars.common.util.Address hardwareAddress;
    private final Collection<Address> addresses;
    private final int mtu;
    private final boolean pointToPoint;
    private final boolean virtual;
    private final boolean loopback;
    private final boolean up;
    private final NetworkInterface parent;
    private final Collection<NetworkInterface> childs;

    public NetworkInterface(final Builder builder) {
        this.index = builder.index;
        this.name = builder.name;
        this.displayName = builder.displayName;
        this.hardwareAddress = builder.hardwareAddress;
        this.addresses = builder.addresses;
        this.mtu = builder.mtu;
        this.pointToPoint = builder.pointToPoint;
        this.virtual = builder.virtual;
        this.loopback = builder.loopback;
        this.up = builder.up;
        this.parent = builder.parent;
        this.childs = builder.childs;
    }

    public int getIndex() {
        return index;
    }

    public String getName() {
        return name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public com.ardikars.common.util.Address getHardwareAddress() {
        return hardwareAddress;
    }

    public Collection<Address> getAddresses() {
        return addresses;
    }

    public int getMtu() {
        return mtu;
    }

    public boolean isPointToPoint() {
        return pointToPoint;
    }

    public boolean isVirtual() {
        return virtual;
    }

    public boolean isLoopback() {
        return loopback;
    }

    public boolean isUp() {
        return up;
    }

    public NetworkInterface getParent() {
        return parent;
    }

    public Collection<NetworkInterface> getChilds() {
        return childs;
    }

    public void addChild(NetworkInterface networkInterface) {
        synchronized (this) {
            this.childs.add(networkInterface);
        }
    }

    @Immutable
    public static class Address {

        private final InetAddress inetAddress;
        private final int maskLength;

        private Address(final NetworkInterface.Address.Builder builder) {
            this.inetAddress = builder.inetAddress;
            this.maskLength = builder.maskLength;
        }

        public InetAddress getInetAddress() {
            return inetAddress;
        }

        public int getNetworkPrefixLength() {
            return maskLength;
        }

        @Override
        public String toString() {
            return "Address{" +
                    "inetAddress=" + inetAddress +
                    ", maskLength=" + maskLength +
                    '}';
        }

        public static class Builder implements com.ardikars.common.util.Builder<Address, Void> {

            private InetAddress inetAddress;
            private int maskLength;

            public Builder inetAddress(InetAddress inetAddress) {
                this.inetAddress = inetAddress;
                return this;
            }

            public Builder maskLength(int maskLength) {
                this.maskLength = maskLength;
                return this;
            }

            @Override
            public Address build() {
                return new Address(this);
            }

            @Override
            public Address build(Void value) {
                throw new UnsupportedOperationException();
            }

        }

    }

    public static class Builder implements com.ardikars.common.util.Builder<NetworkInterface, Void> {

        private int index;
        private String name;
        private String displayName;
        private com.ardikars.common.util.Address hardwareAddress;
        private Collection<Address> addresses;
        private int mtu;
        private boolean pointToPoint;
        private boolean virtual;
        private boolean loopback;
        private boolean up;
        private NetworkInterface parent;
        private Collection<NetworkInterface> childs;

        public Builder index(int index) {
            this.index = index;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder displayName(String displayName) {
            this.displayName = displayName;
            return this;
        }

        public Builder hardwareAddress(MacAddress hardwareAddress) {
            this.hardwareAddress = hardwareAddress;
            return this;
        }

        public Builder addresses(Collection<Address> addresses) {
            this.addresses = addresses;
            return this;
        }

        public Builder mtu(int mtu) {
            this.mtu = mtu;
            return this;
        }

        public Builder pointToPoint(boolean pointToPoint) {
            this.pointToPoint = pointToPoint;
            return this;
        }

        public Builder virtual(boolean virtual) {
            this.virtual = virtual;
            return this;
        }

        public Builder loopback(boolean loopback) {
            this.loopback = loopback;
            return this;
        }

        public Builder up(boolean up) {
            this.up = up;
            return this;
        }

        public Builder parent(NetworkInterface parent) {
            this.parent = parent;
            return this;
        }

        public Builder childs(Collection<NetworkInterface> childs) {
            this.childs = childs;
            return this;
        }

        @Override
        public NetworkInterface build() {
            return new NetworkInterface(this);
        }

        @Override
        public NetworkInterface build(Void value) {
            throw new UnsupportedOperationException();
        }

    }

    @Override
    public String toString() {
        return "NetworkInterface{" +
                "index=" + index +
                ", name='" + name + '\'' +
                ", displayName='" + displayName + '\'' +
                ", hardwareAddress=" + hardwareAddress +
                ", addresses=" + addresses +
                ", mtu=" + mtu +
                ", pointToPoint=" + pointToPoint +
                ", virtual=" + virtual +
                ", loopback=" + loopback +
                ", up=" + up +
                ", parent=" + parent +
                ", childs=" + childs +
                '}';
    }

    public static Collection<NetworkInterface> getNetworkInterfaces() throws SocketException {
        Enumeration<java.net.NetworkInterface> networkInterfaces = java.net.NetworkInterface.getNetworkInterfaces();
        Set<NetworkInterface> networkInterfaceSet = new HashSet<>();
        while (networkInterfaces.hasMoreElements()) {
            java.net.NetworkInterface networkInterface = networkInterfaces.nextElement();
            byte[] hardwareAddress = networkInterface.getHardwareAddress();
            Set<Address> parentAddreses = new HashSet<>();
            for (InterfaceAddress interfaceAddress : networkInterface.getInterfaceAddresses()) {
                if (interfaceAddress.getAddress() instanceof java.net.Inet4Address) {
                    Address address = new Address.Builder()
                            .inetAddress(Inet4Address.valueOf(interfaceAddress.getAddress().getAddress()))
                            .maskLength(interfaceAddress.getNetworkPrefixLength())
                            .build();
                    parentAddreses.add(address);
                } else if (interfaceAddress.getAddress() instanceof java.net.Inet6Address) {
                    Address address = new Address.Builder()
                            .inetAddress(Inet6Address.valueOf(interfaceAddress.getAddress().getAddress()))
                            .maskLength(interfaceAddress.getNetworkPrefixLength())
                            .build();
                    parentAddreses.add(address);
                }
            }
            NetworkInterface parent = new Builder()
                    .index(networkInterface.getIndex())
                    .name(networkInterface.getName())
                    .displayName(networkInterface.getDisplayName())
                    .hardwareAddress(hardwareAddress == null ? MacAddress.ZERO : MacAddress.valueOf(hardwareAddress))
                    .addresses(parentAddreses)
                    .mtu(networkInterface.getMTU())
                    .pointToPoint(networkInterface.isPointToPoint())
                    .virtual(networkInterface.isVirtual())
                    .loopback(networkInterface.isLoopback())
                    .up(networkInterface.isUp())
                    .parent(null)
                    .childs(null)
                    .build();
            Enumeration<java.net.NetworkInterface> childs = networkInterface.getSubInterfaces();
            while (childs.hasMoreElements()) {
                java.net.NetworkInterface childNetworkInterface = childs.nextElement();
                hardwareAddress = childNetworkInterface.getHardwareAddress();
                Set<Address> childAddresses = new HashSet<>();
                for (InterfaceAddress interfaceAddress : childNetworkInterface.getInterfaceAddresses()) {
                    if (interfaceAddress.getAddress() instanceof java.net.Inet4Address) {
                        Address address = new Address.Builder()
                                .inetAddress(Inet4Address.valueOf(interfaceAddress.getAddress().getAddress()))
                                .maskLength(interfaceAddress.getNetworkPrefixLength())
                                .build();
                        childAddresses.add(address);
                    } else if (interfaceAddress.getAddress() instanceof java.net.Inet6Address){
                        Address address = new Address.Builder()
                                .inetAddress(Inet6Address.valueOf(interfaceAddress.getAddress().getAddress()))
                                .maskLength(interfaceAddress.getNetworkPrefixLength())
                                .build();
                        childAddresses.add(address);
                    }
                }
                NetworkInterface child = new Builder()
                        .index(childNetworkInterface.getIndex())
                        .name(childNetworkInterface.getName())
                        .displayName(childNetworkInterface.getDisplayName())
                        .hardwareAddress(hardwareAddress == null ? MacAddress.ZERO : MacAddress.valueOf(hardwareAddress))
                        .addresses(childAddresses)
                        .mtu(childNetworkInterface.getMTU())
                        .pointToPoint(childNetworkInterface.isPointToPoint())
                        .virtual(childNetworkInterface.isVirtual())
                        .loopback(childNetworkInterface.isLoopback())
                        .up(childNetworkInterface.isUp())
                        .parent(parent)
                        .childs(null)
                        .build();
                parent.addChild(child);
            }
            networkInterfaceSet.add(parent);
        }
        return networkInterfaceSet;
    }

}
