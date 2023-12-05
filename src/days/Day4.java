package days;

import Utils.OwnReader;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;

public class Day4 extends Day {

    @Override
    public String getAnswerPartOne() throws Exception {
        int partOne = OwnReader.getReaderFromPath("day04.txt")
                .lines()
                .mapToInt(s -> {
                    int winningNumberPoints = 0;
                    String[] cards = s.split(": ")[1].split(" \\| ");

                    String[] winningNumbers = Arrays.stream(cards[0].split(" ")).filter(Predicate.not(String::isEmpty)).toArray(String[]::new);
                    String[] lotNumbers = Arrays.stream(cards[1].split(" ")).filter(Predicate.not(String::isEmpty)).toArray(String[]::new);

                    for (String number: winningNumbers) {

                        if (Arrays.asList(lotNumbers).contains(number)) {
                            if (winningNumberPoints == 0 ) {
                                winningNumberPoints = 1;
                            } else {
                                winningNumberPoints*=2;
                            }
                        }
                    }
                    return winningNumberPoints;
                }).sum();

        System.out.println("Day4 A= "+ partOne);
        return "Day4 A= "+ partOne;
    }

    @Override
    public String getAnswerPartTwo() throws Exception {
        int amountOfWinningNumbers[] = OwnReader.getReaderFromPath("day04.txt")
                .lines()
                .mapToInt(s -> {
                    int winningNumberPoints = 0;
                    String[] cards = s.split(": ")[1].split(" \\| ");

                    String[] winningNumbers = Arrays.stream(cards[0].split(" ")).filter(Predicate.not(String::isEmpty)).toArray(String[]::new);
                    String[] lotNumbers = Arrays.stream(cards[1].split(" ")).filter(Predicate.not(String::isEmpty)).toArray(String[]::new);

                    for (String number: winningNumbers) {

                        if (Arrays.asList(lotNumbers).contains(number)) {
                            winningNumberPoints++;
                        }
                    }
                    return winningNumberPoints;
                }).toArray();


        int[] indexMultiplier = new int[amountOfWinningNumbers.length];

        Arrays.fill(indexMultiplier, 1);

        for( int i = 0; i < amountOfWinningNumbers.length; i++) {
            for( int x = 1; x <= amountOfWinningNumbers[i]; x++) {
                indexMultiplier[i+x]+= indexMultiplier[i];
            }
        }
        System.out.println("Day4 B= "+ Arrays.stream(indexMultiplier).sum());

        return "Day4 ";
    }
}


