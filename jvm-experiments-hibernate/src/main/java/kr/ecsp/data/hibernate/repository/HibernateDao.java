package kr.ecsp.data.hibernate.repository;

import kr.ecsp.data.domain.HibernateParameter;
import kr.ecsp.data.domain.model.StatefulEntity;
import kr.escp.commons.collection.PagedList;
import org.hibernate.Criteria;
import org.hibernate.LockOptions;
import org.hibernate.Query;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;

import java.io.Serializable;
import java.util.Collection;
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

	List<E> getIn(Collection ids);

	List<E> getIn(Object[] ids);

	List<E> getAll();

	List<E> find(DetachedCriteria dc);

	List<E> find(DetachedCriteria dc, int firstResult, int maxResults, Order... orders);

	List<E> find(Criterion[] criterions);

	List<E> find(Criterion[] criterions, int firstResult, int maxResults, Order... orders);

	List<E> find(Query query, HibernateParameter... parameters);

	List<E> find(Query query, int firstResult, int maxResults, HibernateParameter... parameters);

	List<E> findByQueryString(String queryString, HibernateParameter... parameters);

	List<E> findByQueryString(String queryString, int firstResult, int maxResults, HibernateParameter... parameters);

	List<E> findByNamedQuery(String queryName, HibernateParameter... parameters);

	List<E> findByNamedQuery(String queryName, int firstResult, int maxResults, HibernateParameter... parameters);

	PagedList<E> getPage(DetachedCriteria dc, int pageNo, int pageSize, Order... orders);

	PagedList<E> getPage(Query query, int pageNo, int pageSize, HibernateParameter... parameters);

	PagedList<E> getPageByQueryString(String queryString, int pageNo, int pageSize, HibernateParameter... parameters);

	PagedList<E> getPageByNamedQuery(String queryName, int pageNo, int pageSize, HibernateParameter... parameters);


	E findOne(DetachedCriteria dc);

	E findOne(Criterion[] criterions);

	E findOne(Query query, HibernateParameter... parameters);

	E findOneByQueryString(String queryString, HibernateParameter... parameters);

	E findOneByNamedQuery(String queryName, HibernateParameter... parameters);


	E findFirst(DetachedCriteria dc);

	E findFirst(Criterion[] criterions);

	E findFirst(Query query, HibernateParameter... parameters);

	E findFirstByQueryString(String queryString, HibernateParameter... parameters);

	E findFirstByNamedQuery(String queryName, HibernateParameter... parameters);

	boolean exists();

	boolean exists(DetachedCriteria dc);

	boolean exists(Criterion[] criterions);

	boolean exists(Query query, HibernateParameter... parameters);

	boolean existsByQueryString(String queryString, HibernateParameter... parameters);

	boolean existsByNamedQuery(String queryName, HibernateParameter... parameters);

	long Count();

	long count(DetachedCriteria dc);

	long count(Criterion[] criterions);


	void merge(E entity);

	void persist(E entity);

	void save(E entity);

	void saveOrUpdate(E entity);

	void update(E entity);

	void delete(E entity);

	void deleteEntities(Collection<E> entities);

	void delete(DetachedCriteria dc);

	void deleteById(Serializable id);

	int deleteAllWithoutCascade();

	int executeUpdateByQueryString(String queryString, HibernateParameter... parameters);

	int executeUpdateByNamedQuery(String queryName, HibernateParameter... parameters);


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

}
