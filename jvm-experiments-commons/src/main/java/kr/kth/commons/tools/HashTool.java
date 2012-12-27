package kr.kth.commons.tools;

/**
 * 설명을 추가하세요.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 27
 */
public final class HashTool {

	public static final int NullValue = 0;
	public static final int OneValue = 1;
	public static final int Factor = 31;

	private static int compute(Object x) {
		return (x != null) ? x.hashCode() : NullValue;
	}

	/**
	 * 지정된 객체들의 Hash Code를 조합한 Hash Code를 생성합니다.
	 *
	 * @param objs 해쉬코드를 생성할 객체 배열
	 * @return 조합된 Hash code
	 */
	public static int compute(Object... objs) {
		if (objs == null || objs.length == 0)
			return NullValue;

		int hash = NullValue;
		for (Object x : objs) {
			hash = hash * Factor + compute(x);
		}
		return hash;
	}
}
