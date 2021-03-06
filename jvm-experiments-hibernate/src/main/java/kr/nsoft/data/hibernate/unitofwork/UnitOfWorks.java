package kr.nsoft.data.hibernate.unitofwork;

import kr.nsoft.commons.AutoCloseableAction;
import kr.nsoft.commons.Guard;
import kr.nsoft.commons.Local;
import kr.nsoft.commons.spring.Springs;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.annotation.concurrent.ThreadSafe;

/**
 * Unit of Work 패턴을 구현한 Static 클래스입니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 18
 */
@Slf4j
@ThreadSafe
public final class UnitOfWorks {

    private UnitOfWorks() { }

    static {
        if (log.isInfoEnabled())
            log.info("UnitOfWorks 인스턴스가 생성되었습니다.");
    }

    private static final String CURRENT_UNIT_OF_WORK_KEY = "kr.nsoft.data.hibernate.unitofwork.UnitOfWorks";
    private static final String UNIT_OF_WORK_NOT_STARTED = "UnitOfWorks가 시작되지 않았습니다. 사용 전에 UnitOfWorks.start()를 호출하세요.";

    private static volatile IUnitOfWork globalNonThreadSafeUnitOfWork;
    private static volatile IUnitOfWorkFactory unitOfWorkFactory;

    /**
     * UnitOfWork 가 이미 시작되었는지 확인한다.
     */
    public static synchronized boolean isStarted() {
        return globalNonThreadSafeUnitOfWork != null || Local.get(CURRENT_UNIT_OF_WORK_KEY) != null;
    }

    /**
     * 현재 시작된 {@link kr.nsoft.data.hibernate.unitofwork.IUnitOfWork}의 인스턴스 ({@link kr.nsoft.data.hibernate.unitofwork.UnitOfWorkAdapter}를 반환합니다.
     */
    public static synchronized IUnitOfWork getCurrent() {
        if (!isStarted())
            throw new HibernateException(UNIT_OF_WORK_NOT_STARTED);

        if (globalNonThreadSafeUnitOfWork != null)
            return globalNonThreadSafeUnitOfWork;

        return (IUnitOfWork) Local.get(CURRENT_UNIT_OF_WORK_KEY);
    }

    public static synchronized SessionFactory getCurrentSessionFactory() {
        return getUnitOfWorkFactory().getSessionFactory();
    }

    public static synchronized Session getCurrentSession() {
        return getUnitOfWorkFactory().getCurrentSession();
    }

    public static synchronized IUnitOfWorkFactory getUnitOfWorkFactory() {
        if (unitOfWorkFactory == null) {
            unitOfWorkFactory = Springs.getBeansByType(IUnitOfWorkFactory.class).get(0);

            if (log.isInfoEnabled())
                log.info("IUnitOfWorkFactory Bean을 가져옵니다. unitOfWorkFactory=[{}]", unitOfWorkFactory);
            Guard.shouldNotBeNull(unitOfWorkFactory, "unitOfWorkFactory");
        }
        return unitOfWorkFactory;
    }

    public static synchronized void setUnitOfWorkFactory(IUnitOfWorkFactory unitOfWorkFactory) {
        if (log.isInfoEnabled())
            log.info("UnitOfWorkFactory를 설정합니다. unitOfWorkFactory=[{}]", unitOfWorkFactory);
        UnitOfWorks.unitOfWorkFactory = unitOfWorkFactory;
    }

    public static void setCurrent(IUnitOfWork unitOfWork) {
        if (log.isDebugEnabled())
            log.debug("현 Thread Context의 UnitOfWork 인스턴스를 설정합니다. unitOfWork=[{}]", unitOfWork);
        Local.put(CURRENT_UNIT_OF_WORK_KEY, unitOfWork);
    }

    public static synchronized AutoCloseableAction registerGlobalUnitOfWork(IUnitOfWork globalUnitOfWork) {
        if (log.isDebugEnabled())
            log.debug("전역 IUnitOfWork를 설정합니다. globalUnitOfWork=[{}]", globalUnitOfWork);

        globalNonThreadSafeUnitOfWork = globalUnitOfWork;

        return new AutoCloseableAction(new Runnable() {
            @Override
            public void run() {
                globalNonThreadSafeUnitOfWork = null;
            }
        });
    }

    public static synchronized IUnitOfWork start() {
        return start(null, UnitOfWorkNestingOptions.ReturnExistingOrCreateUnitOfWork);
    }

    public static synchronized IUnitOfWork start(UnitOfWorkNestingOptions nestingOptions) {
        return start(null, nestingOptions);
    }

    public static synchronized IUnitOfWork start(SessionFactory sessionFactory) {
        return start(sessionFactory, UnitOfWorkNestingOptions.ReturnExistingOrCreateUnitOfWork);
    }

    public static synchronized IUnitOfWork start(SessionFactory sessionFactory, UnitOfWorkNestingOptions nestingOptions) {
        if (log.isDebugEnabled())
            log.debug("새로운 UnitOfWork를 시작합니다. sessionFactory=[{}], nestingOptions=[{}]", sessionFactory, nestingOptions);

        if (globalNonThreadSafeUnitOfWork != null)
            return globalNonThreadSafeUnitOfWork;

        IUnitOfWorkImplementor existing = (IUnitOfWorkImplementor) Local.get(CURRENT_UNIT_OF_WORK_KEY);

        boolean useExisting =
                existing != null && nestingOptions == UnitOfWorkNestingOptions.ReturnExistingOrCreateUnitOfWork;

        if (useExisting) {
            if (log.isDebugEnabled())
                log.debug("기존 IUnitOfWork 가 존재하므로, 사용횟수만 증가시키고, 기존 IUnitOfWork 인스턴스를 반환합니다. 사용횟수=[{}]", existing.getUsage());

            existing.increseUsage();
            return existing;
        }

        if (log.isDebugEnabled())
            log.debug("새로운 IUnitOfWorkFactory 와 IUnitOfWork 를 생성합니다...");

        if (existing != null && sessionFactory == null) {
            sessionFactory = existing.getSession().getSessionFactory();
        } else if (existing == null) {
            sessionFactory = getCurrentSessionFactory();
        }

        setCurrent(getUnitOfWorkFactory().create(sessionFactory, existing));

        if (log.isDebugEnabled())
            log.debug("새로운 IUnitOfWork를 시작합니다. sessionFactory=[{}]", sessionFactory);

        return getCurrent();
    }

    public static synchronized void closeUnitOfWork(IUnitOfWork unitOfWork) {
        if (log.isDebugEnabled())
            log.debug("UnitOfWork를 종료합니다. 종료되는 IUnitOfWork 의 Previous 를 Current UnitOfWork로 교체합니다.");

        setCurrent((unitOfWork != null) ? ((IUnitOfWorkImplementor) unitOfWork).getPrevious() : null);
    }

    public static synchronized void closeUnitOfWorkFactory() {
        if (log.isInfoEnabled())
            log.info("UnitOfWorkFactory를 초기화합니다...");

        unitOfWorkFactory = null;
    }
}
