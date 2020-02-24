package com.github.krismorte.databasediagrams.app;

import com.github.krismorte.databasediagrams.file.FileGenerator;
import com.github.krismorte.databasediagrams.sql.Query;
import java.util.List;
import java.util.Properties;

/**
 *
 * @author krismorte <krisnamourt_ti@hotmail.com>
 * @git https://github.com/krismorte
 * @date   04/05/2019
 */
public class DatabaseDiagram {

    public static String DatabaseVersion;

    public static List<String> run(String output,Properties prop) throws Exception {

        List<String> rows = executeQuery(prop);
        generateShSchemaSpy(output,rows, prop);
        return rows;
    }

    private static List executeQuery(Properties prop) throws Exception {
        Query query = new Query();

        List<String> rows = query.run(prop);

        DatabaseVersion = query.getDatabaseProductName() + " " + query.getDatabaseProductVersion();

        return rows;
    }

    private static void generateShSchemaSpy(String output,List<String> rows, Properties prop) throws Exception {

        String schemaSpyCommands = "";
        for (String s : rows) {
            schemaSpyCommands += formatSchemaSpyCommand(output,s, prop) + "\n \n";
        }

        String fileName = prop.getProperty("schemaspy.db.type").trim() + "-" + prop.getProperty("db.server").trim() + ".sh";
        FileGenerator.generateSH(fileName, schemaSpyCommands);
    }

    private static String formatSchemaSpyCommand(String output,String databaseName, Properties prop) {
        String logFile = System.getenv("LOGFILE"); 
        
        String schemaParam = "";        
        String outputPath = output+ "/" + prop.getProperty("schemaspy.db.type").trim() + "/" + prop.getProperty("db.server").trim() + "/" + databaseName;
        
        if (prop.getProperty("schemaspy.db.type").equals("mysql")) {
            schemaParam = " -s " + databaseName;
        }
        return "/usr/bin/java -jar schemaspy.jar -t " + prop.getProperty("schemaspy.db.type") + " -dp drives/ -db " + databaseName + " -host " + prop.getProperty("db.server") + " -port " + prop.getProperty("db.port") + " -u " + prop.getProperty("db.user") + " -p '" + prop.getProperty("db.password") + "' "+schemaParam +" -o " + outputPath + " >> " + logFile;
    }

}
