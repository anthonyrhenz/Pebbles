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

    @Before
    public void createBagList() {
        Configurator configurator = new Configurator();
        try{
            configurator.importFromConfig("test_config.txt");
        } catch (FileNotFoundException e) {
            fail("Could not import text config");
        }
        this.bags = configurator.getBags();
    }


    @Before
    public void createPlayer() {
        //re-creates player before every test
        this.player = new Main.Player("testPlayer","testPlayer.txt",this.bags,true);
    }

    @Test
    public void playerCheckTrueWin() {
        this.player.takePebble(new Bag(new ArrayList<>(Collections.singletonList(100)),"bag"));
        assertTrue(this.player.checkWin());
    }

    @Test
    public void playerCheckFalseWin() {
        assertFalse(this.player.checkWin());
    }
}
