package springbook.chap06.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import springbook.chap06.Level;
import springbook.chap06.User;
import springbook.chap06.UserDao;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * springbook.chap06.impl.JdbcUserDao
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 11. 18.
 */
@Slf4j
public class JdbcUserDao implements UserDao {

    private JdbcTemplate jdbcTemplate;

    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private RowMapper<User> userMapper =
            new RowMapper<User>() {
                @Override
                public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                    User user = new User();
                    user.setId(rs.getString("id"));
                    user.setName(rs.getString("name"));
                    user.setPassword(rs.getString("password"));
                    user.setLevel(Level.valueOf(rs.getInt("level")));
                    user.setLogin(rs.getInt("login"));
                    user.setRecommend(rs.getInt("recommend"));
                    return user;
                }
            };

    @Override
    public void add(User user) {
        jdbcTemplate.update("INSERT INTO Users(id, name, password) values(?,?,?)",
                user.getId(),
                user.getName(),
                user.getPassword());
    }

    @Override
    public List<User> getAll() {
        return jdbcTemplate.query("SELECT * FROM Users ORDER BY id", userMapper);
    }

    @Override
    public void upgradeLevels() {
        List<User> users = getAll();

        for (User user : users) {
            log.debug("upgrade user level... user=[{}]", user);
        }
    }
}
