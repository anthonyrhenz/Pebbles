package com.kwakbennett.pebblegame.logger;

import java.io.IOException;

/**
 * Interface for writing text to a logging stream
 */
public interface LogStreamInterface {
    /**
     * writes provided text to the logging stream
     *
     * @param log text to add to the stream
     */
    void write(String log) throws IOException;

    void close() throws IOException;
}
