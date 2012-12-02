package kr.kth.data.hibernate.unitofwork;

import kr.kth.commons.Action1;
import kr.kth.commons.tools.With;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.springframework.transaction.TransactionDefinition;

import java.util.concurrent.atomic.AtomicInteger;

import static kr.kth.commons.Guard.shouldNotBeNull;

/**
 * kr.kth.data.hibernate.unitofwork.UnitOfWorkAdapter
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 11. 29.
 */
@Slf4j
public class UnitOfWorkAdapter extends UnitOfWorkAdapterBase implements UnitOfWorkImplementor {

	@Getter @Setter private String name;
	@Getter private final UnitOfWorkFactory factory;
	@Getter private final Session session;
	@Getter private final UnitOfWorkAdapter previous;
	private AtomicInteger usageCount = new AtomicInteger(-1);
	protected boolean closed = false;

	public UnitOfWorkAdapter(String name, UnitOfWorkFactory factory, Session session) {
		this(name, factory, session, null);
	}

	public UnitOfWorkAdapter(String name, UnitOfWorkFactory factory, Session session, UnitOfWorkAdapter previous) {
		shouldNotBeNull(factory, "factory");
		shouldNotBeNull(session, "session");

		if (log.isDebugEnabled())
			log.debug("UnitOfWorkAdapter를 생성합니다. name=[{}], factory=[{}], session=[{}], previous=[{}]",
			          name, factory, session, previous);

		this.name = name;
		this.factory = factory;
		this.session = session;
		this.previous = previous;
	}

	@Override
	public void increseUsage() {
		int usage = usageCount.incrementAndGet();

		if (log.isDebugEnabled())
			log.debug("UnitOfWorkAdapter의 사용 횟수를 증가했습니다. usageCount=[{}]", usage);
	}

	@Override
	public void flushSession() {
		if (log.isDebugEnabled())
			log.debug("현 Session을 flush 작업을 시작합니다...");

		this.session.flush();

		if (log.isDebugEnabled())
			log.debug("현 Session을 flush 작업을 완료했습니다!!!");
	}

	@Override
	public void clearSession() {
		if (log.isDebugEnabled())
			log.debug("현 Session을 clear 합니다...");

		this.session.clear();
	}

	@Override
	public boolean isInActiveTransaction() {
		return session.getTransaction().isActive();
	}

	@Override
	public UnitOfWorkTransaction beginTransaction() {
		return new UnitOfWorkTransactionAdapter(session.beginTransaction());
	}

	@Override
	public UnitOfWorkTransaction beginTransaction(TransactionDefinition transactionDefinition) {
		return new UnitOfWorkTransactionAdapter(session.beginTransaction());
	}

	@Override
	public void close() throws Exception {
		if (closed)
			return;

		try {
			if (log.isDebugEnabled())
				log.debug("UnitOfWorkAdapter 를 close 합니다...");

			int usage = usageCount.decrementAndGet();

			if (log.isDebugEnabled())
				log.debug("Usage count of UnitOfWork = [{}]", usage);

			if (usage != 0) {
				if (log.isDebugEnabled())
					log.debug("UnitOfWorkAdapter 의 사용 수가 0이 아니므로 실제 내부 리소스를 정리하지 않습니다.");
				return;
			}

			final UnitOfWorkAdapter current = this;
			if (factory != null) {
				With.tryAction(new Action1<UnitOfWorkAdapter>() {
					@Override
					public void perform(UnitOfWorkAdapter unitOfWork) {
						factory.closeUnitOfWork(unitOfWork);
					}
				}, this);
			}
			if (session != null) {
				With.tryAction(new Action1<Session>() {
					@Override
					public void perform(Session session) {
						session.close();
					}
				}, session);
			}

			if (log.isDebugEnabled())
				log.debug("UnitOfWorkAdatper 를 close 했습니다!!!");

		} catch (Exception e) {
			if (log.isErrorEnabled())
				log.error("UnitOfWorkAdapter close 시에 예외가 발생했습니다.", e);
			throw new RuntimeException(e);
		} finally {
			closed = true;
		}
	}
}
