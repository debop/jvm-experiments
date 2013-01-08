package springbook.chap06.advice;

import lombok.Setter;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/**
 * springbook.chap06.advice.TransactionAdvice
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 11. 18.
 */
public class TransactionAdvice implements MethodInterceptor {

    @Setter
    PlatformTransactionManager transactionManager;

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        TransactionStatus status =
                transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            Object result = methodInvocation.proceed();
            transactionManager.commit(status);
            return result;
        } catch (RuntimeException e) {
            transactionManager.rollback(status);
            throw e;
        }
    }
}
