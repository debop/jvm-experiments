package kr.kth.data.hibernate.repository;

import kr.kth.commons.spring3.Spring;
import kr.kth.data.hibernate.unitofwork.IUnitOfWork;
import kr.kth.data.hibernate.unitofwork.UnitOfWorks;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.example.domain.model.Category;
import org.hibernate.example.domain.model.Event;
import org.jpa.example.domain.model.JpaUser;
import org.junit.*;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.List;

/**
 * kr.kth.data.hibernate.repository.HibernateDaoTest
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 11. 26.
 */
@Slf4j
public class HibernateDaoTest {

    HibernateDaoFactory hibernateDaofactory;
    HibernateTransactionManager transactionManager;
    IUnitOfWork unitOfWork;

    @BeforeClass
    public static void beforeClass() {
        if (Spring.isNotInitialized())
            Spring.init("applicationContext.xml");
    }

    @Before
    public void before() {
        hibernateDaofactory = Spring.getBean(HibernateDaoFactory.class);
        transactionManager = Spring.getBean(HibernateTransactionManager.class);

        unitOfWork = UnitOfWorks.start();
    }

    @After
    public void after() throws Exception {
        if (unitOfWork != null)
            unitOfWork.close();
    }

    @AfterClass
    public static void afterClass() {
        if (Spring.isInitialized())
            Spring.reset();
    }

    @Test
    public void createHibernateDao() {

        Assert.assertNotNull(hibernateDaofactory);

        TransactionStatus txstatus = transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            IHibernateDao<JpaUser> jpaUserDao = hibernateDaofactory.getOrCreateHibernateDao(JpaUser.class);
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
        IHibernateDao<Category> categoryDao = hibernateDaofactory.getOrCreateHibernateDao(Category.class);
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
