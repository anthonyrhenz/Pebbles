package com.kwakbennett.pebblegame;

import com.kwakbennett.pebblegame.model.Bag;

import java.io.IOException;
import java.util.*;

/**
 * Class for game configuration using console
 */
public class Configurator {

    private int playerCount;
    private ArrayList<Bag> bags;

    public Configurator() {
    }


    public int getPlayerCount() {
        return playerCount;
    }

    public ArrayList<Bag> getBags() {
        return bags;
    }

    /**
     * Asks user for number of players and pebble values for each bag
     */
    public void start() {
        this.playerCount = this.askPlayerCount();
        this.bags.add(askPebbleValues("X"));
        this.bags.add(askPebbleValues("Y"));
        this.bags.add(askPebbleValues("Z"));
    }

    /**
     * Asks the user for the number of players in the game
     *
     * @return number of players, a positive, non-zero integer
     */
    private int askPlayerCount() {
        Scanner inScanner = new Scanner(System.in);
        int noPlayers = 1;

        System.out.println("Please enter the number of players:");

        while (true) {
            try {
                noPlayers = inScanner.nextInt();
                if (noPlayers <= 0) {
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
     *
     * @param bagName name of the bag to display when asking user
     * @return ArrayList of integers containing pebble values
     */
    public Bag askPebbleValues(String bagName) {
        Scanner inScanner = new Scanner(System.in);
        Bag outputBag;

        System.out.println("Please enter location of bag " + bagName + " to load:");

        while (true) {
            try {
                outputBag = fileToBag(inScanner.nextLine(), bagName);
                break;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                System.out.println("Please enter location of bag " + bagName + " to load:");
            }
        }
        return outputBag;
    }

    //yeah not elegant to put it in the same one
    //pass the file into the scanner as arg with new meth

    //this better maybe?
    private Bag fileToBag(String fileLocation, String bagName) throws IllegalArgumentException {
/*        Scanner scanner = null;
        try {
            scanner = new Scanner(new File(fileLocation));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new FileNotFoundException("Oof");
        }
        String[] weightsStr = scanner.next().split(",");
        ArrayList<Integer> bagWeightsInts = new ArrayList<Integer>(weightsStr.length);
        for (String i : weightsStr){
            bagWeightsInts.add(Integer.parseInt(i));
        }
        Bag bag = new Bag(bagWeightsInts, "X");*/

        ArrayList<Integer> bagWeightsInts = IntegerImporter.importFromFile(fileLocation);
        for (int val : bagWeightsInts) {
            if (val <= 0) {
                throw new IllegalArgumentException("Integers in list must be strictly positive");
            }
            if (bagWeightsInts.size() < 11 * this.playerCount) {
                throw new IllegalArgumentException("File must contain at least 11 times as many numbers as there are players");
            }
        }
        return new Bag(bagWeightsInts, "X");
    }
}
