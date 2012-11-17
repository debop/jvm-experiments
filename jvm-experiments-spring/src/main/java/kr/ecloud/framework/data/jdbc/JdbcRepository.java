package kr.ecloud.framework.data.jdbc;

import org.springframework.jdbc.core.SqlParameter;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

/**
 * Repository for JDBC
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 11. 17.
 */
public interface JdbcRepository {

	DataSource getDataSource();

	void execute(String query, SqlParameter... parameters) throws SQLException;

	<T> List<T> executeEntities(String query, SqlParameter... parameters) throws SQLException;

	<T> T executeScala(String query, SqlParameter... parameters) throws SQLException;
}
