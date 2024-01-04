package days;

import Utils.MapUtils;
import Utils.OwnReader;

import java.awt.*;
import java.io.FileNotFoundException;

public class Day22 extends Day {

    Point startLocation;

    String[][] grid;

    public Day22() throws FileNotFoundException {
        grid = MapUtils.createMapFromList(OwnReader.getReaderFromPath("day21.txt").lines().toList());
    }

    @Override
    public String getAnswerPartOne() throws Exception {
        return "Day22 A= "+ 0;
    }

    @Override
    public String getAnswerPartTwo() throws Exception {
        return "Day22 B= "+ 0;
    }
}
