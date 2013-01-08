package springbook.chap06;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.mockito.Mockito;
import springbook.chap06.impl.UserServiceImpl;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;

/**
 * springbook.chap06.UserServiceTestByMock
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 11. 18.
 */
@Slf4j
public class UserServiceTestByMock {

    private List<User> users = new ArrayList<>();

    @Test
    public void mockUserDao() {
        UserServiceImpl userServiceImpl = new UserServiceImpl();

        UserDao mockUserDao = Mockito.mock(UserDao.class);
        Mockito.when(mockUserDao.getAll()).thenReturn(this.users);
        userServiceImpl.setUserDao(mockUserDao);

        userServiceImpl.add(new User());
        userServiceImpl.getAll();
        userServiceImpl.upgradeLevels();

        // 검증 ( add 메소드 호출 시에 add 메소드가 한번 호출 되었음을 확인한다 )
        Mockito.verify(mockUserDao, times(1)).add(any(User.class));

        Mockito.verify(mockUserDao).getAll();
        Mockito.verify(mockUserDao).upgradeLevels();
    }
}
