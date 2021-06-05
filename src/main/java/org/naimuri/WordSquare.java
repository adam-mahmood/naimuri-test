package org.naimuri;

import org.apache.commons.lang3.StringUtils;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class WordSquare {
    private final Map<Integer,Map<Integer,String>> matrix;
    WordSquare(int dimensions){
        //potentially use a concurrent skiplist
        matrix = new ConcurrentHashMap<>();
//        for (int i = 0; i < dimensions; i++) {
//            ConcurrentHashMap<Integer, String> map = new ConcurrentHashMap<>();
//            matrix.putIfAbsent(i, map);
//            for (int j = 0; j < dimensions; j++) {
//                map.putIfAbsent(j,"");
//            }
//        }
    }

    public void setValue(int row, int column, String letter){
        matrix.putIfAbsent(row,new ConcurrentHashMap<>());
        matrix.computeIfPresent(row,(k,v) -> {
            v.putIfAbsent(column,letter);
            return v;
        });
    }

    public static void main(String[] args) throws URISyntaxException {
        WordSquare wordSquare = new WordSquare(4);
        Map<Integer, String> map = new HashMap<>();
        map.put(1,"");
        map.put(9,"");
        map.put(6,"");
        System.out.println('b' -'a');
        List<String> l = WordSquareSolver.createDictionary("enable1.txt", "aaccdeeeemmnnnoo", 4);

    }
}
