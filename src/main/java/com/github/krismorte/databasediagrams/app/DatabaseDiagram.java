package com.github.krismorte.databasediagrams.app;

import com.github.krismorte.databasediagrams.file.FileGenerator;
import com.github.krismorte.databasediagrams.sql.DatabaseSettings;
import com.github.krismorte.databasediagrams.sql.Query;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 *
 * @author krismorte <krisnamourt_ti@hotmail.com>
 * @git https://github.com/krismorte
 * @date   04/05/2019
 */
public class DatabaseDiagram {

    public static String DatabaseVersion;

    public static List<String> run(String output,Properties prop) throws Exception {
        List<String> rows=null;
        if(prop.getProperty("schemaspy.db.type").equals("orathin")){
            rows = new ArrayList<>();
            rows.add(prop.getProperty("db.oracsid"));
        }else{
            rows = executeQuery(prop);
        }

        generateShSchemaSpy(output,rows, prop);
        return rows;
    }

    private static List<String> executeQuery(Properties prop) throws Exception {
        Query query = new Query();

        List<String> rows = query.run(prop, "db.query");

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

    private static String formatSchemaSpyCommand(String output,String databaseName, Properties prop)throws Exception {
        String logFile = System.getenv("LOGFILE");
        
        String type = DatabaseSettings.unifyingSQLTypes(prop.getProperty("schemaspy.db.type")).trim();
        DbType t = DbType.find(DbType.generateNewList(), type);
        String outputPath = output+ "/" + t.type + "/" + prop.getProperty("db.server").trim() + "/" + databaseName;
        String schemaParam = "";
        List<String> schemas= new ArrayList<>();
        

        String dbType = prop.getProperty("schemaspy.db.type");
        String drivesPath = searchJarDir(dbType);

        if (prop.getProperty("schemaspy.db.type").equals("mysql")) {
            schemaParam = " -s " + databaseName;
        }
        else if (prop.getProperty("schemaspy.db.type").equals("orathin")) {
            schemaParam = " -cat % -all";
        }else {
            if (prop.getProperty("db.query.schema") != null){
                Query query = new Query();
                schemas = query.run(prop, "db.query");
            }
        }

        if (schemas.size()==0){
            return "/usr/bin/java -jar schemaspy.jar -t " + prop.getProperty("schemaspy.db.type") + " -dp "+drivesPath+"/ -db " + databaseName + " -host " + prop.getProperty("db.server") + " -port " + DatabaseSettings.getServerPort(prop)  + " -u " + prop.getProperty("db.user") + " -p '" + prop.getProperty("db.password") + "' "+schemaParam +" -o " + outputPath + " >> " + logFile;
        }else{
            String contentPerSchema = "";
            for (String schema : schemas){
                contentPerSchema += "/usr/bin/java -jar schemaspy.jar -t " + prop.getProperty("schemaspy.db.type") + " -dp "+drivesPath+"/ -db " + databaseName + " -host " + prop.getProperty("db.server") + " -port " + DatabaseSettings.getServerPort(prop)  + " -u " + prop.getProperty("db.user") + " -p '" + prop.getProperty("db.password") + "' -s '"+schema +"' -o " + outputPath + " >> " + logFile;
                contentPerSchema += "\n\n";
            }
            return contentPerSchema;
        }
    }


    private static String searchJarDir(final String dbType){
        String dir="";
        String tempDbType="";

        if (dbType.contains("pgsql")){
            tempDbType= "postgresql";
        }
        else if (dbType.contains("mssql")){
            tempDbType= "mssql";
        }
        else if (dbType.contains("orathin")){
            tempDbType= "ojdbc";
        }else{
            tempDbType = dbType;
        }
        try {
            final String nameToSearch = tempDbType;
            List<Path> files=Files.walk(Paths.get(System.getProperty("user.home")+"/.m2/repository/"))
                    .filter(Files::isRegularFile).collect(Collectors.toList());
            Path jarFile = files.stream().filter(f -> f.toString().contains(nameToSearch) && f.toString().endsWith(".jar")).findAny().orElse(null);
            dir = jarFile.getParent().toString();
            return dir;
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

}
