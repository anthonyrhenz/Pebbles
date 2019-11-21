package com.kwakbennett.pebblegame.graphical;

import com.kwakbennett.pebblegame.Configurator;
import com.kwakbennett.pebblegame.Main;
import com.kwakbennett.pebblegame.model.Bag;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class gui {
    private JSlider playerCount;
    private Bag[][] bags = new Bag[2][3];

    public void runUI(){
        //Set up the frame
        JFrame frame = new JFrame("Pebble Game UI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //exit program on close as expected
        frame.setSize(600,400);
        frame.setLocationRelativeTo(null); //center on screen

        //creating our menu bar
        JMenuBar menuBar = new JMenuBar();
        JMenu menuFile = new JMenu("File");
        JMenuItem menuHelp = new JMenuItem("Help");
        menuBar.add(menuFile);
        menuBar.add(menuHelp);
        menuHelp.setMaximumSize(new Dimension(40,60));
        menuHelp.setActionCommand("helpMenu");
        JMenuItem openConfig = new JMenuItem("Run Config");
        openConfig.setActionCommand("runConfig");
        JMenuItem saveConfig = new JMenuItem("Save Config");
        saveConfig.setEnabled(false); // not much time to implement. also very easy to write a config file
        JMenuItem close = new JMenuItem("Exit");
        close.setActionCommand("exit");
        menuFile.add(openConfig);
        menuFile.add(saveConfig);
        menuFile.add(new JSeparator());
        menuFile.add(close);
        //set the menu bar
        frame.setJMenuBar(menuBar);

        //configuring components
        JLabel desc = new JLabel("Welcome to Anthony and Jakub's Pebble Game");
        desc.setBounds(160,10,500,20);


        JLabel playerCounter = new JLabel("Player Count: 3");
        playerCounter.setBounds(260,40,500,20);
        playerCount = new JSlider(1,10,3);
        playerCount.setBounds(200,60,200,20);
        //update player count text in real time
        playerCount.addChangeListener(e -> playerCounter.setText("Player Count: "+playerCount.getValue()));

        JButton Field1 = new JButton("Select Bag 1 File");
        Field1.setBounds(200,90,200,30);
        Field1.setActionCommand("selectBag1");

        JButton Field2 = new JButton("Select Bag 2 File");
        Field2.setBounds(200,130,200,30);
        Field2.setActionCommand("selectBag2");

        JButton Field3 = new JButton("Select Bag 3 File");
        Field3.setBounds(200,170,200,30);
        Field3.setActionCommand("selectBag3");

        JButton RunButton = new JButton("Run");
        RunButton.setBounds(250,300,100,40);
        RunButton.setActionCommand("runGame");


        //adding all our components
        //labels
        frame.add(desc);
        frame.add(playerCounter);
        frame.add(playerCount);
        frame.add(Field1);
        frame.add(Field2);
        frame.add(Field3);
        frame.add(RunButton);

        //create action listeners
        Field1.addActionListener(this::actionPerformed);
        Field2.addActionListener(this::actionPerformed);
        Field3.addActionListener(this::actionPerformed);
        openConfig.addActionListener(this::actionPerformed);
        RunButton.addActionListener(this::actionPerformed);

        menuHelp.addActionListener(this::actionPerformed);
        close.addActionListener(this::actionPerformed);

        frame.setLayout(null);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    private void actionPerformed(ActionEvent e){
        if ("selectBag1".equals(e.getActionCommand())){
            try {
                bags[0][0] = Configurator.fileToBag(selectFile(),"X");
                bags[1][0] = new Bag("A");
            } catch (Exception e1) {
                infoBox("Invalid input file. Please select again.","Error");
                return;
            }
        }
        if ("selectBag2".equals(e.getActionCommand())){
            try {
                bags[0][1] = Configurator.fileToBag(selectFile(),"Y");
                bags[1][1] = new Bag("B");
            } catch (Exception e1) {
                infoBox("Invalid input file. Please select again.","Error");
                return;
            }
        }
        if ("selectBag3".equals(e.getActionCommand())){
            try {
                bags[0][2] = Configurator.fileToBag(selectFile(),"Z");
                bags[1][2] = new Bag("C");
            } catch (Exception e1) {
                infoBox("Invalid input file. Please select again.","Error");
                return;
            }
        }
        if ("runGame".equals(e.getActionCommand())){
            try {
                if (!bags[0][0].isEmpty() && !bags[0][1].isEmpty() && !bags[0][2].isEmpty()){
                    runGame(); //custom game class for UI
                }
            }
            catch (Exception e1) {
                infoBox("You haven't selected a bag input file for each bag.","Error");
            }
        }

        if ("runConfig".equals(e.getActionCommand())){
            try {
                Configurator config = new Configurator();
                bags = config.importFromConfig(selectFile());
            } catch (Exception e1) {
                infoBox("Invalid config file. Ensure all input files are in the current working directory.","Error");
                return;
            }
            try {
                if (!bags[0][0].isEmpty() && !bags[0][1].isEmpty() && !bags[0][2].isEmpty()){
                    runGame(); //custom game class for UI
                }
            }
            catch (Exception e1) {
                infoBox("Invalid config file. Ensure all input files are in the current working directory.","Error");
                return;
            }
        }
        if ("helpMenu".equals(e.getActionCommand())){
            infoBox("Threads race towards an improbable condition - randomly taking pebbles of given weights from 3 pairs of bags, hoping that a hand of 10 pebbles have a total weight of 100.\n" +
                    "The game is not turn based; instead players are to be threaded and take pebbles as quickly as possible.\n" +
                    "If another player is taking from a bag, other players may not access it.\n\nGame implementation created by Anthony Bennett and Jakub Kwak (C)2019","Game Information");
        }
        if ("exit".equals(e.getActionCommand())){
            System.exit(0);
        }
    }

    private String selectFile(){
        //file chooser dialog
        final JFileChooser fileChooser = new JFileChooser( System.getProperty("user.dir") );
        //open on button click
        int returnValue = fileChooser.showDialog(null, "Select");
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile().getAbsolutePath();
        }
        return "";
    }

    private static void infoBox(String infoMessage, String titleBar)
    {
        JOptionPane.showMessageDialog(null, infoMessage, titleBar, JOptionPane.INFORMATION_MESSAGE);
    }

    private void runGame(){
        //create our list of players
        Main.Player[] players = new Main.Player[playerCount.getValue()];
        for (int i = 0; i < playerCount.getValue(); ++i) {
            players[i] = new Main.Player("player" + (i + 1), "player" + (i + 1) + "_output.txt", bags, true);
        }

        //save and start the player classes as threads implementing runnable
        ArrayList<Thread> threads = new ArrayList<>();
        for (Main.Player i : players) {
            threads.add(new Thread(i));
            //get most recently added thread and start it
            threads.get(threads.size() - 1).start();
        }

        //wait for threads to finish
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        infoBox(Main.UImessage,"Player Win Message");
        System.exit(0); //exit after win
    }
}
