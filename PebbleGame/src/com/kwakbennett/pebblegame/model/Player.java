package com.kwakbennett.pebblegame.model;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.Random;

public class Player implements Runnable{

    private static final Object lock = new Object();
    BufferedWriter output;
    ArrayList<Integer> hand = new ArrayList<Integer>();
    Random random;
    String playerName;

    //constructors
    public Player(String playerName, String logOutput) throws IOException{
        this.output = new BufferedWriter(new FileWriter(logOutput), 32768);
        Random random = new Random();
        this.playerName = playerName;
    }

    //Methods

    /** I'm moving the methods for bag access over to the players
     * since they need to be synchronized, not the bags - bags will do themselves if these are sync'd
     */

    //according to the docs, first discard a pebble. max hand size is 10, no buffering
    void removePebble(Bag bag){
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

            try {
                this.output.write(this.playerName + " has drawn a 17 from bag Y\r\n"+this.playerName+" hand is " + this.hand.toString());
            }
            catch (IOException e) {
                System.out.println("Failed to log player action on "+ this.playerName);
            }

        }
    }

    @Override
    public void run() {
        System.out.print(this.playerName);
    }
}
