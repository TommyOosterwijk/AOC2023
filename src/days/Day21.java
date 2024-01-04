package days;

import Utils.MapUtils;
import Utils.OwnReader;

import java.awt.*;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.List;

public class Day21 extends Day {

    Point startLocation;

    String[][] grid;

    List<String> stepCounter = new ArrayList<>();


    int maxX;
    int maxY;
    public Day21() throws FileNotFoundException {
        grid = MapUtils.createMapFromList(OwnReader.getReaderFromPath("day21.txt").lines().toList());
        maxY = grid.length;
        maxX = grid[0].length;

        for(int y = 0; y < maxY; y++) {
            for(int x = 0; x < maxX; x++) {
                if( grid[y][x].equals("S")) {
                    startLocation = new Point(x,y);
                    grid[y][x] = ".";
                }
            }
        }
    }

    public long step(Point p, int stepsLeft) {
        long result = 0;
        Point nextPoint;

        if(stepsLeft == 0) {
            return 1;
        }

        if( (p.y + 1) < maxY && grid[p.y+1][p.x].equals(".")) {
            nextPoint = new Point(p.x, p.y+1);
            if (!stepCounter.contains(nextPoint.x + "-" + nextPoint.y + "-" + (stepsLeft-1))) {
                stepCounter.add(nextPoint.x + "-" + nextPoint.y + "-" + (stepsLeft-1));
                result += step(nextPoint, stepsLeft-1);
            }

        }
        if( (p.y - 1) >= 0 && grid[p.y-1][p.x].equals(".")) {
            nextPoint = new Point(p.x, p.y-1);
            if (!stepCounter.contains(nextPoint.x + "-" + nextPoint.y + "-" + (stepsLeft-1))) {
                stepCounter.add(nextPoint.x + "-" + nextPoint.y + "-" + (stepsLeft-1));
                result += step(nextPoint, stepsLeft-1);
            }
        }

        if( (p.x + 1) < maxX && grid[p.y][p.x+1].equals(".")) {
            nextPoint = new Point(p.x + 1, p.y);
            if (!stepCounter.contains(nextPoint.x + "-" + nextPoint.y + "-" + (stepsLeft-1))) {
                stepCounter.add(nextPoint.x + "-" + nextPoint.y + "-" + (stepsLeft-1));
                result += step(nextPoint, stepsLeft-1);
            }
        }
        if( (p.x - 1) >= 0 && grid[p.y][p.x-1].equals(".")) {
            nextPoint = new Point(p.x -1, p.y);
            if (!stepCounter.contains(nextPoint.x + "-" + nextPoint.y + "-" + (stepsLeft-1))) {
                stepCounter.add(nextPoint.x + "-" + nextPoint.y + "-" + (stepsLeft-1));
                result+= step(nextPoint, stepsLeft-1);
            }
        }
        return result;
    }
    @Override
    public String getAnswerPartOne() throws Exception {
        System.out.println("Day21 A= "+ step(startLocation, 64));
        return "Day21 A= "+ 0;
    }

    @Override
    public String getAnswerPartTwo() throws Exception {
        return "Day20 B= "+ 0;
    }
}
