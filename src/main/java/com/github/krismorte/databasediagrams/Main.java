
package com.github.krismorte.databasediagrams;

import com.github.krismorte.databasediagrams.app.DatabaseDiagram;
import com.github.krismorte.databasediagrams.app.IndexPage;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

/**
 *
 * @author krismorte <krisnamourt_ti@hotmail.com>
 * @git https://github.com/krismorte
 * @date   04/05/2019
 */
public class Main {

    public static void main(String[] args) {
        splash();
        try {
            String output = System.getenv("OUTPUT");
            String indexTemplate =  System.getenv("TMP_IDX");
            String dbconfigDir =  "dbconf" ;

            IndexPage indexPage = new IndexPage(output,indexTemplate);

            for (File f : new File(dbconfigDir).listFiles((dir, name) -> name.toLowerCase().endsWith(".prop"))) {
                System.out.println("processing " + f.getName());

                InputStream input = new FileInputStream(f.getAbsolutePath());
                Properties prop = new Properties();

                // load a properties file
                prop.load(input);
                List<String> rows = DatabaseDiagram.run(output, prop);
                String dbVersion = DatabaseDiagram.DatabaseVersion;

                indexPage.addServer(prop.getProperty("db.type"), prop.getProperty("db.server"), dbVersion, rows);
            }
            indexPage.generate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void splash() {
        System.out.println(
                "                   ____         _        _                    \n"
                        + "                  |  _ \\  __ _| |_ __ _| |__   __ _ ___  ___ \n"
                        + "                  | | | |/ _` | __/ _` | '_ \\ / _` / __|/ _ \\\n"
                        + "                  | |_| | (_| | || (_| | |_) | (_| \\__ \\  __/\n"
                        + "                  |____/ \\__,_|\\__\\__,_|_.__/ \\__,_|___/\\___|\n"
                        + "                                                             \n"
                        + "                  ____  _                                     \n"
                        + "                 |  _ \\(_) __ _  __ _ _ __ __ _ _ __ ___  ___ \n"
                        + "                 | | | | |/ _` |/ _` | '__/ _` | '_ ` _ \\/ __|\n"
                        + "                 | |_| | | (_| | (_| | | | (_| | | | | | \\__ \\\n"
                        + "                 |____/|_|\\__,_|\\__, |_|  \\__,_|_| |_| |_|___/\n"
                        + "                                |___/                         ");
        System.out.println("\t\t database-diagrams 1.0v\t SchemaSpy 6.0v");
        System.out.println("Author: krismorte <krisnamourt_ti@hotmail.com>");
        System.out.println("");
    }

}
