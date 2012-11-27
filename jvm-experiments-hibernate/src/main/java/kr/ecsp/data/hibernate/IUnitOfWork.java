package kr.ecsp.data.hibernate;

import org.hibernate.Session;

/**
 * kr.ecsp.data.hibernate.IUnitOfWork
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 11. 27.
 */
public interface IUnitOfWork extends AutoCloseable {

	Session getSession();
}
