package com.wordstat.processor;

import java.util.ArrayList;
import java.util.List;

public class WordTokenizer {
    public List<String> tokenize(List<String> lines) {
        List<String> words = new ArrayList<>();

        String[] parts = null;
        for (String line : lines) {
            String newLine = line.toLowerCase()
                    .replaceAll("[^a-zA-Z]+", " ")
                    .trim();
            parts = newLine.split("\\s+");
        }
        for (String part : parts) {
            if(!part.isBlank()){
                words.add(part);
            }
        }
        return words;
    }
}
