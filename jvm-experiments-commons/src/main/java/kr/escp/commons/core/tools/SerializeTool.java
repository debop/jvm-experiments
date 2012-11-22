package kr.escp.commons.core.tools;

import kr.escp.commons.core.BinaryStringFormat;
import kr.escp.commons.core.Guard;
import kr.escp.commons.core.Serializer;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * {@link Serializer} 를 이용한 직렬화/역직렬화를 수행하는 Utility Method 를 제공합니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 9. 14
 */
@Slf4j
public final class SerializeTool {

	private SerializeTool() {}

	/**
	 * 객체를 직렬화하여 문자열로 반환합니다.
	 */
	public static String serializeAsString(Serializer serializer, Object graph) throws Exception {
		Guard.shouldNotBeNull(serializer, "serializer");
		if (graph == null)
			return "";

		return StringTool.getStringFromBytes(serializer.serialize(graph), BinaryStringFormat.HexDecimal);
	}

	/**
	 * 직렬화된 문자열을 역직렬화하여, 객체로 빌드합니다.
	 */
	public static Object deserializeFromString(Serializer serializer, String serializedStr) throws Exception {
		Guard.shouldNotBeNull(serializer, "serializer");
		if (StringTool.isEmpty(serializedStr))
			return null;

		return serializer.deserialize(StringTool.getBytesFromHexString(serializedStr));
	}

	/**
	 * 객체를 직렬화하여 {@link java.io.OutputStream} 으로 변환합니다.
	 */
	public static OutputStream serializeAsStream(Serializer serializer, Object graph) throws Exception {
		Guard.shouldNotBeNull(serializer, "serializer");
		if (graph == null)
			return new ByteArrayOutputStream();

		return StreamTool.toOutputStream(serializer.serialize(graph));
	}

	/**
	 * {@link java.io.InputStream} 을 읽어 역직렬화하여, 객체를 빌드합니다.
	 */
	public static Object deserializeFromStream(Serializer serializer, InputStream inputStream) throws Exception {
		Guard.shouldNotBeNull(serializer, "serializer");
		if (inputStream == null)
			return null;

		return serializer.deserialize(StreamTool.toByteArray(inputStream));
	}
}
