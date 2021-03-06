package kr.nsoft.commons.parallelism;

import kr.nsoft.commons.Action1;
import kr.nsoft.commons.AutoStopwatch;
import kr.nsoft.commons.Function1;
import kr.nsoft.commons.collection.NumberRange;
import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * User: sunghyouk.bae@gmail.com
 * Date: 13. 1. 11
 */
@Slf4j
public class ParallelsTest {

    private static final int LowerBound = 0;
    private static final int UpperBound = 99999;

    @Test
    public void parallelRunAction() {
        final Action1<Integer> action1 =
                new Action1<Integer>() {
                    @Override
                    public void perform(Integer x) {
                        for (int i = LowerBound; i < UpperBound; i++) {
                            Hero.findRoot(i);
                        }
                        if (log.isDebugEnabled())
                            log.debug("FindRoot({}) returns [{}]", UpperBound, Hero.findRoot(UpperBound));
                    }
                };

        @Cleanup
        AutoStopwatch stopwatch = new AutoStopwatch();
        Parallels.run(0, 100, action1);
    }

    @Test
    public void parallelRunEachAction() {
        final Action1<Integer> action1 =
                new Action1<Integer>() {
                    @Override
                    public void perform(Integer x) {
                        for (int i = LowerBound; i < UpperBound; i++) {
                            Hero.findRoot(i);
                        }
                        if (log.isDebugEnabled())
                            log.debug("FindRoot({}) returns [{}]", UpperBound, Hero.findRoot(UpperBound));
                    }
                };

        @Cleanup
        AutoStopwatch stopwatch = new AutoStopwatch();
        Parallels.runEach(NumberRange.range(0, 100), action1);
    }

    @Test
    public void parallelRunFunction() {
        final Function1<Integer, Double> function1 =
                new Function1<Integer, Double>() {
                    @Override
                    public Double execute(Integer x) {
                        for (int i = LowerBound; i < UpperBound; i++) {
                            Hero.findRoot(i);
                        }
                        if (log.isDebugEnabled())
                            log.debug("FindRoot({}) returns [{}]", UpperBound, Hero.findRoot(UpperBound));
                        return Hero.findRoot(UpperBound);
                    }
                };

        @Cleanup
        AutoStopwatch stopwatch = new AutoStopwatch();
        List<Double> results = Parallels.run(0, 100, function1);

        Assert.assertNotNull(results);
        Assert.assertEquals(100, results.size());
    }

    @Test
    public void parallelRunEachFunction() {
        final Function1<Integer, Double> function1 =
                new Function1<Integer, Double>() {
                    @Override
                    public Double execute(Integer x) {
                        for (int i = LowerBound; i < UpperBound; i++) {
                            Hero.findRoot(i);
                        }
                        if (log.isDebugEnabled())
                            log.debug("FindRoot({}) returns [{}]", UpperBound, Hero.findRoot(UpperBound));
                        return Hero.findRoot(UpperBound);
                    }
                };

        @Cleanup
        AutoStopwatch stopwatch = new AutoStopwatch();
        List<Double> results = Parallels.runEach(NumberRange.range(0, 100), function1);
        Assert.assertNotNull(results);
        Assert.assertEquals(100, results.size());
    }

    public static class Hero {

        private static final double Tolerance = 1.0e-10;

        public static double findRoot(double number) {
            double guess = 1.0;
            double error = Math.abs(guess * guess - number);

            while (error > Tolerance) {
                guess = (number / guess + guess) / 2.0;
                error = Math.abs(guess * guess - number);
            }
            return guess;
        }
    }
}
