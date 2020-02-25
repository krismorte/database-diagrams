package com.github.krismorte.databasediagrams.file;

import java.io.FileWriter;

/**
 *
 * @author krismorte <krisnamourt_ti@hotmail.com>
 * @git https://github.com/krismorte
 * @date   04/05/2019
 */
public class FileGenerator {
    
    public static void generate(String fileName, String fileContent) throws Exception {
        FileWriter writer = new FileWriter(fileName, false);
        writer.write(fileContent);
        writer.close();
    }
    
      public static void generateSH(String fileName, String fileContent) throws Exception {
        FileWriter writer = new FileWriter(fileName, false);
        writer.write("#!/bin/sh\n");
        writer.write(fileContent);
        writer.close();
    }

}
