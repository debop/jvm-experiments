package kr.ecsp.data.hibernate.repository;

import kr.ecsp.data.domain.model.StatefulEntity;
import kr.escp.commons.Local;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

/**
 * HibernateRepository를 생성해주는 Factory 입니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 11. 27.
 */
@Slf4j
@Component
public class HibernateDaoFactory {

	private static final String LocalSessionKey = "kr.ecsp.data.hibernate.repository.Session";
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public Session getLocalSession() {
		Session session = (Session) Local.get(LocalSessionKey);
		if (session == null) {
			session = sessionFactory.openSession();
			Local.put(LocalSessionKey, session);
		}
		return session;
	}

	public <E extends StatefulEntity> HibernateDao<E> createHibernateDao(Class<E> entityClass) {


		if (log.isDebugEnabled())
			log.debug("HibernateDao<{}> 를 생성합니다.", entityClass.getSimpleName());

		HibernateDaoImpl<E> hibernateDao = new HibernateDaoImpl<>();
		hibernateDao.setEntityClass(entityClass);
		hibernateDao.setSession(getLocalSession());

		return hibernateDao;
	}
}
