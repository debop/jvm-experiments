package springbook.chap06.impl;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import springbook.chap06.User;
import springbook.chap06.UserService;

import java.util.List;

/**
 * springbook.chap06.impl.UserServiceTx
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 11. 18.
 */
@Slf4j
public class UserServiceTx implements UserService {

	@Setter private UserService userService;
	@Setter private PlatformTransactionManager transactionManager;

	@Override
	public void add(User user) {
		userService.add(user);
	}

	@Override
	public void upgradeLevels() {

		TransactionStatus txStatus =
			transactionManager.getTransaction(new DefaultTransactionDefinition());
		try {

			userService.upgradeLevels();

			this.transactionManager.commit(txStatus);
		} catch (RuntimeException e) {
			if (log.isErrorEnabled())
				log.error("error in upgradeLevels... ", e);
			this.transactionManager.rollback(txStatus);
			throw e;
		}
	}

	@Override
	public List<User> getAll() {
		return userService.getAll();
	}
}
