package org.hibernate.example.domain.model;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.hibernate.example.domain.AbstractHibernateTest;
import org.hibernate.example.domain.model.join.Join_Customer;
import org.hibernate.metadata.ClassMetadata;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;
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
public class MappingTest extends AbstractHibernateTest {

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
	public void categoryAndEvent() {
		Category category = new Category("category1");

		Event event1 = new Event("event1", new Date());
		Event event2 = new Event("event2", new Date());
		category.getEvents().add(event1);
		event1.setCategory(category);

		category.getEvents().add(event2);
		event2.setCategory(category);

		session.saveOrUpdate(category);
		session.flush();

		session.clear();

		@SuppressWarnings("unchecked")
		final List<Category> categories = (List<Category>) session.createCriteria(Category.class).list();
		assertEquals(1, categories.size());
		assertEquals(2, categories.get(0).getEvents().size());

		if (log.isDebugEnabled())
			log.debug("Category=[{}]", categories.get(0));
	}

	@Test
	public void stateEntityImplSave() {

		StatefulEntityImpl stateEntity = new StatefulEntityImpl("abc");
		session.persist(stateEntity);
		session.flush();

		StatefulEntityImpl stateEntity2 = new StatefulEntityImpl("가나다");
		session.persist(stateEntity2);
		session.flush();

		if (log.isDebugEnabled())
			log.debug("엔티티를 저장했습니다. entity=" + stateEntity);

		session.clear();

		@SuppressWarnings("unchecked")
		final List<StatefulEntityImpl> loaded =
			(List<StatefulEntityImpl>) session.createQuery("from " + StatefulEntityImpl.class.getName()).list();

		assertEquals(2, loaded.size());

		StatefulEntityImpl entity = loaded.get(0);
		assertNotNull(entity);
		assertEquals("abc", entity.getName());

		entity.setName("modified");
		session.saveOrUpdate(entity);
		session.flush();

		log.debug("엔티티를 로드했습니다. entity=" + entity);
	}

	@Test
	public void joinEntityTest() {
		Join_Customer customer =
			new Join_Customer("debop", "debop@kthcorp.com");

		customer.getAddress().setCity("Seoul");
		customer.getAddress().setZipcode("131-101");

		session.saveOrUpdate(customer);
		session.flush();
		session.clear();

		Join_Customer loaded =
			(Join_Customer) session.createCriteria(Join_Customer.class)
			                       .add(Restrictions.eq("name", customer.getName()))
			                       .setMaxResults(1)
			                       .uniqueResult();

		Assert.assertNotNull(loaded);
		Assert.assertEquals(customer.getName(), loaded.getName());
		Assert.assertEquals(customer.getEmail(), loaded.getEmail());
	}
}
