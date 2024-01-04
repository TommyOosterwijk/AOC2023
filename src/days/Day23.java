package days;

import Utils.MapUtils;
import Utils.OwnReader;

import java.io.FileNotFoundException;
import java.util.*;

public class Day23 extends Day {
    String[][] grid;

    int maxX;
    int maxY;

    int[][] neighbours = {{-1,0},{0,1},{1,0},{0,-1}};

    List<Hike> hikers = new ArrayList<>();

    List<Intersection> intersections = new ArrayList<>();

    long steps = 0;
    long stepsB = 0;

    public Day23() throws FileNotFoundException {
        grid = MapUtils.createMapFromList(OwnReader.getReaderFromPath("day23.txt").lines().toList());
        maxY = grid.length;
        maxX = grid[0].length;
        hikers.add(new Hike());
    }

    public void walkA() {
        while (hikers.size() > 0) {
            Hike hiker = hikers.removeFirst();
            for (int i = 0; i < 4; i++) {
                int targetY = hiker.y + neighbours[i][0];
                int targetX = hiker.x + neighbours[i][1];
                String target = targetY + "-" + targetX;

                if (!(targetX < 0 || targetX >= maxX || targetY < 0 || targetY >= maxY)) {

                    String currentPos = grid[hiker.y][hiker.x];

                    if (currentPos.equals(">") && i != 1) {
                    } else if (currentPos.equals("<") && i != 3) {
                    } else if (currentPos.equals("^") && i != 0) {
                    } else if (currentPos.equals("v") && i != 2) {
                    } else if (!grid[targetY][targetX].equals("#") && !hiker.containsStep(target)) {
                        Hike newStep = new Hike();
                        newStep.y = targetY;
                        newStep.x = targetX;

                        newStep.steps = new ArrayList<>(hiker.steps);
                        newStep.addStep(target);
                        hikers.add(newStep);

                        if (newStep.y == maxY - 1 && newStep.x == maxX - 2) {
                            if (newStep.steps.size() > steps) {
                                steps = newStep.steps.size();
                            }
                        }
                    }
                }
            }
        }
    }

    public void walkB() {
        int[][] memo = new int[grid.length][grid[0].length];

        while (hikers.size() > 0) {
            Hike hiker = hikers.removeFirst();

            if (hiker.y == maxY - 1 && hiker.x == maxX - 2) {
                if (hiker.steps.size() > steps) {
                    steps = hiker.steps.size();
                }
            } else {
                if( hiker.y == maxY-16 && hiker.x ==  maxX - 8) {
                    hiker.y+=1;
                    hiker.steps.add(hiker.y + "-" + hiker.x);
                    hikers.add(hiker);
                } else {
                    for (int i = 0; i < 4; i++) {
                        int targetY = hiker.y + neighbours[i][0];
                        int targetX = hiker.x + neighbours[i][1];
                        String target = targetY + "-" + targetX;

                        if (!(targetX < 0 || targetX >= maxX || targetY < 0 || targetY >= maxY)) {
                            if (!grid[targetY][targetX].equals("#") && !hiker.containsStep(target)) {
                                if (memo[targetY][targetX] <= hiker.steps.size() + 1) {
                                    Hike newStep = new Hike();
                                    newStep.y = targetY;
                                    newStep.x = targetX;

                                    newStep.steps = new ArrayList<>(hiker.steps);
                                    newStep.addStep(target);
                                    hikers.add(newStep);
                                }
                            }
                        }
                    }
                }
            }
        }
    }


    public void mapIntersection() {
        for( int h = 0; h < intersections.size(); h++) {
            Intersection intersection = intersections.get(h);

            for (int i = 0; i < 4; i++) {
                List<String> t = new ArrayList<>();
                t.add(intersection.name);
                int x = intersection.x + neighbours[i][1];
                int y = intersection.y + neighbours[i][0];
                t.add(y + "-" + x);

                if (x >= 0 && x < maxX && y >= 0 && y < maxY) {
                    if(intersection.neighbours[i][1].equals("") && !grid[y][x].equals("#")) {
                        Intersection intersection1 = walkUntillIntersection(intersection.name, y, x, t, 1);
                        intersection.neighbours[i][0] = intersection1.name;
                        for( int z = 0; z < 4; z++) {
                            if( intersection1.neighbours[z][0].equals(intersection.name)) {
                                intersection.neighbours[i][1] = intersection1.neighbours[z][1];
                                boolean add = true;
                                for( int q = 0; q < intersections.size(); q++) {
                                    Intersection cache = intersections.get(q);

                                    if(cache.name.equals( intersection1.name)) {
                                        add = false;
                                        cache.neighbours[z][0] = intersection1.neighbours[z][0];
                                        cache.neighbours[z][1] = intersection1.neighbours[z][1];
                                    }
                                }
                                if(add) {
                                    intersections.add(intersection1);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public Intersection walkUntillIntersection(String currentIntersection, int currentY, int currentX, List<String> visited, int stepCounter) {
        Intersection intersection;
        while(true) {
            int dotCounter = 0;
            int posIntersecion = 0;

            for (int i = 0; i < 4; i++) {
                int targetY = currentY + neighbours[i][0];
                int targetX = currentX + neighbours[i][1];

                if (targetX >= 0 && targetX < maxX && targetY >= 0 && targetY < maxY) {
                    if(visited.contains(targetY +"-" + targetX)) {
                        posIntersecion = i;
                    }
                    if(!grid[targetY][targetX].equals("#")) {
                        dotCounter++;
                    }
                }
            }
            if( dotCounter > 2 || (currentY == maxY -1 && currentX == maxX-2)) {

                intersection = new Intersection(currentY, currentX);
                intersection.neighbours[posIntersecion][0] = currentIntersection;
                intersection.neighbours[posIntersecion][1] = stepCounter++ + "";

                return intersection;
            }

            for (int i = 0; i < 4; i++) {
                int targetY = currentY + neighbours[i][0];
                int targetX = currentX + neighbours[i][1];

                String target = targetY + "-" + targetX;

                if (targetX >= 0 && targetX < maxX && targetY >= 0 && targetY < maxY && !visited.contains(target)) {
                    if(!grid[targetY][targetX].equals("#")) {
                        currentY = targetY;
                        currentX = targetX;
                        visited.add(target);
                        break;
                    }
                }
            }
            stepCounter++;
        }
    }
    @Override
    public String getAnswerPartOne() throws Exception {

        walkA();
        System.out.println(steps-1);
        return "Day23 A= "+ 0;
    }

    public void gogoIntersections(Intersection section, List<String> usedIntersections, int stepsUsed, String finalSection) {
        usedIntersections.add(section.name);

        if(section.name.equals(finalSection)) {
            if( stepsUsed > stepsB) {
                stepsB = stepsUsed;
            }
        } else {
            for (int i = 0; i < 4; i++) {
                if (!section.neighbours[i][0].isBlank()) {
                    if (!usedIntersections.contains(section.neighbours[i][0])) {

                        for( Intersection nextIntersection: intersections) {
                            if( nextIntersection.name.equals(section.neighbours[i][0])) {
                                List<String> nextIntersectionList = new ArrayList<>(usedIntersections);
                                gogoIntersections(nextIntersection, nextIntersectionList,(stepsUsed + Integer.parseInt(section.neighbours[i][1])), finalSection);
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public String getAnswerPartTwo() throws Exception {
        List<String> t = new ArrayList<>();
        t.add("0-1");
        intersections.add(walkUntillIntersection("", 0, 1, t, 0));
        mapIntersection();
        List<String> tempList = new ArrayList<>();
        tempList.add("start");
        int stepsUsed = 0;

        for(int i = 0; i < 4; i++) {
            if(intersections.get(0).neighbours[i][0].isBlank() && !intersections.get(0).neighbours[i][1].isBlank()) {
                stepsUsed = Integer.parseInt(intersections.get(0).neighbours[i][1]);
                intersections.get(0).neighbours[i][0] = "start";
            }
        }

        gogoIntersections(intersections.get(0), tempList, stepsUsed, (maxY-1)+"-"+(maxX-2));
        System.out.println(stepsB);
        return "Day23 B= "+ 0;
    }

    class Intersection {
        int x;
        int y;

        String name;

        String[][] neighbours = new String[4][2];
        public Intersection( int y, int x) {
            this.x = x;
            this.y = y;
            this.name = y + "-" + x;

            for( int y1 = 0; y1 <4; y1++) {
                for( int x1 = 0; x1 < 2; x1++) {
                    neighbours[y1][x1] = "";
                }
            }
        }
        String[] getNeighbour(int pos) {
            return neighbours[pos];
        }
    }

    class Hike {
        List<String> steps = new ArrayList<>();
        int x = 1;
        int y = 0;

        public Hike() {
            steps.add("0-1");
        }
        public void addStep(String step) {
            steps.add(step);
        }
        public boolean containsStep(String step){
            return steps.contains(step);
        }
    }
}

//5046 TO LOW
