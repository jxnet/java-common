package com.ardikars.common.util.buffer;

import com.ardikars.common.util.BaseTest;
import com.ardikars.common.util.Platforms;
import org.junit.Test;

import java.nio.ByteBuffer;

/**
 * @author common 2018/12/13
 * @author <a href="mailto:contact@ardikars.com">Langkuy</a>
 */
public class ByteBuffersTest extends BaseTest {

    @Test
    public void getDirectBufferAddress() {
        if (Platforms.getJavaMojorVersion() < 9) {
            ByteBuffer direct = ByteBuffer.allocateDirect(1);
            long address = ByteBuffers.directByteBufferAddress(direct);
            assert address != 0;
        } else {
            assert true;
        }
    }

    @Test
    public void allocateUnsafeDirectByteBuffer() {
        if (Platforms.getJavaMojorVersion() < 9) {
//        ByteBuffer buffer = ByteBuffer.allocateDirect(4 * 2);
            ByteBuffer buffer = ByteBuffers.allocateDirectByteBuffer(4 * 2);
//        buffer.putInt(9);
//        buffer.putInt(8);
//        buffer.rewind();
            int a = buffer.getInt();
            int b = buffer.getInt();
            System.out.println(a + " : " + b);
            System.out.println(buffer.capacity());
            ByteBuffers.freeDirectByteBuffer(buffer);
        } else {
            assert true;
        }
    }

    @Test
    public void freeDirectBuffer() throws InterruptedException {
        if (Platforms.getJavaMojorVersion() < 9) {
            ByteBuffer direct = ByteBuffer.allocateDirect(4);
            direct.putInt(4);
            direct.rewind();
            int b = direct.getInt();
            direct.rewind();
            assert b == 4;
            b = -1;
            ByteBuffers.freeDirectByteBuffer(direct);
            Thread.sleep(1000); // wait 1 second for free the buffer.
            b = direct.getInt();
            assert b != 4;
        } else {
            assert true;
        }
    }

}
