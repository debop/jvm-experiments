package kr.ecsp.commons.tools;

import lombok.extern.slf4j.Slf4j;

/**
 * 설명을 추가하세요.
 * JpaUser: sunghyouk.bae@gmail.com
 * Date: 12. 11. 19
 */
@Slf4j
public class StringTool {

	public static final byte[] MultiBytesPrefixBytes = new byte[]{(byte) 0xEF, (byte) 0xBB, (byte) 0xBF};
	public static final char[] WhiteSpaceChars = new char[]{' ', '\t', '\n', '\r', '\f', '\b'};
	public static final String TrimmingString = "...";
	public static final String NullStr = "NULL";

	private StringTool() {}

	public static boolean isNull(String str) {
		return (str == null);
	}

	public static boolean isNotNull(String str) {
		return (str != null);
	}

	public static boolean isEmpty(String str) {
		return isEmpty(str, true);
	}

	public static boolean isEmpty(String str, boolean withTrim) {
		return isNull(str) || (((withTrim) ? str.trim() : str).length() == 0);
	}

	public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}

	public static boolean isNotEmpty(String str, boolean withTrim) {
		return !isEmpty(str, withTrim);
	}

	/**
	 * 문자열 내부에 공백이나 제어문자가 있는지 검사한다.
	 *
	 * @param str 검사할 문자열
	 * @return 문자열 내부에 공백이나 제어문제가 있는 경우 true 를 반환, 그외는 false
	 */
	static boolean hasWhiteSpaceChars(String str) {
		if (str == null)
			return true;

		for (int i = 0; i < str.length(); i++)
			for (char x : WhiteSpaceChars)
				if (str.charAt(i) == x)
					return true;

		return false;
	}

	/**
	 * 문자열이 null이거나 공백만 있거나, 제어문자만 있는 경우에는 White Space 문자열이라 판단하여 true를 반환합니다.
	 *
	 * @param str 검사할 문자열
	 * @return 문자열이 White Space 문자열인지 여부
	 */
	public static boolean isWhiteSpace(String str) {
		return isNull(str) ||
			       str.trim().length() == 0 ||
			       hasWhiteSpaceChars(str);
	}

	/**
	 * 문자열이 White Space 문자열이 아님을 검사합니다.
	 *
	 * @param str 검사할 문자열
	 * @return 문자열이 WhiteSpace 문자열이 아닌 경우 True 를 반환합니다.
	 */
	public static boolean isNotWhiteSpace(String str) {
		return !isWhiteSpace(str);
	}
}
