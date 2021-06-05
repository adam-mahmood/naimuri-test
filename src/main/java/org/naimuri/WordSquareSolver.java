package org.naimuri;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class WordSquareSolver {


    public List<String> solve(String input){
        long startTime = System.nanoTime();
        List<String> wordSquare = new ArrayList<>();

        String[] split = input.split("\\s+");
        int length = Integer.parseInt(split[0]);
        String letters = split[1];


        System.out.println("\nExecution Time: " + Double.toString((System.nanoTime() - startTime) / 1000000000.0)+"s");
        return wordSquare;
    }

    public Map<Character,Integer> getCharFrequency(String letters){
        Map<Character,Integer> map = new HashMap<>();

        for (int i = 0; i < letters.length(); i++) {
            char c = letters.charAt(i);
            map.putIfAbsent(c,1);
            map.computeIfPresent(c,(k,v) -> v+1);
        }
        return  map;
    }

    public static List<String> createDictionary(String resourceName, String letters, int length) throws URISyntaxException {
        Path filePath = Paths.get(ClassLoader.getSystemResource(resourceName).toURI());

        try (Stream<String> lines = Files.lines(filePath))
        {
            List<String> filteredLines = lines
                    .filter(s -> s.length() == length && StringUtils.containsOnly(s,letters) )
                    .collect(Collectors.toList());
            return  filteredLines;
        }
        catch (IOException e) {

            e.printStackTrace();
        }
        return  new ArrayList<>();
    }
}
