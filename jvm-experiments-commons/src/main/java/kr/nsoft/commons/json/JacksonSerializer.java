package kr.nsoft.commons.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.nsoft.commons.Guard;
import kr.nsoft.commons.tools.ArrayTool;
import kr.nsoft.commons.tools.StringTool;
import lombok.Getter;

/**
 * jackson 라이브러리를 이용한 Json Serializer
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 9. 14
 */
public class JacksonSerializer implements IJsonSerializer {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(JacksonSerializer.class);
    private static final boolean isDebugEnabled = log.isDebugEnabled();

    @Getter
    private final ObjectMapper mapper;

    public JacksonSerializer() {
        this(new ObjectMapper());
    }

    public JacksonSerializer(ObjectMapper mapper) {
        this.mapper = Guard.firstNotNull(mapper, new ObjectMapper());
    }

    @Override
    public byte[] serialize(Object graph) {
        try {
            if (graph == null)
                return ArrayTool.EmptyByteArray;

            if (log.isDebugEnabled())
                log.debug("인스턴스를 JSON 포맷으로 직렬화합니다. graph=[{}]", graph);

            return mapper.writeValueAsBytes(graph);
        } catch (Exception e) {
            if (log.isErrorEnabled())
                log.error("객체를 Json 직렬화하는데 실패했습니다.", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public String serializeToText(Object graph) {
        try {
            if (graph == null)
                return "";

            if (log.isDebugEnabled())
                log.debug("인스턴스를 JSON 포맷으로 직렬화합니다. graph=[{}]", graph);

            return mapper.writeValueAsString(graph);
        } catch (Exception e) {
            if (log.isErrorEnabled())
                log.error("객체를 Json 직렬화하는데 실패했습니다.", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T> T deserializeFromText(String jsonText, Class<T> targetType) {
        try {
            if (StringTool.isWhiteSpace(jsonText))
                return (T) null;

            if (log.isDebugEnabled())
                log.debug("JSON 역직렬화를 수행합니다. valueType=[{}]", targetType.getName());

            return mapper.readValue(jsonText, targetType);
        } catch (Exception e) {
            if (log.isErrorEnabled())
                log.error("Json 역직렬화하는데 실패했습니다.", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> targetType) {
        try {
            if (ArrayTool.isEmpty(bytes))
                return (T) null;

            if (log.isDebugEnabled())
                log.debug("JSON 역직렬화를 수행합니다. targetType=[{}]", targetType.getName());

            return mapper.readValue(bytes, targetType);
        } catch (Exception e) {
            if (log.isErrorEnabled())
                log.error("Json 역직렬화하는데 실패했습니다.", e);
            throw new RuntimeException(e);
        }
    }
}
