package kr.kth.data.hibernate.unitofwork;

import kr.kth.commons.Guard;
import kr.kth.commons.Local;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Hibernate 용 UnitOfWork Factory 클래스입니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 11. 30.
 */
@Slf4j
@Component
public class HibernateUnitOfWorkFactory implements UnitOfWorkFactory {

	public static final String CURRENT_HIBERNATE_SESSION = "hibernateUnitOfWorkFactory.current.hibernate.session";

	private SessionFactory sessionFactory;
	private Map<String, SessionFactory> sessionFactories;

	@Override
	public SessionFactory getSessionFactory() {
		return this.sessionFactory;
	}

	@Override
	public void setSessionFactory(SessionFactory sessionFactory) {
		if (log.isInfoEnabled())
			log.info("SessionFactory를 설정합니다. sessionFactory=[{}]", sessionFactory);
		this.sessionFactory = sessionFactory;
	}

	@Override
	public Map<String, SessionFactory> getSessionFactories() {
		return this.sessionFactories;
	}

	@Override
	public void setSessionFactories(Map<String, SessionFactory> sessionFactories) {
		this.sessionFactories = sessionFactories;
	}

	@Override
	public Session getCurrentSession() {
		Session session = (Session) Local.get(CURRENT_HIBERNATE_SESSION);
		Guard.assertTrue(session != null, "Session이 현 Thread Context에서 생성되지 않았습니다. UnitOfWorkManager.start() 를 먼저 호출하셔야 합니다.");
		return session;
	}

	@Override
	public void setCurrentSession(Session session) {
		if (log.isDebugEnabled())
			log.debug("현 ThreadContext에서 사용할 Session을 설정합니다.");
		Local.put(CURRENT_HIBERNATE_SESSION, session);
	}

	@Override
	public void Init() {
		if (log.isInfoEnabled())
			log.info("Hibernate 용 UnitOfWorkFactory를 초기화합니다.");
	}

	@Override
	public UnitOfWorkImplementor create(SessionFactory sessionFactory, UnitOfWorkImplementor previous) {
		return null;  //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public void closeUnitOfWork(UnitOfWorkImplementor adapter) {
		//To change body of implemented methods use File | Settings | File Templates.
	}
}
