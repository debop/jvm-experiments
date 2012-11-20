package kr.ecsp.data.domain;

import com.google.common.base.Objects;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.type.Type;

/**
 * Hibernate용 Parameter
 * JpaUser: sunghyouk.bae@gmail.com
 * Date: 12. 11. 19
 */
@Slf4j
public class HibernateParameter extends NamedParameterBase {

	private static final long serialVersionUID = -6291985997768450558L;

	@Getter
	private Type type;

	public HibernateParameter(String name, Object value) {
		super(name, value);
	}

	public HibernateParameter(String name, Object value, Type type) {
		super(name, value);
		this.type = type;
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(this)
		              .add("name", getName())
		              .add("value", getValue())
		              .add("type", getType())
		              .toString();
	}
}
