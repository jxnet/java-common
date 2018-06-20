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

package com.ardikars.common.util;

import com.ardikars.common.annotation.Mutable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Native library loader (inner jar).
 * @author <a href="mailto:contact@ardikars.com">Ardika Rommy Sanjaya</a>
 * @since 1.0.0
 */
@Mutable
public final class IntenalNativeLibrary implements Loader<Void> {

    private static final int BUFFER_SIZE = 1024;

    private final Set<String> libraryPaths = new HashSet<>();

    /**
     * Load all registered (inner jar) native libarary.
     * @param callback callback.
     */
    @Override
    public void load(Callback<Void> callback) {
        Iterator<String> iterator = libraryPaths.iterator();
        while (iterator.hasNext()) {
            String path = iterator.next();
            if (path == null || path.isEmpty()) {
                callback.onFailure(new NullPointerException("Path should be not null or empty string."));
            }
            if (!(path.charAt(0) == '/')) {
                callback.onFailure(new IllegalArgumentException("The path has to be absolute (start with '/')."));
            }
            String[] parts = Pattern.compile("/").split(path);
            if (parts != null && parts.length > 1) {
                parts = Pattern.compile("\\.").split(parts[parts.length - 1]);
            } else {
                callback.onFailure(new IllegalArgumentException("Failed to compile path: " + path));
            }
            File temp = null;
            try {
                temp = File.createTempFile(parts[0], "." + parts[1]);
                temp.deleteOnExit();
            } catch (IOException e) {
                callback.onFailure(e);
            }
            final byte[] buffer = new byte[BUFFER_SIZE];
            int readBytes;
            final InputStream is = IntenalNativeLibrary.class.getResourceAsStream(path);
            if (is == null) {
                callback.onFailure(new IOException("Error: " + path + " not found in classpath."));
            }
            OutputStream os = null;
            try {
                os = new FileOutputStream(temp);
                while ((readBytes = is.read(buffer)) != -1) {
                    try {
                        os.write(buffer, 0, readBytes);
                    } catch (IOException e) {
                        callback.onFailure(e);
                    }
                }
            } catch (IOException e) {
                callback.onFailure(e);
            } finally {
                try {
                    if (os != null) {
                        os.close();
                    }
                    if (is != null) {
                        is.close();
                    }
                } catch (IOException e) {
                    callback.onFailure(e);
                }
            }
            System.load(temp.getAbsolutePath());
        }
    }

    /**
     * Add (inner jar) native library path.
     * @param libararyPath library path (start with {@code &#39;/&#39;}).
     */
    public void register(String libararyPath) {
        synchronized (this) {
            this.libraryPaths.add(libararyPath);
        }
    }

}
