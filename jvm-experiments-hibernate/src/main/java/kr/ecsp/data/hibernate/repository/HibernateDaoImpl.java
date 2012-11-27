package kr.ecsp.data.hibernate.repository;

import kr.ecsp.data.domain.model.StatefulEntity;
import kr.ecsp.data.hibernate.UnitOfWork;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;

import java.io.Serializable;
import java.util.List;

/**
 * Hibernate 용 Repository 입니다.
 * Spring의 HibernateDaoSupport 및 HibernateTemplate는 더 이상 사용하지 말라.
 * 참고: http://forum.springsource.org/showthread.php?117227-Missing-Hibernate-Classes-Interfaces-in-spring-orm-3.1.0.RC1
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 11. 27.
 */
@Slf4j
public class HibernateDaoImpl<E extends StatefulEntity> implements HibernateDao<E> {

	@Getter
	Class<E> entityClass;

	@Setter
	private UnitOfWork unitOfWork;

	public HibernateDaoImpl(Class<E> entityClass) {
		this.entityClass = entityClass;
	}

	protected Session getSession() {
		return unitOfWork.getSession();
	}


	@Override
	@SuppressWarnings("unchecked")
	public E get(Serializable id) {
		return (E) this.getSession().get(entityClass, id);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<E> getAll() {
		return this.getSession().createQuery("from " + entityClass.getName()).list();
	}
}
