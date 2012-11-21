package org.hibernate.example.domain.model;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.hibernate.example.domain.AbstractHibernateTest;
import org.hibernate.example.domain.model.join.Customer;
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
	}

	@Test
	public void stateEntityImplSave() {

		StateEntityImpl stateEntity = new StateEntityImpl("abc");
		session.save(stateEntity);
		session.flush();

		if (log.isDebugEnabled())
			log.debug("엔티티를 저장했습니다. entity=" + stateEntity);

		session.clear();

		@SuppressWarnings("unchecked")
		final List<StateEntityImpl> loaded =
			(List<StateEntityImpl>) session.createQuery("from " + StateEntityImpl.class.getName()).list();

		assertEquals(1, loaded.size());

		StateEntityImpl entity = loaded.get(0);
		assertNotNull(entity);
		assertEquals("abc", entity.getName());

		log.debug("엔티티를 로드했습니다. entity=" + entity);
	}

	@Test
	public void joinEntityTest() {
		org.hibernate.example.domain.model.join.Customer customer =
			new org.hibernate.example.domain.model.join.Customer("debop", "debop@kthcorp.com");

		customer.getAddress().setCity("Seoul");
		customer.getAddress().setZipcode("131-101");

		session.saveOrUpdate(customer);
		session.flush();
		session.clear();

		Customer loaded =
			(Customer) session.createCriteria(org.hibernate.example.domain.model.join.Customer.class)
			                  .add(Restrictions.eq("name", customer.getName()))
			                  .setMaxResults(1)
			                  .uniqueResult();

		Assert.assertNotNull(loaded);
		Assert.assertEquals(customer.getName(), loaded.getName());
		Assert.assertEquals(customer.getEmail(), loaded.getEmail());
	}
}
