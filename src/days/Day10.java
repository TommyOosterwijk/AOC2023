package days;

import Utils.OwnReader;

import java.awt.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Day10 extends Day {

    int[][] yo = {{-2,0}, {0,-2}, {0,2}, {2,0}};

    int[][] neighbourEmptyCheck = { {-1,-1},{-1,0},{-1,1}, {0,-1}, {0,1}, {+1,-1},{+1,0},{+1,1},};
    Pipe[][] grid;
    String[][] secondGrid;
    Point startingLocation = new Point();
    List<Point> tempPosMarked = new ArrayList();
    List<Point> finalPosMarked = new ArrayList<>();

    int maxY = 0;
    int maxX = 0;
    Map<String, List<Point>> lookup = Map.of(
            "S", Arrays.asList(new Point(-2,-2), new Point(-2,-2)),
            ".", Arrays.asList(new Point(-2,-2), new Point(-2,-2)),
            "|", Arrays.asList(new Point(0, 2), new Point(0,-2)),
            "-", Arrays.asList(new Point(2,0), new Point(-2, 0)),
            "L", Arrays.asList(new Point(0,-2), new Point(2,0)),
            "J", Arrays.asList(new Point(0,-2), new Point(-2, 0)),
            "7", Arrays.asList(new Point(0, +2), new Point(-2, 0)),
            "F", Arrays.asList(new Point(2,0), new Point(0,+2))
    );
    public Day10() throws FileNotFoundException {
        List<String> lines = OwnReader.getReaderFromPath("day10.txt").lines().toList();
        maxY = lines.size() * 2;
        maxX = lines.getFirst().length() * 2;
        grid = new Pipe[maxY][maxX];

        for( int y = 0; y < maxY/2; y++) {
            String row = lines.get(y);
            for( int x = 0; x < maxX/2; x++) {
                String ch = row.charAt(x) + "";

                if(ch.equals("S")) {
                    startingLocation = new Point(x*2, y*2);
                }

                List<Point> neighbours = lookup.get(ch);

                grid[y*2][x*2] = new Pipe(ch, new Point(x*2,y*2), new Point((x*2)+neighbours.get(0).x, (y*2)+neighbours.get(0).y), new Point((x*2)+neighbours.get(1).x, (y*2)+neighbours.get(1).y));
                grid[y*2][(x*2)+1] = new Pipe("*", null, null, null);
                grid[(y*2)+1][x*2] = new Pipe("*", null, null, null);
                grid[(y*2)+1][(x*2)+1] = new Pipe("*", null, null, null);
            }
        }
    }
    @Override
    public String getAnswerPartOne() throws Exception {
        long result = 0;
        for (int i = 0; i < yo.length; i++) {
            long steps = startRoute(new Point(startingLocation.x+ yo[i][0], startingLocation.y + yo[i][1]), startingLocation, 1);
            if(steps > result) {
                result = steps;
            }
        }
        System.out.println("Day10 A= " + result/2);
        return "Day10 A= " + 0;
    }

    @Override
    public String getAnswerPartTwo() throws Exception {
        long result = 0;
        for (int i = 0; i < yo.length; i++) {
            long steps = startRoute(new Point(startingLocation.x+ yo[i][0], startingLocation.y + yo[i][1]), startingLocation, 1);

            if(steps > result) {
                result = steps;
                finalPosMarked = tempPosMarked;
            }
            finalPosMarked.add(startingLocation);
        }
        secondGrid = new String[maxY][maxX];

        for (int y = 0; y < maxY; y++) {
            for (int x = 0; x < maxX; x++) {
                secondGrid[y][x] = ".";
            }
        }

        for (int i = 0; i < finalPosMarked.size(); i++) {
            secondGrid[finalPosMarked.get(i).y][finalPosMarked.get(i).x] = "X";
        }

        boolean run = true;
        while (run) {
            run = false;
            for (int y = 0; y < maxY; y++) {
                for (int x = 0; x < maxX; x++) {
                    if (secondGrid[y][x].equals(".")) {
                        if (y == 0 || y == maxY - 1 || x == 0 || x == maxX - 1) {
                            secondGrid[y][x] = "-";
                            run = true;
                        } else if (secondGrid[y + 1][x].equals("-") || secondGrid[y - 1][x].equals("-") || secondGrid[y][x - 1].equals("-") || secondGrid[y][x + 1].equals("-")) {
                            secondGrid[y][x] = "-";
                            run = true;
                        }
                    }
                }
            }
        }

        long finalResult = 0;
        for( int y = 0; y < maxY; y+=2) {
            for( int x = 0; x < maxX; x+=2) {
                if (secondGrid[y][x].equals(".")) {

                    boolean allEmpty = true;

                    for( int z = 0; z < neighbourEmptyCheck.length; z++) {
                        if(!secondGrid[y + neighbourEmptyCheck[z][0]][x + neighbourEmptyCheck[z][1]].equals(".")) {
                            allEmpty =  false;
                        }
                    }
                    if( allEmpty) {
                        finalResult++;
                    }
                }
            }
        }

        System.out.println("Day10 B= " + finalResult);
        return "Day10 B= " + 0;
    }

    public int startRoute(Point self, Point previous, int stepCounter) {
        boolean run = true;
        while ( run) {
            if ((self.y < 0 || self.y >= maxY) || (self.x < 0 || self.x >= maxX)) {
                return 0;
            }
            Pipe currentPipe = grid[self.y][self.x];
            if (currentPipe.id.equals("S")) {
                return stepCounter;
            }
            if (currentPipe.isConnected(previous)) {
                int extraY = self.y;
                int extraX = self.x;

                if( previous.x > self.x) {
                    extraX += 1;
                } else if ( previous.x < self.x) {
                    extraX-= 1;
                }

                if( previous.y > self.y) {
                    extraY+= 1;
                } else if ( previous.y < self.y) {
                    extraY-=1;
                }

                tempPosMarked.add(new Point(extraX, extraY));
                tempPosMarked.add(self);
                var temp = currentPipe.returnOtherPoint(previous);
                previous = self;
                self = temp;
                stepCounter++;
            } else {
                return 0;
            }
        }
        return 0;
    }

    record Pipe(String id, Point self, Point n1, Point n2) {
        boolean isConnected(Point target){
            return isSameLocation(target, n1) || isSameLocation(target, n2);
        }

        Point returnOtherPoint( Point previous) {
            if (isSameLocation(previous, n2)) {
                return n1;
            } else {
                return n2;
            }
        }
        boolean isSameLocation(Point a, Point b) {
            return a.x == b.x && a.y == b.y;
        }
    }
}
