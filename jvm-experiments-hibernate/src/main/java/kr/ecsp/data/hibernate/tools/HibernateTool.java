package kr.ecsp.data.hibernate.tools;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import javax.naming.Context;
import javax.naming.InitialContext;

/**
 * Hibernate 관련 Tool
 * User: sunghyouk.bae@gmail.com
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
			//configuration.setNamingStrategy(new OracleNamingStrategy());
			configuration.configure();
			ServiceRegistry serviceRegistry =
				new ServiceRegistryBuilder()
					.applySettings(configuration.getProperties())
					.buildServiceRegistry();

			sessionFactory = configuration.buildSessionFactory(serviceRegistry);

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
}
