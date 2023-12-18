package days;

import Utils.OwnReader;

import java.awt.*;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import static Utils.MapUtils.*;

public class Day17 extends Day {
    private final Map<String, Integer> cache = new ConcurrentHashMap<>(4096);
    int[][] grid;

    List<Crucible> crucibles = new ArrayList();


    int maxX;
    int maxY;

    //Top, right, down and left
    int[][] neighbours = {{-1,0},{0,1},{1,0}, {0,-1}};
    public Day17() throws FileNotFoundException {
        grid = createMapFromListToInteger(OwnReader.getReaderFromPath("day17.txt").lines().toList());
        maxY = grid.length;
        maxX = grid[0].length;
    }

    public long findSmallestHeatLoss() {
        long result = 0;
        boolean keepGoing = true;
        while(keepGoing) {
            Crucible c = crucibles.removeFirst();
            //System.out.println("Target crucible " + c.y + "," + c.x + " and heatLoss " + c.heatLoss + " and counter =" + c.directionCounter);


            for( int i = 0; i < 4; i++) {
                int targetDirectionCounter = c.directionCounter;
                //System.out.println(i + " - " + targetDirectionCounter);

                boolean add = false;
                int[] neighbour = neighbours[i];
                int newY = c.y + (neighbour[0]);
                int newX = c.x + neighbour[1];

                if(newX >= 0 && newX < maxX && newY >= 0 && newY < maxY) {
                    if( i == c.direction) {
                        if( c.directionCounter < c.maxDistance) {
                            targetDirectionCounter++;
                            add = true;
                        }
                    } else {
                        if(c.directionCounter >= c.minDistance) {
                            targetDirectionCounter = 1;
                            add = true;
                        }
                    }
                }

                if( c.directionCounter == 1) {


                    int longY = c.y + (neighbour[0]*(c.minDistance-1));
                    int longX = c.x + (neighbour[1]*(c.minDistance-1));

                    if(longX < 0 || longX >= maxX || longY < 0 || longY >= maxY) {
                        add = false;
                    }
                }

                if (add && !(c.prevX == newX && c.prevY == newY)) {
                    Crucible c_temp = new Crucible(newY, newX, i, targetDirectionCounter, c.heatLoss + grid[newY][newX], c.y, c.x, c.minDistance, c.maxDistance);

                    if (newX  == maxX -1 && newY == maxY-1) {
                        return c_temp.heatLoss;
                    }
                    if(!cache.containsKey(c_temp.toString())) {
                        cache.put(c_temp.toString(), c_temp.heatLoss);
                        crucibles.add(c_temp);
                        Collections.sort(crucibles, Comparator.comparingInt((Crucible b) -> b.heatLoss));
                    } else {
                        if( cache.get(c_temp.toString()) > c_temp.heatLoss) {
                        }
                    }
                }
            }
        }
        return result;
    }

    @Override
    public String getAnswerPartOne() throws Exception {
        crucibles.add(new Crucible(0,0, 1, 0, 0, 0, -1, 1, 3));
        System.out.println("Day17 A= " + findSmallestHeatLoss());
        return "" + 0;
    }

    @Override
    public String getAnswerPartTwo() throws Exception {
        cache.clear();
        crucibles.clear();

        crucibles.add(new Crucible(0,0, 1, 1, 0, 0, -1, 4, 10));
        crucibles.add(new Crucible(0,0, 2, 1, 0, 0, -1, 4, 10));


        System.out.println("Day17 B= " + findSmallestHeatLoss());
        return "" + 0;
    }

    class Crucible {
        int x= 0;
        int y = 0;

        //up = 0, right =1, down = 2 and left = 3;
        int direction = 1;

        int directionCounter = 0;

        int heatLoss= 0;

        int prevX = 0;
        int prevY = 0;

        int minDistance = 0;

        int maxDistance = 0;

        public Crucible( int y, int x, int direction, int directionCounter, int heatLoss, int prevY, int prevX, int minDistance, int maxDistance) {
            this.y = y;
            this.x = x;
            this.direction = direction;
            this.directionCounter = directionCounter;
            this.heatLoss = heatLoss;
            this.prevY = prevY;
            this.prevX = prevX;
            this.minDistance = minDistance;
            this.maxDistance = maxDistance;
        }

        public String toString() {
            return y +"," + x + "," + directionCounter + "," + direction;
        }

        public String fullString() {
            return y +"," + x + " dir = " + direction + " with steps: " + directionCounter + " and heatLoss " + heatLoss;
        }
    }
}



//42971 TO LOW
