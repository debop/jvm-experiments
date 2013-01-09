package scala.impatient;

import kr.kth.commons.slf4j.Logger;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13. 1. 8
 * Time: 오전 10:08
 */
public class LoggingTest {

    @Test
    public void loggingTest() {
        Logger log = Logger.apply(getClass());

        Assert.assertNotNull(log);
        log.debug("String [{}]", "abc");
    }
}
