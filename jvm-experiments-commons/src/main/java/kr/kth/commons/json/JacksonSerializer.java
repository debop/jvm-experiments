package kr.kth.commons.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.kth.commons.Guard;
import kr.kth.commons.tools.ArrayTool;
import kr.kth.commons.tools.StringTool;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * Json ISerializer
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 9. 14
 */
@Slf4j
public class JacksonSerializer implements IJsonSerializer {

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
        if (graph == null)
            return ArrayTool.EmptyByteArray;

        if (log.isDebugEnabled())
            log.debug("인스턴스를 JSON 포맷으로 직렬화합니다. graph=[{}]", graph);

        try {
            return mapper.writeValueAsBytes(graph);
        } catch (Exception e) {
            if (log.isErrorEnabled())
                log.error("객체를 Json 직렬화하는데 실패했습니다.", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public String serializeToText(Object graph) {
        if (graph == null)
            return "";

        if (log.isDebugEnabled())
            log.debug("인스턴스를 JSON 포맷으로 직렬화합니다. graph=[{}]", graph);
        try {
            return mapper.writeValueAsString(graph);
        } catch (Exception e) {
            if (log.isErrorEnabled())
                log.error("객체를 Json 직렬화하는데 실패했습니다.", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T> T deserializeFromText(String jsonText, Class<T> targetType) {
        if (StringTool.isWhiteSpace(jsonText))
            return (T) null;

        if (log.isDebugEnabled())
            log.debug("JSON 역직렬화를 수행합니다. valueType=[{}]", targetType.getName());

        try {
            return mapper.readValue(jsonText, targetType);
        } catch (Exception e) {
            if (log.isErrorEnabled())
                log.error("Json 역직렬화하는데 실패했습니다.", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> targetType) {
        if (ArrayTool.isEmpty(bytes))
            return (T) null;

        if (log.isDebugEnabled())
            log.debug("JSON 역직렬화를 수행합니다. targetType=[{}]", targetType.getName());

        try {
            return mapper.readValue(bytes, targetType);
        } catch (Exception e) {
            if (log.isErrorEnabled())
                log.error("Json 역직렬화하는데 실패했습니다.", e);
            throw new RuntimeException(e);
        }
    }
}
