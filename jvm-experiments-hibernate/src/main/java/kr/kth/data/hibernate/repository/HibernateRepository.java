package kr.kth.data.hibernate.repository;

import kr.kth.commons.collection.PagedList;
import kr.kth.data.domain.model.StatefulEntity;
import kr.kth.data.hibernate.HibernateParameter;
import org.hibernate.*;
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

public interface HibernateRepository extends Serializable {

	Session getCurrentSession();

	Object get(Class<? extends StatefulEntity> entityType, Serializable id);

	Object get(Class<? extends StatefulEntity> entityType, Serializable id, LockMode lockMode);

	Object load(Class<? extends StatefulEntity> entityType, Serializable id);

	Object load(Class<? extends StatefulEntity> entityType, Serializable id, LockMode lockMode);

	List getIn(Class<? extends StatefulEntity> entityType, Collection ids);

	List getIn(Class<? extends StatefulEntity> entityType, Object[] ids);

	List getList(DetachedCriteria dc);

	List getList(DetachedCriteria dc, int firstResult, int maxResults, Order... orders);

	List getList(Query query, HibernateParameter... params);

	List getList(Query query, int firstResult, int maxResults, HibernateParameter... params);

	List getList(Class<? extends StatefulEntity> entityType,
	             int firstResult,
	             int maxResults,
	             Order[] orders,
	             Criterion... criterions);

	List getListByHql(String hql, HibernateParameter... params);

	List getListByHql(String hql, int firstResult, int maxResults, HibernateParameter... params);

	List getListByNamedQuery(final String namedQuery, HibernateParameter... params);

	List getListByNamedQuery(final String namedQuery, int firstResult, int maxResults, HibernateParameter... params);

	PagedList getPagedList(DetachedCriteria dc, int pageNo, int pageSize, Order... orders);

	PagedList getPagedList(final Query query, final int pageNo, final int pageSize, final HibernateParameter... params);

	PagedList getPagedListByHql(final String hql, final int pageNo, final int pageSize,
	                            final HibernateParameter... params);

	PagedList getPagedListByNamedQuery(final String namedQuery, final int pageNo, final int pageSize,
	                                   final HibernateParameter... params);

	Object getFirst(DetachedCriteria dc, Order... orders);

	Object getFirst(Criteria criteria, Order... orders);

	Object getFirst(Query query, HibernateParameter... params);

	Object getFirstByQueryString(String hql, HibernateParameter... params);

	Object getFirstByNamedQuery(String namedQuery, HibernateParameter... params);

	Object getUnique(DetachedCriteria dc);

	Object getUnique(Query query, HibernateParameter... params);

	Object getUniqueByHql(String hql, HibernateParameter... params);

	Object getUniqueByNamedQuery(String namedQuery, HibernateParameter... params);

	boolean exists(DetachedCriteria dc);

	boolean exists(Criteria criteria);

	boolean exists(Query query, HibernateParameter... params);

	boolean existsByHql(String hql, HibernateParameter... params);

	boolean existsByNamedQuery(String sqlString, HibernateParameter... params);

	long count(Class<? extends StatefulEntity> entityType);

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

	void merge(StatefulEntity entity);

	void persist(StatefulEntity entity);

	void replicate(StatefulEntity entity, ReplicationMode replicationMode);

	Serializable save(StatefulEntity entity);

	void saveOrUpdate(StatefulEntity entity);

	void update(StatefulEntity entity);

	void delete(StatefulEntity entity);

	void deleteById(Class<? extends StatefulEntity> entityType, Serializable id);

	void deleteById(Class<? extends StatefulEntity> entityType, Serializable id, LockMode lockMode);

	void deleteAll(Class<? extends StatefulEntity> entityType);

	void deleteList(Collection<StatefulEntity> entities);

	int executeUpdate(String hql, HibernateParameter... params);

	int executeUpdateWithTx(String hql, HibernateParameter... params);
}
