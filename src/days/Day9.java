package days;

import Utils.OwnReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public class Day9 extends Day {

    @Override
    public String getAnswerPartOne() throws Exception {
        long result = OwnReader.getReaderFromPath("day09.txt").lines().filter(s -> { return !s.isEmpty();}).mapToLong(s -> calcNextVal(s, Day9::backward)).sum();
        System.out.println("Day9 A= " + result);
        return "Day9 A= " + 0;
    }


    public long calcNextVal(String input, BiFunction<List<Long>, String, Long> operation) {
        String[] s1 = input.split(" ");
        List<Long> longInput = Arrays.stream(s1).map(Long::parseLong).toList();
        boolean allZeroDiff = false;
        List<Long> allLastDiffs = new ArrayList<>();
        List<Long> allFirstDiffs = new ArrayList<>();
        allFirstDiffs.add(longInput.getFirst());
        allLastDiffs.add(longInput.getLast());

        while(!longInput.stream().allMatch(Long.valueOf(0L)::equals)) {

            List<Long> newDiffSize = new ArrayList<>();
            for( int i = 0; i < longInput.size()-1; i++) {
                newDiffSize.add(longInput.get(i+1) - longInput.get(i));
            }
            allLastDiffs.add(newDiffSize.getLast());
            allFirstDiffs.add(newDiffSize.getFirst());
            longInput = new ArrayList<>(newDiffSize);
        }
        //return operation.apply()
        return 0;
    }

    @Override
    public String getAnswerPartTwo() throws Exception {

        long result = OwnReader.getReaderFromPath("day09.txt").lines().filter(s -> { return !s.isEmpty();}).mapToLong(s -> calcNextVal(s, Day9::forward)).sum();
        System.out.println("Day9 B= " + result);
        return "Day9 B= " + 0;
    }

    private static long forward(List<Long> sequences, String t) {
        return sequences.stream().collect(Collectors.summingLong(Long::longValue));
    }

    private static long backward(List<Long> sequences, String t) {
        long value = 0;
        for ( int i = sequences.size()-1; i >= 0; i--)
            value= sequences.get(i) - value;

        return value;
    }
}
