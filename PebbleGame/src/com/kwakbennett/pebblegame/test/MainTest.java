package com.kwakbennett.pebblegame.test;

import com.kwakbennett.pebblegame.Configurator;
import com.kwakbennett.pebblegame.Main;
import com.kwakbennett.pebblegame.model.Bag;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

public class MainTest {
    private Bag[][] bags;
    private Main.Player player;

    @Before
    void createBagList() {
        Configurator configurator = new Configurator();

        //this is here until configurator has import from config method
        this.bags = new Bag[2][1];
        try {
            bags[0][0] = configurator.fileToBag("t.txt", "bag");
            bags[1][0] = new Bag("discardBag");
        } catch (Exception e) {
            fail("Test file 't.txt' missing or wrong");
        }
    }

    @Before
    void createPlayer() {
        this.player = new Main.Player("testPlayer","testPlayer.txt",this.bags,true);
    }

    @Test
    public void playerCheckTrueWin() {
        this.player.takePebble(new Bag(new ArrayList<Integer>(Arrays.asList(100)),"bag"));
        assertTrue(this.player.checkWin());
        //clean up after itself
        this.player.removePebble(bags[1][0]);
    }

    @Test
    public void playerCheckFalseWin() {
        assertFalse(this.player.checkWin());
    }
}
