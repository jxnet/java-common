/**
 * Copyright 2017-2019 the original author or authors.
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
import com.ardikars.common.util.Address;

/**
 * This class represents an raw address.
 *
 * @author <a href="mailto:contact@ardikars.com">Ardika Rommy Sanjaya</a>
 * @since 1.2.0
 */
@Immutable
public class RawAddress implements Address {

    private final byte[] address;

    private RawAddress(byte[] address) {
        this.address = address;
    }

    /**
     * Create instance of {@link RawAddress}.
     * @param bytes byte array.
     * @return returns {@link RawAddress}/
     */
    public static RawAddress valueOf(byte[] bytes) {
        return new RawAddress(bytes);
    }

    @Override
    public byte[] getAddress() {
        byte[] data = new byte[address.length];
        System.arraycopy(address, 0, data, 0, data.length);
        return data;
    }

}
