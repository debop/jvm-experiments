package kr.kth.data.domain;

import com.google.common.base.Objects;
import com.google.common.base.Strings;
import kr.kth.commons.ValueObjectBase;
import lombok.Getter;
import lombok.Setter;

/**
 * 설명을 추가하세요.
 * JpaUser: sunghyouk.bae@gmail.com
 * Date: 12. 11. 19
 */
public abstract class NamedParameterBase extends ValueObjectBase implements NamedParameter {

	private static final long serialVersionUID = -5298525421726000937L;

	@Getter @Setter private String name;
	@Getter @Setter private Object value;

	protected NamedParameterBase(String name, Object value) {
		assert !Strings.isNullOrEmpty(name);

		this.name = name;
		this.value = value;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(name);
	}

	@Override
	protected Objects.ToStringHelper buildStringHelper() {
		return super.buildStringHelper()
		            .add("name", name)
		            .add("value", value);
	}
}
