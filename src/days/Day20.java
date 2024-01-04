package days;

import Utils.OwnReader;
import java.io.FileNotFoundException;
import java.util.*;

public class Day20 extends Day {

    boolean rxPulseFound = false;
    Map<String, Module> modules = new HashMap<>();
    List<Pulse> actions = new ArrayList<>();
    Broadcaster broadcaster;
    long lowPulse = 0;
    long highPulse = 0;

    Map<String, Long> LCMIncoming = new HashMap<>();

    long stepCounter = 0;




    public Day20() throws FileNotFoundException {
        var lines = OwnReader.getReaderFromPath("day20.txt").lines().toList();

        for (String str : lines) {
            if( str.contains("broadcaster")) {
                broadcaster = new Broadcaster(Arrays.stream(str.split("-> ")[1].split(", ")).toList());
            } else {
                String[] parts = str.split(" -> ");
                String name = parts[0].substring(1);
                int type = 0;
                if( parts[0].contains("&")) {
                    type = 1;
                }
                modules.put(name, new Module(name, type, false, Arrays.stream(parts[1].split(", ")).toList()));
            }
        }
        for (String str : lines) {
            if( !str.contains("broadcaster")) {
                String[] parts = str.split(" -> ");
                String[] targetModules = parts[1].split(", ");
                String name = parts[0].substring(1);
                for( int i = 0; i < targetModules.length; i++) {
                    if(modules.containsKey(targetModules[i])) {
                        modules.get(targetModules[i]).addInpulseSender(name);
                    }
                }
            }
        }
    }

    public void pressButton() {
        lowPulse++;
        for(int i = 0; i < broadcaster.connectedModules.size(); i++) {
            String target =broadcaster.connectedModules.get(i);
            actions.add(new Pulse(false, target, "broadcaster"));
        }
    }

    public void runInpulses() {
        while(actions.size() > 0) {
            Pulse targetPulse = actions.removeFirst();

            if(targetPulse.target.equals("rx") && !targetPulse.high) {
                rxPulseFound = true;
                break;
            }
            if( targetPulse.high) {
                highPulse++;
            } else {
                lowPulse++;
            }
            if(modules.containsKey(targetPulse.target)) {
                modules.get(targetPulse.target).recieveAndSendInpulse(targetPulse);
            }

        }
    }

    @Override
    public String getAnswerPartOne() throws Exception {
        for(int i = 0; i < 1000; i++) {
            pressButton();

            runInpulses();
        }

        System.out.println("Day20 A= "+ (lowPulse * highPulse));
        return "Day20 A= "+ 0;
    }

    @Override
    public String getAnswerPartTwo() throws Exception {
        stepCounter = 0;
        //while( !rxPulseFound) {
        for ( int  i = 0; i < 10000; i++) {
            stepCounter++;
            pressButton();

            runInpulses();
        }

        System.out.println("Day20 B= " +  stepCounter);
        return "Day20 B= "+ 0;
    }

    class Broadcaster {
        List<String> connectedModules;

        public Broadcaster(List<String> connectedModules) {
            this.connectedModules = connectedModules;
        }
    }

    class Pulse {
        boolean high;

        String target;

        String source;

        public Pulse(boolean high, String target, String source) {
            this.high = high;
            this.target = target;
            this.source = source;
        }
    }

    class Module {
        String name;
        int type;
        boolean state;

        List<String> connectedModules;

        Map<String, Boolean> inpulses = new HashMap<>();

        public Module(String name, int type, boolean state, List<String> connectedModules) {
            this.name = name;
            this.type = type;
            this.state = state;
            this.connectedModules = connectedModules;
        }

        void addInpulseSender(String name) {
            if( type == 1) {
                inpulses.put(name, false);
            }
        }

        void recieveAndSendInpulse(Pulse pulse) {
            if(type == 0) {
                if (!pulse.high) {
                    state = !state;
                    for( int i = 0; i < connectedModules.size(); i++) {
                        actions.add(new Pulse(state, connectedModules.get(i), this.name));
                    }
                }
            } else {
                inpulses.replace(pulse.source, pulse.high);
                boolean pulseState = inpulses.containsValue(false)? true : false;

                if(!pulseState) {
                    if (!LCMIncoming.containsKey(name)) {
                        System.out.println("Adding " + name + " on step " + stepCounter);
                        LCMIncoming.put(name, stepCounter);
                    }
                }
                for( int i = 0; i < connectedModules.size(); i++) {
                    actions.add(new Pulse(pulseState, connectedModules.get(i), this.name));
                }
            }
        }
    }
}
