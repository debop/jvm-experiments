package kr.ecsp.data.hibernate.repository;

import kr.ecsp.data.domain.HibernateParameter;
import kr.ecsp.data.domain.model.StatefulEntity;
import kr.escp.commons.collection.PagedList;
import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.ReplicationMode;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * Hibernate ORM 용 Repository 의 기본 인터페이스입니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 9. 14.
 */

public interface HibernateRepository<E extends StatefulEntity> extends Serializable {

	Class<E> getEntityClass();

	E get(Serializable id);

	E get(Serializable id, LockMode lockMode);

	E load(Serializable id);

	E load(Serializable id, LockMode lockMode);

	List<E> getIn(Collection ids);

	List<E> getIn(Object[] ids);

	List<E> getList(DetachedCriteria dc);

	List<E> getList(DetachedCriteria dc, int firstResult, int maxResults, Order... orders);

	List<E> getList(Query query, HibernateParameter... params);

	List<E> getList(Query query, int firstResult, int maxResults, HibernateParameter... params);

	List<E> getList(int firstResult, int maxResults, Order[] orders, Criterion... criterions);

	List<E> getListByHql(String hql, HibernateParameter... params);

	List<E> getListByHql(String hql, int firstResult, int maxResults, HibernateParameter... params);

	List<E> getListByNamedQuery(final String namedQuery, HibernateParameter... params);

	List<E> getListByNamedQuery(final String namedQuery, int firstResult, int maxResults, HibernateParameter... params);

	PagedList<E> getPagedList(DetachedCriteria dc, int pageNo, int pageSize, Order... orders);

	PagedList<E> getPagedList(final Query query, final int pageNo, final int pageSize, final HibernateParameter... params);

	PagedList<E> getPagedListByHql(final String hql, final int pageNo, final int pageSize,
	                               final HibernateParameter... params);

	PagedList<E> getPagedListByNamedQuery(final String namedQuery, final int pageNo, final int pageSize,
	                                      final HibernateParameter... params);

	E getFirst(DetachedCriteria dc, Order... orders);

	E getFirst(Criteria criteria, Order... orders);

	E getFirst(Query query, HibernateParameter... params);

	E getFirstByHql(String hql, HibernateParameter... params);

	E getFirstByNamedQuery(String namedQuery, HibernateParameter... params);

	E getUnique(DetachedCriteria dc);

	E getUnique(Query query, HibernateParameter... params);

	E getUniqueByHql(String hql, HibernateParameter... params);

	E getUniqueByNamedQuery(String namedQuery, HibernateParameter... params);

	boolean exists(DetachedCriteria dc);

	boolean exists(Criteria criteria);

	boolean exists(Query query, HibernateParameter... params);

	boolean existsByHql(String hql, HibernateParameter... params);

	boolean existsByNamedQuery(String sqlString, HibernateParameter... params);

	long count(DetachedCriteria dc);

	long count(Criteria criteria);

	long count(final Query query, final HibernateParameter... params);

	long countByHql(final String hql, final HibernateParameter... params);

	long countByNamedQuery(final String namedQuery, final HibernateParameter... params);

	<TProject> TProject reportOne(Class<TProject> projectClass,
	                              ProjectionList projectionList,
	                              DetachedCriteria dc);

	<TProject> TProject reportOne(Class<TProject> projectClass,
	                              ProjectionList projectionList,
	                              Criteria criteria);


	<TProject> List<TProject> reportList(Class<TProject> projectClass,
	                                     ProjectionList projectionList,
	                                     DetachedCriteria dc);

	<TProject> List<TProject> reportList(Class<TProject> projectClass,
	                                     ProjectionList projectionList,
	                                     Criteria criteria);

	void merge(E entity);

	void persist(E entity);

	void replicate(E entity, ReplicationMode replicationMode);

	Serializable save(E entity);

	void saveOrUpdate(E entity);

	void update(E entity);

	void delete(E entity);

	void deleteById(Serializable id);

	void deleteById(Serializable id, LockMode lockMode);

	void deleteAll();

	void deleteList(DetachedCriteria dc);

	void deleteList(Query query, HibernateParameter... params);

	int executeUpdate(String hql, HibernateParameter... params);

	int executeUpdateWithTx(String hql, HibernateParameter... params);


	DetachedCriteria createDetachedCriteria();

	DetachedCriteria createDetachedCriteria(String alias);
}
