package com.kwakbennett.pebblegame.logger;

//this whole class seems redundant, prob should delete, sorry about that

//import com.kwakbennett.pebblegame.model.Pebble;
import java.util.ArrayList;

/**
 * class for logging player activity during a game
 */
public class Logger {
    private String playerName;
    private LogStreamInterface logStream;

    public Logger(String playerName, LogStreamInterface logStream) {
        this.playerName = playerName;
        this.logStream = logStream;
    }

    /**
     * logs the player drawing a pebble
     * @param drawnPebble pebble which was drawn
     * @param bagName bag which the pebble was drawn from
     */
    public void writeLogDraw(Integer drawnPebble, String bagName) {
        logStream.write(playerName + " has drawn a "
                    + Integer.toString(drawnPebble)
                    + " from bag " + bagName +"\n");
    }

    /**
     * logs the player discarding a pebble
     * @param discardedPebble pebble which was discarded
     * @param bagName bag which the pebble was discarded to
     */
    public void writeLogDiscard(Integer discardedPebble, String bagName) {
        logStream.write(playerName + " has discarded a "
                + Integer.toString(discardedPebble)
                + " to bag " + bagName +"\n");
    }

    /**
     * Logs the player's current hand
     * @param valueList list of pebble values in the player's hand
     */
    public void writeLogHand(ArrayList<Integer> valueList) {
        logStream.write(playerName + " hand is " + valueList);
    }
}
