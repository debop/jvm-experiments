package kr.kth.data.hibernate.repository;

import kr.kth.data.domain.model.StatefulEntity;

/**
 * 설명을 추가하세요.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 18
 */
public interface IHibernateDaoFactory {

	<E extends StatefulEntity> IHibernateDao<E> getOrCreateHibernateDao(Class<E> entityClass);
}
