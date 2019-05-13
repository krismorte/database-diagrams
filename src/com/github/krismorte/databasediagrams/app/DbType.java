package com.github.krismorte.databasediagrams.app;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author krismorte <krisnamourt_ti@hotmail.com>
 * @git https://github.com/krismorte
 * @date   04/05/2019
 */
public class DbType {

    String type;
    int totalServer = 0;
    int totalDatabases = 0;
    String img = "";

    public static List<DbType> generateNewList() {
        List<DbType> types = new ArrayList<>();
        for (DbImages d : DbImages.values()) {
            DbType t = new DbType();
            t.type = d.toString().toLowerCase().trim();
            t.img = d.path;
            types.add(t);
        }

        return types;
    }
    
    public static  DbType find(List<DbType> list,String dbType){
        DbType type = null;
        for (DbType t : list) {
            if(t.type.equals(dbType)){
                type = t;                
            }
        }
        if(type==null){
            type = list.stream().filter(t -> t.type.equals(DbImages.OTHER.toString().toLowerCase())).findFirst().get();
        }
        return type;
    }

}
