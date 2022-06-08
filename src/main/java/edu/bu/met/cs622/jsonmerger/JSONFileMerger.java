package edu.bu.met.cs622.jsonmerger;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.List;
import java.util.regex.Pattern;

public final class JSONFileMerger implements FileMerger {
    private final String lookupWord;
    private final Pattern pattern;
    
    public JSONFileMerger(String lookupWord) {
        this.lookupWord = lookupWord.toLowerCase();
        this.pattern = Pattern.compile("\\W" + lookupWord.trim().toLowerCase() + "\\W");
    }
    
    /** Reads multiple JSON files and merge them into a single JSON file.
     * lookupWord is checked against tagline, tags, title field of the JSON object.
     *
     * @param objectMapper to read the JSON objects
     * @param jsonFiles list of JSON files to read and merge
     * @param printWriter writes to mergedFile which contains all JSON files contents
     */
    @Override
    public void merge(ObjectMapper objectMapper, List<File> jsonFiles, PrintWriter printWriter) throws Exception {
        for(File jsonFile : jsonFiles) {
            try {
                // auto closes each file
                try(BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(jsonFile)))) {
                    String line = null;
                
                    while((line = bufferedReader.readLine()) != null) {
                        printWriter.write(line + "\n");
                        line = line.trim().toLowerCase();
    
                        JsonNode dataField = objectMapper.readTree(objectMapper.readTree(line).get("data").toString());
                        
                        String tagline = dataField.get("tagline").asText().toLowerCase();
                        String tags = dataField.get("tags").asText().toLowerCase();
                        String title = dataField.get("title").asText().toLowerCase();
                        
                        if(isLookUpWordInJSONFields(tagline, tags, title)) {
                            String closeDate = dataField.get("close_date").asText().toUpperCase();
                            String funds_raised_percent = dataField.get("funds_raised_percent").asText();
                        
                            System.out.println("close_date: " + closeDate + ", funds_raised_percent: " + funds_raised_percent);
                        }
                    }
                }
            } catch(FileNotFoundException fnf) {
                System.out.println("File: " + jsonFile + " not found.");
                fnf.printStackTrace();
                throw fnf;
            } catch(IOException iox) {
                iox.printStackTrace();
                throw iox;
            }
        }
    }
    
    /** Returns true if any of the given string representing the field of a JSON object
     *  contains the lookup word otherwise false.
     *
     * @param taglineFiled tagline field of the JSON object
     * @param tagsFiled tags field of the JSON object
     * @param titleFiled title field of the JSON object
     * @return true if any of the field contains the lookup word otherwise false
     */
    public boolean isLookUpWordInJSONFields(String taglineFiled, String tagsFiled, String titleFiled) {
        if(pattern.matcher(taglineFiled).find()) return true;
        else if(pattern.matcher(tagsFiled).find()) return true;
        return pattern.matcher(titleFiled).find();
    }
}
