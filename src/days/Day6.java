package days;

import Utils.OwnReader;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class Day6 extends Day {

    @Override
    public String getAnswerPartOne() throws Exception {
        long partOne = 1;

        List<String> lines = OwnReader.getReaderFromPath("day06.txt").lines().toList();
        List<Long> time = Arrays.stream(lines.get(0).split(":")[1].split(" ")).filter(s -> !s.isEmpty()).map(s -> Long.parseLong(s)).toList();
        List<Long> targetDistance = Arrays.stream(lines.get(1).split(":")[1].split(" ")).filter(s -> !s.isEmpty()).map(s -> Long.parseLong(s)).toList();


        for( int i = 0; i < time.size(); i++) {
            long targetTime = time.get(i);
            long targetDist = targetDistance.get(i);

            long targetCounter = 0;
            for (int x = 1; x <= targetTime; x++) {

                if( (x * (targetTime-x)) > targetDist) {
                    targetCounter++;
                }

            }

            partOne *= targetCounter;
        }

        System.out.println("Day6 B= "+ partOne);

        return "Day6 A= "+ partOne;
    }

    @Override
    public String getAnswerPartTwo() throws Exception {


        System.out.println("Day6 B= "+ 0);

        return "Day6 B= "+ 0;
    }
}


