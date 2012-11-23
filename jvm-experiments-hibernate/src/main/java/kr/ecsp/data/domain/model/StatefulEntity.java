package kr.ecsp.data.domain.model;

import kr.escp.commons.ValueObject;

/**
 * 저장 상태 정보를 가지는 엔티티임을 나타내는 인터페이스입니다.
 * JpaUser: sunghyouk.bae@gmail.com
 * Date: 12. 11. 19
 */
public interface StatefulEntity extends ValueObject {

	boolean isPersisted();

	void onSave();

	void onPersist();

	void onLoad();
}
