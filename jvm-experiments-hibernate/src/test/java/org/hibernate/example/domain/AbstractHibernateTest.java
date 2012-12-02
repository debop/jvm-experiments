package org.hibernate.example.domain;

import kr.kth.data.hibernate.tools.HibernateTool;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;

/**
 * org.hibernate.example.domain.AbstractHibernateTest
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 11. 20.
 */
@Slf4j
public abstract class AbstractHibernateTest {

	@Getter(lazy = true)
	private static final SessionFactory sessionFactory = HibernateTool.getSessionFactory();

	@Getter @Setter protected Session session;

	@Before
	public void before() {
		onBefore();
	}

	@After
	public void after() {
		onAfter();
	}

	protected void onBefore() {
		if (session == null)
			session = getSessionFactory().openSession();

	}

	protected void onAfter() {
		if (session != null) {
			session.close();
			session = null;
		}
	}
}
