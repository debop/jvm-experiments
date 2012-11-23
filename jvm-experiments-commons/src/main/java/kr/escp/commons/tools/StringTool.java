package kr.escp.commons.tools;

import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import com.google.common.primitives.Chars;
import kr.escp.commons.BinaryStringFormat;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.binary.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * 문자열 처리를 위한 Utility Class 입니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 9. 12
 */
@Slf4j
public final class StringTool {

	/**
	 * 멀티바이트 문자열을 바이트 배열로 변환 시에 선두번지에 접두사로 넣는 값입니다.
	 * 이 값이 있으면 꼭 UTF-8 으로 변환해야 한다는 뜻입니다.
	 */
	public static final byte[] MULTI_BYTES_PREFIX = new byte[] { (byte) 0xEF, (byte) 0xBB, (byte) 0xBF };
	public static final String TRIMMING_STR = "...";
	public static final String NULL_STR = "NULL";
	public static final String EMPTY_STR = "";

	private StringTool() {}

	//region << isNull / isEmpty / isWhiteSpace / isMultiByteString >>

	public static boolean isNull(final String str) {
		return (str == null);
	}

	public static boolean isNotNull(final String str) {
		return (str != null);
	}

	public static boolean isEmpty(final String str) {
		return isEmpty(str, false);
	}

	public static boolean isEmpty(final String str, boolean withTrim) {
		return isNull(str) || (((withTrim) ? str.trim() : str).length() == 0);
	}

	public static boolean isNotEmpty(final String str) {
		return !isEmpty(str);
	}

	public static boolean isNotEmpty(final String str, boolean withTrim) {
		return !isEmpty(str, withTrim);
	}

	public static boolean isWhiteSpace(final String str) {
		return isEmpty(str, true);
	}

	public static boolean isNotWhiteSpace(final String str) {
		return !isEmpty(str, true);
	}

	public static boolean isMultiByteString(final byte[] bytes) {
		if (bytes == null || bytes.length < MULTI_BYTES_PREFIX.length)
			return false;

		return Arrays.equals(MULTI_BYTES_PREFIX,
		                     Arrays.copyOf(bytes, MULTI_BYTES_PREFIX.length));
	}

	public static boolean isMultiByteString(final String str) {
		if (!isWhiteSpace(str))
			return false;

		try {
			byte[] bytes = StringUtils.getBytesUsAscii(str.substring(0, Math.min(2, str.length())));
			return isMultiByteString(bytes);
		} catch (Exception e) {
			if (log.isErrorEnabled())
				log.error("멀티바이트 문자열인지 확인하는데 실패했습니다. str=[{}], exception={}", str, e);
			return false;
		}
	}

	//endregion

	// region << ellipsis >>

	static boolean needEllipsis(String str, int maxLength) {
		return isNotEmpty(str) && str.length() > maxLength;
	}

	public static String ellipsisChar(String str, int maxLength) {
		if (str.isEmpty() || !needEllipsis(str, maxLength))
			return str;

		return str.substring(0, maxLength - TRIMMING_STR.length()) + TRIMMING_STR;
	}

	public static String ellipsisPath(String str, int maxLength) {
		if (str.isEmpty() || !needEllipsis(str, maxLength))
			return str;

		int length = maxLength / 2;

		StringBuilder builder = new StringBuilder();
		builder.append(str.substring(0, length))
		       .append(TRIMMING_STR);

		if (maxLength % 2 == 0)
			builder.append(str.substring(str.length() - length));
		else
			builder.append(str.substring(str.length() - length - 1));

		return builder.toString();
	}

	public static String ellipsisFirst(String str, int maxLength) {
		if (str.isEmpty() || !needEllipsis(str, maxLength))
			return str;

		return TRIMMING_STR + str.substring(str.length() - maxLength);
	}

	//endregion

	//region << encoding string - hex decimal / base64 >>

	public static char intToHex(final int n) {
		if (n <= 9)
			return (char) (n + 48);
		return (char) (n - 10 + 97);
	}

	public static int hexToInt(final char h) {
		if (h >= '0' && h <= '9')
			return h - '0';
		if (h >= 'a' && h <= 'f')
			return h - 'a' + 10;
		if (h >= 'A' && h <= 'F')
			return h - 'A' + 10;

		return -1;
	}

	/**
	 * 16 진수로 표현된 데이타를 바이트 배열로 변환합니다.
	 *
	 * @param hexString 16진수로 표현된 문자열
	 * @return
	 */
	public static byte[] getBytesFromHexString(final String hexString) throws Exception {
		return Hex.decodeHex(hexString.toCharArray());
	}

	/**
	 * 데이터를 16 진수로 표현합니다.
	 *
	 * @param bytes 바이트 배열
	 * @return 바이트를 16진수로 표현한 문자열
	 */
	public static String getHexString(final byte[] bytes) {
		return Hex.encodeHexString(bytes);
	}

	public static byte[] encodeBase64(final byte[] input) {
		return Base64.encodeBase64URLSafe(input);
	}

	public static String encodeBase64String(final byte[] input) {
		return StringUtils.newStringUtf8(encodeBase64(input));
	}

	public static byte[] decodeBase64(final byte[] base64Data) {
		return Base64.decodeBase64(base64Data);
	}

	public static byte[] decodeBase64(String base64String) {
		return Base64.decodeBase64(base64String);
	}

	public static String decodeBase64String(final byte[] base64Data) {
		return StringUtils.newStringUtf8(Base64.decodeBase64(base64Data));
	}

	public static String decodeBase64String(final String base64String) {
		return StringUtils.newStringUtf8(Base64.decodeBase64(base64String));
	}

	public static byte[] getUtf8Bytes(final String str) {
		return StringUtils.getBytesUtf8(str);
	}

	public static String getUtf8String(final byte[] bytes) {
		return StringUtils.newStringUtf8(bytes);
	}

	public static String getStringFromBytes(byte[] bytes, final BinaryStringFormat format) {
		return format == BinaryStringFormat.HexDecimal
		       ? getHexString(bytes)
		       : encodeBase64String(bytes);

	}

	public static byte[] getBytesFromString(final String str, final BinaryStringFormat format) throws Exception {
		return format == BinaryStringFormat.HexDecimal
		       ? getBytesFromHexString(str)
		       : decodeBase64(str);
	}

	// endregion

	// region << String manipulation >>

	public static String deleteCharAny(final String str, char... chars) {
		if (isEmpty(str))
			return str;

		StringBuilder builder = new StringBuilder();
		final List<Character> charList = Chars.asList(chars);

		char[] strArray = str.toCharArray();
		for (char c : strArray) {
			if (!charList.contains(c))
				builder.append(c);
		}
		return builder.toString();
	}

	public static String deleteChar(final String str, char[] chars) {
		if (isEmpty(str))
			return str;

		StringBuilder builder = new StringBuilder();
		final List<Character> charList = Chars.asList(chars);

		char[] strArray = str.toCharArray();
		for (char c : strArray) {
			if (!charList.contains(c))
				builder.append(c);
		}
		return builder.toString();
	}

	public static String deleteChar(final String str, char dc) {
		if (isEmpty(str))
			return str;

		StringBuilder builder = new StringBuilder();

		char[] strArray = str.toCharArray();
		for (char c : strArray) {
			if (c != dc)
				builder.append(c);
		}
		return builder.toString();
	}

	@SafeVarargs
	public static <T> String join(final T... strs) {
		return join(Arrays.asList(strs), ",");
	}

	public static <T> String join(final T[] strs, final String separator) {
		return join(Arrays.asList(strs), separator);
	}

	public static <T> String join(Collection<T> strs) {
		return join(strs, ",");
	}

	public static <T> String join(Collection<T> strs, String separator) {
		return Joiner.on(separator).join(strs);
	}

	public static <T> String join(Iterable<T> strs) {
		return join(strs, ",");
	}

	public static <T> String join(Iterable<T> strs, String separator) {
		return Joiner.on(separator).join(strs);
	}

	public static String quotedStr(final String str) {
		return isNull(str) ? NULL_STR : String.format("\'%s\'", str.replace("\'", "\'\'"));
	}

	public static String quotedStr(final String str, final String defaultStr) {
		return isWhiteSpace(str) ? quotedStr(defaultStr) : quotedStr(str);
	}

	public static String reverse(final String str) {
		if (isEmpty(str))
			return str;

		char[] cs = new char[str.length()];
		for (int i = cs.length - 1, j = 0; i >= 0; i--, j++) {
			cs[i] = str.charAt(j);
		}
		return new String(cs);
	}

	public static String replicate(final String str, int n) {
		return Strings.repeat(str, n);
	}

	public static Iterable<String> split(final String str, final char... separators) {
		if (isEmpty(str))
			return new ArrayList<String>();

		List<String> result = new ArrayList<String>();
		List<Character> seps = Chars.asList(separators);

		int length = str.length();
		int startIndex = 0;
		for (int i = 0; i < length; i++) {
			if (seps.contains(str.charAt(i))) {
				if (i - 1 - startIndex > 0) {
					result.add(str.substring(startIndex, i - 1));
				}
				startIndex = i + 1;
			}
		}

		return result;
	}

	public static Iterable<String> split(final String str, final String... separators) {
		if (isEmpty(str))
			return new ArrayList<String>();

		List<String> result = new ArrayList<String>();

		List<char[]> seps = new ArrayList<char[]>();
		for (String sep : separators) {
			seps.add(sep.toCharArray());
		}
		char[] strArray = str.toCharArray();
		int startIndex = 0;
		for (int i = 0; i < strArray.length; i++) {
			for (char[] sep : seps) {
				if (Arrays.equals(sep, Arrays.copyOf(strArray, sep.length))) {
					if (i - 1 - startIndex > 0) {
						result.add(new String(Arrays.copyOfRange(strArray, startIndex, i - 1)));
					}
					startIndex = i + sep.length;
				}
			}
		}

		return result;
	}

	// endregion

	// region << WordCount, FirstOf, LastOf >>

	public static int wordCount(final String str, final String word) {
		return wordCount(str, word, false);
	}

	public static int wordCount(final String str, final String word, final boolean ignoreCase) {

		if (isEmpty(str) || isEmpty(word))
			return 0;

		final String targetStr = (ignoreCase) ? str.toUpperCase() : str;
		final String searchWord = (ignoreCase) ? word.toUpperCase() : word;

		int wordLength = searchWord.length();
		int maxLength = targetStr.length() - wordLength;

		int count = 0;
		int index = 0;
		while (index >= 0 && index <= maxLength) {

			index = targetStr.indexOf(searchWord, index);

			if (index > 0) {
				count++;
				index += wordLength;
			}
		}
		return count;
	}

	public static String getFirstLine(final String str) {
		if (isEmpty(str))
			return str;

		int index = str.indexOf('\n');
		if (index > 0)
			return str.substring(0, index - 1);

		return str;
	}

	public static String getBetween(final String text, final String start, final String end) {
		if (isEmpty(text))
			return text;

		int startIndex = 0;
		if (!isEmpty(start)) {
			int index = text.indexOf(start);
			if (index > -1) {
				startIndex += start.length();
			}
		}

		int endIndex = text.length() - 1;
		if (!isEmpty(end)) {
			int index = text.lastIndexOf(end);
			if (index > -1)
				endIndex = index - 1;
		}

		if (endIndex > startIndex)
			return text.substring(startIndex, endIndex);

		return EMPTY_STR;
	}

	// endregion
}