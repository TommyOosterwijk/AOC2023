package days;

import Utils.MapUtils;
import Utils.OwnReader;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyEditorSupport;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

import static Utils.MapUtils.createMapFromList;
import static Utils.MapUtils.printBoard;

public class Day15 extends Day {
    private final Map<String, Integer> cache = new ConcurrentHashMap<>(4096);

    List<String> sequence;
    Map<String, Integer>[] box = new Map[256];
    List<String>[] lookUp = new ArrayList[256];

    public Day15() throws FileNotFoundException {
        sequence = Arrays.stream(OwnReader.getReaderFromPath("day15.txt").lines().toList().getFirst().split(",")).toList();
        for(int  i = 0; i < box.length; i++) {
            box[i] = new HashMap<String, Integer>();
            lookUp[i] = new ArrayList<>();
        }
    }

    long algorithmStep(String str ) {
        long result = 0;
        for(char ch: str.toCharArray()) {
            long value = (long) ch;
            result+= value;
            result *= 17;
            result = result % 256;
        }
        return result;
    }

    void hashMap(String str) {

        String[] split = str.split("[=-]");
        int index;
        String lens = split[0];

        if (cache.containsKey(lens)) {
            index = cache.get(lens);
        } else {
            index = (int)algorithmStep(lens);
            cache.put(lens, index);
        }

        if (split.length > 1) {
            int value = Integer.parseInt(split[1]);
            if (box[index].containsKey(lens)) {
                box[index].replace(lens, value);
            } else {
                box[index].put(lens, value);
                lookUp[index].addLast(lens);
            }
        } else {
            box[index].remove(lens);
            lookUp[index].remove(lens);
        }
    }

    long calculateHashMap() {
        long result = 0;

        //System.out.println("Starting");
        for(int i = 0; i < box.length; i++) {
            //System.out.println("Here?");
            for( int i2 = 0; i2 < lookUp[i].size(); i2++) {
                String target = lookUp[i].get(i2);
                //System.out.println("Finding target " + target);
                //System.out.println(box[i]);
                int value = box[i].get(lookUp[i].get(i2));
                //System.out.println(value);
                result += value * (i+1) * (i2+1);
                //System.out.println();
            }
        }

System.out.println("Returning result = " + result);
        return result;
    }
    @Override
    public String getAnswerPartOne() throws Exception {
        long result = sequence.stream().mapToLong(this::algorithmStep).sum();
        System.out.println("Day15 A= " + result);
        return "Day15 A= " + result;
    }


    @Override
    public String getAnswerPartTwo() throws Exception {
        for( String str : sequence) {
            hashMap(str);
        }
        long result = calculateHashMap();
        System.out.println("Day15 B= " + result);
        return "Day15 B= " + result;
    }

    record cacheRecord(String str, long value) { }

}



//42971 TO LOW
