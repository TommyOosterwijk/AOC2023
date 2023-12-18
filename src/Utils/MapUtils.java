package Utils;

import java.util.List;

public class MapUtils {

    public static String[][] createMapFromList(List lines) {
        int maxY = lines.size();
        int maxX = lines.get(0).toString().length();

        String[][] board = new String[maxY][maxX]; ;

        for (int y = 0; y < maxY; y++) {
            for (int x = 0; x < maxX; x++) {
                board[y][x] = lines.get(y).toString().charAt(x) + "";
            }
        }

        return board;
    }

    public static int[][] createMapFromListToInteger(List lines) {
        int maxY = lines.size();
        int maxX = lines.get(0).toString().length();

        int[][] board = new int[maxY][maxX]; ;

        for (int y = 0; y < maxY; y++) {
            for (int x = 0; x < maxX; x++) {
                board[y][x] = Integer.parseInt(lines.get(y).toString().charAt(x) + "");
            }
        }

        return board;
    }

    public static void printBoard(String[][] board, int maxX, int maxY) {
        for (int y = 0; y < maxY; y++) {
            for (int x = 0; x < maxX; x++) {
                System.out.print(board[y][x]);
            }
            System.out.println();
        }
    }

    public static void printIntBoard(int[][] board, int maxX, int maxY) {
        for (int y = 0; y < maxY; y++) {
            for (int x = 0; x < maxX; x++) {
                System.out.print(board[y][x]);
            }
            System.out.println();
        }
    }
}
