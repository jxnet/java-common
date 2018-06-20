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

/**
 * This class is a abstraction for IP Address.
 * @see Inet4Address
 * @see java.net.InetAddress
 * @author <a href="mailto:contact@ardikars.com">Ardika Rommy Sanjaya</a>
 * @since 1.0.0
 */
public abstract class InetAddress {

	/**
	 * Determines the IPv4 or IPv6 address.
	 * @param stringAddress ipv4 or ipv6 string address.
	 * @return an IPv4 or IPv6 address.
	 */
	public static InetAddress valueOf(String stringAddress) {
		if (stringAddress.contains(":")) {
			return Inet6Address.valueOf(stringAddress);
		} else {
			return Inet4Address.valueOf(stringAddress);
		}
	}

	/**
	 * Validate given ip string address.
	 * @param stringAddress ipv4 or ipv6 string address.
	 * @return a {@code boolean} indicating if the stringAddress is a valid ip address;
	 * or false otherwise.
	 */
	public static boolean isValidAddress(String stringAddress) {
		try {
			valueOf(stringAddress);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	protected abstract boolean isMulticastAddress();

	protected abstract boolean isAnyLocalAddress();

	protected abstract boolean isLoopbackAddress();

	protected abstract boolean isLinkLocalAddress();

	protected abstract boolean isSiteLocalAddress();

	protected abstract boolean isMcGlobal();

	protected abstract boolean isMcNodeLocal();

	protected abstract boolean isMcLinkLocal();

	protected abstract boolean isMcSiteLocal();

	protected abstract boolean isMcOrgLocal();

	protected abstract byte[] toBytes();

}
