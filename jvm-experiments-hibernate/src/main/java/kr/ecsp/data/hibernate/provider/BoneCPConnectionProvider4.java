package kr.ecsp.data.hibernate.provider;

import com.google.common.base.Objects;
import com.google.common.base.Strings;
import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;
import com.jolbox.bonecp.hooks.ConnectionHook;
import lombok.Cleanup;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.HibernateException;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.cfg.Environment;
import org.hibernate.service.jdbc.connections.spi.ConnectionProvider;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * BoneCP 를 이용한 Hibernate 용 connection provider 입니다.
 * JpaUser: sunghyouk.bae@gmail.com
 * Date: 12. 9. 26.
 */
@Slf4j
public class BoneCPConnectionProvider4 implements ConnectionProvider {

	private static final long serialVersionUID = -6671785386423003789L;

	/**
	 * Config key.
	 */
	protected static final String CONFIG_CONNECTION_DRIVER_CLASS_ALTERNATE = "javax.persistence.jdbc.driver";
	/**
	 * Config key.
	 */
	protected static final String CONFIG_CONNECTION_PASSWORD_ALTERNATE = "javax.persistence.jdbc.password";
	/**
	 * Config key.
	 */
	protected static final String CONFIG_CONNECTION_USERNAME_ALTERNATE = "javax.persistence.jdbc.user";
	/**
	 * Config key.
	 */
	protected static final String CONFIG_CONNECTION_URL_ALTERNATE = "javax.persistence.jdbc.url";

	private BoneCP pool;
	private BoneCPConfig config;
	private Integer isolation;
	private boolean autocommit;

	@Getter
	@Setter
	private ClassLoader classLoader;

	@Override
	public Connection getConnection() throws SQLException {
		if (pool == null)
			throw new RuntimeException("configure을 먼저 수행하여 BonCP 인스턴스를 생성해야 합니다.");

		@Cleanup
		Connection connection = pool.getConnection();

		if (isolation != null && connection.getTransactionIsolation() != isolation) {
			connection.setTransactionIsolation(isolation);
		}
		if (connection.getAutoCommit() != autocommit) {
			connection.setAutoCommit(autocommit);
		}
		return connection;

	}

	@Override
	public void closeConnection(Connection conn) throws SQLException {
		if (conn != null)
			conn.close();
	}

	@Override
	public boolean supportsAggressiveRelease() {
		return false;
	}

	@Override
	public boolean isUnwrappableAs(Class unwrapType) {
		return false;
	}

	@Override
	public <T> T unwrap(Class<T> unwrapType) {
		return null;
	}

	public void configure(Properties props) throws HibernateException {
		try {
			config = new BoneCPConfig(props);


			String url = Objects.firstNonNull(props.getProperty(AvailableSettings.URL),
			                                  props.getProperty(CONFIG_CONNECTION_URL_ALTERNATE));
			String username = Objects.firstNonNull(props.getProperty(AvailableSettings.USER),
			                                       props.getProperty(CONFIG_CONNECTION_USERNAME_ALTERNATE));
			String password = Objects.firstNonNull(props.getProperty(AvailableSettings.PASS),
			                                       props.getProperty(CONFIG_CONNECTION_PASSWORD_ALTERNATE));
			String driver = Objects.firstNonNull(props.getProperty(AvailableSettings.DRIVER),
			                                     props.getProperty(CONFIG_CONNECTION_DRIVER_CLASS_ALTERNATE));

			if (url != null)
				config.setJdbcUrl(url);
			if (username != null)
				config.setUsername(username);
			if (password != null)
				config.setPassword(password);


			isolation = Integer.parseInt(props.getProperty(Environment.ISOLATION));
			autocommit = Boolean.parseBoolean(props.getProperty(Environment.AUTOCOMMIT));

			if (!Strings.isNullOrEmpty(driver))
				loadClass(driver);

			if (config.getConnectionHookClassName() != null) {
				Object hookClass = loadClass(config.getConnectionHookClassName()).newInstance();
				config.setConnectionHook((ConnectionHook) hookClass);
			}
			pool = createPool(config);
		} catch (Exception e) {
			throw new HibernateException(e);
		}
	}

	protected BoneCP createPool(BoneCPConfig config) {
		try {
			return new BoneCP(config);
		} catch (SQLException e) {
			throw new HibernateException(e);
		}
	}

	protected Class<?> loadClass(String clazz) throws ClassNotFoundException {
		if (this.classLoader == null) {
			return Class.forName(clazz);
		}

		return Class.forName(clazz, true, classLoader);
	}

	protected BoneCPConfig getConfig() {
		return config;
	}
}
