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
            configurator.importFromConfig("test_config.txt");
        } catch (FileNotFoundException e) {
            fail("Config file not found");
        }
        assertEquals(1, configurator.getPlayers());
    }

    @Test
    public void canImportDiscardStrategy() {
        try {
            configurator.importFromConfig("test_config.txt");
        } catch (FileNotFoundException e) {
            fail("Config file not found");
        }
        assertTrue(configurator.getShouldDiscardHighest());
    }

    @Test
    public void canImportBags() {
        try {
            configurator.importFromConfig("test_config.txt");
        } catch (FileNotFoundException e) {
            fail("Config file not found");
        }
        Bag[][] bags = configurator.getBags();
        for (Bag[] bagGroup : bags) {
            for (Bag bag : bagGroup) {
                assertNotNull(bag);
            }
        }
    }
}
