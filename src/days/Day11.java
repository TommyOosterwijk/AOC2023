package days;

import Utils.OwnReader;

import java.awt.*;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.*;
import java.util.List;
import java.util.stream.Stream;

public class Day11 extends Day {

    SpaceSpot[][] grid;
    List<Point> galaxy = new ArrayList<>();
    int maxX = 0;
    int maxY = 0;

    public Day11() throws FileNotFoundException {
        var lines = OwnReader.getReaderFromPath("day11.txt").lines().toList();

        maxX = lines.getFirst().length();
        maxY = lines.size();
        grid = new SpaceSpot[maxY][maxX];

        for (int y = 0; y < maxY; y++) {
            String val = lines.get(y);
            for (int x = 0; x < maxX; x++) {
                String s = val.charAt(x) + "";

                if( s.equals("#")) {
                    galaxy.add(new Point(x, y));
                }

                grid[y][x] = new SpaceSpot(s);
            }
        }
    }
    public void prepareStepSize (int size) {
        for( int x = 0; x < maxX; x++) {
            boolean found = false;
            for (int y = 0; y < maxY; y++) {
                if(grid[y][x].value.equals("#")) {
                    found = true;
                }
            }
            if (!found) {
                for (int y = 0; y < maxY; y++) {
                    grid[y][x].xStepWeight= size;
                }
            }
        }
        for( int y = 0; y < maxY; y++) {
            boolean found = false;
            for (int x = 0; x < maxX; x++) {
                if(grid[y][x].value.equals("#")) {
                    found = true;
                }

            }
            if (!found) {
                for (int x = 0; x < maxX; x++) {
                    grid[y][x].yStepWeight=size;
                }
            }
        }
    }

    public long calculateSteps() {
        long stepCounter = 0;
        for( int i = 0; i < galaxy.size()-1; i++) {
            Point startingGalaxy = galaxy.get(i);

            for( int target = i +1; target < galaxy.size(); target++) {
                Point targetGalaxy = galaxy.get(target);

                if ( startingGalaxy.x < targetGalaxy.x) {
                    for (int x = startingGalaxy.x; x < targetGalaxy.x; x++) {
                        stepCounter+= grid[startingGalaxy.y][x].xStepWeight;
                    }
                } else {
                    for (int x = targetGalaxy.x; x < startingGalaxy.x; x++) {
                        stepCounter+= grid[startingGalaxy.y][x].xStepWeight;
                    }
                }

                if ( startingGalaxy.y < targetGalaxy.y) {
                    for (int y = startingGalaxy.y; y < targetGalaxy.y; y++) {
                        stepCounter+= grid[y][targetGalaxy.x].yStepWeight;
                    }
                } else {
                    for (int y = targetGalaxy.y; y < startingGalaxy.y; y++) {
                        stepCounter+= grid[y][targetGalaxy.x].yStepWeight;
                    }
                }
            }
        }
        return stepCounter;
    }
    @Override
    public String getAnswerPartOne() throws Exception {
        prepareStepSize(2);
        System.out.println("Day11 A= " + calculateSteps());
        return "Day11 A= " + 0;
    }

    @Override
    public String getAnswerPartTwo() throws Exception {

        prepareStepSize(1000000);
        System.out.println("Day11 B= " + calculateSteps());
        return "Day11 B= " + 0;
    }

    class SpaceSpot {

        public SpaceSpot (String str){
            this.value = str;
        }
        String value;
        int yStepWeight = 1;
        int xStepWeight = 1;
    }
}
