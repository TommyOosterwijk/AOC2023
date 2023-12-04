package days;

import Utils.OwnReader;

import java.util.HashMap;
import java.util.Map;

public class Day2 extends Day {

    @Override
    public String getAnswerPartOne() throws Exception {
        int partOne = 0;

        final Map<String, Integer> bagSize = Map.of(
                "blue", 14,
                "red", 12,
                "green", 13
        );

        partOne = OwnReader.getReaderFromPath("day02.txt")
                .lines()
                .filter(s -> {
                    String[] rows = s.split(": ")[1].split("; ");
                    for (String row: rows) {
                        String[] values = row.split(", ");

                        for (String value: values) {
                            String[] bag = value.split(" ");
                            if( bagSize.get(bag[1]) < Integer.parseInt(bag[0])) {
                                return false;
                            }
                        }
                    }
                    return true;
                }).mapToInt(s -> {
                    int result = Integer.parseInt(s.split(":")[0].split(" ")[1]);
                    return result;
                }).sum();

        System.out.println("Day2 A= "+ partOne);
        return "Day2 A= "+ partOne;
    }

    @Override
    public String getAnswerPartTwo() throws Exception {

        int partTwo = OwnReader.getReaderFromPath("day02.txt")
                .lines()
                .mapToInt(s -> {
                    Map<String, Integer> bagSize = new HashMap<>(Map.of(
                            "blue", 0,
                            "red", 0,
                            "green", 0
                    ));

                    String[] rows = s.split(": ")[1].split("; ");
                    for (String row: rows) {
                        String[] values = row.split(", ");

                        for (String value: values) {
                            String[] bag = value.split(" ");
                            int bagValue = Integer.parseInt(bag[0]);
                            if(bagSize.get(bag[1]) < bagValue) {
                                bagSize.replace(bag[1], bagValue);
                            }
                        }
                    }
                    return bagSize.get("blue") * bagSize.get("green") * bagSize.get("red");
                }).sum();

        System.out.println("Day2 B= "+ partTwo);

        return "Day2 B= "+ partTwo;
    }
}


