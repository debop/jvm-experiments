package kr.ecloud.framework.data.jdbc;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import java.util.List;

/**
 * Repository for JDBC
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 11. 17.
 */
public interface IJdbcRepository {

    void execute(String query, MapSqlParameterSource parameterSource);

    <T> List<T> executeEntities(String query, MapSqlParameterSource parameterSource, Class<? extends T> returnType);

    <T> T executeScala(String query, MapSqlParameterSource parameterSource, Class<? extends T> returnType);

    <T> List<T> query(String sql, SqlParameterSource parameterSource, RowMapper<T> rowMapper);
}
