package com.kwakbennett.pebblegame.test;

import com.kwakbennett.pebblegame.model.Bag;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.Assert.*;

public class BagTest {

    @Test
    public void isEmptyTest() {
        Bag emptyBag = new Bag("Empty Bag");

        assertTrue(emptyBag.isEmpty());
    }
    @Test
    public void isNotEmptyTest() {
        Bag nonEmptyBag = new Bag(new ArrayList<>(Collections.singletonList(1)), "Non-Empty Bag");

        assertFalse(nonEmptyBag.isEmpty());
    }

    @Test
    public void takeRandomPebbleTest() {
        Bag testBag = new Bag(new ArrayList<>(Collections.singletonList(1)), "Test Bag");

        assertEquals(1, testBag.takeRandomPebble());
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
