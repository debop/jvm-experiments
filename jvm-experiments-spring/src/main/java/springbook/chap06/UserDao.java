package springbook.chap06;

import java.util.List;

/**
 * springbook.chap06.UserDao
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 11. 18.
 */
public interface UserDao {

    void add(User user);

    List<User> getAll();

    void upgradeLevels();
}
