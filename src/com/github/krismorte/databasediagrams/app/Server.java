package com.github.krismorte.databasediagrams.app;

import java.util.List;

/**
 *
 * @author krismorte <krisnamourt_ti@hotmail.com>
 * @git https://github.com/krismorte
 * @date   04/05/2019
 */
public class Server {

    String type;
    String name;
    String version;
    List<String> databases;
    String img = "";

    public Server(String type, String name,String version, List<String> databases) {
        this.type = type;
        this.name = name;
        this.version = version;
        this.databases = databases;
        this.img = DbImages.valueOf(type.toUpperCase()).path;
    }

}
