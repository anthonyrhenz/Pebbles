package com.kwakbennett.pebblegame;

import com.kwakbennett.pebblegame.model.Bag;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Class for game configuration using console
 */
class Configurator {
    private int noPlayers;

    int getPlayers() {
        return noPlayers;
    }

    /**
     * Asks user for number of players and pebble values for each bag
     */
    Bag[][] start() {
        this.noPlayers = askPlayers();
        Bag[][] bags = new Bag[2][3];

        String[] blacks = {"X","Y","Z"};
        String[] whites = {"A","B","C"};

        //group of bags at [0][n] are black, group at [1][n] are corresponding white bags
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
        String playerInput;

        System.out.println("Please enter the number of players:");
        while (true) {
            try {
                playerInput = inScanner.nextLine();
                if (playerInput.equals("E")) {
                    System.exit(0);
                }
                if (Integer.parseInt(playerInput) <= 0) {
                    throw new IllegalArgumentException();
                }
                break;
            } catch (InputMismatchException | IllegalArgumentException e) {
                System.out.println("Please enter a positive integer larger than 0");
            }
        }
        noPlayers = Integer.parseInt(playerInput);
        return noPlayers;
    }

    /**
     * Asks the user for a file containing pebble values
     * @param bagName name of the bag to display when asking user
     * @return ArrayList of integers containing pebble values
     */
    private Bag askPebbleValues(String bagName) {
        Scanner inScanner = new Scanner(System.in);
        Bag outputBag;
        String playerInput;
        System.out.println("Please enter location of bag " + bagName + " to load:");
        while (true) {
            try {
                playerInput = inScanner.nextLine();
                if (playerInput.equals("E")){
                    System.exit(0);
                }
                outputBag = fileToBag(playerInput, bagName);
                break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println("Please enter location of bag " + bagName + " to load:");
            }
        }
        return outputBag;
    }

    //takes an input file and puts it into a named and filled bag
    private Bag fileToBag(String fileLocation, String bagName) throws Exception {
        Scanner scanner;
        try {
            scanner = new Scanner(new File(fileLocation));
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("File not found at given location.");
        }
        String[] weightsStr = scanner.next().split(",");
        ArrayList<Integer> bagWeightsInts = new ArrayList<>(weightsStr.length);

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
        return new Bag(bagWeightsInts,bagName);
    }
}
