package kr.ecsp.data.domain.model;

import com.google.common.base.Objects;
import kr.ecsp.commons.tools.ReflectionTool;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

/**
 * Hibernate/JPA 기본 도메인 엔티티의 추상화 클래스입니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 11. 19
 */
@Slf4j
public abstract class EntityBase<TId extends Serializable> extends StateEntityBase implements Entity<TId> {

	private static final long serialVersionUID = 4766509654284022534L;
	protected TId id;


	@Override
	@SuppressWarnings("unchecked")
	public <TId> TId getId() {
		return (TId)this.id;
	}

	protected void setId(TId id) {
		this.id = id;
	}

	public String toString() {
		return Objects.toStringHelper(this)
		              .add("id", id)
		              .toString();
	}

	@Override
	@SuppressWarnings("unchecked")
	public boolean equals(Object obj) {
		boolean sameType = (obj!=null) && (getClass()==obj.getClass());

		if (sameType) {
			EntityBase<TId> entity = (EntityBase<TId>) obj;
			return hasSameNonDefaultIdAs(entity) ||
				       ((!isPersisted() || entity.isPersisted()) && hashSameBusinessSignature(entity));
		}
		return false;
	}

	/**
	 * Entity의 HashCode 를 제공합니다. 저장소에 저장된 엔티티의 경우는 Identifier 의 HashCode를 제공합니다.
	 *
	 * @return hash code
	 */
	public int hashCode() {
		if (id == null || !isPersisted())
			System.identityHashCode(this);

		return Objects.hashCode(id);
	}

	private boolean hasSameNonDefaultIdAs(Entity<TId> entity) {

		try {
			Class<TId> idClass = ReflectionTool.getGenericParameterType(this);
			TId defaultValue = (TId) idClass.newInstance();

			boolean idHasValue = !java.util.Objects.equals(id, defaultValue);
			if (idHasValue) {
				boolean entityIdHasValue = !java.util.Objects.equals(entity.getId(), defaultValue);

				if (entityIdHasValue)
					return java.util.Objects.equals(id, entity.getId());
			}
		} catch (Exception ex) {
			log.error("Identifier 값 비교 시 예외 발생. entity=" + entity, ex);
		}
		return false;
	}

	private boolean hashSameBusinessSignature(Entity<TId> other) {
		return (other != null) && (hashCode() == other.hashCode());
	}
}