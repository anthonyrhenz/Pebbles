package com.kwakbennett.pebblegame;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        // this text is taken directly from the specification PDF
        System.out.println("Welcome to the PebbleGame!\n"
                + "You will be asked to enter the number of players\n"
                + "And then for the location of of three files in turn containing comma separated integer values for the pebble weights.\n"
                + "The integer values must be strictly positive.\n"
                + "The game will then be simulated, and output written to files in this directory.\n\n");

        /*
         * putting all of this in com.kwakbennet.pebblegame.Main for now just so I can test, probably best to have it all in its own class
         * responsible for setting up all the bags and players etc and then just call that class here
         */
        Configurator configurator = new Configurator();
        configurator.start();
        int players = configurator.getPlayers();
        ArrayList<ArrayList<Integer>> pebbleValues = configurator.getPebbleValues();

        System.out.println(4);
    }
}