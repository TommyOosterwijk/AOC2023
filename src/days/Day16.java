package days;

import Utils.OwnReader;

import java.beans.beancontext.BeanContext;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import static Utils.MapUtils.createMapFromList;
import static Utils.MapUtils.printBoard;

public class Day16 extends Day {
    private final Map<String, Integer> cache = new ConcurrentHashMap<>(4096);
    private final Map<String, Integer> gridWalkedCache = new ConcurrentHashMap<>(4096);

    List<Beam> beams = new ArrayList();

    String[][] grid;

    int maxX;
    int maxY;
    public Day16() throws FileNotFoundException {
        grid = createMapFromList(OwnReader.getReaderFromPath("day16.txt").lines().toList());
        maxY = grid.length;
        maxX = grid[0].length;

        Beam startingBeam = new Beam(-1,0,1);
        beams.add(startingBeam);
        //cache.put(startingBeam.toString(), 1);
        //gridWalkedCache.put(startingBeam.locationString(), 1);
    }

    void beamsMove() {
        while(beams.size() > 0) {
            List<Beam> tempList = new ArrayList<>();

            for(int i = 0; i < beams.size(); i++) {
                Beam temp = beams.get(i);
                Beam secondBeam = new Beam(-1,-1,-1);
                boolean removeBeam = false;
                 switch (temp.direction) {

                     case 0 -> {
                         if( temp.y -1 < 0) {
                             removeBeam = true;
                             break;
                         }

                         temp.y--;
                         String str = grid[temp.y][temp.x];

                         if( str.equals("-")) {
                             temp.direction = 1;
                             secondBeam = new Beam(temp.x, temp.y, 3);

                         } else if( str.equals("/")) {
                             temp.direction = 1;
                         }else if( str.equals("\\")) {
                             temp.direction = 3;
                         }
                     }
                    case 1 -> {
                        if( temp.x +1 >= maxX) {
                            removeBeam = true;
                            break;
                        }

                        temp.x++;
                        String str = grid[temp.y][temp.x];

                        if( str.equals("|")) {
                            temp.direction = 0;
                            secondBeam = new Beam(temp.x, temp.y, 2);

                        } else if( str.equals("/")) {
                            temp.direction = 0;
                        }else if( str.equals("\\")) {
                            temp.direction = 2;
                        }

                    }

                     case 2 -> {
                         if( temp.y +1 >= maxY) {
                             removeBeam = true;
                             break;
                         }

                         temp.y++;
                         String str = grid[temp.y][temp.x];

                         if( str.equals("-")) {
                             temp.direction = 1;
                             secondBeam = new Beam(temp.x, temp.y, 3);

                         } else if( str.equals("/")) {
                             temp.direction = 3;
                         }else if( str.equals("\\")) {
                             temp.direction = 1;
                         }
                     }

                     case 3 -> {
                         if( temp.x -1 < 0) {
                             removeBeam = true;
                             break;
                         }

                         temp.x--;
                         String str = grid[temp.y][temp.x];

                         if( str.equals("|")) {
                             temp.direction = 0;
                             secondBeam = new Beam(temp.x, temp.y, 2);

                         } else if( str.equals("/")) {
                             temp.direction = 2;
                         }else if( str.equals("\\")) {
                             temp.direction = 0;
                         }
                     }
                }

                if(secondBeam.y != -1 && secondBeam.x != -1) {
                    if (!gridWalkedCache.containsKey(temp.locationString())) {
                        gridWalkedCache.put(temp.locationString(), 1);
                    }

                    if(!cache.containsKey(secondBeam.toString())) {
                        cache.put(secondBeam.toString(), 1);
                        tempList.add(secondBeam);
                    }
                }

                if(!cache.containsKey(temp.toString())) {
                    cache.put(temp.toString(), 1);
                } else {
                    //already had a beam with same stats on that spot.
                    removeBeam = true;
                }

                if (!gridWalkedCache.containsKey(temp.locationString())) {
                    gridWalkedCache.put(temp.locationString(), 1);
                }

                if(!removeBeam) {
                    tempList.add(temp);
                }
            }
            beams = tempList;
        }
    }
    @Override
    public String getAnswerPartOne() throws Exception {
        beamsMove();
        System.out.println("Day16 A= " + gridWalkedCache.size());
        return "" + 0;
    }

    void resetBeams() {
        cache.clear();
        gridWalkedCache.clear();
        beams.clear();
    }

    @Override
    public String getAnswerPartTwo() throws Exception {
        long result = 0;
        resetBeams();

        for( int y = 0; y < maxY; y++) {
            for( int x = 0; x < maxX; x++) {
                if(x == 0 ) {
                    Beam startingBeam = new Beam(-1, y,1);
                    beams.add(startingBeam);
                }

                if( x == maxX -1) {
                    Beam startingBeam = new Beam(maxX, y,3);
                    beams.add(startingBeam);
                }
                beamsMove();

                int gridResult = gridWalkedCache.size();

                if(gridResult > result) {
                    result = gridResult;
                }

                resetBeams();

                if ( y == 0) {
                    Beam startingBeam = new Beam(x, -1,2);
                    beams.add(startingBeam);
                }

                if ( y == maxY-1) {
                    Beam startingBeam = new Beam(x, maxY,0);
                    beams.add(startingBeam);
                }

                beamsMove();

                gridResult = gridWalkedCache.size();

                if(gridResult > result) {
                    result = gridResult;
                }
                resetBeams();
            }
        }
        System.out.println("Day16 B= " + result);
        return "" + 0;
    }

    class Beam {
        int x= 0;
        int y = 0;

        //up = 0, right =1, down = 2 and left = 3;
        int direction = 1;

        public Beam(int x, int y, int direction) {
            this.x = x;
            this.y = y;
            this.direction = direction;
        }

        public String toString() {
            return x +"," + y + "," + direction;
        }

        public String locationString() {
            return x +"," + y;
        }
    }

    record cacheBeam(String str, int direction) { }

}



//42971 TO LOW
