package days;

import Utils.MapUtils;
import Utils.OwnReader;

import java.awt.*;
import java.beans.PropertyEditorSupport;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

import static Utils.MapUtils.createMapFromList;
import static Utils.MapUtils.printBoard;

public class Day14 extends Day {

    int maxY = 0;
    int maxX = 0;
    String[][] grid;

    private final Map<cacheRecord, Long> cache = new ConcurrentHashMap<>(4096);



    public Day14() throws FileNotFoundException {
        var lines = OwnReader.getReaderFromPath("day14.txt").lines().toList();
        grid = createMapFromList(lines);
        maxY = grid.length;
        maxX = grid[0].length;
    }

    public void flipGridNorth() {
        for(int x = 0; x < maxX; x++) {
            int targetY = 0;

            for (int y = 0; y < maxY; y++) {

                if(grid[y][x].equals("O")  && targetY == y) {
                    targetY= y+1;
                }
                if(grid[y][x].equals("#")) {
                    targetY = y+1;
                }
                if (grid[y][x].equals("O") && targetY <= y) {
                    grid[y][x] = ".";
                    grid[targetY][x] = "O";
                    targetY++;
                }
            }
        }
    }

    public void flipGridEast() {
        for (int y = 0; y < maxY; y++) {
            int targetX = maxX-1;

            for(int x = maxX-1; x >= 0; x--) {
                if(grid[y][x].equals("O")  && targetX == x) {
                    targetX= x-1;
                }
                if(grid[y][x].equals("#")) {
                    targetX = x-1;
                }
                if (grid[y][x].equals("O") && targetX >= x) {
                    grid[y][x] = ".";
                    grid[y][targetX] = "O";
                    targetX--;
                }
            }
        }
    }

    public void flipGridSouth() {
        for(int x = 0; x < maxX; x++) {
            int targetY = maxY-1;

            for (int y = maxY-1; y >= 0; y--) {

                if(grid[y][x].equals("O")  && targetY == y) {
                    targetY= y-1;
                }
                if(grid[y][x].equals("#")) {
                    targetY = y-1;
                }
                if (grid[y][x].equals("O") && targetY >= y) {
                    grid[y][x] = ".";
                    grid[targetY][x] = "O";
                    targetY--;
                }
            }
        }
    }

    public void flipGridWest() {
        for (int y = 0; y < maxY; y++) {
            int targetX = 0;

            for(int x = 0; x < maxX; x++) {
                if(grid[y][x].equals("O")  && targetX == x) {
                    targetX= x+1;
                }
                if(grid[y][x].equals("#")) {
                    targetX = x+1;
                }
                if (grid[y][x].equals("O") && targetX <= x) {
                    grid[y][x] = ".";
                    grid[y][targetX] = "O";
                    targetX++;
                }
            }
        }
    }

    public long calculateGrid() {
        long result = 0;
        for(int y = 0; y < maxY; y++) {
            for(int x = 0; x < maxX; x++) {
                if(grid[y][x].equals("O")) {
                    result+= maxY - y;
                }
            }
        }
        return result;
    }

    public String makeGridInString() {
        return Arrays.deepToString(grid);
    }
    @Override
    public String getAnswerPartOne() throws Exception {
        long result = 0;
        flipGridNorth();
        System.out.println("Day14 A= " + calculateGrid());
        return "Day14 A= " + result;
    }


    @Override
    public String getAnswerPartTwo() throws Exception {
        //Keep flipping, using output from answer A.
        flipGridWest();
        flipGridSouth();
        flipGridEast();

        cache.put(new cacheRecord(makeGridInString(), calculateGrid()), 1l);

        long maxI = 1000000000;
        for(long i = 2; i <= maxI; i++) {
            flipGridNorth();
            flipGridWest();
            flipGridSouth();
            flipGridEast();

            cacheRecord temp = new cacheRecord(makeGridInString(), calculateGrid());
            if(cache.containsKey(temp)) {

                long value = cache.get(temp);
                long distance = i - value;
                long remainingSteps = ((maxI - i) % distance);
                if ( remainingSteps == 0) {
                    break;
                }
            } else {
                cache.put(temp, i);
            }
        }

        System.out.println("Day14 B= " + calculateGrid());
        return "Day14 B= " + calculateGrid();
    }

    record cacheRecord(String str, long value) { }

}



//42971 TO LOW
