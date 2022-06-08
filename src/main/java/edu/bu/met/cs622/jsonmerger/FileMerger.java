package edu.bu.met.cs622.jsonmerger;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.PrintWriter;
import java.util.List;

public interface FileMerger {
    public abstract void merge(ObjectMapper objectMapper, List<File> files, PrintWriter printWriter) throws Exception;
}
