package kr.kth.commons;

import kr.kth.commons.tools.StringTool;

import static java.lang.String.format;


/**
 * Guarded Pattern 을 이용하여, 작업 수행 전/후에 조건을 검사하고, 조건에 위배되는 경우 예외를 발생시킵니다.
 * {@link com.google.common.base.Preconditions} 과 유사합니다만, 더 많은 검사를 쉽게 할 수 있습니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 9. 12
 */
public class Guard {

	public static <T> T firstNotNull(T first, T second) {
		if (first != null)
			return first;
		if (second != null)
			return second;

		throw new IllegalArgumentException("all parameters is null.");
	}

	public static void assertTrue(boolean condition) throws IllegalArgumentException {
		if (!condition)
			throw new IllegalArgumentException();
	}

	public static void assertTrue(boolean condition, String format, Object... args) throws IllegalArgumentException {
		if (!condition)
			throw new IllegalArgumentException(format(format, args));
	}

	public static void shouldBeEquals(Object actual,
	                                  Object expected,
	                                  String actualName) throws IllegalArgumentException {
		if (actual != expected)
			throw new IllegalArgumentException(format(SR.ShouldBeEquals, actualName, actual, expected));
	}

	public static void shouldNotBeEquals(Object actual,
	                                     Object expected,
	                                     String actualName) throws IllegalArgumentException {
		if (actual == expected)
			throw new IllegalArgumentException(String.format(SR.ShouldNotBeEquals, actualName, actual, expected));
	}

	public static <T> void shouldBeNull(T arg, String argName) throws IllegalArgumentException {
		if (arg != null)
			throw new IllegalArgumentException(String.format(SR.ShouldBeNull, argName));
	}

	public static <T> T shouldNotBeNull(T arg, String argName) throws IllegalArgumentException {
		if (arg == null)
			throw new IllegalArgumentException(String.format(SR.ShouldNotBeNull, argName));

		return arg;
	}

	public static String shouldBeEmpty(String arg, String argName) throws IllegalArgumentException {
		if (StringTool.isNotEmpty(arg))
			throw new IllegalArgumentException(String.format(SR.ShouldBeEmptyString, argName));

		return arg;
	}

	public static String shouldNotBeEmpty(String arg, String argName) throws IllegalArgumentException {
		if (StringTool.isEmpty(arg))
			throw new IllegalArgumentException(String.format(SR.ShouldNotBeEmptyString, argName));

		return arg;
	}

	public static String shouldBeWhiteSpace(String arg, String argName) throws IllegalArgumentException {
		if (StringTool.isNotWhiteSpace(arg))
			throw new IllegalArgumentException(String.format(SR.ShouldBeWhiteSpace, argName));

		return arg;
	}

	public static String shouldNotBeWhiteSpace(String arg, String argName) throws IllegalArgumentException {
		if (StringTool.isWhiteSpace(arg))
			throw new IllegalArgumentException(String.format(SR.ShouldNotBeWhiteSpace, argName));

		return arg;
	}

	public static void shouldBePositiveNumber(int arg, String argName) throws IllegalArgumentException {
		if (arg <= 0)
			throw new IllegalArgumentException(String.format(SR.ShouldBePositiveNumber, argName));
	}

	public static void shouldBePositiveNumber(long arg, String argName) throws IllegalArgumentException {
		if (arg <= 0L)
			throw new IllegalArgumentException(String.format(SR.ShouldBePositiveNumber, argName));
	}

	public static void shouldBePositiveNumber(float arg, String argName) throws IllegalArgumentException {
		if (arg <= 0f)
			throw new IllegalArgumentException(String.format(SR.ShouldBePositiveNumber, argName));
	}

	public static void shouldBePositiveNumber(double arg, String argName) throws IllegalArgumentException {
		if (arg <= 0.0)
			throw new IllegalArgumentException(String.format(SR.ShouldBePositiveNumber, argName));
	}

	public static void shouldNotBePositiveNumber(int arg, String argName) throws IllegalArgumentException {
		if (arg > 0)
			throw new IllegalArgumentException(String.format(SR.ShouldNotBePositiveNumber, argName));
	}

	public static void shouldNotBePositiveNumber(long arg, String argName) throws IllegalArgumentException {
		if (arg > 0L)
			throw new IllegalArgumentException(String.format(SR.ShouldNotBePositiveNumber, argName));
	}

	public static void shouldNotBePositiveNumber(float arg, String argName) throws IllegalArgumentException {
		if (arg > 0f)
			throw new IllegalArgumentException(String.format(SR.ShouldNotBePositiveNumber, argName));
	}

	public static void shouldNotBePositiveNumber(double arg, String argName) throws IllegalArgumentException {
		if (arg > 0.0)
			throw new IllegalArgumentException(String.format(SR.ShouldNotBePositiveNumber, argName));
	}

	public static void shouldBeNegativeNumber(int arg, String argName) throws IllegalArgumentException {
		if (arg < 0)
			throw new IllegalArgumentException(String.format(SR.ShouldBeNegativeNumber, argName));
	}

	public static void shouldBeNegativeNumber(long arg, String argName) throws IllegalArgumentException {
		if (arg < 0L)
			throw new IllegalArgumentException(String.format(SR.ShouldBeNegativeNumber, argName));
	}

	public static void shouldBeNegativeNumber(float arg, String argName) throws IllegalArgumentException {
		if (arg < 0f)
			throw new IllegalArgumentException(String.format(SR.ShouldBeNegativeNumber, argName));
	}

	public static void shouldBeNegativeNumber(double arg, String argName) throws IllegalArgumentException {
		if (arg < 0.0)
			throw new IllegalArgumentException(String.format(SR.ShouldBeNegativeNumber, argName));
	}

	public static void shouldNotBeNegativeNumber(int arg, String argName) throws IllegalArgumentException {
		if (arg >= 0)
			throw new IllegalArgumentException(String.format(SR.ShouldNotBeNegativeNumber, argName));
	}

	public static void shouldNotBeNegativeNumber(long arg, String argName) throws IllegalArgumentException {
		if (arg >= 0L)
			throw new IllegalArgumentException(String.format(SR.ShouldNotBeNegativeNumber, argName));
	}

	public static void shouldNotBeNegativeNumber(float arg, String argName) throws IllegalArgumentException {
		if (arg >= 0f)
			throw new IllegalArgumentException(String.format(SR.ShouldNotBeNegativeNumber, argName));
	}

	public static void shouldNotBeNegativeNumber(double arg, String argName) throws IllegalArgumentException {
		if (arg >= 0.0)
			throw new IllegalArgumentException(String.format(SR.ShouldNotBeNegativeNumber, argName));
	}
}
