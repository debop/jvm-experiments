package kr.ecsp.data.hibernate.repository;

import kr.ecsp.data.domain.HibernateParameter;
import kr.ecsp.data.domain.model.StatefulEntity;
import org.hibernate.LockOptions;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;

import java.io.Serializable;
import java.util.List;

/**
 * kr.ecsp.data.hibernate.repository.HibernateDao
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 11. 27.
 */
public interface HibernateDao<E extends StatefulEntity> {

	Class<E> getEntityClass();

	E load(Serializable id);

	E load(Serializable id, LockOptions lockOptions);

	E get(Serializable id);

	E get(Serializable id, LockOptions lockOptions);

	List<E> getAll();

	List<E> find(DetachedCriteria dc);

	List<E> find(DetachedCriteria dc, int firstResult, int maxResults, Order... orders);

	List<E> find(Criterion[] criterions);

	List<E> find(Criterion[] criterions, int firstResult, int maxResults, Order... orders);

	List<E> findByQueryString(String queryString, HibernateParameter... parameters);

	List<E> findByQueryString(String queryString, int firstResult, int maxResults, HibernateParameter... parameters);

	List<E> findByNamedQuery(String queryName, HibernateParameter... parameters);

	List<E> findByNamedQuery(String queryName, int firstResult, int maxResults, HibernateParameter... parameters);
}
