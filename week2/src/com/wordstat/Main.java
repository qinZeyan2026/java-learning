package com.wordstat;

import com.wordstat.io.TextFileReader;
import com.wordstat.processor.WordTokenizer;
import com.wordstat.service.WordFrequencyService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        TextFileReader reader = new TextFileReader();
        String path = "words.txt";

        try {
            List<String> lines = reader.readlines(path);
            System.out.println("读取到的内容：");
            for (String line : lines) {
                System.out.println(line);
            }

            WordTokenizer tokenizer = new WordTokenizer();
            List<String> words = tokenizer.tokenize(lines);
            System.out.println("分词之后的单词列表：");
            System.out.println(words);

            WordFrequencyService frequencyService = new WordFrequencyService();
            Map<String, Integer> freqMap = frequencyService.countWords(words);
            System.out.println("词频Map结果：");
            System.out.println(freqMap);

            List<Map.Entry<String,Integer>> sortedList = frequencyService.sortByCount(freqMap);
            System.out.println("排序后的entry列表：");
            System.out.println(sortedList);

            System.out.println("\n====最终统计结果====");
            printResult(sortedList);

        } catch (IOException e) {
            System.out.println("读取文件出错！" + e.getMessage());
        }
    }

    private static void printResult(List<Map.Entry<String,Integer>> list){
        for(Map.Entry<String, Integer> entry : list){
            System.out.println(entry.getKey() + " : " + entry.getValue() + " ");
        }
    }

}