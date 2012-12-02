package kr.kth.data.hibernate.repository;

import org.springframework.stereotype.Component;

/**
 * kr.kth.data.hibernate.repository.HibernateRepositoryFactory
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
