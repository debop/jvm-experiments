package springbook.user.dao;

import lombok.Cleanup;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import springbook.user.domain.User;

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

	@Setter private IConnectionMaker connectionMaker;

	public UserDao() {
		this(new SimpleConnectionMaker());
	}

	public UserDao(IConnectionMaker connectionMaker) {
		this.connectionMaker = connectionMaker;
	}

	public void add(User user) throws ClassNotFoundException, SQLException {
		@Cleanup
		Connection conn = connectionMaker.makeConnection();
		@Cleanup
		PreparedStatement ps = conn.prepareStatement("INSERT INTO Users(id, name, password) values(?, ?, ?)");

		ps.setString(1, user.getId());
		ps.setString(2, user.getName());
		ps.setString(3, user.getPassword());

		ps.executeUpdate();
	}

	public User get(String id) throws ClassNotFoundException, SQLException {

		User user = null;

		@Cleanup Connection conn = connectionMaker.makeConnection();
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

	public void delete(String id) throws ClassNotFoundException, SQLException {
		@Cleanup Connection conn = connectionMaker.makeConnection();
		@Cleanup PreparedStatement ps =
			conn.prepareStatement("DELETE FROM Users WHERE id=?");
		ps.setString(1, id);

		ps.executeUpdate();
	}


	public static void main(String[] args) throws Exception {
		UserDao dao = new UserDao();

		User user = new User();
		user.setId("debop");
		user.setName("배성혁");
		user.setPassword("real21");

		dao.delete(user.getId());

		dao.add(user);

		if (log.isDebugEnabled())
			log.debug("User=[{}] 등록 성공", user);

		User user2 = dao.get(user.getId());

		if (log.isDebugEnabled())
			log.debug("User=[{}] retrive success", user2);
	}
}
