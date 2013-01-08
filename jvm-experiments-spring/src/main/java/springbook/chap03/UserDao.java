package springbook.chap03;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import springbook.user.domain.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * springbook.chap03.UserDaoJdbc
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 11. 17.
 */
@Slf4j
public class UserDao {

    @Getter
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource, true);
    }

    private RowMapper<User> userMapper =
            new RowMapper<User>() {
                @Override
                public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                    User user = new User();
                    user.setId(rs.getString("id"));
                    user.setName(rs.getString("name"));
                    user.setPassword(rs.getString("password"));
                    return user;
                }
            };

    public void add(final User user) {

        if (log.isDebugEnabled())
            log.debug("add user = [{}]", user);

        this.jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement("INSERT INTO Users (id, name, password) values(?, ?, ?)");
                ps.setString(1, user.getId());
                ps.setString(2, user.getName());
                ps.setString(3, user.getPassword());
                return ps;
            }
        });

    }

    public void deleteAll() {
        if (log.isDebugEnabled())
            log.debug("delete all users...");

        this.jdbcTemplate.execute("DELETE FROM Users");
    }

    public int getCount() {
        return this.jdbcTemplate.queryForInt("SELECT COUNT(*) FROM Users");
//		return
//			this.jdbcTemplate.query(
//				new PreparedStatementCreator() {
//					@Override
//					public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
//						return con.prepareStatement("SELECT COUNT(*) FROM Users");
//					}
//				},
//				new ResultSetExtractor<Integer>() {
//					@Override
//					public Integer extractData(ResultSet rs) throws SQLException, DataAccessException {
//						rs.next();
//						return rs.getInt(1);
//					}
//				}
//			);
    }

    public User get(String id) {
        return
                this.jdbcTemplate.queryForObject(
                        "SELECT * FROM Users WHERE id=?",
                        new Object[]{id},
                        this.userMapper);
    }

    public List<User> getAll() {
        return
                this.jdbcTemplate.query(
                        "SELECT * FROM Users ORDER BY id",
                        this.userMapper);
    }
}
