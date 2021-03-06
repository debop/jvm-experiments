package kr.nsoft.data.hibernate.tools;

import com.google.common.collect.Maps;
import kr.nsoft.commons.Guard;
import kr.nsoft.commons.spring.Springs;
import kr.nsoft.commons.tools.SerializeTool;
import kr.nsoft.data.domain.model.IStatefulEntity;
import kr.nsoft.data.hibernate.HibernateParameter;
import kr.nsoft.data.hibernate.listener.UpdateTimestampedEventListener;
import kr.nsoft.data.hibernate.repository.HibernateDaoFactory;
import kr.nsoft.data.hibernate.repository.IHibernateDao;
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

import java.util.Map;

import static kr.nsoft.commons.Guard.firstNotNull;
import static kr.nsoft.commons.Guard.shouldNotBeNull;

/**
 * Hibernate 관련 Tool
 * JpaUser: sunghyouk.bae@gmail.com
 * Date: 12. 11. 19
 */
@Slf4j
public class HibernateTool {

    private HibernateTool() {
    }

    private static final boolean isDebugEnabled = log.isDebugEnabled();

    public static HibernateDaoFactory getHibernateDaoFactory() {
        return Springs.getBean(HibernateDaoFactory.class);
    }

    public static <E extends IStatefulEntity> IHibernateDao getHibernateDao(Class<E> entityClass) {
        return getHibernateDaoFactory().getOrCreateHibernateDao(entityClass);
    }

    public static void registerUpdateTimestampEventListener(SessionFactory sessionFactory) {

        if (log.isInfoEnabled())
            log.info("지정된 SessionFactory에 UpdateTimestampedEventListener를 등록합니다.");

        EventListenerRegistry registry =
                ((SessionFactoryImpl) sessionFactory)
                        .getServiceRegistry()
                        .getService(EventListenerRegistry.class);

        UpdateTimestampedEventListener listener = new UpdateTimestampedEventListener();
        registry.getEventListenerGroup(EventType.PRE_INSERT).appendListener(listener);
        registry.getEventListenerGroup(EventType.PRE_UPDATE).appendListener(listener);
    }

    public static Map<String, Object> toMap(HibernateParameter... parameters) {
        Map<String, Object> map = Maps.newHashMap();
        for (HibernateParameter parameter : parameters) {
            map.put(parameter.getName(), parameter.getValue());
        }
        return map;
    }


    public static <T extends IStatefulEntity> DetachedCriteria createDetachedCriteria(Class<T> clazz) {
        return DetachedCriteria.forClass(clazz);
    }

    public static Criteria createCriteria(Class entityClass, Session session, Order[] orders, Criterion... criterions) {
        if (log.isDebugEnabled())
            log.debug("엔티티 [{}] 에 대한 Criteria를 생성합니다...", entityClass.getName());

        Criteria criteria = session.createCriteria(entityClass);
        addOrders(criteria, orders);
        return addCriterions(criteria, criterions);
    }

    /**
     * {@link DetachedCriteria} 를 복사합니다.
     */
    public static DetachedCriteria copyDetachedCriteria(DetachedCriteria dc) {
        shouldNotBeNull(dc, "dc");
        return (DetachedCriteria) SerializeTool.copyObject(dc);
    }

    public static Criteria copyCriteria(Criteria criteria) {
        shouldNotBeNull(criteria, "criteria");
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
        shouldNotBeNull(dc, "dc");

        for (Order order : orders) {
            dc.addOrder(order);
        }
        return dc;
    }

    public static DetachedCriteria addOrders(DetachedCriteria dc, Iterable<Order> orders) {
        shouldNotBeNull(dc, "dc");

        for (Order order : orders)
            dc.addOrder(order);

        return dc;
    }

    public static Criteria addOrders(Criteria criteria, Order... orders) {
        shouldNotBeNull(criteria, "criteria");

        for (Order order : orders) {
            criteria.addOrder(order);
        }
        return criteria;
    }

    public static Criteria addOrders(Criteria criteria, Iterable<Order> orders) {
        shouldNotBeNull(criteria, "criteria");

        for (Order order : orders) {
            criteria.addOrder(order);
        }
        return criteria;
    }

    /**
     * {@link Criteria} 에 {@link Criterion} 들을 AND 로 추가합니다.
     */
    public static Criteria addCriterions(Criteria criteria, Criterion... criterions) {
        shouldNotBeNull(criteria, "criteria");

        for (Criterion criterion : criterions) {
            criteria.add(criterion);
        }
        return criteria;
    }

    /**
     * {@link Query} 의 인자에 값을 설정합니다.
     */
    public static Query setParameters(Query query, HibernateParameter... params) {
        Guard.shouldNotBeNull(query, "query");

        for (HibernateParameter param : params) {
            if (isDebugEnabled)
                log.debug("쿼리문의 인자값을 설정합니다. param=[{}]", param);

            query.setParameter(param.getName(),
                               param.getValue(),
                               firstNotNull(param.getType(), ObjectType.INSTANCE));
        }
        return query;
    }

    /**
     * {@link Criteria} 에 조회 범위를 지정합니다.
     */
    public static Criteria setPaging(Criteria criteria, Integer firstResult, Integer maxResults) {
        Guard.shouldNotBeNull(criteria, "criteria");

        if (isDebugEnabled)
            log.debug("criteria에 fetch range를 지정합니다. firstResult=[{}], maxResults=[{}]", firstResult, maxResults);

        if (firstResult != null && firstResult >= 0)
            criteria.setFirstResult(firstResult);

        if (maxResults != null && maxResults > 0)
            criteria.setMaxResults(maxResults);

        return criteria;
    }

    /**
     * {@link Query} 에 조회 범위를 지정합니다.
     */
    public static Query setPaging(Query query, Integer firstResult, Integer maxResults) {
        Guard.shouldNotBeNull(query, "query");

        if (isDebugEnabled)
            log.debug("query에 fetch range를 지정합니다. firstResult=[{}], maxResults=[{}]", firstResult, maxResults);

        if (firstResult != null && firstResult >= 0)
            query.setFirstResult(firstResult);

        if (maxResults != null && maxResults > 0)
            query.setMaxResults(maxResults);

        return query;
    }
}
