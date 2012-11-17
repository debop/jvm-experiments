package springbook.chap06.impl;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import springbook.chap06.User;
import springbook.chap06.UserDao;
import springbook.chap06.UserService;

import java.util.List;

/**
 * springbook.chap06.impl.UserServiceImpl
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 11. 18.
 */
@Slf4j
public class UserServiceImpl implements UserService {

	@Autowired @Setter private UserDao userDao;

	@Override
	public void add(User user) {
		userDao.add(user);
	}

	@Override
	public void upgradeLevels() {
		userDao.upgradeLevels();
	}

	@Override
	public List<User> getAll() {
		return userDao.getAll();
	}
}
