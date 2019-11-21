package com.kwakbennett.pebblegame.test;

import com.kwakbennett.pebblegame.Configurator;
import com.kwakbennett.pebblegame.model.Bag;
import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;

import static org.junit.Assert.*;

public class ConfiguratorTest {
    private Configurator configurator;

    @Before
    public void createConfigurator() {
        this.configurator = new Configurator();
    }

    @Test
    public void canImportPlayerCount() {
        try {
            Configurator.importFromConfig("test_config.txt");
        } catch (FileNotFoundException e) {
            fail("Config file not found");
        }
        assertEquals(1, configurator.getPlayers());
        //checks if importing the player count works
    }

    @Test
    public void canImportDiscardStrategy() {
        try {
            Configurator.importFromConfig("test_config.txt");
        } catch (FileNotFoundException e) {
            fail("Config file not found");
        }
        assertTrue(configurator.getShouldDiscardHighest());
        //checks if importing the discard strategy works
    }

    @Test
    public void canImportBags() {
        try {
            Configurator.importFromConfig("test_config.txt");
        } catch (FileNotFoundException e) {
            fail("Config file not found");
        }
        Bag[][] bags = configurator.getBags();
        for (Bag[] bagGroup : bags) {
            for (Bag bag : bagGroup) {
                assertNotNull(bag);
            }
        }
        //checks if importing bags actually crates the bags
    }
}
