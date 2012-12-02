package kr.kth.data.hibernate.unitofwork;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.Map;

/**
 * {@link UnitOfWork} 를 생성하는 Factory 의 인터페이스 입니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 11. 29.
 */
public interface UnitOfWorkFactory {

	/**
	 * 현 UnitOfWorkFactory가 사용하는 {@link SessionFactory}
	 *
	 * @return
	 */
	SessionFactory getSessionFactory();

	/**
	 * 현 UnitOfWorkFactory 가 사용할 {@link SessionFactory} 를 설정합니다.
	 *
	 * @param sessionFactory
	 */
	void setSessionFactory(SessionFactory sessionFactory);


	Map<String, SessionFactory> getSessionFactories();

	/**
	 * Multi-Tenancy 를 적용할 때, 복수의 {@link SessionFactory}를 등록하여, 사용할 수 있도록 한다.
	 *
	 * @param sessionFactories
	 */
	void setSessionFactories(Map<String, SessionFactory> sessionFactories);

	/**
	 * 현 Thread-Context 에서 사용할 Session 을 반환합니다.
	 * {@link kr.kth.data.hibernate.unitofwork.UnitOfWorkManager#start()} 시에 Session은 생성됩니다.
	 *
	 * @return
	 */
	Session getCurrentSession();

	/**
	 * 현 Thread-Context 에서 사용할 Session 을 설정합니다.
	 *
	 * @param session
	 */
	void setCurrentSession(Session session);

	/**
	 * 현 UnitOfWorkFactory를 초기화합니다. Spring 에서 init-method 를 이용하여 호출해도 됩니다.
	 */
	void Init();


	UnitOfWorkImplementor create(SessionFactory sessionFactory, UnitOfWorkImplementor previous);


	/**
	 * 지정된 {@link UnitOfWorkImplementor} 인스턴스를 닫습니다.
	 *
	 * @param adapter {@link UnitOfWorkImplementor} instance to be closed.
	 */
	void closeUnitOfWork(UnitOfWorkImplementor adapter);
}
