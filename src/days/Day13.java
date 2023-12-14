package days;

import Utils.MapUtils;
import Utils.OwnReader;

import java.awt.*;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.List;
import java.util.stream.Stream;

public class Day13 extends Day {

    long counter = 0;
    List<List<String>> grids = new ArrayList<>();

    Point impactPoint = new Point(-1, -1)
;
    public Day13() throws FileNotFoundException {
        var lines = OwnReader.getReaderFromPath("day13.txt").lines().toList();
        List<String> reading = new ArrayList<>();
        for (String str : lines) {
            if (str.isBlank() ) {
                grids.add(reading);
                reading = new ArrayList<>();
            } else {
                reading.add(str);
            }
        }
    }

    public String[][] transformGrids(List<String> strGrid) {
        String[][] grid;
        int maxX = strGrid.get(0).length();
        int maxY = strGrid.size();

        grid = new String[maxY][maxX];

        for ( int y = 0; y < maxY; y++) {
            String str = strGrid.get(y);
            for (int x = 0; x < maxX; x++) {
                grid[y][x] = str.charAt(x) + "";
            }
        }
        return grid;
    }

    public long swapCharAndCountMirrorIndex(String[][] grid) {
        long originalResult = countMirrorIndex(grid, -1);
        int maxY = grid.length;
        int maxX = grid[0].length;

        for( int y = 0; y < maxY; y++) {
            for( int x = 0; x < maxX; x++) {
                String temp = grid[y][x];

                grid[y][x] = temp.equals("#") ? "." : "#";

                long newResult = countMirrorIndex(grid, originalResult);

                if(newResult != 0 ) {
                    return newResult;
                } else {
                    grid[y][x] = temp;
                }
            }
        }
        System.out.println("That was not good.");
        return 0;
    }

    public long countMirrorIndex(String[][] grid, long value) {
        long width = 0;
        long height = 0;

        int maxY = grid.length;
        int maxX = grid[0].length;

        for(int x = 0; x < maxX; x++) {
            boolean mirrored = true;
            int leftX = x;
            int rightX = x+1;

            if(leftX < 0 || rightX >= maxX) {
                mirrored = false;
            }

            while(mirrored && leftX >= 0 && rightX < maxX && x != impactPoint.x) {
                for (int y = 0; y < maxY; y++) {
                    if (!(grid[y][leftX].equals(grid[y][rightX]))) {
                        mirrored = false;
                        break;
                    }
                }
                leftX--;
                rightX++;
            }

            if( mirrored && x+1 != value) {
                width = x+1;

                break;
            }
        }

        for(int y = 0; y < maxY; y++) {
            boolean mirrored = true;
            int topY = y;
            int belowY = y+1;

            if(topY < 0 || belowY >= maxY) {
                mirrored = false;
            }

            while(mirrored && topY >= 0 && belowY < maxY && y != impactPoint.y) {
                for (int x = 0; x < maxX; x++) {
                    if (!(grid[belowY][x].equals(grid[topY][x]))) {
                        mirrored = false;
                        break;
                    }
                }
                belowY++;
                topY--;
            }

            if( mirrored && ((y+1) * 100) != value) {
                height = (y+1) * 100;
                break;
            }
        }

        return width + height;
    }
    @Override
    public String getAnswerPartOne() throws Exception {
        long result = 0;

        //add parralel
        result = grids.stream().map(s -> transformGrids(s)).mapToLong(s -> countMirrorIndex(s, -1)).sum();
        System.out.println("Day13 A= " + result);
        return "Day13 A= " + result;
    }

    @Override
    public String getAnswerPartTwo() throws Exception {
        long result = 0;
        result = grids.stream().map(s -> transformGrids(s)).mapToLong(this::swapCharAndCountMirrorIndex).sum();

        System.out.println("Day13 B= " + result);
        return "Day13 B= " + result;
    }
}

//42971 TO LOW
