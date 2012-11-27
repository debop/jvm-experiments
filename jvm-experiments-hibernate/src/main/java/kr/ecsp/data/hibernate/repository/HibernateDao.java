package kr.ecsp.data.hibernate.repository;

import kr.ecsp.data.domain.model.StatefulEntity;
import org.hibernate.Session;

import java.io.Serializable;
import java.util.List;

/**
 * kr.ecsp.data.hibernate.repository.HibernateDao
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 11. 27.
 */
public interface HibernateDao<E extends StatefulEntity> {

	Class<E> getEntityClass();

	void setEntityClass(Class<E> entityClass);

	void setSession(Session session);

	E get(Serializable id);

	List<E> getAll();
}
