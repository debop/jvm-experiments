package kr.ecsp.data.hibernate.repository;

import kr.ecsp.data.domain.model.StatefulEntity;
import kr.ecsp.data.hibernate.UnitOfWork;
import kr.escp.commons.Guard;
import kr.escp.commons.Local;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * HibernateRepository를 생성해주는 Factory 입니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 11. 27.
 */
@Slf4j
@Component
public class HibernateDaoFactory {

	private static final String HibernateDaoKey = "kr.ecsp.data.hibernate.repository.HibernateDao";

	// TODO: 이 코드는 향후 제거할 예정입니다.
	@Autowired UnitOfWork unitOfWork;

	public <E extends StatefulEntity> HibernateDao<E> getOrCreateHibernateDao(Class<E> entityClass) {
		Guard.shouldNotBeNull(entityClass, "entityClass");
		return getOrCreateHibernateDao(entityClass);
	}

	@SuppressWarnings("unchecked")
	protected synchronized <E extends StatefulEntity> HibernateDao<E> getOrCreateHibernateDaoInternal(Class<E> entityClass) {

		String daoKey = HibernateDaoKey + "." + entityClass.getName();
		HibernateDaoImpl<E> dao = (HibernateDaoImpl<E>) Local.get(daoKey);

		if (dao == null) {
			if (log.isDebugEnabled())
				log.debug("HibernateDao<{}> 인스턴스를 생성합니다.", entityClass.getName());

			dao = new HibernateDaoImpl<E>(entityClass);
			dao.setUnitOfWork(unitOfWork);
			Local.put(daoKey, dao);
		}
		return dao;
	}
}
