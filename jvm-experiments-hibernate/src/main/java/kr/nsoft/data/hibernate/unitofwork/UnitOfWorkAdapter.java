package kr.nsoft.data.hibernate.unitofwork;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.springframework.transaction.TransactionDefinition;

import java.util.concurrent.atomic.AtomicInteger;

import static kr.nsoft.commons.Guard.shouldNotBeNull;

/**
 * {@link IUnitOfWork} 의 가장 기본적인 구현 클래스입니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 11. 29.
 */
@Slf4j
public class UnitOfWorkAdapter extends UnitOfWorkAdapterBase {

    @Getter
    private final IUnitOfWorkFactory factory;
    @Getter
    private final Session session;
    @Getter
    private final UnitOfWorkAdapter previous;
    private AtomicInteger usageCount = new AtomicInteger(1);
    protected boolean closed = false;

    public UnitOfWorkAdapter(IUnitOfWorkFactory factory, Session session) {
        this(factory, session, null);
    }

    public UnitOfWorkAdapter(IUnitOfWorkFactory factory, Session session, UnitOfWorkAdapter previous) {
        shouldNotBeNull(factory, "factory");
        shouldNotBeNull(session, "session");

        if (log.isDebugEnabled())
            log.debug("UnitOfWorkAdapter를 생성합니다. factory=[{}], session=[{}], previous=[{}]",
                      factory, session, previous);

        this.factory = factory;
        this.session = session;
        this.previous = previous;
    }

    @Override
    public int getUsage() {
        return usageCount.get();
    }

    @Override
    public int increseUsage() {
        int usage = usageCount.incrementAndGet();

        if (log.isDebugEnabled())
            log.debug("UnitOfWorkAdapter의 사용 횟수를 증가했습니다. usageCount=[{}]", usage);

        return usage;
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
        return session.getTransaction() != null &&
                session.getTransaction().isActive();
    }

    @Override
    public IUnitOfWorkTransaction beginTransaction() {
        return new UnitOfWorkTransactionAdapter(session.beginTransaction());
    }

    @Override
    public IUnitOfWorkTransaction beginTransaction(TransactionDefinition transactionDefinition) {
        return new UnitOfWorkTransactionAdapter(session.beginTransaction());
    }

    @Override
    public void close() {
        if (closed)
            return;

        try {
            if (log.isDebugEnabled())
                log.debug("UnitOfWorkAdapter 를 close 합니다...");

            int usage = usageCount.decrementAndGet();

            if (log.isDebugEnabled())
                log.debug("Usage of IUnitOfWork = [{}]", usage);

            if (usage != 0) {
                if (log.isDebugEnabled())
                    log.debug("UnitOfWorkAdapter 의 사용 수가 0이 아니므로 실제 내부 리소스를 정리하지 않습니다.");
                return;
            }

            if (factory != null) {
                try { factory.closeUnitOfWork(this);} catch (Exception ignored) {}
            }
            if (session != null) {
                try {session.close();} catch (Exception ignored) {}
            }

            if (log.isDebugEnabled())
                log.debug("UnitOfWorkAdatper 를 close 했습니다!!!");

        } catch (Exception e) {
            if (log.isWarnEnabled())
                log.warn("UnitOfWorkAdapter close 시에 예외가 발생했습니다. 단 예외를 무시합니다.", e);
        } finally {
            closed = true;
        }
    }
}
