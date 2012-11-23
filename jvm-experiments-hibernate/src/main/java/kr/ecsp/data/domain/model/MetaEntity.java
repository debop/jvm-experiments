package kr.ecsp.data.domain.model;

import java.util.Set;

/**
 * 메타정보({@link java.util.Map} 구조)를 가지는 엔티티의 인터페이스입니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 9. 19
 */
public interface MetaEntity extends StatefulEntity {

	MetaValue getMetaValue(String key);

	Set<String> getMetaKeys();

	void addMetaValue(String metaKey, MetaValue metaValue);

	void addMetaValue(String metaKey, Object value);

	void removeMetaValue(String metaKey);
}