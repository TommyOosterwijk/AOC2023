package days;

import Utils.OwnReader;

import java.awt.*;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.*;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

public class Day12 extends Day {

    private final Map<cacheRecord, Long> cache = new ConcurrentHashMap<>(4096);

    List<String> input = new ArrayList<>();
    List<List<Integer>>  inputValues = new ArrayList<>();
    public Day12() throws FileNotFoundException {
        var lines = OwnReader.getReaderFromPath("day12.txt").lines().toList();

        for(String line : lines) {
            String[] split = line.split(" ");
            input.add(split[0]);
            List<Integer> temp = new ArrayList<>();
            for(String str : split[1].split(",")) {
                temp.add(Integer.parseInt(str));
            }
            inputValues.add(temp);
        }

    }
    @Override
    public String getAnswerPartOne() throws Exception {
        long answer = 0;
        for( int i = 0; i < input.size(); i++) {
            String targetString = input.get(i);
            long result = findSolution(targetString, inputValues.get(i));
            answer+= result;
        }

        System.out.println("Day12 A= " + answer);
        return "Day12 A= " + 0;
    }

    @Override
    public String getAnswerPartTwo() throws Exception {
        long answer = 0;
        for( int i = 0; i < input.size(); i++) {
            String targetString = input.get(i);

            String part2String = targetString + "?" + targetString + "?" + targetString + "?" + targetString + "?" + targetString;
            List<Integer> part2List = new ArrayList<>();
            part2List.addAll(inputValues.get(i));
            part2List.addAll(inputValues.get(i));
            part2List.addAll(inputValues.get(i));
            part2List.addAll(inputValues.get(i));
            part2List.addAll(inputValues.get(i));

            long result = findSolution(part2String, part2List);
            //System.out.println("Row " + i + " = " + result);
            answer+= result;
        }
        System.out.println("Day12 B= " + answer);

        return "Day11 B= " + 0;
    }

    public long findSolution( String value, List<Integer> values) {

        cacheRecord cr = new cacheRecord(value, values);
        if (cache.containsKey(cr))
            return cache.get(cr);

        long result = 0;

        if (value.isBlank())
            return values.isEmpty() ? 1L : 0L;

        char firstChar = value.charAt(0);

        switch (firstChar) {
            case '.' -> result = findSolution(value.substring(1), values);
            case '?' -> result = findSolution("." + value.substring(1), values)
                    + findSolution("#" + value.substring(1), values);
            case '#' -> {
                if (!values.isEmpty()) {
                    int damagedAmount = values.getFirst();
                    if (damagedAmount <= value.length()
                            && value.chars().limit(damagedAmount).allMatch(c -> c == '#' || c == '?')) {
                        var remainingDamagedAmounts = values.subList(1, values.size());
                        if (damagedAmount == value.length())
                            result = remainingDamagedAmounts.isEmpty() ? 1 : 0;
                        else if (value.charAt(damagedAmount) == '.')
                            result = findSolution(value.substring(damagedAmount + 1), remainingDamagedAmounts);
                        else if (value.charAt(damagedAmount) == '?')
                            result = findSolution("." + value.substring(damagedAmount + 1), remainingDamagedAmounts);
                    }
                }
            }
            default -> throw new RuntimeException("Hoi Sander");
        }

        cache.put(cr, result);
        return result;
    }

    record cacheRecord(String value, List<Integer> values) { }
}
