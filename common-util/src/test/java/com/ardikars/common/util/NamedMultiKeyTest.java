package com.ardikars.common.util;

import com.ardikars.common.util.model.IcmpTypeAndCode;
import org.junit.Test;


import static org.junit.Assert.assertEquals;

public class NamedMultiKeyTest {

    public static final MultipleObject<Byte> NO_ROUTE_TO_DESTINATION = MultipleObject.of((byte) 1, (byte) 0);
    public static final MultipleObject<Byte> COMMUNICATION_WITH_DESTINATION_ADMINIS_TRATIVELY_PROHIBITED = MultipleObject.of((byte) 1, (byte) 1);
    public static final MultipleObject<Byte> UNKNOWN_CODE = MultipleObject.of((byte) -1, (byte) -1);

    @Test
    public void found() {
        IcmpTypeAndCode icmpTypeAndCode = IcmpTypeAndCode.valueOf(NO_ROUTE_TO_DESTINATION);
        assertEquals(IcmpTypeAndCode.NO_ROUTE_TO_DESTINATION.getValue(), icmpTypeAndCode.getValue());
    }

    @Test
    public void notFound() {
        IcmpTypeAndCode icmpTypeAndCode = IcmpTypeAndCode.valueOf(UNKNOWN_CODE);
        assertEquals(IcmpTypeAndCode.UNKNOWN.getValue(), icmpTypeAndCode.getValue());
    }

    @Test
    public void registerNewCode() {
        /**
         * Register icmp type and code.
         */
        IcmpTypeAndCode icmpTypeAndCode = new IcmpTypeAndCode(COMMUNICATION_WITH_DESTINATION_ADMINIS_TRATIVELY_PROHIBITED
                , "Communication with destination administratively prohibited");
        IcmpTypeAndCode.register(icmpTypeAndCode);

        /**
         * Test
         */
        IcmpTypeAndCode communicationWithxxx = IcmpTypeAndCode.valueOf(COMMUNICATION_WITH_DESTINATION_ADMINIS_TRATIVELY_PROHIBITED);
        assertEquals(COMMUNICATION_WITH_DESTINATION_ADMINIS_TRATIVELY_PROHIBITED, communicationWithxxx.getValue());
    }

}
