package springbook.user.dao;

import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Make SQL Connection
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 11. 16
 */
@Slf4j
public class SimpleConnectionMaker implements IConnectionMaker {

    public Connection makeConnection() throws ClassNotFoundException, SQLException {

        if (log.isDebugEnabled())
            log.debug("Driver=[{}], ConnectionUrl=[{}], UserName=[{}], Password=[{}]",
                    Connections.DriverName_PostgreSQL, Connections.ConnetionUrl, Connections.UserName, Connections.Password);

        Class.forName(Connections.DriverName_PostgreSQL);
        return DriverManager.getConnection(Connections.ConnetionUrl,
                Connections.UserName,
                Connections.Password);
    }
}
