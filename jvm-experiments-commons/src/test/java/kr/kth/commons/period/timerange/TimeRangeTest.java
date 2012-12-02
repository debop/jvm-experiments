package kr.kth.commons.period.timerange;

import kr.kth.commons.AbstractTest;
import kr.kth.commons.period.tools.TimeTool;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * pudding.pudding.commons.period.timerange.TimeRangeTest
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 9. 30.
 */
@Slf4j
public class TimeRangeTest extends AbstractTest {

	@Test
	public void createTest() {

		Date now = TimeTool.getNow();
		TimeRange timeRange = new TimeRange(now);

		if (log.isDebugEnabled())
			log.debug("now=[{}], timeRange=[{}]", now, timeRange);

		assertNotNull(timeRange);
		assertEquals(now, timeRange.getStart());
		assertEquals(now, timeRange.getEnd());
		assertFalse(timeRange.isReadonly());
		assertFalse(timeRange.isAnyTime());
		assertTrue(timeRange.isMoment());
	}
}
