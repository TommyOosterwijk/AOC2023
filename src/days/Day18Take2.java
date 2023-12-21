package days;

import Utils.OwnReader;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.List;

public class Day18Take2 extends Day {
    List<String> list;

    List<Point> points = new ArrayList<>();

    long boundary = 0;

    public void calcPointsB() {
        long x = 0;
        long y = 0;
        for( int i = 0; i < list.size(); i++) {
            String str = list.get(i);

            String[] parts = str.split(" ");
            String hexValue = parts[2].replaceAll("[()#]", "");
            int value = Integer.parseInt(hexValue.substring(0,5), 16);
            char ch = hexValue.charAt(5);
            boundary += value;
            switch (ch) {
                case '0' -> x += value;
                case '2' -> x -= value;
                case '1' -> y += value;
                case '3' -> y -= value;
            }
            points.add(new Point(x,y));
        }
    }

    public void calcPointsA() {
        long x = 0;
        long y = 0;
        for( int i = 0; i < list.size(); i++) {
            String str = list.get(i);

            String[] parts = str.split(" ");
            int value = Integer.parseInt(parts[1]);
            String dir = parts[0];
            boundary += value;
            switch (dir) {
                case "R" -> x += value;
                case "L" -> x -= value;
                case "D" -> y += value;
                case "U" -> y -= value;
            }
            points.add(new Point(x,y));
        }
    }

    @Override
    public String getAnswerPartOne() throws Exception {
        list = OwnReader.getReaderFromPath("day18.txt").lines().toList();

        calcPointsA();
        long area = calculatePolygonSurfaceArea(points);
        System.out.println("Day18 A= " + (area + (boundary/2) +1));
        return "" + 0;
    }

    @Override
    public String getAnswerPartTwo() throws Exception {
        list = OwnReader.getReaderFromPath("day18.txt").lines().toList();
        points.clear();
        boundary = 0;
        calcPointsB();
        long area = calculatePolygonSurfaceArea(points);
        System.out.println("Day18 B= " + (area + (boundary/2) +1));
        return "" + 0;
    }

    public long calculatePolygonSurfaceArea(List<Point> points) {
        int n = points.size();
        long area = 0;
        for (int i = 0; i < n - 1; i++) {
            area += points.get(i).getX() * points.get(i + 1).getY() - points.get(i + 1).getX() * points.get(i).getY();
        }
        area += points.get(n - 1).getX() * points.get(0).getY() - points.get(0).getX() * points.get(n - 1).getY();
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
