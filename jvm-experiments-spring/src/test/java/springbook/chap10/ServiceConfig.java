package springbook.chap10;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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

	@Value("org.postgresql.Driver") Class driverClass;
	@Value("jdbc://postgresql://localhost/testdb") private String url;
	@Value("root") private String username;
	@Value("root") private String password;

	@Bean
	public DataSource dataSource() {

		if (log.isInfoEnabled())
			log.info("create DataSource. url=[{}], username=[{}], password=[{}]",
			         url, username, password);

		SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
		dataSource.setDriverClass(driverClass); //(org.postgresql.Driver.class);
		dataSource.setUrl(url);
		dataSource.setUsername(username);
		dataSource.setPassword(password);

		return dataSource;
	}


}
