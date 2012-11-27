package kr.ecsp.data.hibernate.repository;

import kr.ecsp.data.domain.model.StatefulEntity;

import java.io.Serializable;
import java.util.List;

/**
 * kr.ecsp.data.hibernate.repository.HibernateDao
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 11. 27.
 */
public interface HibernateDao<E extends StatefulEntity> {

	Class<E> getEntityClass();

	E get(Serializable id);

	List<E> getAll();
}
