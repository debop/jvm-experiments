package kr.kth.commons.tools;

import com.carrotsearch.junitbenchmarks.BenchmarkOptions;
import kr.kth.commons.YearWeek;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13. 1. 7
 * Time: 오후 4:07
 * To change this template use File | Settings | File Templates.
 */
@Slf4j
public class HashToolTest {

    /**
     * 테스트에 성공합니다. 아마 Scala IDE나 컴파일러의 문제로 인해, 에러로 뜨는 것 같습니다.
     */
    @BenchmarkOptions(benchmarkRounds = 100, warmupRounds = 1)
    @Test
    public void scalaVarargsTest() {
        int a = ScalaHash.compute(1, 2);
        int b = ScalaHash.compute(2, 1);

        Assert.assertNotEquals(a, b);
        Assert.assertEquals(a, ScalaHash.compute(1, 2));

        int withNull1 = ScalaHash.compute(new YearWeek(2013, 1), null);
        int withNull2 = ScalaHash.compute(null, new YearWeek(2013, 1));
        int withNull3 = ScalaHash.compute(new YearWeek(2013, 1), null);

        Assert.assertNotEquals(withNull1, withNull2);
        Assert.assertNotEquals(withNull2, withNull3);
        Assert.assertEquals(withNull1, withNull3);
    }
}
