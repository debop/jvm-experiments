package kr.kth.data.hibernate.unitofwork;


/**
 * kr.kth.data.hibernate.unitofwork.UnitOfWorkTransaction
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 11. 29.
 */
public interface UnitOfWorkTransaction {

	/**
	 * Transaction을 commit 합니다.
	 */
	void commit();

	/**
	 * Transaction을 Rollback합니다.
	 */
	void rollback();
}
