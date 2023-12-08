package days;

import Utils.OwnReader;

import java.util.*;

public class Day7 extends Day {


    final Map<String, Integer> cardLookup = Map.of(
            "T", 10,
            "J", 11,
            "Q", 12,
            "K", 13,
            "A", 14
    );

    final Map<String, Integer> cardLookupB = Map.of(
            "T", 10,
            "J", 0,
            "Q", 12,
            "K", 13,
            "A", 14
    );

    @Override
    public String getAnswerPartOne() throws Exception {
        long valuePartA = 0;
        var partOne = OwnReader.getReaderFromPath("day07.txt").lines()
                .map(s -> new Card(s)).toArray(Card[]::new);

        var result = Arrays.stream(partOne).sorted((o1, o2)-> {

            if( o1.handValue == o2.handValue) {
                for (int i = 0; i < 5; i++) {
                    String v1 = o1.value.charAt(i) +"";
                    String v2 = o2.value.charAt(i) +"";
                    int iv1 = 0;
                    int iv2 = 0;
                    if(!v1.equals(v2)) {
                        if (cardLookup.containsKey(v1)) {
                            iv1 = cardLookup.get(v1);
                        } else {
                            iv1 = Integer.parseInt(v1);
                        }

                        if (cardLookup.containsKey(v2)) {
                            iv2 = cardLookup.get(v2);
                        } else {
                            iv2 = Integer.parseInt(v2);
                        }
                        return iv1 - iv2;
                    }
                }
            }

            return o1.handValue - o2.handValue;
        }).toList();

        for( int i = 0; i < result.size(); i++) {
            valuePartA+= (i+1)* result.get(i).bid;
        }

        System.out.println("Day7 A= " + valuePartA);
        return "Day7 A= "+ partOne;
    }

    @Override
    public String getAnswerPartTwo() throws Exception {

        long valuePartB = 0;
        var partTwo = OwnReader.getReaderFromPath("day07.txt").lines()
                .map(s -> new CardB(s)).toArray(CardB[]::new);

        var result = Arrays.stream(partTwo).sorted((o1, o2)-> {

            if( o1.handValue == o2.handValue) {
                for (int i = 0; i < 5; i++) {
                    String v1 = o1.value.charAt(i) +"";
                    String v2 = o2.value.charAt(i) +"";
                    int iv1 = 0;
                    int iv2 = 0;
                    if(!v1.equals(v2)) {
                        if (cardLookupB.containsKey(v1)) {
                            iv1 = cardLookupB.get(v1);
                        } else {
                            iv1 = Integer.parseInt(v1);
                        }

                        if (cardLookupB.containsKey(v2)) {
                            iv2 = cardLookupB.get(v2);
                        } else {
                            iv2 = Integer.parseInt(v2);
                        }
                        return iv1 - iv2;
                    }
                }
            }

            return o1.handValue - o2.handValue;
        }).toList();

        for( int i = 0; i < result.size(); i++) {
            valuePartB+= (i+1)* result.get(i).bid;
        }

        System.out.println("Day7 B= " + valuePartB);
        return "Day7 B= "+ valuePartB;
    }

    class CardB {
        String value = "";
        int bid = 0;
        Map<String, Integer> cardValue = new HashMap();
        int handValue = 1;
        int jokerCounter = 0;
        String highestKey = "";
        int highestValue = 0;

        public CardB(String s) {
            this.value = s.split(" ")[0];
            this.bid = Integer.parseInt(s.split(" ")[1]);

            for(int i = 0; i < value.length(); i++) {
                String s1 = value.charAt(i) + "";
                if (s1.equals("J")) {
                    jokerCounter++;
                } else {
                    if (cardValue.containsKey(s1)) {
                        int cardVal = cardValue.get(s1);
                        if( cardVal > highestValue) {
                            highestValue = cardVal;
                            highestKey = s1;
                        }
                        handValue += cardVal;
                        cardValue.replace(s1, cardVal, cardVal + 1);
                    } else {
                        cardValue.put(s1, 1);
                        if( highestValue == 0) {
                            highestKey = s1;
                        }
                    }
                }
            }

            if (jokerCounter == 5) {
                handValue = 11;
            } else {
                for (int i = 0; i < jokerCounter; i++) {
                    int cardVal = cardValue.get(highestKey);
                    handValue += cardVal;
                    cardValue.replace(highestKey, cardVal, cardVal + 1);
                }
            }
        }

        @Override
        public String toString() {
            return value;
        }
    }

    class Card {
        String value = "";
        int bid = 0;
        Map<String, Integer> cardValue = new HashMap();
        int handValue = 1;

        public Card(String s) {
            this.value = s.split(" ")[0];
            this.bid = Integer.parseInt(s.split(" ")[1]);

            for(int i = 0; i < value.length(); i++) {
                String s1 = value.charAt(i) + "";

                 if(cardValue.containsKey(s1)) {
                     handValue+= cardValue.get(s1);
                     cardValue.replace(s1, cardValue.get(s1), cardValue.get(s1)+1);
                 } else {
                     cardValue.put(s1, 1);
                 }
            }

        }

        @Override
        public String toString() {
            return value;
        }
    }
}





