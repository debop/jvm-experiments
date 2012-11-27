package kr.ecsp.data.hibernate.repository;

import kr.ecsp.data.domain.model.StatefulEntity;
import kr.ecsp.data.hibernate.UnitOfWork;
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

	@Autowired UnitOfWork unitOfWork;

	public <E extends StatefulEntity> HibernateDao<E> createHibernateDao(Class<E> entityClass) {

		if (log.isDebugEnabled())
			log.debug("HibernateDao<{}> 를 생성합니다.", entityClass.getSimpleName());

		HibernateDaoImpl<E> dao = new HibernateDaoImpl<>(entityClass);
		dao.setUnitOfWork(unitOfWork);

		return dao;
	}
}
