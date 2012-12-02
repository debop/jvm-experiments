package kr.kth.data.hibernate.unitofwork;

import org.hibernate.Session;

/**
 * kr.kth.data.hibernate.unitofwork.UnitOfWorkImplementor
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 11. 29.
 */
public interface UnitOfWorkImplementor extends UnitOfWork {

	/**
	 * 현 인스턴스의 사용 Count를 증가 시킨다.
	 */
	void increseUsage();

	/**
	 * 중첩 방식으로 UnitOfWork 를 사욜할 때, 바로 전의 {@link UnitOfWorkImplementor} 를 나타낸다.
	 * 중첩이 아니면 null을 반환한다.
	 *
	 * @return
	 */
	UnitOfWorkImplementor getPrevious();

	/**
	 * 현 Thread-Context 에서 사용할 {@link Session}
	 */
	Session getSession();
}
