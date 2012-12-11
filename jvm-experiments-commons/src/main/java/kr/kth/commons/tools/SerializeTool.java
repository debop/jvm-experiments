package kr.kth.commons.tools;

import kr.kth.commons.base.BinaryStringFormat;
import kr.kth.commons.base.Serializer;
import kr.kth.commons.io.BinarySerializer;
import kr.kth.commons.parallelism.AsyncTool;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

import static kr.kth.commons.base.Guard.shouldNotBeNull;

/**
 * {@link Serializer} 를 이용한 직렬화/역직렬화를 수행하는 Utility Method 를 제공합니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 9. 14
 */
@Slf4j
public final class SerializeTool {

	private static final BinarySerializer binarySerializer = new BinarySerializer();

	private SerializeTool() {}

	/**
	 * 객체를 직렬화하여 문자열로 반환합니다.
	 */
	public static String serializeAsString(Serializer serializer, Object graph) {
		shouldNotBeNull(serializer, "serializer");
		if (graph == null)
			return StringTool.EMPTY_STR;

		return StringTool.getStringFromBytes(serializer.serialize(graph), BinaryStringFormat.HexDecimal);
	}

	/**
	 * 직렬화된 문자열을 역직렬화하여, 객체로 빌드합니다.
	 */
	public static Object deserializeFromString(Serializer serializer, String serializedStr) {
		shouldNotBeNull(serializer, "serializer");
		if (StringTool.isEmpty(serializedStr))
			return null;

		return serializer.deserialize(StringTool.getBytesFromHexString(serializedStr));
	}

	/**
	 * 객체를 직렬화하여 {@link java.io.OutputStream} 으로 변환합니다.
	 */
	public static OutputStream serializeAsStream(Serializer serializer, Object graph) throws IOException {
		shouldNotBeNull(serializer, "serializer");
		if (graph == null)
			return new ByteArrayOutputStream();

		return StreamTool.toOutputStream(serializer.serialize(graph));
	}

	/**
	 * {@link java.io.InputStream} 을 읽어 역직렬화하여, 객체를 빌드합니다.
	 */
	public static Object deserializeFromStream(Serializer serializer, InputStream inputStream) throws IOException {
		shouldNotBeNull(serializer, "serializer");
		if (inputStream == null)
			return null;

		return serializer.deserialize(StreamTool.toByteArray(inputStream));
	}

	/**
	 * 지정된 객체를 직렬화하여, byte[] 로 변환합니다.
	 *
	 * @param graph 직렬화 대상 객체
	 * @return 직렬화된 정보
	 */
	public static byte[] serializeObject(Object graph) {
		return binarySerializer.serialize(graph);
	}

	/**
	 * 직렬화된 byte[] 정보를 역직렬화하여, 객체로 변환합니다.
	 *
	 * @param bytes 직렬화된 정보
	 * @return 역직렬화된 객체
	 */
	public static Object deserializeObject(byte[] bytes) {
		return binarySerializer.deserialize(bytes);
	}

	/**
	 * 객체를 {@link BinarySerializer} 를 이용하여 deep copy 를 수행합니다.
	 */
	public static Object copyObject(Object graph) {
		if (graph == null)
			return null;
		return deserializeObject(serializeObject(graph));
	}

	public static Future<byte[]> serializeObjectAsync(final Object graph) {
		return
			AsyncTool.startNew(new Callable<byte[]>() {
				@Override
				public byte[] call() throws Exception {
					return binarySerializer.serialize(graph);
				}
			});
	}

	public static Future<Object> deserializeObjectAsync(final byte[] bytes) {
		return
			AsyncTool.startNew(new Callable<Object>() {
				@Override
				public Object call() throws Exception {
					return binarySerializer.deserialize(bytes);
				}
			});
	}

	@SuppressWarnings("unchecked")
	public static <T> Future<T> copyObjectAsync(final T graph) {

		if (graph == null)
			return AsyncTool.getTaskHasResult(null);

		return AsyncTool.startNew(new Callable<T>() {
			@Override
			public T call() throws Exception {
				return (T) binarySerializer.deserialize(binarySerializer.serialize(graph));
			}
		});
	}

}
