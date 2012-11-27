package kr.ecsp.data.hibernate.repository;

import kr.ecsp.data.hibernate.UnitOfWork;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.example.domain.model.Category;
import org.hibernate.example.domain.model.Event;
import org.jpa.example.domain.model.JpaUser;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.List;

/**
 * kr.ecsp.data.hibernate.repository.HibernateRepositoryTest
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 11. 26.
 */
@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/applicationContext.xml")
public class HibernateRepositoryTest {

	@Autowired ApplicationContext springContext;
	@Autowired HibernateDaoFactory hibernateDaofactory;
	@Autowired HibernateTransactionManager transactionManager;
	@Autowired UnitOfWork unitOfWork;

	@Test
	public void createHibernateDao() {

		Assert.assertNotNull(hibernateDaofactory);

		TransactionStatus txstatus = transactionManager.getTransaction(new DefaultTransactionDefinition());
		try {
			HibernateDao<JpaUser> jpaUserDao = hibernateDaofactory.getOrCreateHibernateDao(JpaUser.class);
			List<JpaUser> users = jpaUserDao.getAll();

			Assert.assertEquals(0, users.size());

			transactionManager.commit(txstatus);
		} catch (Exception e) {
			transactionManager.rollback(txstatus);
			log.error("예외가 발생했습니다.", e);
			Assert.fail();
		}
	}

	@Test
	public void createCategoryHiberateDao() {
		HibernateDao<Category> categoryDao = hibernateDaofactory.getOrCreateHibernateDao(Category.class);
		List<Category> categories = categoryDao.getAll();
		Assert.assertEquals(0, categories.size());
	}

	@Test
	@SuppressWarnings("unchecked")
	public void loadSessionFactory() {
		SessionFactory sessionFactory = springContext.getBean("sessionFactory", SessionFactory.class);
		Assert.assertNotNull(sessionFactory);

		Session session = sessionFactory.openSession();
		Assert.assertNotNull(session);
		List<Event> events = session.createCriteria(Event.class).list();

		session.close();
	}
}
