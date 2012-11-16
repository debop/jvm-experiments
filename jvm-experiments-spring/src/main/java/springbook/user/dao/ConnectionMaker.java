package springbook.user.dao;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Make Connection
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 11. 16
 */
public interface ConnectionMaker {

	Connection makeConnection() throws ClassNotFoundException, SQLException;
}
