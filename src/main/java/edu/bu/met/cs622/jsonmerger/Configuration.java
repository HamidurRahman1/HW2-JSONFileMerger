package edu.bu.met.cs622.jsonmerger;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public final class Configuration {
    // directory that contains the all the JSONs
    public static final String JSON_DIRECTORY = "/Users/hamidurrahman/Downloads/Indiegogo_Data";
    
    // single file which will be created to store the content of all the other JSONs
    // this file will be under the JSON_DIRECTORY
    public static final String MERGED_FILE = "merged.json";
    
    // word to be looked up in tags and title fields. Assuming case-insensitive
    public static final String LOOKUP_WORD = "wearable".trim().toLowerCase();
    
    // ObjectMapper object reads/parses JSON objects
    private static final ObjectMapper objectMapper = new ObjectMapper();
    
    /** Returns the singleton ObjectMapper object.
     *
     * @return objectMapper which used to parse the JSON object.
     */
    public static ObjectMapper getObjectMapper() {
        return objectMapper;
    }
    
    /** Returns the all the JSON files from the given directory.
     *
     * @param directory directory containing all the JSON files
     * @return List of JSON files as File objects
     * @throws Exception when directory is null
     */
    public static List<File> getAllJSONFiles(final String directory) throws Exception {
        if (directory == null) {
            throw new Exception("Expected a string representing a directory but found null");
        }
        
        if(!new File(directory).isDirectory()) {
            throw new Exception("Expected a valid directory but given input is not. Given input: " + directory);
        }
        
        List<File> jsonFiles = Arrays.stream(Objects.requireNonNull(new File(directory)
                                             .listFiles(obj -> obj.isFile() && obj.getName().endsWith(".json"))))
                                     .collect(Collectors.toList());
        
        if (jsonFiles.size() == 0) {
            throw new Exception("Directory: " + directory + " does not contain any JSON files.");
        }
        return jsonFiles;
    }
    
    /** Combines the JSON_DIRECTORY with the mergedFile and returns the absolute path to the
     * mergedFile (generated Path is system independent).
     *
     * @return Absolute Path to mergedFile
     */
    public static Path getAbsoluteMergedFilePath() throws Exception {
        if(!new File(JSON_DIRECTORY).isDirectory()) {
            throw new Exception("Invalid JSON directory or JSON file name.");
        }
        return Paths.get(JSON_DIRECTORY, MERGED_FILE);
    }
}
