package com.kwakbennett.pebblegame.test;

import com.kwakbennett.pebblegame.Configurator;
import com.kwakbennett.pebblegame.Main;
import com.kwakbennett.pebblegame.model.Bag;
import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;

import static org.junit.Assert.*;

public class MainTest {
    private Bag[][] bags;
    private Main.Player player;
    private boolean discardStrategy;

    @Before
    public void createBagList() {
        Configurator configurator = new Configurator();
        try{
            configurator.importFromConfig("test_config.txt");
        } catch (FileNotFoundException e) {
            fail("Could not import text config");
        }
        this.bags = configurator.getBags();
        this.discardStrategy = configurator.getShouldDiscardHighest();
        //imports configuration from file before every test
    }


    @Before
    public void createPlayer() {
        this.player = new Main.Player("testPlayer","testPlayer.txt",this.bags,discardStrategy);
        //re-creates player before every test
    }

    @Test
    public void playerCheckTrueWin() {
        this.player.takePebble(new Bag(new ArrayList<>(Collections.singletonList(90)),"bag"));
        assertTrue(this.player.checkWin());
    }

    @Test
    public void playerCheckFalseWin() {
        assertFalse(this.player.checkWin());
        //checks if checkWin() returns false on a losing hand
    }
}
