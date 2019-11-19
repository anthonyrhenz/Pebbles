package com.kwakbennett.pebblegame;

import com.kwakbennett.pebblegame.model.Bag;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;

/**
 * Class for game configuration using console
 */
public class Configurator {

    private int players;
    public int noPlayers;
//    private ArrayList<ArrayList<Integer>> pebbleValues;

    public Configurator() {
    }

//    public ArrayList<ArrayList<Integer>> getPebbleValues() {
//        return pebbleValues;
//    }

    public int getPlayers() {
        return players;
    }

    /**
     * Asks user for number of players and pebble values for each bag
     */
    public Bag[][] start() throws Exception {
        this.players = this.askPlayers();
//        this.pebbleValues = new ArrayList<>();

        //we make 6 very publicly accessible baggies
        //empty array for empty bags
        Bag[][] bags = new Bag[2][3];
        ArrayList<ArrayList<Bag>> empty = new ArrayList<ArrayList<Bag>>();

        String[] blacks = {"X","Y","Z"};
        String[] whites = {"A","B","C"};
        for (int i = 0; i<3; ++i){
            bags[0][i] = askPebbleValues(blacks[i]); //Bags stored at array index 0 are the black bags
            bags[1][i] = new Bag(whites[i]);  //Bags at index 1 are the white counterparts, with matching indices
        }
        return bags;
    }

    /**
     * Asks the user for the number of players in the game
     *
     * @return number of players, a positive, non-zero integer
     */
    private int askPlayers() {
        Scanner inScanner = new Scanner(System.in);
        this.noPlayers = 1;

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
    public Bag askPebbleValues(String bagName) throws Exception {
        Scanner inScanner = new Scanner(System.in);
        Bag outputBag;

        //again, not a very elegant solution, can fix/replace later
//        thats chill dw
        System.out.println("Please enter location of bag " + bagName + " to load:");
        while (true) {
            try {
                outputBag = fileToBag(inScanner.nextLine(), bagName);//IntegerImporter.importFromFile(inScanner.nextLine());
                break;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                System.out.println("Please enter location of bag " + bagName + " to load:");
            } catch (IOException e) {
                System.out.println(e.getMessage());
                System.out.println("Please enter location of bag " + bagName + " to load:");
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println("Please enter location of bag " + bagName + " to load:");
            }
        }
        return outputBag;
    }

    //yeah not elegant to put it in the same one
    //pass the file into the scanner as arg with new meth
    private Bag fileToBag(String fileLocation, String bagName) throws Exception {
        Scanner scanner = null;
        try {
            scanner = new Scanner(new File(fileLocation));
        } catch (FileNotFoundException e) {
//            e.printStackTrace();
            throw new FileNotFoundException("File not found at given location.");
        }
        String[] weightsStr = scanner.next().split(",");
        ArrayList<Integer> bagWeightsInts = new ArrayList<Integer>(weightsStr.length);

        //for ensuring the input is just a CSV of integers.
        //if this parseInt fails and we catch generic Exception, then we know it's an invalid input
        try {
            for (String i : weightsStr){
                bagWeightsInts.add(Integer.parseInt(i));
            }
        } catch (Exception e)
        {
            throw new Exception("Invalid input file. Please choose another file.");
        }

        //each black bag must have at least (11*number of players) pebbles
        if (bagWeightsInts.size() < this.noPlayers*11) {
            System.out.println(bagWeightsInts.size());
            System.out.println(this.noPlayers);
            throw new Exception("Input file is too small for number of players.");
        }
        Bag bag = new Bag(bagWeightsInts,"X");
        return bag;
    }
}
