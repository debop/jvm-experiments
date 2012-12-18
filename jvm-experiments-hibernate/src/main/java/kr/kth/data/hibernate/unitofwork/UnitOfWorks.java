package kr.kth.data.hibernate.unitofwork;

import lombok.extern.slf4j.Slf4j;

import javax.annotation.concurrent.ThreadSafe;

/**
 * Unit of Work 패턴을 구현한 Static 클래스입니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 18
 */
@Slf4j
@ThreadSafe
public final class UnitOfWorks {

	private UnitOfWorks() {
	}

	static {
		if (log.isInfoEnabled())
			log.info("UnitOfWork 인스턴스가 생성되었습니다.");
	}
}
