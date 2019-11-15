package com.kwakbennett.pebblegame.model;

/**
 * Simple pebble class
 */

public class Pebble {

    private int value;

    /**
     * Pebble constructor
     *
     * @param value the value of the pebble
     */
    public Pebble(int value) {
        if (value < 0) {
            throw new IllegalArgumentException("Value cannot be negative");
        }

        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
