package com.kwakbennett.pebblegame;

import com.kwakbennett.pebblegame.model.Bag;
import com.kwakbennett.pebblegame.model.Player;

// Everything runs from here
public class Main {

    //define accessible volatile switch for finding winner
    public static volatile boolean gameWon = false;

    //main loop vibes
    public static void main(String[] args) throws Exception {
        // this text is taken directly from the specification PDF
        System.out.println("Welcome to the PebbleGame!\n"
                + "You will be asked to enter the number of players\n"
                + "And then for the location of of three files in turn containing comma separated integer values for the pebble weights.\n"
                + "The integer values must be strictly positive.\n"
                + "The game will then be simulated, and output written to files in this directory.\n\n");

        Configurator configurator = new Configurator();
        Bag[][] bags = configurator.start();
        int playerCount = configurator.getPlayers();

        //create our list of players
        Player[] players = new Player[playerCount];
        for (int i=0; i < playerCount; ++i) {
            players[i] = new Player("player"+(i+1), "player"+(i+1)+"_output.txt", bags);
        }

        //start the player classes as threads implementing runnable
        for (Player i : players) {
            new Thread(i).start();
        }
    }
}