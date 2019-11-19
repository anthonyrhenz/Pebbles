package com.kwakbennett.pebblegame;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * class for importing integer values from file
 */
public class IntegerImporter {

    /**
     * Imports number values from file and converts to integer array list
     *
     * @param filePath File to the path
     * @return Returns an array list of integers
     */
    public static ArrayList<Integer> importFromFile(String filePath) throws IllegalArgumentException {
        try {
            Scanner scanner = new Scanner(new File(filePath));
            ArrayList<Integer> intList = new ArrayList<>();

            for (String s : scanner.next().split(",")) {
                intList.add(Integer.valueOf(s));
            }

            return intList;
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("Provided file path does not point at an existing file");
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid file format. Comma separated integer values are expected");
        }
    }
}
