package kr.ecsp.data.hibernate;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;

/**
 * Hibernate 용 UnitOfWork 입니다. (Spring 에서 제공하는 것은 Hibernate 4이상에서는 지원 안됩니다. 그래서 직접 만들었습니다.)
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 11. 27.
 */
@Slf4j
public class UnitOfWork {

	@Setter
	private SessionFactory sessionFactory;

	/**
	 * UnitOfWork 를 시작합니다.
	 */
	public void start() {

	}

	/**
	 * UnitOfWork 를 끝냅니다.
	 */
	public void close() {

	}
}
