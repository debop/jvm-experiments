package kr.ecsp.data.domain.model;

/**
 * Value Object (Component) 의 최상위 추상화 클래스입니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 11. 19
 */
public abstract class ValueObjectBase implements ValueObject {

	private static final long serialVersionUID = 5546630455380910528L;

	@Override
	public boolean equals(Object obj) {
		return obj == this ||
			       (obj != null &&
				        getClass() == obj.getClass() &&
				        hashCode() == obj.hashCode());
	}

	@Override
	public int hashCode() {
		return System.identityHashCode(this);
	}
}
