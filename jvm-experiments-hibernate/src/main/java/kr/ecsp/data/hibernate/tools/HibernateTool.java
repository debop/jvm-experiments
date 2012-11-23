package kr.ecsp.data.hibernate.tools;

import kr.ecsp.data.domain.HibernateParameter;
import kr.ecsp.data.domain.model.StatefulEntity;
import kr.ecsp.data.hibernate.interceptor.UpdateTimestampedInterceptor;
import kr.ecsp.data.hibernate.listener.UpdateTimestampedEventListener;
import kr.escp.commons.Guard;
import kr.escp.commons.tools.ArrayTool;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.internal.SessionFactoryImpl;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.hibernate.type.ObjectType;

import javax.naming.Context;
import javax.naming.InitialContext;
import java.util.HashMap;
import java.util.Map;

import static kr.escp.commons.Guard.firstNotNull;

/**
 * Hibernate 관련 Tool
 * JpaUser: sunghyouk.bae@gmail.com
 * Date: 12. 11. 19
 */
@Slf4j
public class HibernateTool {

	private static SessionFactory sessionFactory;
	private static Context jndiContext;

	static {
		buildSessionFactory();
	}

	private static void buildSessionFactory() {

		if (log.isInfoEnabled())
			log.info("새로운 SessionFactory 생성을 시작합니다...");

		try {
			Configuration configuration = new Configuration();

			configuration.addResource("hibernate.cfg.xml");
			configuration.setInterceptor(new UpdateTimestampedInterceptor());
			//configuration.setNamingStrategy(new OracleNamingStrategy());
			configuration.configure();
			ServiceRegistry serviceRegistry =
				new ServiceRegistryBuilder()
					.applySettings(configuration.getProperties())
					.buildServiceRegistry();

			sessionFactory = configuration.buildSessionFactory(serviceRegistry);

			registerListeners(sessionFactory);

			jndiContext = new InitialContext();

			if (log.isInfoEnabled())
				log.info("새로운 SessionFactory를 생성했습니다.");
		} catch (Throwable ex) {
			// Make sure you log the exception, as it might be swallowed
			System.err.println("Initial SessionFactory creation failed." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

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
	 *
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
		for (Order order : orders)
			dc.addOrder(order);

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

		if (firstResult != null && firstResult > 0)
			criteria.setFirstResult(firstResult);

		if (maxResults != null && maxResults > 0)
			criteria.setMaxResults(maxResults);

		return criteria;
	}

	public static Query setPaging(Query query, Integer firstResult, Integer maxResults) {
		Guard.shouldNotBeNull(query, "query");

		if (firstResult != null && firstResult > 0)
			query.setFirstResult(firstResult);
		if (maxResults != null && maxResults > 0)
			query.setMaxResults(maxResults);

		return query;
	}
}
