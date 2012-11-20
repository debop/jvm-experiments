package kr.ecsp.data.domain.model;

/**
 * JPA 기본 도메인 엔티티의 추상화 클래스입니다.
 * JpaUser: sunghyouk.bae@gmail.com
 * Date: 12. 11. 19
 */
public abstract class StateEntityBase extends ValueObjectBase implements StateEntity {

	private static final long serialVersionUID = -2050040672948447046L;
	private boolean persisted = false;

	@Override
	public boolean isPersisted() {
		return persisted;
	}

	@Override
	public void onSave() {
		persisted = true;
	}

	@Override
	public void onPersist() {
		persisted = true;
	}

	@Override
	public void onLoad() {
		persisted = true;
	}
}
