package kr.kth.data.domain;

import com.google.common.base.Objects;
import kr.kth.commons.base.Guard;
import kr.kth.commons.base.ValueObjectBase;
import lombok.Getter;
import lombok.Setter;

/**
 * SQL 실행 문의 인자 정보를 나타내는 {@link NamedParameter} 의 추상클래스입니다.
 * JpaUser: sunghyouk.bae@gmail.com
 * Date: 12. 11. 19
 */
public abstract class NamedParameterBase extends ValueObjectBase implements NamedParameter {

	private static final long serialVersionUID = -5298525421726000937L;

	@Getter @Setter private String name;
	@Getter @Setter private Object value;

	protected NamedParameterBase(String name, Object value) {
		Guard.shouldBeEmpty(name, "name");

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
