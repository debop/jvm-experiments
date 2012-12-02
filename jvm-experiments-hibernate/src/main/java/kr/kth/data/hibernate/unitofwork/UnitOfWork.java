package kr.kth.data.hibernate.unitofwork;

import org.hibernate.Session;
import org.springframework.transaction.TransactionDefinition;

/**
 * kr.kth.data.hibernate.unitofwork.UnitOfWork
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 11. 27.
 */
public interface UnitOfWork extends AutoCloseable {

	String getName();

	void setName(String unitOfWorkName);

	/**
	 * Current {@link Session} 의 변경 내용을 flush 해서 저장소에 적용되도록 합니다.
	 */
	void flushSession();

	/**
	 * Current {@link Session} 의 정보를 메모리에서 삭제합니다.
	 */
	void clearSession();

	/**
	 * 현 UnitOfWork 에 Transaction이 활성화 여부를 반환합니다.
	 */
	boolean isInActiveTransaction();

	/**
	 * 현 UnitOfWork 에 새로운 Transaction을 시작합니다.
	 */
	UnitOfWorkTransaction beginTransaction();

	/**
	 * 지정된 Transaction 정의를 이용하여 Transaction을 생성, 시작합니다.
	 */
	UnitOfWorkTransaction beginTransaction(TransactionDefinition transactionDefinition);

	/**
	 * 현 {@link Session} 의 변경내용을 Transaction을 적용하여 flush를 수행합니다.
	 */
	void transactionalFlush();

	/**
	 * 현 {@link Session} 의 변경 내용을 {@link TransactionDefinition} 설정을 가지는 Transaction을 이용하여 flush 를 수행합니다.
	 */
	void transactionalFlush(TransactionDefinition transactionDefinition);
}
