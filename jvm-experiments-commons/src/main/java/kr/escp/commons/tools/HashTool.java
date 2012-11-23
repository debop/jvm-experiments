package kr.escp.commons.tools;

import lombok.extern.slf4j.Slf4j;

/**
 * hashcode 를 생성하는 tool 입니다. {@link com.google.common.base.Objects#hashCode(Object...)} 를 사용하세요.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 9. 12
 */
@Slf4j
public final class HashTool {

	public static final int NullValue = 0;
	public static final int OneValue = 1;
	static final int Factor = 31;

	private HashTool() { }

	/**
	 * 지정한 객체의 Hash Code 를 반환합니다. 객체가 null이면, 0을 반환합니다.
	 *
	 * @param x hashcode 값을 얻고자하는 객체
	 * @return 객체의 hashcode 값
	 */
	public static int compute(Object x) {
		return (x != null) ? x.hashCode() : NullValue;
	}

	/**
	 * 지정된 객체들의 Hash Code를 조합한 Hash Code를 생성합니다.
	 *
	 * @param x
	 * @param y
	 * @return
	 */
	public static int compute(Object x, Object y) {
		return compute(x) * Factor + compute(y);
	}

	/**
	 * 지정된 객체들의 Hash Code를 조합한 Hash Code를 생성합니다.
	 *
	 * @param x
	 * @param y
	 * @param z
	 * @return
	 */
	public static int compute(Object x, Object y, Object z) {
		return compute(x, y) * Factor + compute(z);
	}

	/**
	 * 지정된 객체들의 Hash Code를 조합한 Hash Code를 생성합니다.
	 *
	 * @param objs 해쉬코드를 생성할 객체 배열
	 * @return 조합된 Hash code
	 */
	public static int compute(Object... objs) {
		int hash = NullValue;

		if (objs == null || objs.length == 0)
			return hash;

		for (Object x : objs) {
			hash = hash * Factor + compute(x);
		}
		return hash;
	}
}
