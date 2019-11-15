package com.kwakbennett.pebblegame;

import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Class for game configuration using console
 */
public class Configurator {

    private int players;
    private ArrayList<ArrayList<Integer>> pebbleValues;

    public Configurator() {
    }

    public ArrayList<ArrayList<Integer>> getPebbleValues() {
        return pebbleValues;
    }

    public int getPlayers() {
        return players;
    }

    /**
     * Asks user for number of players and pebble values for each bag
     */
    public void start() {
        this.players = this.askPlayers();
        this.pebbleValues = new ArrayList<>();

        this.pebbleValues.add(askPebbleValues("X"));
        this.pebbleValues.add(askPebbleValues("Y"));
        this.pebbleValues.add(askPebbleValues("Z"));
    }

    /**
     * Asks the user for the number of players in the game
     *
     * @return number of players, a positive, non-zero integer
     */
    private int askPlayers() {
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
    private ArrayList<Integer> askPebbleValues(String bagName) {
        Scanner inScanner = new Scanner(System.in);
        ArrayList<Integer> pebbleValueList = new ArrayList<>();

        //again, not a very elegant solution, can fix/replace later
        System.out.println("Please enter location of bag " + bagName + " to load:");
        while (true) {
            try {
                pebbleValueList = IntegerImporter.importFromFile(inScanner.nextLine());
                break;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                System.out.println("Please enter location of bag " + bagName + " to load:");
            } catch (IOException e) {
                System.out.println("Something went wrong when reading the file");
                System.out.println("Please enter location of bag " + bagName + " to load:");

            }
        }
        return pebbleValueList;
    }
}
