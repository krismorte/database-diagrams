package com.github.krismorte.databasediagrams.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 *
 * @author krismorte <krisnamourt_ti@hotmail.com>
 * @git https://github.com/krismorte
 * @date   04/05/2019
 */
public class Query {

    private String driverName;
    private String driverVersion;
    private String databaseProductName;
    private String databaseProductVersion;

    public List run(/*String drive, String url, String user, String password, String query*/Properties prop) throws Exception {
        List<String> rows = new ArrayList<>();
        
        String user = prop.getProperty("db.user");
        String password = prop.getProperty("db.password");
        String query =  prop.getProperty("db.query") ==null
                ? DatabaseSettings.getDatabasesQuery(prop) : prop.getProperty("db.query") ;
        
        Connection connection = ConnectionFactory.getConnetion(DatabaseSettings.getDriveClass(prop)
                , DatabaseSettings.getUrl(prop), user, password);

        driverName = connection.getMetaData().getDriverName();
        driverVersion = connection.getMetaData().getDriverVersion();
        databaseProductName = connection.getMetaData().getDatabaseProductName();
        databaseProductVersion = connection.getMetaData().getDatabaseProductVersion();

        PreparedStatement command = connection.prepareStatement(query);

        ResultSet set = command.executeQuery();

        while (set.next()) {
            rows.add(set.getString(1));
        }

        ConnectionFactory.closeConnetion(connection, command);
        return rows;
    }

    /**
     * @return the driverName
     */
    public String getDriverName() {
        return driverName;
    }

    /**
     * @return the driverVersion
     */
    public String getDriverVersion() {
        return driverVersion;
    }

    /**
     * @return the databaseProductName
     */
    public String getDatabaseProductName() {
        return databaseProductName;
    }

    /**
     * @return the databaseProductVersion
     */
    public String getDatabaseProductVersion() {
        return databaseProductVersion;
    }

}
