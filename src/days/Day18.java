package days;

import Utils.OwnReader;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.List;

public class Day18 extends Day {
    List<String> list;

    List<Point> points = new ArrayList<>();

    public Day18() throws FileNotFoundException {
        list = OwnReader.getReaderFromPath("day18.txt").lines().toList();
        long x = 0;
        long y = 0;
        String prev = "";
        long pointX = 0;
        long pointY = 0;
        points.add(new Point(0l,0l));


        for( int i = 0; i < list.size()-1; i++) {
            String str = list.get(i);

            String[] parts = str.split(" ");
            String hexValue = parts[2].replaceAll("[()#]", "");
            int value = Integer.parseInt(hexValue.substring(0,5), 16);
            char ch = hexValue.charAt(5);

            char nextVal = list.get(i+1).split(" ")[2].replaceAll("[()#]", "").charAt(5);
            switch (ch) {
                case '0' -> {

                    x += value;
                    if (nextVal == '3') {
                        pointX = x;
                    } else {
                        pointX = x +1;
                    }
                }
                case '2' -> {
                    x -= value;
                    if (nextVal == '1') {
                        pointX = x + 1;
                    } else {
                        pointX = x;
                    }
                }
                case '3' -> {
                    y -= value;
                    if (nextVal == '2') {
                        pointY = y + 1;
                    } else {
                        pointY = y;
                    }
                }
                case '1' -> {
                    y += value;
                    if (nextVal == '0') {
                        pointY = y;
                    } else {
                        pointY = y + 1;
                    }
                }
            }
            points.add(new Point(pointX,pointY));
        }
    }
    @Override
    public String getAnswerPartOne() throws Exception {
        System.out.println(points.getLast());
        System.out.println("Day18 A= " + calculatePolygonSurfaceArea(points));
        return "" + 0;
    }

    @Override
    public String getAnswerPartTwo() throws Exception {
        return "" + 0;
    }

    public long calculatePolygonSurfaceArea(List<Point> points) {
        int n = points.size();
        long area = 0;
        for (int i = 0; i < n - 1; i++) {
            area += points.get(i).getX() * points.get(i + 1).getY() - points.get(i + 1).getX() * points.get(i).getY();
        }
        area += points.get(n - 1).getX() * points.get(0).getY() - points.get(0).getX() * points.get(n - 1).getY();
        System.out.println(area);
        area = Math.abs(area) / 2;
        return area;
    }

    record Point(Long x, long y) {

        public long getX() {
            return x;
        }

        public long getY() {
            return y;
        }
    }
}

//111131643941706 TO LOW
//111131796624955 // wrong answer
//111131643784319
//111131644099093
//111131644245209
