package days;

import Utils.OwnReader;

import javax.sound.sampled.Line;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Day5 extends Day {

    List<Long> seeds;
    List<List<Long>> steps;
    List<List<List<Long>>> stepsGood;

    long lowestValue = -1;
    public Day5() throws FileNotFoundException {
        List lines = OwnReader.getReaderFromPath("day05.txt")
                .lines().toList();

        seeds = Arrays.stream(lines.getFirst().toString().split(": ")[1].split(" ")).map(Long::valueOf).collect(Collectors.toList());

        steps = lines.stream()
                .filter(s -> !(s.toString().contains("map") || s.toString().contains("seeds")))
                .map(s -> {
                    if (s.toString().isEmpty()) {
                        return new ArrayList();
                    }
                    return Arrays.stream(s.toString().split(" ")).map(Long::valueOf).collect(Collectors.toList());
                })
                .toList();

        cleanupList();
    }

    public void cleanupList() {
        stepsGood = new ArrayList<>();
        int indexParent = -1;
        int indexChild = -1;

        for(int i = 0; i < steps.size(); i++) {
            List<Long> l = steps.get(i);

            if(l.isEmpty()) {
                stepsGood.add(new ArrayList<>());
                indexParent++;
                indexChild = -1;
            } else {
                stepsGood.get(indexParent).add(new ArrayList<>());
                indexChild++;

                stepsGood.get(indexParent).get(indexChild).add(l.get(0));
                stepsGood.get(indexParent).get(indexChild).add(l.get(1));
                stepsGood.get(indexParent).get(indexChild).add(l.get(2));
            }
        }
    }

    public void playTheGame(int startingIndex, long value) {
        if(stepsGood.size() <= startingIndex) {
            if (value < lowestValue || lowestValue == -1) {
                lowestValue = value;
            }
            return;
        }
        List<List<Long>> map = stepsGood.get(startingIndex);
        boolean mapped = false;
        for(List<Long> m : map) {
            long startValNew = m.get(0);
            long startRange = m.get(1);
            long maxRange = startRange + m.get(2);

            if ( value >= startRange && value < maxRange) {
                playTheGame(startingIndex+1,  startValNew + (value - startRange));
                mapped = true;
            }
        }

        if (!mapped) {
            playTheGame(startingIndex+1,  value);
        }
    }

    @Override
    public String getAnswerPartOne() throws Exception {
        seeds.stream().forEach(s -> playTheGame(0, s));

        System.out.println("Day5 A= "+ lowestValue);
        return "Day5 A= "+ lowestValue;
    }

    @Override
    public String getAnswerPartTwo() throws Exception {
        lowestValue = -1;
        for(int i = 0; i< seeds.size(); i+=2) {
            long start = seeds.get(i);
            long end = seeds.get(i+1) + start -1;

            for( long s = start; s < end; s++) {
                playTheGame(0, s);
            }
        }
        System.out.println("Day5 B= "+ lowestValue);

        return "Day5 ";
    }
}


