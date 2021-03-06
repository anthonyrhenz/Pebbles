package com.kwakbennett.pebblegame;

import com.kwakbennett.pebblegame.graphical.gui;
import com.kwakbennett.pebblegame.model.Bag;
import com.kwakbennett.pebblegame.logger.FileLogStream;
import com.kwakbennett.pebblegame.logger.LogStreamInterface;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

// Everything runs from here
public class Main {

    //define accessible volatile switch for finding winner
    private static volatile boolean gameWon = false;
    public static volatile String UImessage = "";

    //define player class as nested
    public static class Player implements Runnable {

        //definitions
        private static final Object lock = new Object();
        private final String playerName;
        private final LogStreamInterface output;
        private final ArrayList<Integer> hand = new ArrayList<>();
        private final Bag[][] bags;
        private final boolean shouldDiscardHighest;
        private int randomBag;

        //constructors
        public Player(String playerName, String logOutput, Bag[][] bags, boolean shouldDiscardHighest) {
            this.output = new FileLogStream(logOutput);
            this.playerName = playerName;
            this.bags = bags;
            this.shouldDiscardHighest = shouldDiscardHighest;

            //load player hands at construction
                                randomBag = ThreadLocalRandom.current().nextInt( 0, 3 );
            for (int i = 0; i < 10; ++i) {
                this.hand.add(bags[0][randomBag].takeRandomPebble());
            }
        }

        //Methods

        /**
         * discards a pebble from the bag
         *
         * @param bag bag that pebble gets discarded to
         */
        void removePebble(Bag bag) {
            int pebble;
            synchronized (lock) {
                if (shouldDiscardHighest) {
                    pebble = this.hand.remove(this.hand.indexOf(Collections.max(this.hand)));
                } else {
                    pebble = this.hand.remove( ThreadLocalRandom.current().nextInt( 0, this.hand.size() ));
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
        public void takePebble(Bag bag) {
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
        public boolean checkWin() {
            int sum = 0;
            for (int weight : this.hand) sum += weight;
            return sum == 100;
        }

        //main loop over run
        @Override
        public void run() {
            while (!gameWon) {
                //check if win to terminate
                if (checkWin()) {
                    System.out.println(this.playerName + " has won with hand " + this.hand);
                    UImessage = this.playerName+" has won the game with hand " + this.hand.toString();
                    try {
                        this.output.write(this.playerName+" has won the game with hand " + this.hand.toString());
                    } catch (IOException e) {
                        System.out.println("Failed to log player win: " + e.getMessage());
                    }
                    gameWon = true;
                    break;
                }

                //first we must get rid of a pebble into the bag we last took from
                removePebble(bags[1][randomBag]);
                //then we must take a new pebble from a random bag
                //select that bag
                randomBag = ThreadLocalRandom.current().nextInt( 0, 3 );
                //first check if the bag's empty though, so we can refill it
                synchronized (lock) {
                    if (bags[0][randomBag].isEmpty()) {
                        // refill and proceed with our choice
                        bags[1][randomBag].movePebbles(bags[0][randomBag]);
                    }
                    //now we take a pebble from said bag
                    takePebble(bags[0][randomBag]);
                }

            }

            //save our file to disk when game is over
            try {
                this.output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private static boolean askGUI() {
        Scanner inScanner = new Scanner(System.in);
        System.out.println("Would you like to run in GUI mode? [Y/N]");

        while (true) {
            String playerInput = inScanner.nextLine().toUpperCase();
            switch (playerInput) {
                case "E":
                    System.exit(0);
                case "Y":
                    return true;
                case "N":
                    return false;
                default:
                    System.out.println("Please reply with either Y or N");
                    break;
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
        boolean keepPlaying = true;


        //check if we want to be GUI
        if (askGUI()) {
            gameWon = false;
            gui ui = new gui();
            ui.runUI();
        }
        else {
            while (keepPlaying) {
                gameWon = false;
                configurator.start();
                Bag[][] bags = configurator.getBags();
                int playerCount = configurator.getPlayers();
                boolean shouldDiscardHighest = configurator.getShouldDiscardHighest();

                //create our list of players
                Player[] players = new Player[playerCount];
                for (int i = 0; i < playerCount; ++i) {
                    players[i] = new Player("player" + (i + 1), "player" + (i + 1) + "_output.txt", bags, shouldDiscardHighest);
                }

                //save and start the player classes as threads implementing runnable
                ArrayList<Thread> threads = new ArrayList<>();
                for (Player i : players) {
                    threads.add(new Thread(i));
                    //get most recently added thread and start it
                    threads.get(threads.size() - 1).start();
                }

                //wait for threads to finish
                for (Thread thread : threads) {
                    try {
                        thread.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                //ask user if game should be played again
                keepPlaying = configurator.askPlayAgain();
            }
        }
    }
}