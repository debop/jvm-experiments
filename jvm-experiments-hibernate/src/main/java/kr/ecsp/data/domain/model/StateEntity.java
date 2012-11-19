package kr.ecsp.data.domain.model;

/**
 * 설명을 추가하세요.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 11. 19
 */
public interface StateEntity extends ValueObject {

	boolean isPersisted();

	void onSave();

	void onPersist();

	void onLoad();
}
