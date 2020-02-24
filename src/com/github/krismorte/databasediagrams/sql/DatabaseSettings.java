/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.krismorte.databasediagrams.sql;

import java.util.Properties;

/**
 *
 * @author krismorte <krisnamourt_ti@hotmail.com>
 * @git https://github.com/krismorte
 * @date 23/02/2020
 */
public class DatabaseSettings {

    private final static String JDBC_URL = "jdbc:%s://%s:%s%s";
    private final static String MYSQL_QUERY = "SELECT DISTINCT SCHEMA_NAME FROM information_schema.SCHEMATA WHERE SCHEMA_NAME NOT IN ('information_schema', 'performance_schema', 'mysql','sys','tmp') ORDER BY SCHEMA_NAME";
    private final static int MYSQL_PORT = 3306;
    private final static String MSSQL_QUERY = "select name from master..sysdatabases where name not in ('master','tempdb','msdb','model') order by 1";
    private final static int MSSQL_PORT = 1433;
    private final static String PGSQL_QUERY = "select datname from pg_catalog.pg_database where datname not in ('template0','template1','postgres') order by 1";
    private final static int PGSQL_PORT = 5432;

    public static String getDriveClass(Properties prop) {
        String driveClass = "";
        String dbType = prop.getProperty("schemaspy.db.type");
        dbType = unifyingSQLTypes(dbType);
        switch (dbType) {
            case "mysql":
                driveClass = prop.getProperty("jdbc.drive.class") == null ? "com.mysql.jdbc.Driver" : prop.getProperty("jdbc.drive.class");
                break;
            case "mssql":
                driveClass = prop.getProperty("jdbc.drive.class") == null ? "com.microsoft.sqlserver.jdbc.SQLServerDriver" : prop.getProperty("jdbc.drive.class");
                break;
            case "pgsql":
                driveClass = prop.getProperty("jdbc.drive.class") == null ? "org.postgresql.Driver" : prop.getProperty("jdbc.drive.class");
                break;

        }

        return driveClass;
    }

    public static String getUrl(Properties prop) {
        String url = "";
        String dbType = prop.getProperty("schemaspy.db.type");
        dbType = unifyingSQLTypes(dbType);
        switch (dbType) {
            case "mysql":
                url = prop.getProperty("db.url") == null ? String.format(JDBC_URL, prop.getProperty("schemaspy.db.type"), prop.getProperty("db.server"), getServerPort(prop), "/mysql") : prop.getProperty("db.url");
                break;
            case "mssql":
                url = prop.getProperty("db.url") == null ? String.format(JDBC_URL, "sqlserver", prop.getProperty("db.server"), getServerPort(prop), ";databaseName=master") : prop.getProperty("db.url");
                break;
            case "pgsql":
                url = prop.getProperty("db.url") == null ? String.format(JDBC_URL, "postgresql", prop.getProperty("db.server"), getServerPort(prop), "/postgres") : prop.getProperty("db.url");
                break;
        }

        return url;
    }

    public static String getDatabasesQuery(Properties prop) {
        String query = "";
        String dbType = prop.getProperty("schemaspy.db.type");
        dbType = unifyingSQLTypes(dbType);
        switch (dbType) {
            case "mysql":
                query = prop.getProperty("db.query") == null ? MYSQL_QUERY : prop.getProperty("db.query");
                break;
            case "mssql":
                query = prop.getProperty("db.query") == null ? MSSQL_QUERY : prop.getProperty("db.query");
                break;
            case "pgsql":
                query = prop.getProperty("db.query") == null ? PGSQL_QUERY : prop.getProperty("db.query");
                break;

        }

        return query;
    }

    public static int getServerPort(Properties prop) {
        int port = -1;
        String dbType = prop.getProperty("schemaspy.db.type");
        dbType = unifyingSQLTypes(dbType);
        switch (dbType) {
            case "mysql":
                port = prop.getProperty("db.port") == null ? MYSQL_PORT : Integer.parseInt(prop.getProperty("db.port"));
                break;
            case "mssql":
                port = prop.getProperty("db.port") == null ? MSSQL_PORT : Integer.parseInt(prop.getProperty("db.port"));
                break;
            case "pgsql":
                port = prop.getProperty("db.port") == null ? PGSQL_PORT : Integer.parseInt(prop.getProperty("db.port"));
                break;

        }

        return port;
    }

    private static String unifyingSQLTypes(String dbType) {
        if (dbType.contains("mss")) {//mssql, mssql05 and mssql08
            return "mssql";
        }
        if (dbType.contains("mariadb")) {//mysql and mariadb
            return "mysql";
        }
        if (dbType.contains("pg")) {//pgsql and pgsql11
            return "pgsql";
        }
        return dbType;
    }

}
