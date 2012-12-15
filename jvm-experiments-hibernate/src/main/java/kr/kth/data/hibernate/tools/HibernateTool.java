package kr.kth.data.hibernate.tools;

import kr.kth.commons.base.Guard;
import kr.kth.commons.tools.ArrayTool;
import kr.kth.commons.tools.SerializeTool;
import kr.kth.data.domain.model.StatefulEntity;
import kr.kth.data.hibernate.HibernateParameter;
import kr.kth.data.hibernate.listener.UpdateTimestampedEventListener;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.internal.CriteriaImpl;
import org.hibernate.internal.SessionFactoryImpl;
import org.hibernate.type.ObjectType;

import java.util.HashMap;
import java.util.Map;

import static kr.kth.commons.base.Guard.firstNotNull;

/**
 * Hibernate 관련 Tool
 * JpaUser: sunghyouk.bae@gmail.com
 * Date: 12. 11. 19
 */
@Slf4j
public class HibernateTool {

	public static void registerListeners(SessionFactory sessionFactory) {
		EventListenerRegistry registry =
			((SessionFactoryImpl) sessionFactory)
				.getServiceRegistry()
				.getService(EventListenerRegistry.class);

		UpdateTimestampedEventListener listener = new UpdateTimestampedEventListener();
		registry.getEventListenerGroup(EventType.PRE_INSERT).appendListener(listener);
		registry.getEventListenerGroup(EventType.PRE_UPDATE).appendListener(listener);
	}

	public static Map<String, Object> toMap(HibernateParameter... parameters) {
		Map<String, Object> map = new HashMap<>();
		for (HibernateParameter parameter : parameters) {
			map.put(parameter.getName(), parameter.getValue());
		}
		return map;
	}


	public static <T extends StatefulEntity> DetachedCriteria createDetachedCriteria(Class<T> clazz) {
		return DetachedCriteria.forClass(clazz);
	}

	public static Criteria createCriteria(Class entityClass, Session session,
	                                      Order[] orders, Criterion... criterions) {
		if (log.isDebugEnabled())
			log.debug(entityClass.getName() + "에 대한 Criteria를 생성합니다...");

		Criteria criteria = session.createCriteria(entityClass);

		if (orders != null && orders.length > 0) {
			for (Order order : orders) {
				criteria.addOrder(order);
			}
		}

		for (Criterion criterion : criterions) {
			criteria.add(criterion);
		}
		return criteria;
	}

	/**
	 * {@link DetachedCriteria} 를 복사합니다.
	 */
	public static DetachedCriteria copyDetachedCriteria(DetachedCriteria dc) {
		return (DetachedCriteria) SerializeTool.copyObject(dc);
	}

	public static Criteria copyCriteria(Criteria criteria) {
		return (Criteria) SerializeTool.copyObject((CriteriaImpl) criteria);
	}

	/**
	 * {@link DetachedCriteria} 를 현 {@link Session} 에서 사용할 {@link Criteria} 로 변환합니다.
	 */
	public static Criteria getExecutableCriteria(Session session, DetachedCriteria dc) {
		return dc.getExecutableCriteria(session);
	}

	/**
	 * {@link org.hibernate.criterion.DetachedCriteria} 를 가지고 {@link org.hibernate.Criteria} 를 빌드합니다.
	 */
	public static Criteria getExecutableCriteria(DetachedCriteria dc, Session session, Order... orders) {
		Guard.shouldNotBeNull(dc, "dc");
		Guard.shouldNotBeNull(session, "session");

		if (log.isDebugEnabled())
			log.debug("DetachedCriteria를 가지고 Criteria를 빌드합니다.");

		Criteria criteria = dc.getExecutableCriteria(session);

		for (Order order : orders) {
			criteria.addOrder(order);
		}
		return criteria;
	}

	public static DetachedCriteria addOrders(DetachedCriteria dc, Order... orders) {
		for (Order order : orders) {
			dc.addOrder(order);
		}
		return dc;
	}

	public static DetachedCriteria addOrders(DetachedCriteria dc, Iterable<Order> orders) {
		for (Order order : orders)
			dc.addOrder(order);

		return dc;
	}

	public static Criteria addOrders(Criteria criteria, Order... orders) {
		for (Order order : orders) {
			criteria.addOrder(order);
		}
		return criteria;
	}

	public static Criteria addOrders(Criteria criteria, Iterable<Order> orders) {
		for (Order order : orders) {
			criteria.addOrder(order);
		}
		return criteria;
	}

	/**
	 * {@link Criteria} 에 {@link Criterion} 들을 AND 로 추가합니다.
	 */
	public static Criteria addCriterions(Criteria criteria, Criterion... criterions) {
		for (Criterion criterion : criterions) {
			criteria.add(criterion);
		}
		return criteria;
	}

	/**
	 * {@link org.hibernate.Query} 의 인자에 값을 설정합니다.
	 */
	public static Query setParameters(Query query, HibernateParameter... params) {
		Guard.shouldNotBeNull(query, "query");

		if (ArrayTool.isEmpty(params))
			return query;

		for (HibernateParameter param : params) {
			query.setParameter(param.getName(),
			                   param.getValue(),
			                   firstNotNull(param.getType(), ObjectType.INSTANCE));
		}
		return query;
	}

	public static Criteria setPaging(Criteria criteria, Integer firstResult, Integer maxResults) {
		Guard.shouldNotBeNull(criteria, "criteria");

		if (firstResult != null && firstResult >= 0)
			criteria.setFirstResult(firstResult);

		if (maxResults != null && maxResults > 0)
			criteria.setMaxResults(maxResults);

		return criteria;
	}

	public static Query setPaging(Query query, Integer firstResult, Integer maxResults) {
		Guard.shouldNotBeNull(query, "query");

		if (firstResult != null && firstResult >= 0)
			query.setFirstResult(firstResult);
		if (maxResults != null && maxResults > 0)
			query.setMaxResults(maxResults);

		return query;
	}
}
