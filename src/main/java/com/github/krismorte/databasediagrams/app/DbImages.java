
package com.github.krismorte.databasediagrams.app;

/**
 *
 * @author krismorte <krisnamourt_ti@hotmail.com>
 * @git https://github.com/krismorte
 * @date   04/05/2019
 */
public enum DbImages {
    MSSQL("img/mssql.png"), MYSQL("img/mysql.jpg"), PGSQL("img/postgres.png"), OTHER("img/db.png");
    
    String path;

    private DbImages(String path) {
        this.path = path;
    }
    
}
