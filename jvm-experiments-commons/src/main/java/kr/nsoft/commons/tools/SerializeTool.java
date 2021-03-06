package kr.nsoft.commons.tools;

import kr.nsoft.commons.BinaryStringFormat;
import kr.nsoft.commons.ISerializer;
import kr.nsoft.commons.io.BinarySerializer;
import kr.nsoft.commons.parallelism.AsyncTool;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import static kr.nsoft.commons.Guard.shouldNotBeNull;
import static kr.nsoft.commons.tools.StringTool.getBytesFromHexString;

/**
 * {@link kr.nsoft.commons.ISerializer} 를 이용한 직렬화/역직렬화를 수행하는 Utility Method 를 제공합니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 9. 14
 */
@Slf4j
public final class SerializeTool {

    private static final BinarySerializer binarySerializer = new BinarySerializer();

    private SerializeTool() {
    }

    /**
     * 객체를 직렬화하여 문자열로 반환합니다.
     */
    public static String serializeAsString(ISerializer serializer, Object graph) {
        shouldNotBeNull(serializer, "serializer");
        if (graph == null)
            return StringTool.EMPTY_STR;

        return StringTool.getStringFromBytes(serializer.serialize(graph), BinaryStringFormat.HexDecimal);
    }

    /**
     * 직렬화된 문자열을 역직렬화하여, 객체로 빌드합니다.
     */
    public static <T> T deserializeFromString(ISerializer serializer,
                                              Class<T> clazz,
                                              String serializedStr) {
        shouldNotBeNull(serializer, "serializer");
        if (StringTool.isEmpty(serializedStr))
            return null;

        return serializer.deserialize(getBytesFromHexString(serializedStr), clazz);
    }

    /**
     * 객체를 직렬화하여 {@link java.io.OutputStream} 으로 변환합니다.
     */
    public static OutputStream serializeAsStream(ISerializer serializer, Object graph) throws IOException {
        shouldNotBeNull(serializer, "serializer");
        if (graph == null)
            return new ByteArrayOutputStream();

        return StreamTool.toOutputStream(serializer.serialize(graph));
    }

    /**
     * {@link java.io.InputStream} 을 읽어 역직렬화하여, 객체를 빌드합니다.
     */
    public static <T> T deserializeFromStream(ISerializer serializer,
                                              Class<T> clazz,
                                              InputStream inputStream) throws IOException {
        shouldNotBeNull(serializer, "serializer");
        if (inputStream == null)
            return null;

        return serializer.deserialize(StreamTool.toByteArray(inputStream), clazz);
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
    public static <T> T deserializeObject(byte[] bytes, Class<T> clazz) {
        return binarySerializer.deserialize(bytes, clazz);
    }

    /**
     * 객체를 {@link BinarySerializer} 를 이용하여 deep copy 를 수행합니다.
     */
    @SuppressWarnings("unchecked")
    public static <T> T copyObject(T graph) {
        if (graph == null)
            return null;

        return (T) deserializeObject(serializeObject(graph), graph.getClass());
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

    public static <T> Future<T> deserializeObjectAsync(final byte[] bytes, final Class<T> clazz) {
        return
                AsyncTool.startNew(new Callable<T>() {
                    @Override
                    public T call() throws Exception {
                        return binarySerializer.deserialize(bytes, clazz);
                    }
                });
    }

    @SuppressWarnings("unchecked")
    public static <T> Future<T> copyObjectAsync(final T graph) {
        if (graph == null) {
            return new FutureTask<T>(new Callable<T>() {
                @Override
                public T call() throws Exception {
                    return null;
                }
            });
        }

        return AsyncTool.startNew(new Callable<T>() {
            @Override
            public T call() throws Exception {
                return (T) binarySerializer.deserialize(binarySerializer.serialize(graph),
                                                        graph.getClass());
            }
        });
    }

}
