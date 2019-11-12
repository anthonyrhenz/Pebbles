import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Parser for user input in the console
 */
public class InputParser {

    public InputParser() {
    }

    /**
     * Asks the user for the number of players in the game
     *
     * @return number of players, a positive, non-zero integer
     */
    public int getPlayers() {
        Scanner inScanner = new Scanner(System.in);
        int noPlayers = 1;

        System.out.println("Please enter the number of players:");
        //not the most elegant solution, feel free to improve/fix
        while (true) {
            try {
                noPlayers = inScanner.nextInt();
                if (noPlayers <= 0) { //Maybe this should be done by a separate validator class?
                    throw new IllegalArgumentException();
                }
                break;
            } catch (InputMismatchException | IllegalArgumentException e) {
                System.out.println("Please enter a positive integer larger than 0");
                inScanner.next();
            }
        }
        return noPlayers;
    }

    /**
     * Asks the user for a file containing pebble values
     * @param bagName name of the bag to display when asking user
     * @return ArrayList of integers containing pebble values
     */
    public ArrayList<Integer> getPebbleValues(String bagName) {
        Scanner inScanner = new Scanner(System.in);
        ArrayList<Integer> pebbleValueList = new ArrayList<Integer>();

        //again, not a very elegant solution, can fix/replace later
        System.out.println("Please enter location of bag " + bagName + " to load:");
        while (true) {
            try {
                pebbleValueList = IntegerImporter.importFromFile(inScanner.nextLine());
                break;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                inScanner.next();
            } catch (IOException e) {
                System.out.println("Something went wrong when reading the file");
                inScanner.next();
            }
        }
        return pebbleValueList;
    }
}
