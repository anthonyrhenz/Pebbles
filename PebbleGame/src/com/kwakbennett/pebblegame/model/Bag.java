package com.kwakbennett.pebblegame.model;

import java.util.ArrayList;
import java.util.Random;
import java.util.stream.Collectors;

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
    public Integer takeIndex0() {
        return pebbles.remove(0);
    }

    public void addPebble(Integer pebble) {
        pebbles.add(pebble);
    }

    public String asString(){
        return pebbles.stream().map(Object::toString).collect(Collectors.joining(", "));
    }

    public boolean isEmpty(){
        if (pebbles.size() <= 0) return true;
        return false;
    }

    /**
     * Adds a list of pebbles to the bag's pebble list
     * @param pebbleList an ArrayList containing pebbles
     */
    //just pass em in when defined
//    public void addPebbles(ArrayList<Integer> pebbleList) {
//        this.pebbles.addAll(pebbleList);
//    }

    /**
     * Takes the bag's pebbles and adds them to another bag
     * Should only ever run from a white bag
     * @param bag bag to add the pebbles to
     */
    public void movePebbles(Bag bag) {
        bag.pebbles.addAll(this.pebbles);
        this.pebbles.clear();
    }
}