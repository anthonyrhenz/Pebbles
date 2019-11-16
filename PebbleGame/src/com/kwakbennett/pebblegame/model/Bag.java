package com.kwakbennett.pebblegame.model;

import java.util.ArrayList;
import java.util.Random;

/**
 * A simple bag class which contains a pebble lists
 */
public class Bag {
    private ArrayList<Pebble> pebbles;
    private String name;
    private Random ran;

    public Bag() {
        this.ran = new Random();
        this.pebbles = new ArrayList<>();
    }

    public Bag(ArrayList<Integer> pebbleValues) {
        this.ran = new Random();
        this.pebbles = new ArrayList<>();

        for (int pebbleValue : pebbleValues) {
            this.pebbles.add(new Pebble(pebbleValue));
        }
    }

    public String getName() {
        return name;
    }

    /**
     * Removes a random pebble from the bags's pebble list
     * @return a random pebble from the pebble list
     */
    public Pebble takeRandomPebble() {
        return pebbles.remove(ran.nextInt(pebbles.size()));
    }

    /**
     * Adds a list of pebbles to the bag's pebble list
     * @param pebbleList an ArrayList containing pebbles
     */
    public void addPebbles(ArrayList<Pebble> pebbleList) {
        this.pebbles.addAll(pebbleList);
    }

    /**
     * Takes the bag's pebbles and adds them to another bag
     * @param bag bag to add the pebbles to
     */
    public void movePebbles(Bag bag) {
        bag.addPebbles(pebbles);
        pebbles.clear();
    }
}