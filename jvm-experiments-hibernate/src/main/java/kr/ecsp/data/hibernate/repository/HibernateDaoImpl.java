package kr.ecsp.data.hibernate.repository;

import kr.ecsp.data.domain.HibernateParameter;
import kr.ecsp.data.domain.model.StatefulEntity;
import kr.ecsp.data.hibernate.UnitOfWork;
import kr.ecsp.data.hibernate.tools.HibernateTool;
import kr.escp.commons.Guard;
import kr.escp.commons.tools.ReflectTool;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Criteria;
import org.hibernate.LockOptions;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

/**
 * Hibernate 용 Repository 입니다.
 * Spring의 HibernateDaoSupport 및 HibernateTemplate는 더 이상 사용하지 말라.
 * 참고: http://forum.springsource.org/showthread.php?117227-Missing-Hibernate-Classes-Interfaces-in-spring-orm-3.1.0.RC1
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 11. 27.
 */
@Slf4j
@Repository
@SuppressWarnings("unchecked")
public class HibernateDaoImpl<E extends StatefulEntity> implements HibernateDao<E> {

	@Getter
	private Class<E> entityClass;

	// TODO: 이 값은 RCL처럼 Singleton으로 제공되도록 해야 한다.
	@Setter
	private UnitOfWork unitOfWork;

	public HibernateDaoImpl(Class<E> entityClass) {
		this.entityClass = entityClass;
	}

	private Session getSession() {
		return unitOfWork.getSession();
	}

	@Override
	@SuppressWarnings("unchecked")
	public E load(Serializable id) {
		return (E) getSession().load(entityClass, id);
	}

	@Override
	public E load(Serializable id, LockOptions lockOptions) {
		return (E) getSession().load(entityClass, id, lockOptions);
	}

	@Override
	public E get(Serializable id) {
		return (E) getSession().get(entityClass, id);
	}

	@Override
	public E get(Serializable id, LockOptions lockOptions) {
		return (E) getSession().get(entityClass, id, lockOptions);
	}

	@Override

	public List<E> getAll() {
		return
			getSession()
				.createQuery("from " + entityClass.getName())
				.list();
	}


	@Override
	public List<E> find(DetachedCriteria dc) {
		return dc.getExecutableCriteria(getSession()).list();
	}

	@Override
	public List<E> find(DetachedCriteria dc, int firstResult, int maxResults, Order... orders) {
		Criteria criteria = dc.getExecutableCriteria(getSession());

		HibernateTool.setPaging(criteria, firstResult, maxResults);
		HibernateTool.addOrders(criteria, orders);

		return criteria.list();
	}

	@Override
	public List<E> find(Criterion[] criterions) {
		return find(criterions, -1, -1);
	}

	@Override
	public List<E> find(Criterion[] criterions, int firstResult, int maxResults, Order... orders) {
		Criteria criteria = getSession().createCriteria(entityClass);
		for (Criterion criterion : criterions)
			criteria.add(criterion);

		HibernateTool.setPaging(criteria, firstResult, maxResults);
		HibernateTool.addOrders(criteria, orders);

		return criteria.list();
	}

	@Override
	public List<E> findByQueryString(String queryString, HibernateParameter... parameters) {

		return findByQueryString(queryString, -1, -1, parameters);
	}

	@Override
	public List<E> findByQueryString(String queryString, int firstResult, int maxResults, HibernateParameter... parameters) {
		Guard.shouldNotBeEmpty(queryString, "queryString");
		if (log.isDebugEnabled())
			log.debug("쿼리문을 실행합니다. queryString=[{}], firstResult=[{}], maxResults=[{}],parameters=[{}]",
			          queryString, firstResult, maxResults, ReflectTool.listToString(parameters));

		Query query = getSession().createQuery(queryString);
		HibernateTool.setPaging(query, firstResult, maxResults);
		HibernateTool.setParameters(query, parameters);

		return query.list();
	}

	@Override
	public List<E> findByNamedQuery(String queryName, HibernateParameter... parameters) {
		return findByNamedQuery(queryName, -1, -1, parameters);
	}

	@Override
	public List<E> findByNamedQuery(String queryName, int firstResult, int maxResults, HibernateParameter... parameters) {
		Guard.shouldNotBeEmpty(queryName, "queryName");
		if (log.isDebugEnabled())
			log.debug("NamedQuery를 로드하여 실행합니다. queryName=[{}], firstResult=[{}], maxResults=[{}], parameters=[{}]",
			          queryName, firstResult, maxResults, ReflectTool.listToString(parameters));

		Query query = getSession().getNamedQuery(queryName);
		HibernateTool.setPaging(query, firstResult, maxResults);
		HibernateTool.setParameters(query, parameters);

		return query.list();
	}
}
