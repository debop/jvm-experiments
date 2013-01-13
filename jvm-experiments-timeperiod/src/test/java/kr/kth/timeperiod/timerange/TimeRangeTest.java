package kr.kth.timeperiod.timerange;

import kr.kth.timeperiod.AbstractTest;
import kr.kth.timeperiod.tools.TimeTool;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * pudding.pudding.commons.timeperiod.timerange.TimeRangeTest
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 9. 30.
 */
@Slf4j
public class TimeRangeTest extends AbstractTest {

    @Test
    public void createTest() {

        DateTime now = TimeTool.getNow();
        TimeRange timeRange = new TimeRange(now);

        if (log.isDebugEnabled())
            log.debug("today=[{}], timeRange=[{}]", now, timeRange);

        assertNotNull(timeRange);
        assertEquals(now, timeRange.getStart());
        assertEquals(now, timeRange.getEnd());
        assertFalse(timeRange.isReadonly());
        assertFalse(timeRange.isAnyTime());
        assertTrue(timeRange.isMoment());
    }
}
