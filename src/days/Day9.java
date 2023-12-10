package days;

import Utils.OwnReader;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day9 extends Day {

    @Override
    public String getAnswerPartOne() throws Exception {
        long result = OwnReader.getReaderFromPath("day09.txt").lines().filter(s -> { return !s.isEmpty();}).mapToLong(s -> calcNextVal(s)).sum();
        System.out.println("Day9 A= " + result);
        return "Day9 A= " + 0;
    }

    public long calcNextVal(String input) {
        String[] s1 = input.split(" ");
        List<Long> longInput = Arrays.stream(s1).map(Long::parseLong).toList();
        boolean allZeroDiff = false;
        List<Long> allLastDiffs = new ArrayList<>();
        allLastDiffs.add(longInput.getLast());

        while(!allZeroDiff) {

            allZeroDiff = true;
            List<Long> newDiffSize = new ArrayList<>();

            for( int i = 0; i < longInput.size()-1; i++) {
                long diff = longInput.get(i+1) - longInput.get(i);
                newDiffSize.add(diff);
                if (diff != 0) {
                    allZeroDiff = false;
                }
            }
            allLastDiffs.add(newDiffSize.getLast());
            longInput = new ArrayList<>(newDiffSize);
        }
        return allLastDiffs.stream().collect(Collectors.summingLong(Long::longValue));
    }


    @Override
    public String getAnswerPartTwo() throws Exception {

        long result = OwnReader.getReaderFromPath("day09.txt").lines().filter(s -> { return !s.isEmpty();}).mapToLong(s -> calcPreviousVal(s)).sum();
        System.out.println("Day9 B= " + result);
        return "Day9 B= " + 0;
    }

    public long calcPreviousVal(String input) {
        String[] s1 = input.split(" ");
        List<Long> longInput = Arrays.stream(s1).map(Long::parseLong).toList();
        boolean allZeroDiff = false;
        List<Long> allFirstDiffs = new ArrayList<>();
        allFirstDiffs.add(longInput.getFirst());

        while(!allZeroDiff) {

            allZeroDiff = true;
            List<Long> newDiffSize = new ArrayList<>();

            for( int i = 0; i < longInput.size()-1; i++) {
                long diff = longInput.get(i+1) - longInput.get(i);
                newDiffSize.add(diff);
                if (diff != 0) {
                    allZeroDiff = false;
                }
            }
            allFirstDiffs.add(newDiffSize.getFirst());
            longInput = new ArrayList<>(newDiffSize);
        }
        long value = 0;
        for ( int i = allFirstDiffs.size()-1; i >= 0; i--) {
            value= allFirstDiffs.get(i) - value;
        }
        return value;
    }
}



