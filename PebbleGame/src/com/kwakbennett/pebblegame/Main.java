package com.kwakbennett.pebblegame;

import com.kwakbennett.pebblegame.model.Bag;
import com.kwakbennett.pebblegame.model.Player;

import java.io.IOException;
import java.util.ArrayList;

/**
 * notes and random thought land
 * game starts right
 * BLACK bags start full
 * seperate synchronized thread for each player implementing runnable
 * class for player yeyeye
 * <p>
 * oke cool threading done
 * all now is just pair bags, pass around the pebbles
 * then check for win
 * maybe have an external thread checking for winning if feeling bored :s idc
 */
//dont forget to check for number of pebbles = noPlayers*11

//everything goes in here
public class Main {

    //main loop vibes
    public static void main(String[] args) throws IOException {
        // this text is taken directly from the specification PDF
        System.out.println("Welcome to the PebbleGame!\n"
                + "You will be asked to enter the number of players\n"
                + "And then for the location of of three files in turn containing comma separated integer values for the pebble weights.\n"
                + "The integer values must be strictly positive.\n"
                + "The game will then be simulated, and output written to files in this directory.\n\n");

        Configurator configurator = new Configurator();
        configurator.start();
        int playerCount = configurator.getPlayerCount();
        ArrayList<Bag> bags = configurator.getBags();


        //create our list of players
        Player[] players = new Player[playerCount];

        for (int i = 0; i < playerCount; ++i) {
            players[i] = new Player("player" + (i + 1), "player" + (i + 1) + "_output.txt", bags);
        }

        for (Player i : players) {
            new Thread(i).start();
        }

        System.out.println(4);
    }
}