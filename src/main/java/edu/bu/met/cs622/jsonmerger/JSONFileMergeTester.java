package edu.bu.met.cs622.jsonmerger;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;

public class JSONFileMergeTester {
    
    public static void main(String[] args) {
//         JSONFileMerger object to merge the files
        FileMerger fileMerger = new JSONFileMerger(Configuration.LOOKUP_WORD);

        try {
            Path pathToMergedFile = Configuration.getAbsoluteMergedFilePath();

            // delete the mergedFile if exists. Possible IOException
            Files.deleteIfExists(pathToMergedFile);

            // try-with-resources to auto close the resources (mergedFile) once done merging.
            try (PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter(pathToMergedFile.toString(), false)))){

                fileMerger.merge(Configuration.getObjectMapper(),
                        Configuration.getAllJSONFiles(Configuration.JSON_DIRECTORY),
                        printWriter);
            }

        } catch(IOException iox) {
            System.out.println(iox.getMessage());
            iox.printStackTrace();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
}
