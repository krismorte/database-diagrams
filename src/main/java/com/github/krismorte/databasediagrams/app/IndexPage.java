package com.github.krismorte.databasediagrams.app;

import com.github.krismorte.databasediagrams.file.FileGenerator;
import com.github.krismorte.databasediagrams.sql.DatabaseSettings;

import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author krismorte <krisnamourt_ti@hotmail.com>
 * @git https://github.com/krismorte
 * @date 04/05/2019
 */
public class IndexPage {

    public String output;
    public String templateIndex;
    private List<DbType> types = new ArrayList<>();
    private List<Server> servers = new ArrayList<>();

    public IndexPage(String output,String templateIndex) {
        this.output = output;
        this.templateIndex = templateIndex;
    }

    public void addServer(String type, String server, String version, List<String> databaseNames) {
        type = DatabaseSettings.unifyingSQLTypes(type);

        if (types.isEmpty()) {
            types = DbType.generateNewList();
        }
        DbType t = DbType.find(types, type);
        t.totalServer++;
        t.totalDatabases += databaseNames.size();

        servers.add(new Server(t.type, server, version, databaseNames));
    }

    public void generate() throws Exception {
        String content = readTemplate();

        content = lastExecution(content);
        content = databasesBoxes(content);
        content = databasesRows(content);

        FileGenerator.generate(output + "/index.html", content);
    }

    private String lastExecution(String content) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        String newContentPart = "<!-- last-exec --><h4 >SchemaSpy Analysis of all databases at your environment</h4>\n"
                + "          <p>Generated on " + LocalDateTime.now().format(formatter) + "</p>";
        content = replaceContentPart(content, "<!-- last-exec -->", "<!-- /last-exec -->", newContentPart);
        return content;
    }

    private String databasesBoxes(String content) {

        String newContentPart = "<!-- databases-boxes -->";
        for (DbType t : types) {
            newContentPart += "	<div class=\"col-md-3 col-sm-4 col-xs-12\">\n"
                    + "			  <div class=\"info-box\">\n"
                    + "				  <span class=\"info-box-icon \">\n"
                    + "						<img src='" + t.img + "' class=\"img-fluid\" height=\"80\" width=\"80\" />\n"
                    + "						</span>\n"
                    + "				  <div class=\"info-box-content\">\n"
                    + "					  <span class=\"info-box-text\">Servers: " + t.totalServer + "</span>\n"
                    + "					  <span class=\"info-box-text\">Databases</span>\n"
                    + "					  <span class=\"info-box-number\">" + t.totalDatabases + "</span>\n"
                    + "				  </div>\n"
                    + "				  <!-- /.info-box-content -->\n"
                    + "			  </div>\n"
                    + "			  <!-- /.info-box -->\n"
                    + "			</div>\n";
        }

        content = replaceContentPart(content, "<!-- databases-boxes -->", "<!-- /databases-boxes -->", newContentPart);
        return content;
    }

    private String databasesRows(String content) {
        String newContentPart = "<!-- list-databases -->";
        for (Server s : servers) {
            for (String d : s.databases) {
                String linkPath = s.type + "/" + s.name + "/" + d;
                newContentPart += "      <tr class=\"tbl even\" valign=\"top\">  \n"
                        + "        <td class=\"detail\" align=\"middle\" ><img src='" + s.img + "' class=\"img-fluid\" height=\"40\" width=\"40\" /></td>\n"
                        + "        <td class=\"detail\" align=\"middle\">" + s.name + "</td>\n"
                        + "        <td class=\"detail\" align=\"right\">" + s.version + "</td>\n"
                        + "        <td class=\"detail\" align=\"middle\"><a href='" + linkPath + "/index.html'>" + d + "</a></td>\n"
                        + "        </tr>\n";
            }
        }

        content = replaceContentPart(content, "<!-- list-databases -->", "<!-- /list-databases -->", newContentPart);
        return content;
    }

    private String replaceContentPart(String content, String keyOpen, String keyClose, String newPart) {
        int init = content.indexOf(keyOpen);
        int end = content.indexOf(keyClose);
        String originalContentPart = content.substring(init, end);
        content = content.replace(originalContentPart, newPart);
        return content;
    }

    private String readTemplate() throws Exception {
        String content = "";

        // This will reference one line at a time
        String line = null;

        // FileReader reads text files in the default encoding.
         FileReader fileReader
                = new FileReader(templateIndex);
        

        // Always wrap FileReader in BufferedReader.
       
        BufferedReader bufferedReader
                = new BufferedReader(fileReader);

        while ((line = bufferedReader.readLine()) != null) {
            content += line + "\n";
        }

        // Always close files.
        bufferedReader.close();
        return content;

    }
}
