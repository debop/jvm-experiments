package kr.nsoft.commons.collection;

import com.google.common.collect.Lists;
import kr.nsoft.commons.tools.StringTool;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * kr.nsoft.commons.collection.RangeTest
 * User: sunghyouk.bae@gmail.com
 * Date: 13. 1. 12.
 */
@Slf4j
public class RangeTest {

    @Test
    public void createIntRange() {
        Range.IntRange intRange = Range.range(10);
        Assert.assertEquals(0, intRange.getFromInclude());
        Assert.assertEquals(10, intRange.getToExclude());
        Assert.assertEquals(1, intRange.getStep());
        Assert.assertEquals(10, intRange.size());

        log.debug(StringTool.join(intRange, ","));
        intRange.reset();
        for (int x : intRange)
            System.out.print(x + ", ");
    }

    @Test
    public void createIntPartition() {
        int from = 0;
        int to = 100;
        int partitionCount = 4;
        int partitionSize = (to - from) / partitionCount + ((to - from) % partitionCount > 0 ? 1 : 0);

        List<Range.IntRange> ranges = Range.partition(from, to, partitionCount);

        Assert.assertEquals(4, ranges.size());

        for (int i = 0; i < partitionCount; i++) {
            Range.IntRange intRange = ranges.get(i);

            Assert.assertEquals(from + i * partitionSize, intRange.getFromInclude());
            Assert.assertEquals(from + (i + 1) * partitionSize, intRange.getToExclude());
            Assert.assertEquals(1, intRange.getStep());
            Assert.assertEquals(partitionSize, intRange.size());
        }
    }

    @Test
    public void createIntPartitionUnnormal() {
        int from = 0;
        int to = 102;
        int partitionCount = 4;
        int partitionSize = (to - from) / partitionCount;

        List<Range.IntRange> ranges = Range.partition(from, to, partitionCount);
        Assert.assertEquals(4, ranges.size());

        List<int[]> expectedList =
                Lists.newArrayList(
                        new int[]{0, 26, 1, 26},
                        new int[]{26, 52, 1, 26},
                        new int[]{52, 77, 1, 25},
                        new int[]{77, 102, 1, 25});

        for (int i = 0; i < partitionCount; i++) {
            Range.IntRange intRange = ranges.get(i);
            int[] expected = expectedList.get(i);
            if (log.isDebugEnabled())
                log.debug("Range({})=[{}]", i, intRange);

            Assert.assertEquals(expected[0], intRange.getFromInclude());
            Assert.assertEquals(expected[1], intRange.getToExclude());
            Assert.assertEquals(expected[2], intRange.getStep());
            Assert.assertEquals(expected[3], intRange.size());
        }
    }

    @Test
    public void createIntPartitionUnnormalInverse() {
        int from = 102;
        int to = 0;
        int partitionCount = 4;
        int partitionSize = (to - from) / partitionCount;

        List<Range.IntRange> ranges = Range.partition(from, to, partitionCount);
        Assert.assertEquals(4, ranges.size());

        List<int[]> expectedList =
                Lists.newArrayList(
                        new int[]{102, 76, -1, 26},
                        new int[]{76, 50, -1, 26},
                        new int[]{50, 25, -1, 25},
                        new int[]{25, 0, -1, 25});

        for (int i = 0; i < partitionCount; i++) {
            Range.IntRange intRange = ranges.get(i);
            int[] expected = expectedList.get(i);
            if (log.isDebugEnabled())
                log.debug("Range({})=[{}]", i, intRange);

            Assert.assertEquals(expected[0], intRange.getFromInclude());
            Assert.assertEquals(expected[1], intRange.getToExclude());
            Assert.assertEquals(expected[2], intRange.getStep());
            Assert.assertEquals(expected[3], intRange.size());
        }
    }
}
