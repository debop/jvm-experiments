package springbook.chap05;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * springbook.chap05.UserDaoJdbc
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 11. 17.
 */
@Slf4j
public class UserDaoJdbc extends springbook.chap03.UserDao implements springbook.chap05.UserDao {

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
				user.setLevel(Level.valueOf(rs.getInt("level")));
				user.setLogin(rs.getInt("login"));
				user.setRecommend(rs.getInt("recommend"));
				return user;
			}
		};

	@Override
	public void add(final User user) {

		if (log.isDebugEnabled())
			log.debug("add user = [{}]", user);

		PlatformTransactionManager tm = new DataSourceTransactionManager(jdbcTemplate.getDataSource());
		TransactionStatus ts = tm.getTransaction(new DefaultTransactionDefinition());

		try {
			this.jdbcTemplate.update(new PreparedStatementCreator() {
				@Override
				public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
					PreparedStatement ps =
						con.prepareStatement(
							"INSERT INTO Users (id, name, password, level, login, recommend) values(?, ?, ?, ?, ?, ?)");
					ps.setString(1, user.getId());
					ps.setString(2, user.getName());
					ps.setString(3, user.getPassword());
					ps.setInt(4, user.getLevel().intValue());
					ps.setInt(5, user.getLogin());
					ps.setInt(6, user.getRecommend());
					return ps;
				}
			});
			tm.commit(ts);
		} catch (RuntimeException e) {
			tm.rollback(ts);
			throw e;
		}
	}


}
