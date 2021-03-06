package kr.nsoft.data.jdbc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import java.util.List;

/**
 * Springs Framework의 {@link NamedParameterJdbcDaoSupport}를 상속받아 Jdbc 작업을 손쉽게 할 수 있는 메소드를 제공합니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 11. 25.
 */
@Slf4j
public class SqlRepository extends NamedParameterJdbcDaoSupport {

    /**
     * 쿼리문을 실행합니다.
     *
     * @param sql         실행할 쿼리문
     * @param paramSource 파라미터 명과 값
     * @return 영향을 받은 레코드 수
     */
    public int update(String sql, SqlParameterSource paramSource) {
        if (log.isDebugEnabled())
            log.debug("execute sql=[{}], parameters=[{}]", sql, paramSource);

        return getNamedParameterJdbcTemplate().update(sql, paramSource);
    }

    /**
     * 지정된 쿼리 문을 실행하여, 결과 셋으로부터 엔티티를 빌드한 후 엔티티의 컬렉션으로 반환합니다.
     *
     * @param sql         실행할 쿼리문
     * @param paramSource 파라미터 명과 값
     * @param rowMapper   결과 셋으로부터 엔티티를 빌드하는 함수
     * @param <E>         엔티티의 수형
     * @return 엔티티의 컬렉션
     */
    public <E> List<E> query(String sql, SqlParameterSource paramSource, RowMapper<E> rowMapper) {
        return getNamedParameterJdbcTemplate().query(sql, paramSource, rowMapper);
    }
}
