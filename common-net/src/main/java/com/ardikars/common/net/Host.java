package com.ardikars.common.net;

import com.ardikars.common.annotation.Immutable;
import com.ardikars.common.util.Validate;
import java.io.Serializable;

@Immutable
public class Host implements Serializable {

    private final String hostName;
    private final InetAddress hostAddress;
    private final int port;

    private Host(Builder builder) {
        this.hostName = builder.hostName;
        this.hostAddress = builder.hostAddress;
        this.port = builder.port;
    }

    public String getHostName() {
        return hostName;
    }

    public InetAddress getHostAddress() {
        return hostAddress;
    }

    public int getPort() {
        return port;
    }

    public static final class Builder implements com.ardikars.common.util.Builder<Host, Void> {

        private String hostName;
        private InetAddress hostAddress;
        private int port;

        public Builder hostName(String hostName) {
            Validate.notIllegalArgument(hostName != null
                    , new IllegalArgumentException("Hostname should be not null."));
            this.hostName = hostName;
            return this;
        }

        public Builder address(InetAddress address) {
            Validate.notIllegalArgument(address != null
                    , new IllegalArgumentException("Address sould be not null."));
            this.hostAddress = address;
            return this;
        }
        public Builder port(int port) {
            Validate.notIllegalArgument(port >= 0 && port < 65536
                    , new IllegalArgumentException("Invalid port."));
            this.port = port;
            return this;
        }

        @Override
        public Host build() {
            return new Host(this);
        }

        @Override
        public Host build(Void value) {
            throw new UnsupportedOperationException();
        }

    }

}
