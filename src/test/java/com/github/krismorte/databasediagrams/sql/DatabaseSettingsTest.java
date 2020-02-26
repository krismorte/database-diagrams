package com.github.krismorte.databasediagrams.sql;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseSettingsTest {


    static Properties propMYSQL;
    static final String  MYSQL_DRIVE="com.mysql.jdbc.Driver";
    static String MYSQL_QUERY = "SELECT DISTINCT SCHEMA_NAME FROM information_schema.SCHEMATA WHERE SCHEMA_NAME NOT IN ('information_schema', 'performance_schema', 'mysql','sys','tmp') ORDER BY SCHEMA_NAME";
    static int MYSQL_PORT = 3306;
    static String MYSQL_URL = "jdbc:mysql://localhost:3306/mysql";

    @BeforeAll
    static void config(){
        propMYSQL = new Properties();
        propMYSQL.setProperty("schemaspy.db.type","mysql");
        propMYSQL.setProperty("jdbc.drive.class",MYSQL_DRIVE);
        propMYSQL.setProperty("db.query",MYSQL_QUERY);
        propMYSQL.setProperty("db.port",""+MYSQL_PORT);
        propMYSQL.setProperty("db.server","localhost");

    }


    @Test
    void getDriveClass() {
        Assertions.assertEquals(MYSQL_DRIVE,DatabaseSettings.getDriveClass(propMYSQL));
    }

    @Test
    void getUrl() {
        Assertions.assertEquals(MYSQL_URL,DatabaseSettings.getUrl(propMYSQL));
    }

    @Test
    void getDatabasesQuery() {
        Assertions.assertEquals(MYSQL_QUERY,DatabaseSettings.getDatabasesQuery(propMYSQL));
    }

    @Test
    void getServerPort() {
        Assertions.assertEquals(MYSQL_PORT,DatabaseSettings.getServerPort(propMYSQL));
    }
}