package kr.kth.data.hibernate.repository;

import kr.kth.commons.base.Guard;
import kr.kth.commons.base.Local;
import kr.kth.data.domain.model.StatefulEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * HibernateRepository를 생성해주는 Factory 입니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 11. 27.
 */
@Slf4j
@Component
public class HibernateDaoFactory {

	private static final String HibernateDaoKey = "kr.kth.data.hibernate.repository.HibernateDao";

	public <E extends StatefulEntity> HibernateDao<E> getOrCreateHibernateDao(Class<E> entityClass) {
		Guard.shouldNotBeNull(entityClass, "entityClass");
		return getOrCreateHibernateDaoInternal(entityClass);
	}

	@SuppressWarnings("unchecked")
	protected synchronized <E extends StatefulEntity> HibernateDao<E> getOrCreateHibernateDaoInternal(Class<E> entityClass) {

		String daoKey = HibernateDaoKey + "." + entityClass.getName();
		HibernateDaoImpl<E> dao = (HibernateDaoImpl<E>) Local.get(daoKey);

		if (dao == null) {
			if (log.isDebugEnabled())
				log.debug("HibernateDao<{}> 인스턴스를 생성합니다.", entityClass.getName());

			dao = new HibernateDaoImpl<E>(entityClass);
			Local.put(daoKey, dao);
		}
		return dao;
	}
}
