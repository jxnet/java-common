package com.ardikars.common.util.buffer;

import com.ardikars.common.util.BaseTest;
import org.junit.Test;

import java.nio.ByteBuffer;

/**
 * @author common 2018/12/13
 * @author <a href="mailto:contact@ardikars.com">Langkuy</a>
 */
public class ByteBuffersTest extends BaseTest {

    @Test
    public void getDirectBufferAddress() {
        ByteBuffer direct = ByteBuffer.allocateDirect(1);
        long address = ByteBuffers.directBufferAddress(direct);
        assert address != 0;
    }

    @Test
    public void freeDirectBuffer() throws InterruptedException {
        ByteBuffer direct = ByteBuffer.allocateDirect(4);
        direct.putInt(4);
        direct.rewind();
        int b = direct.getInt();
        direct.rewind();
        assert b == 4;
        b = -1;
        ByteBuffers.freeDirectBuffer(direct);
        Thread.sleep(1000); // wait 1 second for free the buffer.
        b = direct.getInt();
        assert b != 4;
    }

}
