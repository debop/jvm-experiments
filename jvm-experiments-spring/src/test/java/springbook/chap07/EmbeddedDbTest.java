package springbook.chap07;

import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * springbook.chap07.EmbeddedDbTest
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 11. 19.
 */
@Slf4j
@Transactional(isolation = Isolation.READ_COMMITTED)
public class EmbeddedDbTest {

	EmbeddedDatabase db;
	JdbcTemplate template;

	@Before
	public void setup() {
		db = new EmbeddedDatabaseBuilder()
			.setType(EmbeddedDatabaseType.HSQL)
			.addScript("classpath:/springbook/chap07/embeddeddb/schema.sql")
			.addScript("classpath:/springbook/chap07/embeddeddb/data.sql")
			.build();

		template = new JdbcTemplate(db);
	}

	@After
	public void tearDown() {
		db.shutdown();
	}

	@Test
	public void initData() {
		assertThat(template.queryForInt("SELECT count(*) FROM sqlmap"), is(2));

		List<Map<String, Object>> list =
			template.queryForList("SELECT * FROM sqlmap order by key_");

		assertThat((String) list.get(0).get("key_"), is("KEY1"));
		assertThat((String) list.get(0).get("sql_"), is("SQL1"));
		assertThat((String) list.get(1).get("key_"), is("KEY2"));
		assertThat((String) list.get(1).get("sql_"), is("SQL2"));
	}

	@Test
	public void insert() {
		template.update("INSERT INTO sqlmap(key_, sql_) values(?, ?)", "KEY3", "SQL3");
		assertThat(template.queryForInt("SELECT count(*) FROM sqlmap"), is(3));
	}
}
