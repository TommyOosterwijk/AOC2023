package days;

import Utils.MapUtils;
import Utils.OwnReader;

import java.util.ArrayList;
import java.util.List;

public class Day3 extends Day {
    String[][] board = null;
    int[][] neighbours =
            {
                    {-1,-1},{-1,0}, {-1,1},
                    {0,-1}, {0,1},
                    {1,-1},{1,0}, {1,1}
            };

    @Override
    public String getAnswerPartOne() throws Exception {
        long result = 0;
        board = MapUtils.createMapFromList(OwnReader.getReaderFromPath("day03.txt")
                .lines().toList());

        for( int y = 0; y < board.length; y++) {
            boolean symbolFound = false;
            String number = "";

            for( int x = 0; x < board[0].length; x++) {
                if (isPositionAInteger(y, x)) {
                    number+= board[y][x];
                    if( !symbolFound ) {

                        for( int neighbour = 0; neighbour < neighbours.length; neighbour++) {

                            if (isPositionASpecialChar(neighbours[neighbour][0]+y, neighbours[neighbour][1]+x)) {
                                symbolFound = true;
                            }

                        }
                    }
                } else {
                    if (!number.isEmpty()) {
                        if( symbolFound) {
                            result += Integer.parseInt(number);
                        }
                        number="";
                    }
                    symbolFound = false;
                }
            }
            if (!number.isEmpty()) {
                if (symbolFound) {
                    result += Integer.parseInt(number);
                }
            }
        }
        System.out.println("Day3 A= "+ result);
        return "Day3 A= "+ result;
    }

    public boolean isPositionAInteger(int y, int x) {
        if( y < 0 || y >= board.length || x < 0 || x >= board[0].length) {
            return false;
        }
        try {
            Integer.parseInt(board[y][x]);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public boolean isPositionASpecialChar(int y, int x) {
        if( y < 0 || y >= board.length || x < 0 || x >= board[0].length) {
            return false;
        }
        try {
            Integer.parseInt(board[y][x]);
        } catch (NumberFormatException nfe) {
            if (board[y][x].equals(".")) {
                return false;
            }
            return true;
        }
        return false;
    }



    @Override
    public String getAnswerPartTwo() throws Exception {

        long result = 0;
        board = MapUtils.createMapFromList(OwnReader.getReaderFromPath("day03.txt")
                .lines().toList());

        for( int y = 0; y < board.length; y++) {
            for( int x = 0; x < board[0].length; x++) {

                List<Integer> yIndex = new ArrayList<>();
                List<Integer> xIndex = new ArrayList<>();

                int numbersCounter = 0;
                if( board[y][x].equals("*")) {

                    if(isPositionAInteger(y-1,x-1)) {
                        numbersCounter++;
                        yIndex.add(y-1);
                        xIndex.add(x-1);
                        if (!isPositionAInteger(y-1,x) && isPositionAInteger(y-1,x+1)) {
                            numbersCounter++;
                            yIndex.add(y-1);
                            xIndex.add(x+1);
                        }
                    } else if(isPositionAInteger(y-1,x+1)) {
                        numbersCounter++;
                        yIndex.add(y-1);
                        xIndex.add(x+1);
                    } else if(isPositionAInteger(y-1,x)) {
                        numbersCounter++;
                        yIndex.add(y-1);
                        xIndex.add(x);
                    }

                    if(isPositionAInteger(y,x-1)) {
                        numbersCounter++;
                        yIndex.add(y);
                        xIndex.add(x-1);
                    }

                    if(isPositionAInteger(y,x+1)) {
                        numbersCounter++;
                        yIndex.add(y);
                        xIndex.add(x+1);
                    }

                    if(isPositionAInteger(y+1,x-1)) {
                        numbersCounter++;
                        yIndex.add(y+1);
                        xIndex.add(x-1);
                        if (!isPositionAInteger(y+1,x) && isPositionAInteger(y+1,x+1)) {
                            numbersCounter++;
                            yIndex.add(y+1);
                            xIndex.add(x+1);
                        }
                    } else if(isPositionAInteger(y+1,x+1)) {
                        numbersCounter++;
                        yIndex.add(y+1);
                        xIndex.add(x+1);
                    } else if(isPositionAInteger(y+1,x)) {
                        numbersCounter++;
                        yIndex.add(y+1);
                        xIndex.add(x);
                    }



                    if (numbersCounter == 2) {

                        long tempResult = 1;

                        for(int i = 0; i < 2; i++) {
                            int temp_y = yIndex.get(i);
                            int temp_x = xIndex.get(i);
                            String value = "";
                            while(isPositionAInteger(temp_y, temp_x)) {
                                value = board[temp_y][temp_x] + value;
                                temp_x--;
                            }
                            temp_x = xIndex.get(i) +1;
                            while(isPositionAInteger(temp_y, temp_x)) {
                                value += board[temp_y][temp_x];
                                temp_x++;
                            }

                            tempResult*=Long.parseLong(value);
                        }
                        result+= tempResult;
                    }
                }
            }
        }

        System.out.println("Day3 B= "+ result);

        return "Day3 B= "+ result;
    }
}


