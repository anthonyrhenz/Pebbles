package com.kwakbennett.pebblegame.model;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.Random;

public class Player implements Runnable {

    private static final Object lock = new Object();

    //suggestion: 'LogStreamInterface output;' good practice which allows to later completely change output location/type
    BufferedWriter output;
    ArrayList<Integer> hand = new ArrayList<Integer>();
    Random random;
    String playerName;
    ArrayList<Bag> bags = new ArrayList<>();

    //constructors
    public Player(String playerName, String logOutput, ArrayList<Bag> bags) throws IOException {
        //this.output = new FileLogStream(logOutPut); - if we go the interface way
        this.output = new BufferedWriter(new FileWriter(logOutput), 32768);
        Random random = new Random();
        this.playerName = playerName;
        this.bags = bags; //added bag reference list to constructor
    }

    //Methods

    /**
     * I'm moving the methods for bag access over to the players
     * since they need to be synchronized, not the bags - bags will do themselves if these are sync'd
     */

    //according to the docs, first discard a pebble. max hand size is 10, no buffering
    void removePebble(Bag bag) {
        synchronized (lock) {
            //get random pebble, store, and remove from hand
            int pebble = this.hand.remove(random.nextInt(this.hand.size()));

            bag.addPebble(pebble);

            //store action to file
            //oke so this one we gonna think about a bit
            //either we store the logs to memory using it as a RAMcache which is very fast
            //but if the game runs for too long then we become memory hog
            //these logs r probably gonna get real fat
            //so maybe staged buffers idk?

            //buffered writer?
            //since each player needs its own instance anyway should cause thread problems
            //using memory would be a problem as the game should technically be able to run forever

            //also maybe should move logging out of the synchronized block (?but then it won't see pebble?)
            try {
                this.output.write(this.playerName + " has discarded a " + pebble + " to bag " + bag.getName() + "\r\n"
                        + this.playerName + " hand is " + this.hand.toString());
            } catch (IOException e) {
                System.out.println("Failed to log player action on " + this.playerName);
            }

        }
    }

    void addPebble(Bag bag) {
        synchronized (lock) {
            //need to check is bag empty

            int pebble = bag.takeRandomPebble();
            this.hand.add(pebble);

            try {
                this.output.write(this.playerName + " has drawn a " + pebble + " from bag " + bag.getName() + "\r\n"
                        + this.playerName + " hand is " + this.hand.toString());
            } catch (IOException e) {
                System.out.println("Failed to log player action on " + this.playerName);
            }
        }
    }

    boolean checkWin() {
        int sum = 0;
        for (int i : this.hand) {
            sum += i;
        }
        if (sum >= 100) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void run() {
        Bag bag = this.bags.get(random.nextInt(3));
        for (int i = 0; i < 10; i++) {
            addPebble(bag);
        }
        if (checkWin()) {
            // win stuff
        }
        //while loop discard,add,checkWin until a player wins
    }
}
