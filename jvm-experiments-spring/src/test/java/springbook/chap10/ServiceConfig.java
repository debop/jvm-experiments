package springbook.chap10;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import javax.sql.DataSource;

/**
 * springbook.chap10.ServiceConfig
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 11. 20.
 */
@Slf4j
@Configuration
public class ServiceConfig {

	private static final String URL = "jdbc://postgresql://localhost/testdb";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "root";

	@Bean
	public DataSource dataSource() {
		if (log.isInfoEnabled())
			log.info("create DataSource. url=[{}], username=[{}], password=[{}]", URL, USERNAME, PASSWORD);

		SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
		dataSource.setDriverClass(org.postgresql.Driver.class);
		dataSource.setUrl(URL);
		dataSource.setUsername(USERNAME);
		dataSource.setPassword(PASSWORD);

		return dataSource;
	}
}
