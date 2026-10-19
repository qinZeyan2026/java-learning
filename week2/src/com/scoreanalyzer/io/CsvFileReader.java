package com.scoreanalyzer.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class CsvFileReader {
    public List<String> readAllLines(String filePath) throws IOException{
        return Files.readAllLines(Paths.get(filePath));
    }
}
