package springbook.chap01.dao;

import lombok.Cleanup;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import springbook.user.domain.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 설명을 추가하세요.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 11. 16
 */
@Slf4j
public class UserDao {

	@Getter @Setter
	private DataSource dataSource;

	public UserDao() {}

	public UserDao(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public void add(User user) throws SQLException {
		@Cleanup
		Connection conn = dataSource.getConnection();
		@Cleanup
		PreparedStatement ps = conn.prepareStatement("INSERT INTO Users(id, name, password) values(?, ?, ?)");

		ps.setString(1, user.getId());
		ps.setString(2, user.getName());
		ps.setString(3, user.getPassword());

		ps.executeUpdate();
	}

	public User get(String id) throws SQLException {

		User user = null;

		@Cleanup Connection conn = dataSource.getConnection();
		@Cleanup PreparedStatement ps = conn.prepareStatement("SELECT * FROM Users WHERE id=?");
		ps.setString(1, id);

		@Cleanup
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			user = new User();
			user.setId(rs.getString("id"));
			user.setName(rs.getString("name"));
			user.setPassword(rs.getString("password"));
		}

		return user;
	}

	public void delete(String id) throws SQLException {
		@Cleanup Connection conn = dataSource.getConnection();
		@Cleanup PreparedStatement ps =
			conn.prepareStatement("DELETE FROM Users WHERE id=?");

		ps.setString(1, id);
		ps.executeUpdate();
	}

	public void deleteAll() throws SQLException {
		execute("DELETE FROM Users");
	}

	public int getCount() throws SQLException {
		int count = 0;

		@Cleanup Connection conn = getDataSource().getConnection();
		@Cleanup ResultSet rs = executeQuery(conn, "SELECT COUNT(*) FROM Users");

		if (rs.next()) {
			count = rs.getInt(1);
		}
		return count;
	}

	private void execute(String query, Object... args) throws SQLException {
		@Cleanup Connection conn = getDataSource().getConnection();
		@Cleanup PreparedStatement ps = conn.prepareStatement(query);

		if (args != null && args.length > 0)
			for (int i = 0; i < args.length; i++)
				ps.setObject(i, args[i]);

		ps.execute();
	}

	private ResultSet executeQuery(Connection conn, String query, Object... args) throws SQLException {

		PreparedStatement ps = conn.prepareStatement(query);

		if (args != null && args.length > 0)
			for (int i = 0; i < args.length; i++)
				ps.setObject(i, args[i]);

		return ps.executeQuery();
	}

}
