package kr.kth.data.hibernate.repository;

import kr.kth.commons.spring3.Spring;
import kr.kth.data.hibernate.unitofwork.UnitOfWorkManager;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.example.domain.model.Category;
import org.hibernate.example.domain.model.Event;
import org.jpa.example.domain.model.JpaUser;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.List;

/**
 * kr.kth.data.hibernate.repository.HibernateRepositoryTest
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 11. 26.
 */
@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/applicationContext.xml")
public class HibernateRepositoryTest {

	ApplicationContext springContext;
	HibernateDaoFactory hibernateDaofactory;
	HibernateTransactionManager transactionManager;
	UnitOfWorkManager unitOfWork;

	@BeforeClass
	public static void beforeClass() {

	}

	@Before
	public void before() {
		Spring.init("/applicationContext.xml");

		springContext = Spring.getContext();
		hibernateDaofactory = Spring.getBean(HibernateDaoFactory.class);
		transactionManager = Spring.getBean(HibernateTransactionManager.class);
		unitOfWork = Spring.getBean(UnitOfWorkManager.class);

		unitOfWork.start();
	}

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
		SessionFactory sessionFactory = Spring.getBean(SessionFactory.class);
		Assert.assertNotNull(sessionFactory);

		Session session = sessionFactory.openSession();
		Assert.assertNotNull(session);
		List<Event> events = session.createCriteria(Event.class).list();

		session.close();
	}
}