package com.kwakbennett.pebblegame.model;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.stream.Collectors;

import static com.kwakbennett.pebblegame.Main.gameWon;

public class Player implements Runnable{

    private static final Object lock = new Object();
    String playerName;
    BufferedWriter output;
    ArrayList<Integer> hand = new ArrayList<Integer>();
    Random random;
    Bag bags[][];

    //constructors
    public Player(String playerName, String logOutput, Bag[][] bags) throws IOException{
        this.output = new BufferedWriter(new FileWriter(logOutput), 69690);
        this.random = new Random();
        this.playerName = playerName;
        this.bags = bags;

        //load player hands at construction
        for (int i = 0; i<11; ++i){
            this.hand.add(bags[0][0].takeRandomPebble());
        }
    }

    //Methods

    /** I'm moving the methods for bag access over to the players
     * since they need to be synchronized, not the bags - bags will do themselves if these are sync'd
     */

    //according to the docs, first discard a pebble. max hand size is 10, no buffering
    void removePebble(Bag bag){
        int pebble;
        synchronized (lock) {
            //get random pebble, store, and remove from hand
//            int pebble = this.hand.remove(random.nextInt(this.hand.size()));
            //new idea - remove the largest pebble
            pebble = this.hand.remove(this.hand.indexOf(Collections.max(this.hand)));
            bag.addPebble(pebble);

            //store action to file
            //oke so this one we gonna think about a bit
            //either we store the logs to memory using it as a RAMcache which is very fast
            //but if the game runs for too long then we become memory hog
            //these logs r probably gonna get real fat
            //so maybe staged buffers idk?

        }
        //im just going to write to file asynchronously
        try {
            this.output.write(this.playerName + " has drawn a "+ pebble +" from bag "+bag.getName()+"\r\n"+this.playerName+" hand is " + this.hand.toString());
        }
        catch (IOException e) {
            System.out.println("Failed to log player action on "+ this.playerName);
        }
    }

    void takePebble(Bag bag){
        synchronized (lock){
            int pebble = bag.takeRandomPebble(); //take a random pebble from bag
            this.hand.add(pebble); //and put it in our hand
        }
        try {
            this.output.write(this.playerName + " has drawn a 17 from bag Y\r\n"+this.playerName+" hand is " + this.hand.toString());
        }
        catch (IOException e) {
            System.out.println("Failed to log player action on "+ this.playerName);
        }
    }

    boolean checkWin() {
        int sum = 0;
        for(int weight : this.hand) sum += weight;
        if (sum == 100) return true;
        return false;
    }

    @Override
    public void run() {
        removePebble(bags[0][0]);
        //System.out.println(hand.stream().map(Object::toString).collect(Collectors.joining(", "))); //print player hand

        //lets go
        int randomBag;
        while (!gameWon) {
            //check win and terminate
            if (checkWin()){
                gameWon = true;
                System.out.println(this.playerName + " has won with hand " + this.hand);
                break;
            }

            //first we must toss a pebble into a random bag
            removePebble(bags[1][random.nextInt(3)]);
            //then we must take a new pebble from a random bag
            //select that bag
            randomBag = random.nextInt(3);
            //first check if the bag's empty though, so we can refill it
            synchronized (lock) {
                if (bags[0][randomBag].isEmpty() && bags[1][randomBag].isEmpty()) {
                    //if both bags of the pair are empty, then we choose a different pair
                    randomBag = random.nextInt(3);
                }
                else if (bags[0][randomBag].isEmpty()) {
                    //otherwise we refill and proceed with our choice
                    bags[1][randomBag].movePebbles(bags[0][randomBag]);
                }
            }
            //now we take a pebble from said bag
            takePebble(bags[0][randomBag]);

        }

        //save our file to disk
        try {
            this.output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
