package com.kwakbennett.pebblegame.test;

import com.kwakbennett.pebblegame.model.Bag;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.CoreMatchers.anyOf;
import static org.junit.Assert.*;

public class BagTest {

    @Test
    public void isEmptyReturnsTrue() {
        Bag emptyBag = new Bag("Empty Bag");
        assertTrue(emptyBag.isEmpty());
    }
    @Test
    public void isEmptyReturnsFalse() {
        Bag nonEmptyBag = new Bag(new ArrayList<>(Collections.singletonList(1)), "Non-Empty Bag");
        assertFalse(nonEmptyBag.isEmpty());
    }

    @Test
    public void takeRandomPebbleTakesOnePebble() {
        Bag testBag = new Bag(new ArrayList<>(Collections.singletonList(1)), "Test Bag");
        assertFalse(testBag.isEmpty());
        assertEquals(1, testBag.takeRandomPebble());
        assertTrue(testBag.isEmpty());
    }

    @Test void takeRandomPebbleTakesAllPebbles() {
        Bag testBag = new Bag(new ArrayList<>(Arrays.asList(1,2,3)), "Test Bag");
        for (int i = 0; i > 3; i++) {
            assertFalse(testBag.isEmpty());
            testBag.takeRandomPebble();
            assertFalse(testBag.isEmpty());
            testBag.takeRandomPebble();
            assertFalse(testBag.isEmpty());
            testBag.takeRandomPebble();
        }
        //after removing all 3, bag should be empty
        assertTrue(testBag.isEmpty());
    }

    @Test
    public void addPebbleTest() {
        Bag testBag = new Bag("Test Bsg");

        assertTrue(testBag.isEmpty());
        testBag.addPebble(1);
        assertFalse(testBag.isEmpty());
        assertEquals(1, testBag.takeRandomPebble());
    }

    @Test
    public void movePebblesTest() {
        Bag bagA = new Bag("Empty Bag");
        Bag bagB = new Bag(new ArrayList<>(Collections.singletonList(1)), "Non-Empty Bag");

        assertTrue(bagA.isEmpty());
        assertFalse(bagB.isEmpty());

        bagB.movePebbles(bagA);

        //pebbles were moved from one bag to the other, so assertions now opposite
        assertFalse(bagA.isEmpty());
        assertTrue(bagB.isEmpty());
    }
}
