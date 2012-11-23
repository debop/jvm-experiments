package kr.ecsp.data.hibernate.repository;

import kr.ecsp.data.domain.HibernateParameter;
import kr.ecsp.data.domain.model.StatefulEntity;
import kr.ecsp.data.hibernate.tools.HibernateTool;
import kr.escp.commons.collection.PagedList;
import kr.escp.commons.collection.SimplePagedList;
import kr.escp.commons.tools.ArrayTool;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.*;
import org.hibernate.criterion.*;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static kr.ecsp.data.hibernate.tools.HibernateTool.*;
import static kr.escp.commons.tools.ReflectTool.getGenericParameterType;


/**
 * Hibernate 용 Repository 의 추상 클래스
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 9. 15.
 */
@Slf4j
@SuppressWarnings("unchecked")
public abstract class HibernateRepositoryBase<E extends StatefulEntity>
	extends HibernateDaoSupport implements HibernateRepository<E> {

	private static final long serialVersionUID = 5705566511550314658L;

	@Getter
	private final Class<E> entityClass;

	protected HibernateRepositoryBase() {
		this.entityClass = getGenericParameterType(this);
	}

	@Getter
	@Setter
	private boolean cacheable = true;

	@Override
	public E get(Serializable id) {
		return (E) getHibernateTemplate().get(entityClass, id);
	}

	@Override
	public E get(Serializable id, LockMode lockMode) {
		return (E) getHibernateTemplate().get(entityClass, id, lockMode);
	}

	@Override
	public E load(Serializable id) {
		return (E) getHibernateTemplate().load(entityClass, id);
	}

	@Override
	public E load(Serializable id, LockMode lockMode) {
		return (E) getHibernateTemplate().load(entityClass, id, lockMode);
	}

	@Override
	public List<E> getIn(Collection ids) {
		if (!ArrayTool.isEmpty(ids))
			return new ArrayList<E>();

		return getList(createDetachedCriteria().add(Restrictions.in("id", ids)));
	}

	@Override
	public List<E> getIn(Object[] ids) {
		if (!ArrayTool.isEmpty(ids))
			return new ArrayList<E>();

		return getList(createDetachedCriteria().add(Restrictions.in("id", ids)));
	}

	@Override
	public List<E> getList(DetachedCriteria dc) {
		return getList(dc, 0, 0);
	}

	@Override
	public List<E> getList(DetachedCriteria dc, int firstResult, int maxResults, Order... orders) {
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = dc.getExecutableCriteria(session);

		setPaging(criteria, firstResult, maxResults);
		addOrders(criteria, orders);

		return (List<E>) criteria.setCacheable(this.isCacheable())
		                         .list();
	}

	@Override
	public List<E> getList(Query query, HibernateParameter... params) {
		return getList(query, 0, 0, params);
	}

	@Override
	public List<E> getList(Query query, int firstResult, int maxResults, HibernateParameter... params) {
		HibernateTool.setParameters(query, params);
		setPaging(query, firstResult, maxResults);

		return (List<E>) query.setCacheable(this.isCacheable())
		                      .list();
	}

	@Override
	public List<E> getList(int firstResult, int maxResults, Order[] orders, Criterion... criterions) {
		Criteria criteria = HibernateTool.createCriteria(entityClass, getSession(), orders, criterions);
		setPaging(criteria, firstResult, maxResults);

		return (List<E>) criteria.setCacheable(this.isCacheable())
		                         .list();
	}

	@Override
	public List<E> getListByHql(String hql, HibernateParameter... params) {
		return getListByHql(hql, 0, 0, params);
	}

	@Override
	public List<E> getListByHql(String hql, int firstResult, int maxResults, HibernateParameter... params) {
		Query query = getSession().createQuery(hql);
		return getList(query, firstResult, maxResults, params);
	}

	@Override
	public List<E> getListByNamedQuery(String namedQuery, HibernateParameter... params) {
		return getListByNamedQuery(namedQuery, 0, 0, params);
	}

	@Override
	public List<E> getListByNamedQuery(String namedQuery, int firstResult, int maxResults, HibernateParameter... params) {
		Query query = getSession().getNamedQuery(namedQuery);
		return getList(query, firstResult, maxResults, params);
	}

	@Override
	public PagedList<E> getPagedList(DetachedCriteria dc, int pageNo, int pageSize, Order... orders) {
		// 전체 Item Count를 구합니다.
		Long itemCount = count(dc.getExecutableCriteria(getSession()));

		int firstResult = (pageNo - 1) * pageSize;
		List<E> list = getList(dc, firstResult, pageSize, orders);

		return new SimplePagedList<E>(list, firstResult, pageSize, itemCount);
	}

	@Override
	public PagedList<E> getPagedList(final Query query,
	                                 final int pageNo,
	                                 final int pageSize,
	                                 final HibernateParameter... params) {
		// 전체 Item Count를 구합니다.
		Long itemCount = countByHql(query.getQueryString(), params);

		int firstResult = (pageNo - 1) * pageSize;
		List<E> list = getList(query, firstResult, pageSize, params);

		return new SimplePagedList<E>(list, firstResult, pageSize, itemCount);
	}

	@Override
	public PagedList<E> getPagedListByHql(final String hql,
	                                      final int pageNo,
	                                      final int pageSize,
	                                      final HibernateParameter... params) {
		Query query = getSession().createQuery(hql);
		return getPagedList(query, pageNo, pageSize, params);
	}

	@Override
	public PagedList<E> getPagedListByNamedQuery(final String namedQuery,
	                                             final int pageNo,
	                                             final int pageSize,
	                                             final HibernateParameter... params) {
		Query query = getSession().getNamedQuery(namedQuery);
		return getPagedList(query, pageNo, pageSize, params);
	}

	@Override
	public E getFirst(DetachedCriteria dc, Order... orders) {
		return getFirst(dc.getExecutableCriteria(getSession()), orders);
	}

	@Override
	public E getFirst(Criteria criteria, Order... orders) {
		for (Order order : orders)
			criteria.addOrder(order);

		return (E) criteria.setMaxResults(1)
		                   .setCacheable(this.isCacheable())
		                   .uniqueResult();
	}

	@Override
	public E getFirst(Query query, HibernateParameter... params) {
		setParameters(query, params);
		return (E) query.setMaxResults(1)
		                .setCacheable(this.isCacheable())
		                .uniqueResult();
	}

	@Override
	public E getFirstByHql(String hql, HibernateParameter... params) {
		return getFirst(getSession().createQuery(hql), params);
	}

	@Override
	public E getFirstByNamedQuery(String namedQuery, HibernateParameter... params) {
		return getFirst(getSession().getNamedQuery(namedQuery), params);
	}

	@Override
	public E getUnique(DetachedCriteria dc) {
		return (E) dc.getExecutableCriteria(getSession()).uniqueResult();
	}

	@Override
	public E getUnique(Query query, HibernateParameter... params) {
		return (E) setParameters(query, params).uniqueResult();
	}

	@Override
	public E getUniqueByHql(String hql, HibernateParameter... params) {
		return (E) getUnique(getSession().createQuery(hql), params);
	}

	@Override
	public E getUniqueByNamedQuery(String namedQuery, HibernateParameter... params) {
		return (E) getUnique(getSession().getNamedQuery(namedQuery), params);
	}

	@Override
	public boolean exists(DetachedCriteria dc) {
		return exists(dc.getExecutableCriteria(getSession()));
		// return (getFirst(dc) != null);
	}

	@Override
	public boolean exists(Criteria criteria) {
		return criteria.scroll(ScrollMode.FORWARD_ONLY).first();
		// return (getFirst(criteria) != null);
	}

	public boolean exists(Query query, HibernateParameter... params) {
		return query.scroll(ScrollMode.FORWARD_ONLY).first();
		// return getFirst(query, params) != null;
	}

	@Override
	public boolean existsByHql(String hql, HibernateParameter... params) {
		return getFirstByHql(hql, params) != null;
	}

	@Override
	public boolean existsByNamedQuery(String namedQuery, HibernateParameter... params) {
		return getFirstByNamedQuery(namedQuery, params) != null;
	}

	@Override
	public long count(DetachedCriteria dc) {
		return count(dc.getExecutableCriteria(getSession()));
	}

	@Override
	public long count(Criteria criteria) {
		return (Long) criteria.setResultTransformer(Criteria.ROOT_ENTITY)
		                      .setCacheable(this.isCacheable())
		                      .uniqueResult();
	}

	@Override
	public long count(final Query query, final HibernateParameter... params) {
		setParameters(query, params);

		return (Long) query.setResultTransformer(Criteria.ROOT_ENTITY)
		                   .setCacheable(this.isCacheable())
		                   .uniqueResult();
	}

	@Override
	public long countByHql(final String hql, final HibernateParameter... params) {
		final Query query = getSession().createQuery(hql);
		return count(getSession().createQuery(hql), params);
	}

	@Override
	public long countByNamedQuery(final String namedQuery, final HibernateParameter... params) {
		final Query query = getSession().getNamedQuery(namedQuery);
		return count(query, params);
	}

	private <TProject> Criteria buildProjectionCriteria(Class<TProject> projectClass,
	                                                    Criteria criteria,
	                                                    Projection projection,
	                                                    boolean distinctResult) {
		criteria.setProjection(distinctResult ? Projections.distinct(projection) : projection);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(projectClass));

		return criteria;
	}

	@Override
	public <TProject> TProject reportOne(Class<TProject> projectClass,
	                                     ProjectionList projectionList,
	                                     DetachedCriteria dc) {
		Criteria criteria = dc.getExecutableCriteria(getSession());
		return reportOne(projectClass, projectionList, criteria);
	}

	@Override
	public <TProject> TProject reportOne(Class<TProject> projectClass,
	                                     ProjectionList projectionList,
	                                     Criteria criteria) {
		buildProjectionCriteria(projectClass, criteria, projectionList, true);
		return (TProject) criteria.uniqueResult();
	}

	@Override
	public <TProject> List<TProject> reportList(Class<TProject> projectClass,
	                                            ProjectionList projectionList,
	                                            DetachedCriteria dc) {
		Criteria criteria = dc.getExecutableCriteria(getSession());
		return reportList(projectClass, projectionList, criteria);
	}

	@Override
	public <TProject> List<TProject> reportList(Class<TProject> projectClass,
	                                            ProjectionList projectionList,
	                                            Criteria criteria) {

		buildProjectionCriteria(projectClass, criteria, projectionList, false);
		return (List<TProject>) criteria.list();
	}


	@Override
	public void merge(E entity) {
		getSession().merge(entity);
	}

	@Override
	public void persist(E entity) {
		getSession().persist(entity);
	}

	@Override
	public void replicate(E entity, ReplicationMode replicationMode) {
		getSession().replicate(entity, replicationMode);
	}

	@Override
	public Serializable save(E entity) {
		return getSession().save(entity);
	}

	@Override
	public void saveOrUpdate(E entity) {
		getSession().saveOrUpdate(entity);
	}

	@Override
	public void update(E entity) {
		getSession().update(entity);
	}

	@Override
	public void delete(E entity) {
		getSession().delete(entity);
	}

	@Override
	public void deleteById(Serializable id) {
		getSession().delete(load(id));
	}

	@Override
	public void deleteById(Serializable id, LockMode lockMode) {
		getHibernateTemplate().delete(load(id), lockMode);
	}

	@Override
	public void deleteAll() {
		for (E entity : getList(createDetachedCriteria()))
			getSession().delete(entity);
	}

	@Override
	public void deleteList(DetachedCriteria dc) {
		for (E entity : getList(dc))
			getSession().delete(entity);
	}

	@Override
	public void deleteList(Query query, HibernateParameter... params) {
		for (E entity : getList(query, 0, 0, params))
			getSession().delete(entity);
	}

	@Override
	public int executeUpdate(String hql, HibernateParameter... params) {
		return
			setParameters(getSession().createQuery(hql), params)
				.executeUpdate();
	}

	@Override
	public int executeUpdateWithTx(String hql, HibernateParameter... params) {
		Query query = setParameters(getSession().createQuery(hql), params);

		Transaction tx = getSession().beginTransaction();
		int result = -1;
		try {
			result = query.executeUpdate();
			tx.commit();
		} catch (Exception ex) {
			HibernateRepositoryBase.log.error("DB 작업에 에외가 발생했습니다. Tx 을 rollback 합니다.", ex);
			tx.rollback();
		}
		return result;
	}

	@Override
	public DetachedCriteria createDetachedCriteria() {
		return DetachedCriteria.forClass(entityClass);
	}

	@Override
	public DetachedCriteria createDetachedCriteria(String alias) {
		return DetachedCriteria.forClass(entityClass, alias);
	}
}
