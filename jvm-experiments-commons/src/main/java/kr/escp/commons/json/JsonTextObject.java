package kr.escp.commons.json;

import com.google.common.base.Objects;
import kr.escp.commons.ValueObjectBase;
import lombok.Getter;
import lombok.Setter;

import static kr.escp.commons.tools.StringTool.ellipsisChar;

/**
 * 객체를 JSON 직렬화를 수행하여, 그 결과를 저장하려고 할 때 사용한다.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 9. 18
 */
public class JsonTextObject extends ValueObjectBase {

	private static final long serialVersionUID = 8434059177726276296L;

	public static final JsonTextObject Empty = new JsonTextObject(null, null);

	@Getter @Setter private String className;
	@Getter @Setter private String jsonText;

	public JsonTextObject(String className, String jsonText) {
		this.className = className;
		this.jsonText = jsonText;
	}

	public JsonTextObject(JsonTextObject src) {
		if (src == null) {
			this.className = null;
			this.jsonText = null;
		} else {
			this.className = src.className;
			this.jsonText = src.jsonText;
		}
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(className, jsonText);
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(this)
		              .add("className", className)
		              .add("jsonText", ellipsisChar(jsonText, 255))
		              .toString();
	}
}
