package com.kwakbennett.pebblegame.logger;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Class for writing text logs to a given file
 */
public class FileLogStream implements LogStreamInterface {
    private final BufferedWriter output;

    public FileLogStream(String fileName) {
        try {
            this.output = new BufferedWriter(new FileWriter(fileName));
        } catch (IOException e) {
            throw new IllegalArgumentException("Cannot create file for the given file name " + fileName);
        }
    }

    /**
     * add text to file
     *
     * @param log text to add to the file
     */
    @Override
    public void write(String log) throws IOException {
        this.output.write(log);
    }

    public void close() throws IOException {
        this.output.close();

    }
}
