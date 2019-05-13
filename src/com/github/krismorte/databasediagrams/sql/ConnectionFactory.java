package com.github.krismorte.databasediagrams.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author krismorte <krisnamourt_ti@hotmail.com>
 * @git https://github.com/krismorte
 * @date   04/05/2019
 */
public class ConnectionFactory {

    public static Connection getConnetion(String driver, String url, String login, String password
    ) throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        return DriverManager.getConnection(url, login, password);
    }

    public static void closeConnetion(Connection conect, PreparedStatement cmd) throws SQLException {
        cmd.close();
        conect.close();
    }

}
