package com.kwakbennett.pebblegame.model;

import java.util.ArrayList;
import java.util.Random;

/**
 * A simple bag class (UNFINISHED/PLACEHOLDER)
 */
public class Bag {
    private ArrayList<Pebble> pebbleList;
    private String name;
    private Random ran;

    public Bag() {
        this.pebbleList = new ArrayList<>();
        this.ran = new Random();
    }

    public String getName() {
        return name;
    }

    public ArrayList<Pebble> getPebbleList() {
        return pebbleList;
    }

    public void setPebbleList(ArrayList<Pebble> pebbleList) {
        this.pebbleList = pebbleList; //not really sure if we need this method if we have add pebbles
    }

    /**
     * Removes a random pebble from the object's pebbleList
     * @return a random pebble from the pebbleList
     */
    public Pebble takeRandomPebble() {
        return pebbleList.remove(ran.nextInt(pebbleList.size()));
    }

    /**
     * Adds a list of pebbles to the object's pebbleList
     * @param pebbleList an ArrayList containing pebbles
     */
    public void addPebbles(ArrayList<Pebble> pebbleList) {
        this.pebbleList.addAll(pebbleList);
    }
}