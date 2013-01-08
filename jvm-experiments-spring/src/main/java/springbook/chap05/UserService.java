package springbook.chap05;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * springbook.chap05.UserService
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 11. 17.
 */
@Slf4j
public class UserService {

    @Autowired
    @Setter
    UserDao userDao;

    @Autowired
    @Setter
    PlatformTransactionManager transactionManager;
}
