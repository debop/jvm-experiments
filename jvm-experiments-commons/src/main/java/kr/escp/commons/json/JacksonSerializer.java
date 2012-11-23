package kr.escp.commons.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Preconditions;
import kr.escp.commons.tools.ArrayTool;
import kr.escp.commons.tools.StringTool;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * Json Serializer
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 9. 14
 */
@Slf4j
public class JacksonSerializer implements JsonSerializer {

	@Getter @Setter private ObjectMapper mapper;

	public JacksonSerializer() {
		this(new ObjectMapper());
	}

	public JacksonSerializer(ObjectMapper mapper) {
		this.mapper = Preconditions.checkNotNull(mapper, new ObjectMapper());
	}

	@Override
	public byte[] serialize(Object graph) {
		if (graph == null)
			return ArrayTool.EmptyByteArray;

		if (log.isDebugEnabled())
			log.debug("인스턴스를 JSON 포맷으로 직렬화합니다. graph=" + graph);

		try {
			return getMapper().writeValueAsBytes(graph);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public String serializeAsText(Object graph) {
		if (graph == null)
			return "";

		if (log.isDebugEnabled())
			log.debug("인스턴스를 JSON 포맷으로 직렬화합니다. graph=" + graph);
		try {
			return getMapper().writeValueAsString(graph);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public <T> T deserialize(String jsonText, Class<? extends T> targetType) {
		if (StringTool.isWhiteSpace(jsonText))
			return (T) null;

		if (log.isDebugEnabled())
			log.debug("JSON 역직렬화를 수행합니다. valueType=" + targetType.getName());

		try {
			return getMapper().readValue(jsonText, targetType);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public <T> T deserialize(byte[] bytes, Class<? extends T> targetType) {
		if (ArrayTool.isEmpty(bytes))
			return (T) null;

		if (log.isDebugEnabled())
			log.debug("JSON 역직렬화를 수행합니다. valueType=" + targetType.getName());

		try {
			return getMapper().readValue(bytes, targetType);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Object deserialize(byte[] bytes) {
		return deserialize(bytes, Object.class);
	}
}
