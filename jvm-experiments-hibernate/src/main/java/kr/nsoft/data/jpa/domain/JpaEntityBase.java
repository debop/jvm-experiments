package kr.nsoft.data.jpa.domain;

import kr.nsoft.data.domain.model.StatefulEntityBase;

import javax.persistence.PostLoad;
import javax.persistence.PostPersist;

/**
 * JPA 기반의 엔티티의 추상 클래스입니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 11. 25.
 */
public abstract class JpaEntityBase extends StatefulEntityBase {

    private static final long serialVersionUID = 8224137297852297901L;


    @PostPersist
    private void postPersist() {
        onSave();
    }

    @PostLoad
    private void postLoad() {
        onLoad();
    }
}
