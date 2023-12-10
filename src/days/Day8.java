package days;

import Utils.OwnReader;

import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Stream;

public class Day8 extends Day {

    List<Element> map = new ArrayList();
    int index = 0;
    String instructions = "";
    public Day8() throws FileNotFoundException {
        var lines = OwnReader.getReaderFromPath("day08.txt").lines().toList();
        instructions = lines.get(0);

        for( int i = 2; i < lines.size(); i++) {
            Element tempElement = new Element(lines.get(i), i-2);

            map.add( i-2, tempElement);
        }

        for ( Element element : map) {
            for( int i = 0; i < map.size(); i++) {
                Element temp = map.get(i);

                if( element.leftID.equals(temp.ownID)) {
                    element.element_left = i;
                }

                if( element.rightID.equals(temp.ownID)) {
                    element.element_right = i;
                }

                if ( temp.ownID.equals("AAA")) {
                    index = i;
                }
            }
        }
    }

    @Override
    public String getAnswerPartOne() throws Exception {
        int stepCounter = 0;
        int c2 = 0;
        while(c2 < 5) {
            boolean zFound = false;
            while (!zFound) {
                for (int i = 0; i < instructions.length(); i++) {
                    Element element = map.get(index);
                    char ch = instructions.charAt(i);

                    if (ch == 'L') {
                        index = element.element_left;
                    } else {
                        index = element.element_right;
                    }
                    stepCounter++;
                    if (map.get(index).ownID.equals("ZZZ")) {
                        zFound = true;
                        break;
                    }
                }
            }
            c2++;
        }

        System.out.println("Day8 A= " + stepCounter);
        return "Day8 A= " + stepCounter;
    }

    @Override
    public String getAnswerPartTwo() throws Exception {

        int stepCounter = 0;
        List<Integer> indexes = new ArrayList<>();

        for( Integer i = 0; i < map.size(); i++) {
            if(map.get(i).ownID.charAt(2) == 'A') {
                indexes.add(i);
            }
        }
        System.out.println(indexes);

        for( int i2 = 0; i2 < indexes.size(); i2++) {
            stepCounter = 0;
            index = indexes.get(i2);
            boolean zFound = false;
            while(!zFound) {
                for (int i = 0; i < instructions.length(); i++) {

                    Element element = map.get(index);
                    if (element.ownID.charAt(2) == 'Z') {
                        zFound = true;
                        break;
                    }
                    char ch = instructions.charAt(i);

                    if (ch == 'L') {
                        index = element.element_left;
                    } else {
                        index = element.element_right;
                    }

                    stepCounter++;
                }
            }
            indexes.set(i2, stepCounter);
        }

        long r = 1;

        for(int i = 0; i < indexes.size(); i++) {
            r = lcm(r, indexes.get(i));
        }

        Hand hand = new Hand("test", "tesst");
        System.out.println("Day8 B= " + hand);
        return "Day8 B= " + r;
    }

    public static long lcm(long number1, long number2) {
        if (number1 == 0 || number2 == 0) {
            return 0;
        }
        long absNumber1 = Math.abs(number1);
        long absNumber2 = Math.abs(number2);
        long absHigherNumber = Math.max(absNumber1, absNumber2);
        long absLowerNumber = Math.min(absNumber1, absNumber2);
        long lcm = absHigherNumber;
        while (lcm % absLowerNumber != 0) {
            lcm += absHigherNumber;
        }
        return lcm;
    }

    record Hand(String cards, String type) { }


    class Element {

        String ownID = "";
        int ownIndex = -1;

        String leftID = "";
        int element_left = -1;

        String rightID = "";
        int element_right = -1;

        Element(String element, int id) {
            ownIndex = id;
            String[] parts = element.split(" = ");
            ownID = parts[0];
            String part1 = parts[1].replaceAll("[\\(\\)]", "");
            String[] indexes = part1.split(", ");
            leftID = indexes[0];
            rightID = indexes[1];
        }

        @Override
        public String toString() {
            return ownIndex + " ; " + ownID + " leftSide ID: " + element_left + " and rightSide ID: " + element_right;
        }
    }
}


