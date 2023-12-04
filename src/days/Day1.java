package days;

import Utils.OwnReader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class Day1 extends Day {

    final String[][] replacements = {
            {"one", "1"},
            {"two", "2"},
            {"three", "3"},
            {"four", "4"},
            {"five", "5"},
            {"six", "6"},
            {"seven", "7"},
            {"eight", "8"},
            {"nine", "9"},
    };

    @Override
    public String getAnswerPartOne() throws Exception {
        int partOne = OwnReader.getReaderFromPath("day01.txt").lines()
                .map(s -> s.replaceAll("[^0-9]", ""))
                .mapToInt(s -> Integer.parseInt(s.charAt(0) +""+ s.charAt(s.length()-1)))
                .sum();

        System.out.println("Day1 A= "+ partOne);
        return "Day1 A= "+ partOne;
    }

    @Override
    public String getAnswerPartTwo() throws Exception {
        int partTwo = OwnReader.getReaderFromPath("day01.txt").lines()
                .map(s -> {

                    firstNumberLoop:
                    for( int i = 0; i < s.length(); i++){
                        if (s.charAt(i) >= '0' && s.charAt(i) <= '9'){
                            break firstNumberLoop;
                        }
                        for (String[] replacement : replacements)
                        {
                            int targetSize = replacement[0].length();
                            if (i+targetSize <= s.length()) {
                                if (s.substring(i, i+targetSize).equals(replacement[0])) {
                                    s=s.replaceFirst(replacement[0], replacement[1]);
                                    break firstNumberLoop;
                                }
                            }
                        }
                    }

                    lastNumberLoop:
                    for( int i = s.length()-1; i >= 0; i--){
                        for (String[] replacement : replacements)
                        {
                            int targetSize = replacement[0].length();

                            if (i-targetSize >= 0) {
                                if (s.substring(i-targetSize +1, i+1).equals(replacement[0])) {
                                    s=s.replaceAll(replacement[0], replacement[1]);
                                    break lastNumberLoop;
                                }
                            }
                        }
                    }

                    return s;
                })
                .map(s -> s.replaceAll("[^0-9]", ""))
                .mapToInt(s -> Integer.parseInt(s.charAt(0) +""+ s.charAt(s.length()-1)))
                .sum();

        System.out.println("Day1 B= "+ partTwo);
        return "Day1 B= "+ partTwo;
    }
}
