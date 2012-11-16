package springbook.user.dao;

import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;
import springbook.user.domain.User;

import java.sql.*;

/**
 * 설명을 추가하세요.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 11. 16
 */
@Slf4j
public class UserDao {

	public void add(User user) throws SQLException {
		@Cleanup
		Connection conn = getConnection();
		@Cleanup
		PreparedStatement ps = conn.prepareStatement("INSERT INTO Users(id, name, password), values(?, ?, ?)");

		ps.setString(1, user.getId());
		ps.setString(2, user.getName());
		ps.setString(3, user.getPassword());

		ps.executeUpdate();
	}

	public User get(String id) throws SQLException {

		User user = null;

		@Cleanup
		Connection conn = getConnection();
		@Cleanup
		PreparedStatement ps = conn.prepareStatement("SELECT * FROM Users WHERE id=?");
		ps.setString(1, id);

		@Cleanup
		ResultSet rs = ps.executeQuery();
		if(rs.next()) {
			user = new User();
			user.setId(rs.getString("id"));
			user.setName(rs.getString("name"));
			user.setPassword(rs.getString("password"));
		}

		return user;
	}



	private static Connection getConnection() {

		if (log.isDebugEnabled())
			log.debug("Driver=[{}], ConnectionUrl=[{}], UserName=[{}], Password=[{}]",
			          Connections.DriverName_PostgreSQL, Connections.ConnetionUrl, Connections.UserName, Connections.Password);

		try {
			Class.forName(Connections.DriverName_PostgreSQL);
			return DriverManager.getConnection(Connections.ConnetionUrl,
			                                   Connections.UserName,
			                                   Connections.Password);
		} catch (Exception e) {
			log.error("Connection 을 얻는데 실패했습니다.", e);
			throw new RuntimeException(e.getCause());
		}
	}
}
