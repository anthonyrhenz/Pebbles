# Pebbles
Plays Pebbles, a game designed to teach threading to second years at the University of Exeter.

<h2>Description</h2>
Threads race towards an improbable condition - randomly taking pebbles of given weights from 3 pairs of bags, hoping that a hand of 10 pebbles have a total weight of 100.<br>
The game is not turn based; instead players are to be threaded and take pebbles as quickly as possible.<br>
If another player is taking from a bag, other players may not access it. Actions must be performed atomically.<br>

<h2>Authors</h2>
This program was written with the pair programming paradigm in order to reduce errors and develop more efficiently.<br>
Anthony Bennett and Jakub Kwak <3

<h2>Config Files</h2>
The program allows the user to import game configuration from a text file.<br>
The file should consist of:<br>
<br>
<i>Number of players<br>
Bag 1 file location<br>
Bag 2 file location<br>
Bag 3 file location<br>
Should players always discard the highest pebble? (true/false)</i><br>
<br>
Example file:<br>
3<br>
file.txt<br>
file.txt<br>
file.txt<br>
true




