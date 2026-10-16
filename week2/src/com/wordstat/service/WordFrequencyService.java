package com.wordstat.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WordFrequencyService {
    //接收单词列表，统计词频，返回map
    public Map<String,Integer> countWords(List<String> words){
        Map<String,Integer> freqMap=new HashMap<>();
        for (String word : words) {
            freqMap.put(word,freqMap.getOrDefault(word,0)+1);
        }
        return freqMap;
    }

    //接收词频Map，排序，返回排好序的entry列表
    public List<Map.Entry<String,Integer>> sortByCount(Map<String,Integer> freqMap){
        List<Map.Entry<String, Integer>> entryList = new ArrayList<>(freqMap.entrySet());

        entryList.sort( (o1, o2) -> {
            int countCompare = Integer.compare(o2.getValue(), o1.getValue());
            if(countCompare != 0){
                return countCompare;
            }else{
                return o1.getKey().compareTo(o2.getKey());
            }
        });

        return entryList;
    }
}
