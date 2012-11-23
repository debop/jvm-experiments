package kr.ecsp.data.hibernate.repository;

import kr.ecsp.data.domain.model.StatefulEntity;
import lombok.extern.slf4j.Slf4j;

/**
 * Hiberante용 Repository 입니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 9. 25
 */
@Slf4j
public class SimpleHibernateRepository<E extends StatefulEntity> extends HibernateRepositoryBase<E> {

	private static final long serialVersionUID = -3574082395641980747L;
}
