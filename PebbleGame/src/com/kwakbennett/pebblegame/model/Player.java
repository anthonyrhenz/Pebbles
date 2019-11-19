package com.kwakbennett.pebblegame.model;

import com.kwakbennett.pebblegame.logger.FileLogStream;
import com.kwakbennett.pebblegame.logger.LogStreamInterface;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import static com.kwakbennett.pebblegame.Main.gameWon;

public class Player implements Runnable {

    //definitions
    private static final Object lock = new Object();
    private String playerName;
    private LogStreamInterface output;
    private ArrayList<Integer> hand = new ArrayList<>();
    private Random random;
    private Bag[][] bags;
    private boolean shouldDiscardHighest;

    //constructors
    public Player(String playerName, String logOutput, Bag[][] bags, boolean shouldDiscardHighest) {
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
