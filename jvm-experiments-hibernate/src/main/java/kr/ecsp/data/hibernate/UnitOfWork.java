package kr.ecsp.data.hibernate;

import kr.escp.commons.Local;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 * Hibernate 용 UnitOfWork 입니다. (Spring 에서 제공하는 것은 Hibernate 4이상에서는 지원 안됩니다. 그래서 직접 만들었습니다.)
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 11. 27.
 */
@Slf4j
public final class UnitOfWork {

	//
	// TODO: UnitOfWorkImplementor 를 만들고, UnitOfWork 는 Singleton Class로 만들어야 한다.
	//
	private static final String LocalSessionKey = "kr.ecsp.data.hibernate.repository.Session";

	@Getter
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
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

	/**
	 * UnitOfWork 를 시작합니다.
	 */
	public void start() {

	}

	/**
	 * UnitOfWork 를 끝냅니다.
	 */
	public void close() {

	}
}
