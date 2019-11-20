package com.kwakbennett.pebblegame.test;

import com.kwakbennett.pebblegame.Configurator;
import com.kwakbennett.pebblegame.model.Bag;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MainTest {
    private Bag[][] bags;

    @Before
    void createBagList() {
        Configurator configurator = new Configurator();

        this.bags = new Bag[2][3];

        String[] blacks = {"X", "Y", "Z"};
        String[] whites = {"A", "B", "C"};

        try {
            for (int i = 0; i < 3; ++i) {
                bags[0][i] = configurator.fileToBag("t.txt", blacks[i]);
                bags[1][i] = new Bag(whites[i]);
            }
        } catch (Exception e) {
            fail("Test file 't.txt' missing or wrong");
        }
    }

    @Before
    void createPlayer() {

    }

    @Test
    public void playerHand() {

    }
}
