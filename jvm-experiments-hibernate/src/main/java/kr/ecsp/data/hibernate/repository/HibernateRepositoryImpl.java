package kr.ecsp.data.hibernate.repository;

import kr.ecsp.data.domain.HibernateParameter;
import kr.ecsp.data.domain.model.StatefulEntity;
import kr.ecsp.data.hibernate.tools.CriteriaTool;
import kr.ecsp.data.hibernate.tools.HibernateTool;
import kr.escp.commons.Guard;
import kr.escp.commons.Local;
import kr.escp.commons.collection.PagedList;
import kr.escp.commons.collection.SimplePagedList;
import kr.escp.commons.tools.ArrayTool;
import kr.escp.commons.tools.ReflectTool;
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

/**
 * Hibernate 용 Repository 의 추상 클래스
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 9. 15.
 */
@Slf4j
@SuppressWarnings("unchecked")
public class HibernateRepositoryImpl<E extends StatefulEntity>
	extends HibernateDaoSupport implements HibernateRepository {

	private static final long serialVersionUID = 5705566511550314658L;

	private static final String SessionKey = "kr.ecsp.hibernate.repository.Session.key";

	public Session getCurrentSession() {
		Session session = (Session) Local.get(SessionKey);
		if (session == null) {
			session = getSessionFactory().openSession();
			Local.put(SessionKey, session);
		}
		return session;
	}

	@Getter
	@Setter
	private boolean cacheable = true;

	@Override
	public Object get(Class<? extends StatefulEntity> entityType, Serializable id) {
		return getHibernateTemplate().get(entityType, id);
	}

	@Override
	public Object get(Class<? extends StatefulEntity> entityType, Serializable id, LockMode lockMode) {
		return getHibernateTemplate().get(entityType, id, lockMode);
	}

	@Override
	public Object load(Class<? extends StatefulEntity> entityType, Serializable id) {
		return getHibernateTemplate().load(entityType, id);
	}

	@Override
	public Object load(Class<? extends StatefulEntity> entityType, Serializable id, LockMode lockMode) {
		return getHibernateTemplate().load(entityType, id, lockMode);
	}

	@Override
	public List getIn(Class<? extends StatefulEntity> entityType, Collection ids) {
		if (!ArrayTool.isEmpty(ids))
			return new ArrayList<StatefulEntity>();

		return getList(CriteriaTool.addIn(DetachedCriteria.forClass(entityType), "id", ids));
	}

	@Override
	public List getIn(Class<? extends StatefulEntity> entityType, Object[] ids) {
		if (!ArrayTool.isEmpty(ids))
			return new ArrayList<StatefulEntity>();

		return getList(CriteriaTool.addIn(DetachedCriteria.forClass(entityType), "id", ids));
	}

	@Override
	public List getList(final DetachedCriteria dc) {
		return getList(dc, -1, -1);
	}

	@Override
	public List getList(final DetachedCriteria dc, final int firstResult, final int maxResults, final Order... orders) {
		return dc.getExecutableCriteria(getCurrentSession()).list();
	}

	@Override
	public List getList(Query query, HibernateParameter... params) {
		return getList(query, -1, -1, params);
	}

	@Override
	public List getList(Query query, int firstResult, int maxResults, HibernateParameter... params) {
		HibernateTool.setParameters(query, params);
		HibernateTool.setPaging(query, firstResult, maxResults);

		return query.setCacheable(this.isCacheable())
		            .list();
	}

	@Override
	public List getList(Class<? extends StatefulEntity> entityType,
	                    int firstResult,
	                    int maxResults,
	                    Order[] orders,
	                    Criterion... criterions) {
		Criteria criteria = HibernateTool.createCriteria(entityType, getCurrentSession(), orders, criterions);
		HibernateTool.setPaging(criteria, firstResult, maxResults);

		return criteria.setCacheable(this.isCacheable())
		               .list();
	}

	@Override
	public List getListByHql(String hql, HibernateParameter... params) {
		return getListByHql(hql, 0, 0, params);
	}

	@Override
	public List getListByHql(String hql, int firstResult, int maxResults, HibernateParameter... params) {
		Query query = getCurrentSession().createQuery(hql);
		return getList(query, firstResult, maxResults, params);
	}

	@Override
	public List getListByNamedQuery(String namedQuery, HibernateParameter... params) {
		return getListByNamedQuery(namedQuery, 0, 0, params);
	}

	@Override
	public List getListByNamedQuery(String namedQuery, int firstResult, int maxResults, HibernateParameter... params) {
		Query query = getCurrentSession().getNamedQuery(namedQuery);
		return getList(query, firstResult, maxResults, params);
	}

	@Override
	public PagedList getPagedList(DetachedCriteria dc, int pageNo, int pageSize, Order... orders) {
		// 전체 Item Count를 구합니다.
		Long itemCount = count(dc.getExecutableCriteria(getSession(true)));

		int firstResult = (pageNo - 1) * pageSize;
		List list = getList(dc, firstResult, pageSize, orders);

		return new SimplePagedList(list, firstResult, pageSize, itemCount);
	}

	@Override
	public PagedList getPagedList(final Query query,
	                              final int pageNo,
	                              final int pageSize,
	                              final HibernateParameter... params) {
		// 전체 Item Count를 구합니다.
		Long itemCount = countByHql(query.getQueryString(), params);

		int firstResult = (pageNo - 1) * pageSize;
		List list = getList(query, firstResult, pageSize, params);

		return new SimplePagedList(list, firstResult, pageSize, itemCount);
	}

	@Override
	public PagedList getPagedListByHql(final String hql,
	                                   final int pageNo,
	                                   final int pageSize,
	                                   final HibernateParameter... params) {
		Query query = getCurrentSession().createQuery(hql);
		return getPagedList(query, pageNo, pageSize, params);
	}

	@Override
	public PagedList getPagedListByNamedQuery(final String namedQuery,
	                                          final int pageNo,
	                                          final int pageSize,
	                                          final HibernateParameter... params) {
		Query query = getCurrentSession().getNamedQuery(namedQuery);
		return getPagedList(query, pageNo, pageSize, params);
	}

	@Override
	public Object getFirst(final DetachedCriteria dc, final Order... orders) {
		return getFirst(dc.getExecutableCriteria(getCurrentSession()), orders);
	}

	@Override
	public Object getFirst(final Criteria criteria, final Order... orders) {
		for (Order order : orders)
			criteria.addOrder(order);

		return criteria.setMaxResults(1)
		               .setCacheable(this.isCacheable())
		               .uniqueResult();
	}

	@Override
	public Object getFirst(Query query, HibernateParameter... params) {
		HibernateTool.setParameters(query, params);
		return query.setMaxResults(1)
		            .setCacheable(this.isCacheable())
		            .uniqueResult();
	}

	@Override
	public Object getFirstByQueryString(final String queryString, final HibernateParameter... params) {
		if (log.isDebugEnabled())
			log.debug("execute queryString=[{}], params=[{}]", queryString, ReflectTool.listToString(params));

		return getFirst(getCurrentSession().createQuery(queryString), params);
	}

	@Override
	public Object getFirstByNamedQuery(final String queryName, final HibernateParameter... params) {
		if (log.isDebugEnabled())
			log.debug("execute queryName=[{}], params=[{}]", queryName, ReflectTool.listToString(params));
		return getFirst(getCurrentSession().getNamedQuery(queryName), params);
	}

	@Override
	public Object getUnique(DetachedCriteria dc) {
		return dc.getExecutableCriteria(getCurrentSession()).uniqueResult();
	}

	@Override
	public Object getUnique(Query query, HibernateParameter... params) {
		return HibernateTool.setParameters(query, params).uniqueResult();
	}

	@Override
	public Object getUniqueByHql(final String hql, final HibernateParameter... params) {
		if (log.isDebugEnabled())
			log.debug("getUniqueByHql. queryString=[{}], params=[{}]", hql, ReflectTool.listToString(params));

		return getUnique(getCurrentSession().createQuery(hql), params);
	}

	@Override
	public Object getUniqueByNamedQuery(final String queryName, final HibernateParameter... params) {
		if (log.isDebugEnabled())
			log.debug("get unique entity. queryName=[{}], params=[{}]", queryName, params);
		return getUnique(getCurrentSession().getNamedQuery(queryName), params);
	}

	@Override
	public boolean exists(DetachedCriteria dc) {
		return exists(dc.getExecutableCriteria(getCurrentSession()));
	}

	@Override
	public boolean exists(Criteria criteria) {
		return criteria.scroll(ScrollMode.FORWARD_ONLY).first();
	}

	public boolean exists(Query query, HibernateParameter... params) {
		return HibernateTool.setParameters(query, params)
		                    .scroll(ScrollMode.FORWARD_ONLY)
		                    .first();
	}

	@Override
	public boolean existsByHql(final String hql, final HibernateParameter... params) {
		return exists(getCurrentSession().createQuery(hql), params);
	}

	@Override
	public boolean existsByNamedQuery(final String queryName, final HibernateParameter... params) {
		return exists(getCurrentSession().getNamedQuery(queryName), params);
	}

	@Override
	public long count(Class<? extends StatefulEntity> entityType) {
		return count(getCurrentSession().createCriteria(entityType));
	}

	@Override
	public long count(final DetachedCriteria dc) {
		return count(dc.getExecutableCriteria(getCurrentSession()));
	}

	@Override
	public long count(Criteria criteria) {
		Guard.shouldNotBeNull(criteria, "criteria");

		return (Long) criteria.setProjection(Projections.rowCount()).uniqueResult();

//		List list = criteria.setProjection(Projections.rowCount()).list();
//
//		if(list!=null && list.size() > 0)
//			return (Long)list.get(0);
//
//		return 0;
	}

	@Override
	public long count(final Query query, final HibernateParameter... params) {
		List list = HibernateTool.setParameters(query, params)
		                         .setResultTransformer(Criteria.ROOT_ENTITY)
		                         .list();
		if (list != null && list.size() > 0)
			return (Long) list.get(0);

		return 0;
	}

	@Override
	public long countByHql(final String queryString, final HibernateParameter... params) {
		if (log.isDebugEnabled())
			log.debug("레코드 갯수를 얻습니다. queryString=[{}], params=[{}]", queryString, ReflectTool.listToString(params));

		return count(getCurrentSession().createQuery(queryString), params);
	}

	@Override
	public long countByNamedQuery(final String queryName, final HibernateParameter... params) {
		if (log.isDebugEnabled())
			log.debug("레코드 갯수를 얻습니다. queryName=[{}], params=[{}]",
			          queryName, ReflectTool.listToString(params));
		return count(getCurrentSession().getNamedQuery(queryName), params);
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
	public void merge(StatefulEntity entity) {
		getSession().merge(entity);
	}

	@Override
	public void persist(StatefulEntity entity) {
		getSession().persist(entity);
	}

	@Override
	public void replicate(StatefulEntity entity, ReplicationMode replicationMode) {
		getSession().replicate(entity, replicationMode);
	}

	@Override
	public Serializable save(StatefulEntity entity) {
		return getSession().save(entity);
	}

	@Override
	public void saveOrUpdate(final StatefulEntity entity) {
		getHibernateTemplate().saveOrUpdate(entity);
	}

	@Override
	public void update(final StatefulEntity entity) {
		getHibernateTemplate().update(entity);
	}


	@Override
	public void delete(StatefulEntity entity) {
		getHibernateTemplate().delete(entity);
	}

	public void delete(StatefulEntity entity, LockMode lockMode) {
		getHibernateTemplate().delete(entity, lockMode);
	}

	@Override
	public void deleteById(Class<? extends StatefulEntity> entityType, Serializable id) {
		getHibernateTemplate().delete(load(entityType, id));
	}

	@Override
	public void deleteById(Class<? extends StatefulEntity> entityType, Serializable id, LockMode lockMode) {
		getHibernateTemplate().delete(load(entityType, id), lockMode);
	}

	@Override
	public void deleteAll(Class<? extends StatefulEntity> entityType) {
		getHibernateTemplate().deleteAll(getList(DetachedCriteria.forClass(entityType)));
	}

	@Override
	public void deleteList(Collection<StatefulEntity> entities) {
		getHibernateTemplate().deleteAll(entities);
	}

	@Override
	public int executeUpdate(String queryString, HibernateParameter... params) {

		if (HibernateRepositoryImpl.log.isDebugEnabled())
			HibernateRepositoryImpl.log.debug("executeUpdate 를 실행합니다. queryString=[{}], params=[{}]",
			                                  queryString, ReflectTool.listToString(params));

		Query query = getSession(true).createQuery(queryString);
		return HibernateTool.setParameters(query, params).executeUpdate();
	}

	@Override
	public int executeUpdateWithTx(final String hql, final HibernateParameter... params) {
		Session session = getCurrentSession();
		Transaction tx = session.beginTransaction();
		int result = -1;
		try {
			Query query = HibernateTool.setParameters(session.createQuery(hql), params);
			result = query.executeUpdate();
			tx.commit();
		} catch (Exception ex) {
			if (HibernateRepositoryImpl.log.isErrorEnabled())
				HibernateRepositoryImpl.log.error("DB 작업에 에외가 발생했습니다. Tx 을 rollback 합니다.", ex);
			tx.rollback();
		}
		return result;
	}
}
