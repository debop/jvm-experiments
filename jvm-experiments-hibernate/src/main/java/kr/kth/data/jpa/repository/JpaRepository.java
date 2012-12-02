package kr.kth.data.jpa.repository;

import kr.kth.commons.Guard;
import kr.kth.commons.tools.ReflectTool;
import kr.kth.data.jpa.domain.JpaEntityBase;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;

/**
 * JPA 에서 사용할 Repository 입니다.
 * {@ref http://www.baeldung.com/2011/12/13/the-persistence-layer-with-spring-3-1-and-jpa/}
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 11. 25.
 */
@Repository
@Slf4j
public class JpaRepository<E extends JpaEntityBase> {

	@PersistenceContext
	private EntityManager entityManager;

	@Getter
	private Class<E> entityType;

	public JpaRepository() {
		entityType = ReflectTool.getGenericParameterType(this);
		if (log.isDebugEnabled())
			log.debug("JpaRepository<{}> 를 생성했습니다.", entityType.getName());
	}

	public void setEntityManager(EntityManager entityManager) {
		Guard.shouldNotBeNull(entityManager, "entityManager");
		if (log.isDebugEnabled())
			log.debug("EntityManager 설정.");

		this.entityManager = entityManager;
	}

	public E get(Serializable id) {
		return entityManager.getReference(entityType, id);
	}

	public E find(Serializable id) {
		return entityManager.find(entityType, id);
	}

}
