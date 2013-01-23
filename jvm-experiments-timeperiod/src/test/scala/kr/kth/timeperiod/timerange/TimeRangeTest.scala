package kr.kth.timeperiod.timerange

import kr.kth.timeperiod.{TimeRange, Times, AbstractTest}
import org.joda.time.DateTime
import org.junit.Assert._
import org.junit.Test

/**
 * kr.kth.timeperiod.timerange.TimeRangeTest
 * User: sunghyouk.bae@gmail.com
 * Date: 13. 1. 23
 */
class TimeRangeTest extends AbstractTest {

    @Test
    def createTest() {
        val now: DateTime = Times.getNow
        val timeRange: TimeRange = new TimeRange(now, now, false)
        if (log.isDebugEnabled)
            log.debug("today=[{}], timeRange=[{}]", now, timeRange)

        assertNotNull(timeRange)
        assertEquals(now, timeRange.getStart)
        assertEquals(now, timeRange.getEnd)
        assertFalse(timeRange.isReadonly)
        assertFalse(timeRange.isAnytime)
        assertTrue(timeRange.isMoment)
    }
}
