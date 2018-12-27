package com.ardikars.common.util.management;

import com.ardikars.common.util.Sets;

import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Garbage collector status
 * @since 1.2.3
 */
final class DefaultGarbageCollector implements GarbageCollector {

    private static final Set<String> YOUNG_GC;
    private static final Set<String> OLD_GC;

    private final long majorCollectionCount;
    private final long majorCollectionTime;
    private final long minorCollectionCount;
    private final long minorCollectionTime;
    private final long unknownCollectionCount;
    private final long unknownCollectionTime;

    public DefaultGarbageCollector(Builder builder) {
        majorCollectionCount = builder.majorCount;
        majorCollectionTime = builder.majorTime;
        minorCollectionCount = builder.minorCount;
        minorCollectionTime = builder.minorTime;
        unknownCollectionCount = builder.unknownCount;
        unknownCollectionTime = builder.unknownTime;
    }

    @Override
    public long getMajorCollectionCount() {
        return majorCollectionCount;
    }

    @Override
    public long getMajorCollectionTime() {
        return majorCollectionTime;
    }

    @Override
    public long getMinorCollectionCount() {
        return minorCollectionCount;
    }

    @Override
    public long getMinorCollectionTime() {
        return minorCollectionTime;
    }

    @Override
    public long getUnknownCollectionCount() {
        return unknownCollectionCount;
    }

    @Override
    public long getUnknownCollectionTime() {
        return unknownCollectionTime;
    }

    public static class Builder implements com.ardikars.common.util.Builder<DefaultGarbageCollector, List<GarbageCollectorMXBean>> {

        private List<GarbageCollectorMXBean> garbageCollectorMXBeans;

        private long minorCount = 0;
        private long minorTime = 0;
        private long majorCount = 0;
        private long majorTime = 0;
        private long unknownCount = 0;
        private long unknownTime = 0;

        public Builder garbageCollectorMXBeans(List<GarbageCollectorMXBean> garbageCollectorMXBeans) {
            this.garbageCollectorMXBeans = garbageCollectorMXBeans;
            return this;
        }

        private void calculate(Iterator<GarbageCollectorMXBean> iterator) {
            while (iterator.hasNext()) {
                GarbageCollectorMXBean gcMxBean = iterator.next();
                long count = gcMxBean.getCollectionCount();
                if (count >= 0) {
                    if (YOUNG_GC.equals(gcMxBean.getName())) {
                        minorCount += count;
                        minorTime += gcMxBean.getCollectionTime();
                    } else if (OLD_GC.equals(gcMxBean.getName())) {
                        majorCount += count;
                        majorTime += gcMxBean.getCollectionTime();
                    } else {
                        unknownCount += count;
                        unknownTime += gcMxBean.getCollectionTime();
                    }
                }
            }
        }

        @Override
        public DefaultGarbageCollector build() {
            Iterator<GarbageCollectorMXBean> iterator = ManagementFactory.getGarbageCollectorMXBeans().iterator();
            calculate(iterator);
            return new DefaultGarbageCollector(this);
        }

        @Override
        public DefaultGarbageCollector build(List<GarbageCollectorMXBean> value) {
            calculate(value.iterator());
            return new DefaultGarbageCollector(this);
        }

    }

    @Override
    public String toString() {
        return new StringBuilder("DefaultGarbageCollector{")
            .append("majorCollectionCount=").append(majorCollectionCount)
            .append(", majorCollectionTime=").append(majorCollectionTime)
            .append(", minorCollectionCount=").append(minorCollectionCount)
            .append(", minorCollectionTime=").append(minorCollectionTime)
            .append(", unknownCollectionCount=").append(unknownCollectionCount)
            .append(", unknownCollectionTime=").append(unknownCollectionTime)
            .append('}').toString();
    }

    static {
        final Set<String> youngGC = Sets.createHashSet(3);
        youngGC.add("PS Scavenge");
        youngGC.add("ParNew");
        youngGC.add("G1 Young Generation");
        YOUNG_GC = Collections.unmodifiableSet(youngGC);

        final Set<String> oldGC = Sets.createHashSet(3);
        oldGC.add("PS MarkSweep");
        oldGC.add("ConcurrentMarkSweep");
        oldGC.add("G1 Old Generation");
        OLD_GC = Collections.unmodifiableSet(oldGC);
    }

}
