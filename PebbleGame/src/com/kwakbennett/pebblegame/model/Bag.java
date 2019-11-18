package com.kwakbennett.pebblegame.model;

import java.util.ArrayList;
import java.util.Random;

/**
 * Bag class, which can be configured to be black or white
 * Will be either X, Y, or Z
 * Upon matching the bags, they can be accessed independently but must be synchronous and atomic
 * also sorry jakub i killed pebble objects, seemed unnecessary
 * nevermind it doesn't matter what the colour is, just the pairs match
 */
public class Bag {
    private ArrayList<Integer> pebbles;
    private String name; //name the bag X, Y, or Z to match
    private Random ran;

//    public Bag() {
//        this.ran = new Random();
////        int minimumPebbles = 11; //put it in configurator
//        this.pebbles = new ArrayList<>();
//    }

    public Bag(ArrayList<Integer> pebbleValues, String bagName) {
        this.ran = new Random();
        this.pebbles = new ArrayList<>();

//        for (int pebbleValue : pebbleValues) {
//            this.pebbles.add(new Pebble(pebbleValue));
//        }
    }

    public String getName() {
        return name;
    }

    /**
     * Removes a random pebble from the bags's pebble list
     * @return a random pebble from the pebble list
     */
    public Integer takeRandomPebble() {
        return pebbles.remove(ran.nextInt(pebbles.size()));
    }

    public void addPebble(Integer pebble) {
        pebbles.add(pebble);
    }

    /**
     * Adds a list of pebbles to the bag's pebble list
     * @param pebbleList an ArrayList containing pebbles
     */
//    pass em in at the start
//    how will we move discarded pebbles back into original bags without such method?
//    public void addPebbles(ArrayList<Pebble> pebbleList) {
//        this.pebbles.addAll(pebbleList);
//    }

    /**
     * Takes the bag's pebbles and adds them to another bag
     * @param bag bag to add the pebbles to
     */
//    nah dw
//    public void movePebbles(Bag bag) {
//        bag.addPebbles(pebbles);
//        pebbles.clear();
//    }
}