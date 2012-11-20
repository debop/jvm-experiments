package org.hibernate.example.domain.model;

import kr.ecsp.data.hibernate.tools.HibernateTool;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.metadata.ClassMetadata;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * 설명을 추가하세요.
 * JpaUser: sunghyouk.bae@gmail.com
 * Date: 12. 11. 19
 */
@Slf4j
public class MappingTest {

	public static Session getSession() throws HibernateException {
		return HibernateTool.getSessionFactory().openSession();
	}

	private static SessionFactory sessionFactory;
	private Session session;

	@BeforeClass
	public static void beforeClass() {
		sessionFactory = HibernateTool.getSessionFactory();
	}

	@Before
	public void beforeTest() {
		if (session == null)
			session = sessionFactory.openSession();
	}

	@After
	public void afterTest() {
		if (session != null) {
			session.close();
			session = null;
		}
	}

	@Test
	public void entityMappingTest() {
		try {
			log.info("querying all the managed entities...");
			final Map<String, ClassMetadata> metadataMap = session.getSessionFactory().getAllClassMetadata();
			for (Object key : metadataMap.keySet()) {
				final ClassMetadata classMetadata = metadataMap.get(key);
				final String entityName = classMetadata.getEntityName();
				final Query query = session.createQuery("from " + entityName);
				query.setCacheable(true);

				log.info("executing hql= " + query.getQueryString());

				for (Object o : query.list()) {
					log.info("Entity=  " + o);
				}
			}
		} finally {
			session.flush();
		}
		log.info("성공했습니다.");
	}

	@Test
	public void entitySaveTest() {

		StateEntityImpl stateEntity = new StateEntityImpl("abc");
		session.save(stateEntity);
		session.flush();

		if (log.isDebugEnabled())
			log.debug("엔티티를 저장했습니다. entity=" + stateEntity);

		session.clear();

		final List loaded = session.createQuery("from " + StateEntityImpl.class.getName()).list();

		assertEquals(1, loaded.size());

		StateEntityImpl entity = (StateEntityImpl) loaded.get(0);
		assertNotNull(entity);
		assertEquals("abc", entity.getName());

		log.debug("엔티티를 로드했습니다. entity=" + entity);
	}
}
