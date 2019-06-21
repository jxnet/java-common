package com.ardikars.common.memory;

import com.ardikars.common.memory.internal.ByteBufferHelperTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
    ByteBufferHelperTest.class,
    MemoriesTest.class,
    UncheckedMemoryTest.class,
    UncheckedMemoryWriterAndReaderTest.class,
    UncheckedMemorySetterAndGetterTest.class,
    CheckedMemoryTest.class,
    CheckedMemoryWriterAndReaderTest.class,
    CheckedMemorySetterAndGetterTest.class,
//    PooledCheckedMemory.class,
    PooledCheckedMemorySetterAndGetterTest.class,
    PooledCheckedMemoryWriterAndReaderTest.class,
//    PooledUncheckedMemory.class,
    PooledUncheckedMemorySetterAndGetterTest.class,
    PooledUncheckedMemoryWriterAndReaderTest.class
})
public class AllTest {

}
