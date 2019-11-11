import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * class for importing pebble values from file
 */
public class IntegerImporter {

    /**
     * Imports pebble values from file and converts to integer array
     *
     * @param filePath File to the path
     * @return Returns an array of integers
     * @throws IllegalArgumentException When filePath is invalid for any reason
     */
    public static ArrayList<Integer> importFromFile(String filePath) throws IOException, IllegalArgumentException {
        try {
            BufferedReader pebbleBR = new BufferedReader(new FileReader(filePath));
            ArrayList<Integer> pebbleIntList = new ArrayList<Integer>();

            for (String s : pebbleBR.readLine().split(",")) {
                pebbleIntList.add(Integer.valueOf(s));
            }

            return pebbleIntList;
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("Provided filePath does not point at the existing file", e);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid file format. Comma separated integer values are expected");
        }
    }
}
