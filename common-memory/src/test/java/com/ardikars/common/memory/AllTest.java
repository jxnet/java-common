package com.ardikars.common.memory;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
    InternalByteBufferHelperTest.class,
    MemoriesTest.class,
    NativeMemoryTest.class,
    NativeMemoryWriterAndReaderTest.class,
    NativeMemorySetterAndGetterTest.class,
    DirectMemoryTest.class,
    DirectMemoryWriterAndReaderTest.class,
    DirectMemorySetterAndGetterTest.class
})
public class AllTest {

}
