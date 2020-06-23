package edu.slovak;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;
/**
 *
 *  1. Task1:
 *
 *     1.1. Download a text about Harry Potter.
 *     1.2. For each distinct word in the text calculate the number of occurrence.
 *     1.3. Use RegEx.
 *     1.4. Sort in the DESC mode by the number of occurrence.
 *     1.5. Find the first 20 pairs.
 *     1.6  Find all the proper names.
 *     1.7. Count them and arrange in alphabetic order.
 *     1.8. First 20 pairs and names write into to a file test.txt.
 *     1.9. Create a fine header for the file.
 *     1.10 Use Java Collections to demonstrate your experience (Map, List).
 *
 *
 *  @author Vladyslav Zavada KHNUE
 *  @version 1.0 [23] [06] [2020]
 *  Classname Main
 *
 **/

public class Main {

    public static void main(String[] args) throws IOException {
        // -----------------------------------------------------------------------
        // 1.1. Download a text about Harry Potter.
        String textHarry = new String(Files.readAllBytes(Paths.get("D:\\harry.txt")));

        // Cleaning the text from punctuation marks and (1.3.) Use RegEx
        String[] allWords = textHarry
                .replaceAll("[\\s\\.\\?\\!,\\-\":;]+", " ")
                .replaceAll("[^a-zA-Z0-9 ]", " ")
                .split("\\s+"); //RegEx
        
        // 1.2. For each distinct word in the text calculate the number of occurrences.
        Map<String, Integer> countEachWords = new HashMap<>();
        // Check distinct word
        for (String words : allWords) {
            if (countEachWords.containsKey(words)) {
                int count = countEachWords.get(words);
                countEachWords.put(words, count + 1);
            } else {
                countEachWords.put(words, 1);
            }
        }
        
        // 1.4. Sort in the DESC mode by the number of occurrences.
        LinkedHashMap<String, Integer> DescendingSortedMap = new LinkedHashMap<>();
        countEachWords.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEachOrdered(x -> DescendingSortedMap.put(x.getKey(), x.getValue()));
        
        // 1.5. Find the first 20 pairs.
        System.out.println("Result: first 20 pairs:");
        Iterator<Map.Entry<String, Integer>> items = DescendingSortedMap.entrySet().iterator();

        Path path = Paths.get("D:\\FinalTask\\src\\test.txt");
        for (int i = 0; i < 20; i++) {
            Map.Entry<String, Integer> pair = items.next();
            System.out.format("Word: %s, occurrences: %d%n", pair.getKey(), pair.getValue()); // output result
            Files.write(path, (pair.getKey() + "\n").getBytes(), StandardOpenOption.APPEND); // write pair to the file "test.txt"
        }
        
        // 1.6  Find all the proper names.
        List<String> proName = new ArrayList<>();
        // check every single word
        for (String value : countEachWords.keySet()) {
            if (Character.isUpperCase(value.charAt(0))) { 
                proName.add(value);
            }
        }
        
        // 1.7. Count them and arrange in alphabetic order.
        Collections.sort(proName);
        
        // 1.8. First 20 pairs and names write into to a file test.txt
        System.out.println("Result: First 20 names: ");
        for (int i = 0; i < 20; i++) {
            System.out.println(proName.get(i));  // output result
            Files.write(path, (proName.get(i) + "\n").getBytes(), StandardOpenOption.APPEND);
        }
    }
}
