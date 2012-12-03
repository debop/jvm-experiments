package kr.kth.data.hibernate;

import com.google.common.base.Objects;
import kr.kth.data.domain.NamedParameterBase;
import lombok.Getter;
import org.hibernate.type.Type;

/**
 * Hibernate용 Parameter
 * JpaUser: sunghyouk.bae@gmail.com
 * Date: 12. 11. 19
 */
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
	protected Objects.ToStringHelper buildStringHelper() {
		return super.buildStringHelper()
		            .add("type", type);
	}
}
