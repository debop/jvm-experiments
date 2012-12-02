package kr.kth.commons.period;

import kr.kth.commons.AbstractTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * {@link TimeSpec} 단위 테스트
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 9. 30.
 */
@Slf4j
public class TimeSpecTest extends AbstractTest {

	@Test
	public void testTimeSpec() {
		assertEquals(0, TimeSpec.ZeroTimestamp.getTime());
	}
}
