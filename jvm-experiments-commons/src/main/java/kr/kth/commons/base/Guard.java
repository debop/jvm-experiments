package kr.kth.commons.base;

import kr.kth.commons.tools.StringTool;
import lombok.extern.slf4j.Slf4j;

import static java.lang.String.format;


/**
 * Guarded Pattern 을 이용하여, 작업 수행 전/후에 조건을 검사하고, 조건에 위배되는 경우 예외를 발생시킵니다.
 * {@link com.google.common.base.Preconditions} 과 유사합니다만, 더 많은 검사를 쉽게 할 수 있습니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 9. 12
 */
@Slf4j
public final class Guard {

	private Guard() {}

	public static <T> T firstNotNull(T first, T second) {
		if (first != null)
			return first;
		if (second != null)
			return second;

		throw new IllegalArgumentException("all parameters is null.");
	}

	public static void assertTrue(boolean condition) {
		if (!condition)
			throw new IllegalArgumentException();
	}

	public static void assertTrue(boolean condition, String format, Object... args) {
		if (!condition)
			throw new IllegalArgumentException(format(format, args));
	}

	public static void shouldBeEquals(Object actual,
	                                  Object expected,
	                                  final String actualName) {
		if (actual != expected)
			throw new IllegalArgumentException(format(SR.ShouldBeEquals, actualName, actual, expected));
	}

	public static void shouldNotBeEquals(Object actual,
	                                     Object expected,
	                                     final String actualName) {
		if (actual == expected)
			throw new IllegalArgumentException(format(SR.ShouldNotBeEquals, actualName, actual, expected));
	}

	public static <T> void shouldBeNull(T arg, final String argName) {
		if (arg != null)
			throw new IllegalArgumentException(format(SR.ShouldBeNull, argName));
	}

	public static <T> T shouldNotBeNull(T arg, final String argName) {
		if (arg == null)
			throw new IllegalArgumentException(format(SR.ShouldNotBeNull, argName));

		return arg;
	}

	public static String shouldBeEmpty(String arg, final String argName) {
		if (StringTool.isNotEmpty(arg))
			throw new IllegalArgumentException(format(SR.ShouldBeEmptyString, argName));
		return arg;
	}

	public static String shouldNotBeEmpty(String arg, final String argName) {
		if (StringTool.isEmpty(arg))
			throw new IllegalArgumentException(format(SR.ShouldNotBeEmptyString, argName));
		return arg;
	}

	public static String shouldBeWhiteSpace(String arg, final String argName) {
		if (StringTool.isNotWhiteSpace(arg))
			throw new IllegalArgumentException(format(SR.ShouldBeWhiteSpace, argName));

		return arg;
	}

	public static String shouldNotBeWhiteSpace(String arg, final String argName) {
		if (StringTool.isWhiteSpace(arg))
			throw new IllegalArgumentException(format(SR.ShouldNotBeWhiteSpace, argName));

		return arg;
	}

	public static int shouldBePositiveNumber(int arg, final String argName) {
		if (arg <= 0)
			throw new IllegalArgumentException(format(SR.ShouldBePositiveNumber, argName));
		return arg;
	}

	public static long shouldBePositiveNumber(long arg, final String argName) {
		if (arg <= 0L)
			throw new IllegalArgumentException(format(SR.ShouldBePositiveNumber, argName));
		return arg;
	}

	public static float shouldBePositiveNumber(float arg, final String argName) {
		if (arg <= 0f)
			throw new IllegalArgumentException(format(SR.ShouldBePositiveNumber, argName));
		return arg;
	}

	public static double shouldBePositiveNumber(double arg, final String argName) {
		if (arg <= 0.0)
			throw new IllegalArgumentException(format(SR.ShouldBePositiveNumber, argName));
		return arg;
	}

	public static int shouldNotBePositiveNumber(int arg, final String argName) {
		if (arg > 0)
			throw new IllegalArgumentException(format(SR.ShouldNotBePositiveNumber, argName));
		return arg;
	}

	public static long shouldNotBePositiveNumber(long arg, final String argName) {
		if (arg > 0L)
			throw new IllegalArgumentException(format(SR.ShouldNotBePositiveNumber, argName));
		return arg;
	}

	public static float shouldNotBePositiveNumber(float arg, final String argName) {
		if (arg > 0f)
			throw new IllegalArgumentException(format(SR.ShouldNotBePositiveNumber, argName));
		return arg;
	}

	public static double shouldNotBePositiveNumber(double arg, final String argName) {
		if (arg > 0.0)
			throw new IllegalArgumentException(format(SR.ShouldNotBePositiveNumber, argName));
		return arg;
	}

	public static int shouldBeNegativeNumber(int arg, final String argName) {
		if (arg < 0)
			throw new IllegalArgumentException(format(SR.ShouldBeNegativeNumber, argName));
		return arg;
	}

	public static long shouldBeNegativeNumber(long arg, final String argName) {
		if (arg < 0L)
			throw new IllegalArgumentException(format(SR.ShouldBeNegativeNumber, argName));
		return arg;
	}

	public static float shouldBeNegativeNumber(float arg, final String argName) {
		if (arg < 0f)
			throw new IllegalArgumentException(format(SR.ShouldBeNegativeNumber, argName));
		return arg;
	}

	public static double shouldBeNegativeNumber(double arg, final String argName) {
		if (arg < 0.0)
			throw new IllegalArgumentException(format(SR.ShouldBeNegativeNumber, argName));
		return arg;
	}

	public static int shouldNotBeNegativeNumber(int arg, final String argName) {
		if (arg >= 0)
			throw new IllegalArgumentException(format(SR.ShouldNotBeNegativeNumber, argName));
		return arg;
	}

	public static long shouldNotBeNegativeNumber(long arg, final String argName) {
		if (arg >= 0L)
			throw new IllegalArgumentException(format(SR.ShouldNotBeNegativeNumber, argName));
		return arg;
	}

	public static float shouldNotBeNegativeNumber(float arg, final String argName) {
		if (arg >= 0f)
			throw new IllegalArgumentException(format(SR.ShouldNotBeNegativeNumber, argName));
		return arg;
	}

	public static double shouldNotBeNegativeNumber(double arg, final String argName) {
		if (arg >= 0.0)
			throw new IllegalArgumentException(format(SR.ShouldNotBeNegativeNumber, argName));
		return arg;
	}
}
