package kr.ecloud.framework.data.jdbc.impl;

import kr.ecloud.framework.data.jdbc.JdbcRepository;
import lombok.Cleanup;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.datasource.DataSourceUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * kr.ecloud.framework.data.jdbc.impl.JdbcRepositoryImpl
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 11. 17.
 */
@Slf4j
public abstract class JdbcRepositoryImpl implements JdbcRepository {

	@Autowired
	@Getter
	@Setter
	DataSource dataSource;

	private Connection getConnection() {
		if(log.isDebugEnabled())
			log.debug("get database... dataSource=[{}]", getDataSource());

		return DataSourceUtils.getConnection(getDataSource());
	}


	@Override
	public void execute(String query, SqlParameter... parameters) throws SQLException {
		@Cleanup
		Connection conn = getDataSource().getConnection();
		@Cleanup
		PreparedStatement ps = buildPreparedStatemet(conn, query, parameters);

		ps.execute();
	}


	@Override
	public <T> List<T> executeEntities(String query, SqlParameter... parameters) throws SQLException {
		@Cleanup
		Connection conn = getDataSource().getConnection();
		@Cleanup
		PreparedStatement ps = buildPreparedStatemet(conn, query, parameters);

		@Cleanup ResultSet rs = ps.executeQuery();

		List<T> results = new ArrayList<T>();
		while (rs.next()) {
			//TODO: ResultSet 을 Entity List 로 만들기
		}

		return results;
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T executeScala(String query, SqlParameter... parameters) throws SQLException {
		@Cleanup
		Connection conn = getDataSource().getConnection();
		@Cleanup
		PreparedStatement ps = buildPreparedStatemet(conn, query, parameters);

		@Cleanup ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			return (T) rs.getObject(1);
		}
		return (T) null;
	}

	protected PreparedStatement buildPreparedStatemet(Connection connection, String query, SqlParameter... parameters)
		throws SQLException {
		PreparedStatement ps = connection.prepareStatement(query);

		//TODO: Set parameters

		return ps;
	}
}
