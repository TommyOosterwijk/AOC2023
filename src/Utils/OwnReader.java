package Utils;

import java.io.*;
import java.util.Scanner;

public class OwnReader {

    public static BufferedReader getReaderFromPath(String path) throws FileNotFoundException {
        File f = new File("resource/" + path);
        BufferedReader r = null;
        return new BufferedReader(new FileReader(f));

    }
}
