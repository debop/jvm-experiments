package kr.kth.data.hibernate.unitofwork;

import kr.kth.commons.Guard;
import kr.kth.commons.Local;
import kr.kth.commons.spring3.Spring;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

/**
 * Hibernate 용 UnitOfWorkManager 입니다. (Spring 에서 제공하는 것은 Hibernate 4이상에서는 지원 안됩니다. 그래서 직접 만들었습니다.)
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 11. 27.
 */
@Slf4j
@Component
public final class UnitOfWorkManager {

	private static final String LocalSessionKey = "kr.kth.data.hibernate.unitofwork.UnitOfWorkManager.Session";

	@Getter
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		if (log.isInfoEnabled())
			log.info("SessionFactory를 설정합니다. sessionFactory=[{}]", sessionFactory);
		this.sessionFactory = sessionFactory;
	}

	public final Session getSession() {
		Session session = (Session) Local.get(LocalSessionKey);

		if (session == null) {
			if (log.isDebugEnabled())
				log.debug("현 ThreadContext에서 사용할 Session을 빌드합니다...");
			session = sessionFactory.openSession();
			Local.put(LocalSessionKey, session);
		}

		return session;
	}

	private static final String CURRENT_UNIT_OF_WORK_KEY = "kr.kth.data.hibernate.unitofwork.CurrentUnitOfWork";
	private static final Object syncLock = new Object();
	private static volatile UnitOfWork globalNonThreadSafeUnitOfWork;
	private static volatile UnitOfWorkFactory unitOfWorkFactory;

	public static boolean isStarted() {
		synchronized (syncLock) {
			if (globalNonThreadSafeUnitOfWork != null)
				return true;
			return Local.get(CURRENT_UNIT_OF_WORK_KEY) != null;
		}
	}

	public static UnitOfWork getCurrent() {
		Guard.assertTrue(isStarted(), "UnitOfWork 가 시작되지 않았습니다. 사용 전 UnitOfWork.Start() 를 호출하세요.");
		synchronized (syncLock) {
			if (globalNonThreadSafeUnitOfWork != null)
				return globalNonThreadSafeUnitOfWork;
			return (UnitOfWork) Local.get(CURRENT_UNIT_OF_WORK_KEY);
		}
	}

	public static SessionFactory getCurrentSessionFactory() {
		return getUnitOfWorkFactory().getSessionFactory();
	}

	public static Session getCurrentSession() {
		return getUnitOfWorkFactory().getCurrentSession();
	}

	public static UnitOfWorkFactory getUnitOfWorkFactory() {
		if (unitOfWorkFactory == null) {
			synchronized (syncLock) {
				unitOfWorkFactory = Spring.getOrRegisterBean(HibernateUnitOfWorkFactory.class);
			}
		}
		return unitOfWorkFactory;
	}

	public static void setUnitOfWorkFactory(UnitOfWorkFactory factory) {
		unitOfWorkFactory = factory;
	}

	public static void setCurrent(UnitOfWork unitOfWork) {
		Local.put(CURRENT_UNIT_OF_WORK_KEY, unitOfWork);
	}

	public static AutoCloseable registerGlobalUnitOfWork(UnitOfWork global) {
		if (log.isDebugEnabled())
			log.debug("전역 UnitOfWork 를 등록합니다. global=[{}]", global);

		globalNonThreadSafeUnitOfWork = global;

		return new AutoCloseable() {
			@Override
			public void close() throws Exception {
				globalNonThreadSafeUnitOfWork = null;
			}
		};
	}

	public static UnitOfWork start() {
		return start(null, UnitOfWorkNestingOptions.ReturnExistingOrCreateUnitOfWork);
	}

	public static UnitOfWork start(UnitOfWorkNestingOptions nestingOptions) {
		return start(null, nestingOptions);
	}

	public static UnitOfWork start(SessionFactory sessionFactory) {
		return start(sessionFactory, UnitOfWorkNestingOptions.ReturnExistingOrCreateUnitOfWork);
	}

	public static UnitOfWork start(SessionFactory sessionFactory, UnitOfWorkNestingOptions nestingOptions) {
		if (log.isDebugEnabled())
			log.debug("새로운 UnitOfWork를 시작합니다. sessionFactory=[{}], nestingOptions=[{}]", sessionFactory, nestingOptions);

		if (globalNonThreadSafeUnitOfWork != null)
			return globalNonThreadSafeUnitOfWork;

		UnitOfWorkImplementor existing = (UnitOfWorkImplementor) Local.get(CURRENT_UNIT_OF_WORK_KEY);
		if (nestingOptions == UnitOfWorkNestingOptions.ReturnExistingOrCreateUnitOfWork && existing != null) {
			if (log.isDebugEnabled())
				log.debug("기존 UnitOfWork 가 존재하므로, 사용횟수만 증가시키고, 기존 UnitOfWork 인스턴스를 반환합니다.");
			existing.increseUsage();
			return existing;
		}

		if (existing != null && sessionFactory == null)
			sessionFactory = existing.getSession().getSessionFactory();

		setCurrent(getUnitOfWorkFactory().create(sessionFactory, existing));

		return getCurrent();
	}

	public static void closeUnitOfWork(UnitOfWorkImplementor unitOfWork) {
		if (log.isDebugEnabled())
			log.debug("UnitOfWork를 종료합니다. 종료되는 UnitOfWork 의 Previous 를 Current UnitOfWork로 교체합니다.");
		setCurrent((unitOfWork != null) ? unitOfWork.getPrevious() : null);
	}

	public static void closeUnitOfWorkFactory() {
		if (log.isInfoEnabled())
			log.info("UnitOfWorkFactory를 초기화합니다...");
		unitOfWorkFactory = null;
	}
}
