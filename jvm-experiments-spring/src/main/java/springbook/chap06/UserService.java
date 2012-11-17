package springbook.chap06;

import java.util.List;

/**
 * springbook.chap06.UserService
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 11. 18.
 */
public interface UserService {

	void add(User user);

	void upgradeLevels();

	List<User> getAll();
}
