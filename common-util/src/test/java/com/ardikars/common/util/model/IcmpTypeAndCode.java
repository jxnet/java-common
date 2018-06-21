package com.ardikars.common.util.model;

import com.ardikars.common.util.MultiKey;
import com.ardikars.common.util.NamedMultiKey;
import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:contact@ardikars.com">Ardika Rommy Sanjaya</a>
 * @since 1.0.0
 */
public class IcmpTypeAndCode extends NamedMultiKey<MultiKey<Byte>, IcmpTypeAndCode> {

    public static final IcmpTypeAndCode NO_ROUTE_TO_DESTINATION
            = new IcmpTypeAndCode(MultiKey.of((byte) 1, (byte) 0), "No route to destination");

    public static final IcmpTypeAndCode UNKNOWN
            = new IcmpTypeAndCode(MultiKey.of((byte) -1, (byte) -1), "UNKNOWN");


    private static final Map<MultiKey<Byte>, IcmpTypeAndCode> registry
            = new HashMap<>();

    public IcmpTypeAndCode(MultiKey<Byte> multiKey, String name) {
        super(multiKey, name);
    }

    public static final IcmpTypeAndCode register(final IcmpTypeAndCode icmpTypeAndCode) {
        registry.put(icmpTypeAndCode.getValue(), icmpTypeAndCode);
        return icmpTypeAndCode;
    }

    public static final IcmpTypeAndCode valueOf(final MultiKey<Byte> rawValue) {
        IcmpTypeAndCode icmpTypeAndCode = registry.get(rawValue);
        if (icmpTypeAndCode == null) {
            return UNKNOWN;
        }
        return icmpTypeAndCode;
    }

    static {
        registry.put(NO_ROUTE_TO_DESTINATION.getValue(), NO_ROUTE_TO_DESTINATION);
    }

}
