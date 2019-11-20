package com.kwakbennett.pebblegame.model;

import java.util.ArrayList;
import java.util.Random;
import java.util.stream.Collectors;


/**
 * Bag class for pebble game
 * contains a list of pebbles
 */
public class Bag {
    private ArrayList<Integer> pebbles;
    private String name; //name the bag X, Y, or Z to match
    private Random ran;

    // Constructor
    public Bag(ArrayList<Integer> pebbleValues, String bagName) {
        this.ran = new Random();
        this.pebbles = pebbleValues;
        this.name = bagName;
    }

    //Overload constructor for empty (white) bag
    public Bag(String bagName) {
        this.name = bagName;
        this.ran = new Random();
        this.pebbles = new ArrayList<>(0); //empty arraylist ready for filling
    }

    /**
     * gets the name of the bag
     *
     * @return bag name
     */
    public String getName() {
        return name;
    }

    /**
     * Take a random pebble from the bag
     *
     * @return a random pebble from the pebble list
     */
    public Integer takeRandomPebble() {
//        System.out.println(pebbles.size());
        return pebbles.remove(ran.nextInt(pebbles.size()));
    }

    /**
     * Add a pebble to the pebble list
     *
     * @param pebble pebble to add
     */
    public void addPebble(Integer pebble) {
        pebbles.add(pebble);
    }

    //debug-ish to output the bag's arraylist contents as a string
    public String asString() {
        return pebbles.stream().map(Object::toString).collect(Collectors.joining(", "));
    }

    /**
     * Check if the bag is empty
     *
     * @return true if empty, otherwise false
     */
    public boolean isEmpty() {
        return pebbles.size() <= 0;
    }

    /**
     * Take the bag's pebbles and adds them to another bag
     * Should only ever run from a white bag
     *
     * @param bag bag to add the pebbles to
     */
    public void movePebbles(Bag bag) {
        bag.pebbles.addAll(this.pebbles);
        this.pebbles.clear();
    }
}