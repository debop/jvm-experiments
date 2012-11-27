package kr.ecsp.data.hibernate.repository;

import org.springframework.stereotype.Component;

/**
 * kr.ecsp.data.hibernate.repository.HibernateRepositoryFactory
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 11. 27.
 */
@Component
public class HibernateRepositoryFactory {

	public HibernateRepository getRepository(Class<?> clazz) {
		HibernateRepository repository = new HibernateRepositoryImpl();
		return repository;
	}
}
