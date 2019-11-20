package com.kwakbennett.pebblegame;

import com.kwakbennett.pebblegame.model.Bag;
import com.kwakbennett.pebblegame.model.Player;

import com.kwakbennett.pebblegame.logger.FileLogStream;
import com.kwakbennett.pebblegame.logger.LogStreamInterface;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

// Everything runs from here
public class Main {

    //define accessible volatile switch for finding winner
    public static volatile boolean gameWon = false;

    //define player class as nested
    static class Player implements Runnable {

        //definitions
        private static final Object lock = new Object();
        private String playerName;
        private LogStreamInterface output;
        private ArrayList<Integer> hand = new ArrayList<>();
        private Random random;
        private Bag[][] bags;
        private boolean shouldDiscardHighest;

        //constructors
        Player(String playerName, String logOutput, Bag[][] bags, boolean shouldDiscardHighest) {
            this.output = new FileLogStream(logOutput);
            this.random = new Random();
            this.playerName = playerName;
            this.bags = bags;
            this.shouldDiscardHighest = shouldDiscardHighest;

            //load player hands at construction
            for (int i = 0; i < 11; ++i) {
                this.hand.add(bags[0][0].takeRandomPebble());
            }
        }

        //Methods

        /**
         * discards a pebble from the bag
         *
         * @param bag bag that pebble gets discarded to
         */
        private void removePebble(Bag bag) {
            int pebble;
            synchronized (lock) {
                if (shouldDiscardHighest) {
                    pebble = this.hand.remove(this.hand.indexOf(Collections.max(this.hand)));
                } else {
                    pebble = this.hand.remove(random.nextInt(this.hand.size()));
                }

                bag.addPebble(pebble);

            }
            // just going to write to file asynchronously
            try {
                this.output.write(this.playerName + " has discarded a " + pebble + " to bag " + bag.getName() +
                        "\r\n" + this.playerName + " hand is " + this.hand.toString() + "\r\n");
            } catch (IOException e) {
                System.out.println("Failed to log player action on " + this.playerName);
            }
        }

        /**
         * take a random pebble from bag and put it in hand
         *
         * @param bag bag to take pebble from
         */
        private void takePebble(Bag bag) {
            int pebble;
            synchronized (lock) {
                pebble = bag.takeRandomPebble(); //take a random pebble from bag
                this.hand.add(pebble); //and put it in our hand
            }
            //Log pebble value here
            try {
                this.output.write(this.playerName + " has drawn a " + pebble + " from bag " + bag.getName() +
                        "\r\n" + this.playerName + " hand is " + this.hand.toString() + "\r\n");
            } catch (IOException e) {
                System.out.println("Failed to log player action on " + this.playerName);
            }
        }

        /**
         * check if player has a winning hand
         *
         * @return true if player wins, otherwise false
         */
        private boolean checkWin() {
            int sum = 0;
            for (int weight : this.hand) sum += weight;
            return sum == 100;
        }

        //main loop over run
        @Override
        public void run() {
            removePebble(bags[0][0]);
            //System.out.println(hand.stream().map(Object::toString).collect(Collectors.joining(", "))); //print player hand

            int randomBag;
            while (!gameWon) {
                //check if win to terminate
                if (checkWin()) {
                    gameWon = true;
                    System.out.println(this.playerName + " has won with hand " + this.hand);
                    break;
                }

                //first we must get rid of a pebble into a random bag
                removePebble(bags[1][random.nextInt(3)]);
                //then we must take a new pebble from a random bag
                //select that bag
                randomBag = random.nextInt(3);
                //first check if the bag's empty though, so we can refill it
                synchronized (lock) {
                    if (bags[0][randomBag].isEmpty() && bags[1][randomBag].isEmpty()) {
                        //if both bags of the pair are empty, then we choose a different pair
                        randomBag = random.nextInt(3);
                    } else if (bags[0][randomBag].isEmpty()) {
                        //otherwise we refill and proceed with our choice
                        bags[1][randomBag].movePebbles(bags[0][randomBag]);
                    }
                }
                //now we take a pebble from said bag
                takePebble(bags[0][randomBag]);

            }

            //save our file to disk when game is over
            try {
                this.output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    //main loop vibes
    public static void main(String[] args) {
        System.out.println("Welcome to the PebbleGame!\n"
                + "You will be asked to enter the number of players\n"
                + "And then for the location of of three files in turn containing comma separated integer values for the pebble weights.\n"
                + "The integer values must be strictly positive.\n"
                + "The game will then be simulated, and output written to files in this directory.\n\n"
                + "Type 'E' at any time to exit the program.\n\n");

        Configurator configurator = new Configurator();
        Bag[][] bags = configurator.start();
        int playerCount = configurator.getPlayers();
        boolean shouldDiscardHighest = configurator.getShouldDiscardHighest();

        //create our list of players
        Player[] players = new Player[playerCount];
        for (int i = 0; i < playerCount; ++i) {
            players[i] = new Player("player" + (i + 1), "player" + (i + 1) + "_output.txt", bags, shouldDiscardHighest);
        }

        //start the player classes as threads implementing runnable
        for (Player i : players) {
            new Thread(i).start();
        }
    }
}