package kr.kth.data.hibernate.unitofwork;

import kr.kth.commons.base.Action0;
import kr.kth.commons.base.AutoCloseableAction;
import kr.kth.commons.base.Guard;
import kr.kth.commons.base.Local;
import kr.kth.commons.spring3.Spring;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 * Hibernate 용 UnitOfWorkManager 입니다. (Spring 에서 제공하는 것은 Hibernate 4이상에서는 지원 안됩니다. 그래서 직접 만들었습니다.)
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 11. 27.
 */
@Slf4j
public final class UnitOfWorkManager {

	private static final String CURRENT_UNIT_OF_WORK_KEY = "kr.kth.data.hibernate.unitofwork.CurrentUnitOfWork";

	private volatile UnitOfWork globalNonThreadSafeUnitOfWork;
	private volatile UnitOfWorkFactory unitOfWorkFactory;

	public synchronized boolean isStarted() {
		return globalNonThreadSafeUnitOfWork != null || Local.get(CURRENT_UNIT_OF_WORK_KEY) != null;
	}

	public synchronized UnitOfWork getCurrent() {
		Guard.assertTrue(isStarted(), "UnitOfWork 가 시작되지 않았습니다. 사용 전 UnitOfWork.Start() 를 호출하세요.");

		if (globalNonThreadSafeUnitOfWork != null)
			return globalNonThreadSafeUnitOfWork;
		return (UnitOfWork) Local.get(CURRENT_UNIT_OF_WORK_KEY);
	}

	public synchronized SessionFactory getCurrentSessionFactory() {
		return getUnitOfWorkFactory().getSessionFactory();
	}

	public synchronized Session getCurrentSession() {
		return getUnitOfWorkFactory().getCurrentSession();
	}

	public synchronized UnitOfWorkFactory getUnitOfWorkFactory() {
		if (unitOfWorkFactory == null) {
			unitOfWorkFactory = Spring.getOrRegisterBean(HibernateUnitOfWorkFactory.class);
		}
		return unitOfWorkFactory;
	}

	public synchronized void setUnitOfWorkFactory(UnitOfWorkFactory factory) {
		unitOfWorkFactory = factory;
	}

	public void setCurrent(UnitOfWork unitOfWork) {
		Local.put(CURRENT_UNIT_OF_WORK_KEY, unitOfWork);
	}

	public synchronized AutoCloseableAction registerGlobalUnitOfWork(UnitOfWork global) {
		if (log.isDebugEnabled())
			log.debug("전역 UnitOfWork 를 등록합니다. global=[{}]", global);

		globalNonThreadSafeUnitOfWork = global;

		return new AutoCloseableAction(new Action0() {
			@Override
			public void perform() {
				globalNonThreadSafeUnitOfWork = null;
			}
		});
	}

	public synchronized UnitOfWork start() {
		return start(null, UnitOfWorkNestingOptions.ReturnExistingOrCreateUnitOfWork);
	}

	public synchronized UnitOfWork start(UnitOfWorkNestingOptions nestingOptions) {
		return start(null, nestingOptions);
	}

	public synchronized UnitOfWork start(SessionFactory sessionFactory) {
		return start(sessionFactory, UnitOfWorkNestingOptions.ReturnExistingOrCreateUnitOfWork);
	}

	public synchronized UnitOfWork start(SessionFactory sessionFactory, UnitOfWorkNestingOptions nestingOptions) {
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

		if (log.isDebugEnabled())
			log.debug("UnitOfWorkFactory 와 UnitOfWork 를 생성합니다.");

		if (existing != null && sessionFactory == null)
			sessionFactory = existing.getSession().getSessionFactory();
		if (existing == null)
			sessionFactory = getCurrentSessionFactory();

		setCurrent(getUnitOfWorkFactory().create(sessionFactory, existing));

		if (log.isDebugEnabled())
			log.debug("새로운 UnitOfWork 를 시작합니다. sessionFactory=[{}]", sessionFactory);

		return getCurrent();
	}

	public synchronized void closeUnitOfWork(UnitOfWorkImplementor unitOfWork) {
		if (log.isDebugEnabled())
			log.debug("UnitOfWork를 종료합니다. 종료되는 UnitOfWork 의 Previous 를 Current UnitOfWork로 교체합니다.");

		setCurrent((unitOfWork != null) ? unitOfWork.getPrevious() : null);
	}

	public synchronized void closeUnitOfWorkFactory() {
		if (log.isInfoEnabled())
			log.info("UnitOfWorkFactory를 초기화합니다...");

		unitOfWorkFactory = null;
	}
}
