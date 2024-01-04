package days;

import Utils.OwnReader;

import java.math.BigInteger;
import java.util.*;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class Day19 extends Day {

    BigInteger finalResultB = new BigInteger("0");

    private final Map<String, String> rules = new ConcurrentHashMap<>(4096);
    List<String> values = new ArrayList<>();

    List<int[][]> acceptedRules = new ArrayList<>();
    public Day19() throws Exception {
        List<String> list = OwnReader.getReaderFromPath("day19.txt").lines().toList();
        boolean input = false;

        for( String str: list) {
            if( input) {
                values.add(str.replaceAll("[\\{\\}]", ""));
            } else {
                if(str.isBlank()) {
                    input = true;
                } else {
                    str = str.replace("}", "");
                    String[] parts = str.split("\\{");
                    String name = parts[0];
                    rules.put(name,parts[1]);
                }
            }
        }
    }


    public boolean isValidRule(long[] numbers) {
        String target = "in";
        while(true) {

            String[] rule = rules.get(target).split(",");

            for(String r : rule) {

                if (!r.contains(":")) {
                    target = r;
                    break;
                } else {
                    int targetV = 0;

                    switch (r.charAt(0)) {
                        case 'x' -> targetV = 0;
                        case 'm' -> targetV = 1;
                        case 'a' -> targetV = 2;
                        case 's' -> targetV = 3;
                    }

                    String[] rSplit = r.split(":");
                    if (r.contains("<")) {
                        if (numbers[targetV] < Integer.parseInt(rSplit[0].substring(2))) {
                            target = rSplit[1];
                            break;
                        }
                    } else {
                        if (numbers[targetV] > Integer.parseInt(rSplit[0].substring(2))) {
                            target = rSplit[1];
                            break;
                        }
                    }
                }
            }

            if (target.equals("A")) {
                return true;
            }
            if (target.equals("R")) {
                return false;
            }
        }
    }

    @Override
    public String getAnswerPartOne() throws Exception {
        long sum = 0;
        for(int i = 0; i < values.size(); i++) {

            String s = values.get(i);
            var numbers = Arrays.stream(s.split(",")).mapToLong( s1 -> Long.parseLong(s1.substring(2))).toArray();
            if(isValidRule(numbers)) {
                sum+= Arrays.stream(numbers).sum();
            }
        }

        System.out.println("Day19 A= " + sum);
        return "" + 0;
    }

    @Override
    public String getAnswerPartTwo() throws Exception {
        int[][] valueArray = new int[4][2];

        valueArray[0][0] = 0;
        valueArray[1][0] = 0;
        valueArray[2][0] = 0;
        valueArray[3][0] = 0;

        valueArray[0][1] = 4000;
        valueArray[1][1] = 4000;
        valueArray[2][1] = 4000;
        valueArray[3][1] = 4000;

        allRules("in", valueArray);
        countRules();
        System.out.println("Day19 B= " + finalResultB);
        return "" + 0;
    }

    public void countRules() {
        for(int[][] activeValueRange : acceptedRules) {
            int rangeX = activeValueRange[0][1] - activeValueRange[0][0];
            int rangeM = activeValueRange[1][1] - activeValueRange[1][0];
            int rangeA = activeValueRange[2][1] - activeValueRange[2][0];
            int rangeS = activeValueRange[3][1] - activeValueRange[3][0];
            BigInteger counter = new BigInteger("1");

            counter = counter.multiply( BigInteger.valueOf(rangeX));
            counter = counter.multiply( BigInteger.valueOf(rangeM));
            counter = counter.multiply( BigInteger.valueOf(rangeA));
            counter = counter.multiply( BigInteger.valueOf(rangeS));
            finalResultB = finalResultB.add(counter);
        }
    }

    public void allRules(String target, int[][] activeValueRange) {

        int[][] tempArray = new int[4][2];
        for( int y = 0; y < 4; y++) {
            tempArray[y][0] = activeValueRange[y][0];
            tempArray[y][1] = activeValueRange[y][1];
        }
        if(target.equals("A")) {
            List<int[][]> ruleToBeAccepted = new ArrayList<>();
            ruleToBeAccepted.add(activeValueRange);

            for(int[][] activeRule : acceptedRules) {

                for( int i = 0; i < ruleToBeAccepted.size(); i++) {
                    int[][] rulePart = new int[4][2];
                    boolean acceptedRuleApplies = true;
                    boolean fullCovered = true;

                    for( int c = 0; c < 4; c++) {
                        rulePart[c][0] = ruleToBeAccepted.get(i)[c][0];
                        rulePart[c][1] = ruleToBeAccepted.get(i)[c][1];

                        // zit totaal eronder, niets doen
                        // zit totaal erboven, niets doen
                        if ( activeRule[c][0] > rulePart[c][1] || activeRule[c][1] < rulePart[c][0]) {
                            acceptedRuleApplies = false;
                            fullCovered = false;
                            break;
                        } else {

                            if(activeRule[c][0] < rulePart[c][0] && activeRule[c][1] > rulePart[c][1]) {
                                // do nothing, look at the end if the partRule is fully Covered by existing rule.
                            }
                            // zit er deels onder, bottom range aanpassen van nieuwe rule zodat geen overlap
                            if( activeRule[c][0] < rulePart[c][0] && activeRule[c][1] < rulePart[c][1]) {
                                rulePart[c][0] = activeRule[c][1] + 1;
                                fullCovered = false;
                            }

                            // zit er deels boven, top range aanpassen van nieuwe rule zodat geen overlap
                            if( activeRule[c][1] > rulePart[c][1] && activeRule[c][0] > rulePart[c][0]) {
                                rulePart[c][1] = activeRule[c][0] - 1;
                                fullCovered = false;
                            }

                            // zit er tussen, nieuwe rule aanpassen waarbij eerste start size zelfde is, max size is bestaande rule startrange -1
                            // && bovenste startrange is bestaande rule maxsize +1 + eigen max range.
                            if( activeRule[c][0] > rulePart[c][0] && activeRule[c][1] < rulePart[c][1]) {
                                rulePart[c][1] = activeRule[c][0] - 1;
                                fullCovered = false;
                                int[][] ruleSplit = new int[4][2];
                                for( int x = 0; x < 4; x++){
                                    ruleSplit[x][0] = activeRule[x][0];
                                    ruleSplit[x][1] = activeRule[x][1];
                                }

                                ruleSplit[c][0] = activeRule[c][1] + 1;
                                ruleSplit[c][1] = rulePart[c][1];
                                ruleToBeAccepted.add(ruleSplit);
                            }
                        }

                    }

                    if (fullCovered) {
                        ruleToBeAccepted.remove(i);
                        i--;
                    } else if(acceptedRuleApplies) {
                        ruleToBeAccepted.set(i, rulePart);
                    }
                }
            }

            if (ruleToBeAccepted.size() > 0) {
                acceptedRules.addAll(ruleToBeAccepted);
            }
        } else if (!target.equals("R")) {
            String[] rule = rules.get(target).split(",");

            for(String r : rule) {

                if (!r.contains(":")) {
                    allRules(r, activeValueRange);
                } else {
                    int targetV = 0;

                    switch (r.charAt(0)) {
                        case 'x' -> targetV = 0;
                        case 'm' -> targetV = 1;
                        case 'a' -> targetV = 2;
                        case 's' -> targetV = 3;
                    }

                    String[] rSplit = r.split(":");
                    int functionValue = Integer.parseInt(rSplit[0].substring(2));
                    if (r.contains("<")) {
                        if (tempArray[targetV][0] < functionValue) {
                            tempArray[targetV][1] = functionValue -1;
                            allRules(rSplit[1], tempArray);
                        }
                    } else {
                        if (tempArray[targetV][1] > functionValue) {
                            tempArray[targetV][0] = functionValue + 1;
                            allRules(rSplit[1], tempArray);
                        }
                    }
                }
            }
        }
    }
}
