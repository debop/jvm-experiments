package kr.escp.commons.json;

import com.google.common.base.Defaults;
import com.google.gson.Gson;
import kr.escp.commons.tools.StringTool;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * <a href="http://code.google.com/p/google-gson/">google-gson</a> 을 이용한 json serializer 입니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 9. 14
 */
@Slf4j
public class GsonSerializer implements JsonSerializer {

	private static final long serialVersionUID = 6739140512507247814L;

	@Getter
	@Setter
	private Gson gson;

	public GsonSerializer() {
		this(new Gson());
	}

	public GsonSerializer(Gson gson) {
		this.gson = (gson != null) ? gson : new Gson();
	}


	@Override
	public String serializeAsText(Object graph) {
		if (graph == null)
			return "";

		if (log.isDebugEnabled())
			log.debug("Json 직렬화를 수행합니다... graph=" + graph);

		return getGson().toJson(graph);
	}

	@Override
	public <T> T deserialize(byte[] bytes, Class<? extends T> targetType) {
		return deserialize(StringTool.getUtf8String(bytes), targetType);
	}

	@Override
	public <T> T deserialize(String jsonText, Class<? extends T> targetType) {
		if (StringTool.isWhiteSpace(jsonText))
			return Defaults.defaultValue(targetType);

		if (log.isDebugEnabled())
			log.debug("Json 역직렬화를 수행합니다. jsonText=[{}]", StringTool.ellipsisChar(jsonText, 255));

		return getGson().fromJson(jsonText, targetType);
	}

	@Override
	public byte[] serialize(Object graph) {
		return StringTool.getUtf8Bytes(serializeAsText(graph));
	}

	@Override
	public Object deserialize(byte[] bytes) {
		return deserialize(StringTool.getUtf8String(bytes), Object.class);
	}
}
