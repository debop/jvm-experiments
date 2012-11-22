package kr.ecsp.data.hibernate.tools;

import kr.ecsp.data.hibernate.interceptor.UpdateTimestampedInterceptor;
import kr.ecsp.data.hibernate.listener.UpdateTimestampedEventListener;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.internal.SessionFactoryImpl;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import javax.naming.Context;
import javax.naming.InitialContext;

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
}
