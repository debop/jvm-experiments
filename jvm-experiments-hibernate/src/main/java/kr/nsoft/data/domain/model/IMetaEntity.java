package kr.nsoft.data.domain.model;

import java.util.Set;

/**
 * 메타정보({@link java.util.Map} 구조)를 가지는 엔티티의 인터페이스입니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 9. 19
 */
public interface IMetaEntity extends IStatefulEntity {

    IMetaValue getMetaValue(String key);

    Set<String> getMetaKeys();

    void addMetaValue(String metaKey, IMetaValue metaValue);

    void addMetaValue(String metaKey, Object value);

    void removeMetaValue(String metaKey);
}
