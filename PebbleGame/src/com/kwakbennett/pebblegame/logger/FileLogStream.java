package com.kwakbennett.pebblegame.logger;


import java.io.FileWriter;
import java.io.IOException;

/**
 * Class for writing text to a given file
 */
public class FileLogStream implements LogStreamInterface {
    private final FileWriter fileWriter;

    public FileLogStream(String fileName) {
        try {
            this.fileWriter = new FileWriter(fileName);
        } catch (IOException e) {
            throw new IllegalArgumentException("Cannot create file for the given file name " + fileName);
        }
    }

    /**
     * adds text to file
     * @param log text to add to the file
     */
    @Override
    public void write(String log) {
        try {
            this.fileWriter.write(log);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
