package kr.kth.commons.unitTesting;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.Callable;

/**
 * kr.kth.commons.unitTesting.TestToolTest
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 3.
 */
@Slf4j
public class TestToolTest {

	private static final int LowerBound = 0;
	private static final int UpperBound = 1000;


	@Test
	public void runTasksWithAction() {

		TestTool.runTasks(100,
		                  new Runnable() {
			                  @Override
			                  public void run() {
				                  for (int i = LowerBound; i < UpperBound; i++) {
					                  if (i % 10 == 0 && log.isDebugEnabled())
						                  log.debug("FindRoot({}) returns [{}]", i, Hero.findRoot(i));
					                  Hero.findRoot(i);
				                  }
			                  }
		                  });
	}

	@Test
	public void runTasksWithCallables() {

		Callable<Double> callable = new Callable<Double>() {
			@Override
			public Double call() throws Exception {
				for (int i = LowerBound; i < UpperBound; i++) {
					Hero.findRoot(i);
				}
				if (log.isDebugEnabled())
					log.debug("FindRoot({}) returns [{}]", UpperBound, Hero.findRoot(UpperBound));
				return Hero.findRoot(UpperBound);
			}
		};
		TestTool.runTasks(100, callable, callable);
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
