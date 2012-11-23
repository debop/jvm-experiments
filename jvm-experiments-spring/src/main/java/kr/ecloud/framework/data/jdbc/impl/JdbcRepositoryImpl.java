package kr.ecloud.framework.data.jdbc.impl;

import kr.ecloud.framework.data.jdbc.JdbcRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import javax.sql.DataSource;
import java.util.List;

/**
 * kr.ecloud.framework.data.jdbc.impl.JdbcRepositoryImpl
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 11. 17.
 */
@Slf4j
public abstract class JdbcRepositoryImpl implements JdbcRepository {

	NamedParameterJdbcTemplate jdbcTemplate;

	public void setDataSource(DataSource dataSource) {
		jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}


	@Override
	public void execute(String query, MapSqlParameterSource parameterSource) {
		jdbcTemplate.update(query, parameterSource);
	}


	@Override
	@SuppressWarnings("unchecked")
	public <T> T executeScala(String query, MapSqlParameterSource parameterSource, Class<? extends T> returnType) {
		return jdbcTemplate.queryForObject(query, parameterSource.getValues(), returnType);
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> List<T> executeEntities(String query, MapSqlParameterSource parameterSource, Class<? extends T> returnType) {
		return (List<T>) jdbcTemplate.queryForList(query, parameterSource.getValues(), returnType);
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> List<T> query(String sql, SqlParameterSource parameterSource, RowMapper<T> rowMapper) {
		return (List<T>) jdbcTemplate.query(sql, parameterSource, rowMapper);
	}

}
