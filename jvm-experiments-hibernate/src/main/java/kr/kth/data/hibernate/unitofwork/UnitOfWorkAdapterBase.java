package kr.kth.data.hibernate.unitofwork;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/**
 * {@link IUnitOfWorkImplementor} 를 구현한 기본 클래스
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 11. 29.
 */
@Slf4j
public abstract class UnitOfWorkAdapterBase implements IUnitOfWorkImplementor {

	@Autowired UnitOfWorkManager unitOfWorkManager;

	/**
	 * 지정된 TransactionDefinition 에 따른 Transaction 하에서 현 Session 정보를 flush 합니다.
	 *
	 * @param transactionDefinition
	 */
	public void transactionalFlush(TransactionDefinition transactionDefinition) {
		if (transactionDefinition == null)
			transactionDefinition = new DefaultTransactionDefinition();

		IUnitOfWorkTransaction tx = unitOfWorkManager.getCurrent().beginTransaction(transactionDefinition);

		try {
			// forces a flush of the current IUnitOfWork
			tx.commit();

		} catch (Exception e) {
			if (log.isErrorEnabled())
				log.error("Transactional Flush failed!!! transaction rollback", e);

			tx.rollback();
			throw new RuntimeException(e);
		}
	}


	public void transactionalFlush() {
		transactionalFlush(new DefaultTransactionDefinition());
	}
}
