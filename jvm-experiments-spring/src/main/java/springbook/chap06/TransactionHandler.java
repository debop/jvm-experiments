package springbook.chap06;

import lombok.Setter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * springbook.chap06.TransactionHandler
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 11. 18.
 */
public class TransactionHandler implements InvocationHandler {

	@Setter private Object target;
	@Setter private PlatformTransactionManager transactionManager;
	@Setter private String pattern;


	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		if (method.getName().startsWith(pattern)) {
			return invokeInTransaction(method, args);
		} else {
			return method.invoke(target, args);
		}
	}

	private Object invokeInTransaction(Method method, Object[] args) throws Throwable {
		TransactionStatus ts = transactionManager.getTransaction(new DefaultTransactionDefinition());
		try {
			Object ret = method.invoke(this.target, args);
			transactionManager.commit(ts);
			return ret;
		} catch (InvocationTargetException e) {
			transactionManager.rollback(ts);
			throw e.getTargetException();
		}
	}
}
