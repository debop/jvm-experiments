package kr.ecsp.data.hibernate.repository;

import kr.ecsp.data.hibernate.UnitOfWork;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.example.domain.model.Category;
import org.jpa.example.domain.model.JpaUser;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.SessionFactoryUtils;
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
			HibernateDao<JpaUser> jpaUserDao = hibernateDaofactory.createHibernateDao(JpaUser.class);
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
	@SuppressWarnings("unchecked")
	public void createRepository() {

		HibernateRepository repository = springContext.getBean("hibernate.repository", HibernateRepositoryImpl.class);
		Assert.assertNotNull(repository);

		List<JpaUser> users = repository.getList(DetachedCriteria.forClass(JpaUser.class));
		Assert.assertNotNull(users);

		Assert.assertNotNull(repository.getList(DetachedCriteria.forClass(JpaUser.class)));

		Assert.assertEquals(0, repository.count(DetachedCriteria.forClass(Category.class)));
	}

	@Test
	@SuppressWarnings("unchecked")
	public void loadSessionFactory() {
		SessionFactory sessionFactory = springContext.getBean("sessionFactory", SessionFactory.class);
		Assert.assertNotNull(sessionFactory);

		Session session = SessionFactoryUtils.openSession(sessionFactory);//sessionFactory.openSession();
		Assert.assertNotNull(session);

		List<JpaUser> users = session.createCriteria(JpaUser.class).list();
		Assert.assertNotNull(users);

		DetachedCriteria dc = DetachedCriteria.forClass(JpaUser.class);
		List<JpaUser> users2 = dc.getExecutableCriteria(session).list();
		Assert.assertNotNull(users2);
	}
}
