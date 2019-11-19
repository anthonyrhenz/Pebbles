package com.kwakbennett.pebblegame;

import com.kwakbennett.pebblegame.model.Bag;
import com.kwakbennett.pebblegame.model.Player;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.IOException;
import java.util.ArrayList;

/** notes and random thought land
 * game starts right
 * BLACK bags start full
 * seperate synchronized thread for each player implementing runnable
 * class for player yeyeye
 *
 * oke cool threading done
 * all now is just pair bags, pass around the pebbles
 * then check for win
 * maybe have an external thread checking for winning if feeling bored :s idc
 *
 */
//dont forget to check for number of pebbles = noPlayers*11

//everything goes in here
public class Main {

    //define volatile switch for finding winner
    public static volatile boolean gameWon = false;

    //main loop vibes
    public static void main(String[] args) throws Exception {
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
        Bag[][] bags = configurator.start();
        int playerCount = configurator.getPlayers();
//        ArrayList<ArrayList<Integer>> pebbleValues = configurator.getPebbleValues();


        //create our list of players
        Player[] players = new Player[playerCount];

        for (int i=0; i < playerCount; ++i) {
            players[i] = new Player("player"+(i+1), "player"+(i+1)+"_output.txt", bags);
        }

        for (Player i : players) {
            new Thread(i).start();
        }

        System.out.println("End of main");
    }
}