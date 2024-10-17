------------------------------------------------------------------------
This is the project README file. Here, you should describe your project.
Tell the reader (someone who does not know anything about this project)
all he/she needs to know. The comments should usually include at least:
------------------------------------------------------------------------

PROJECT TITLE: Battleships game 

PURPOSE OF PROJECT: Battleships game 
• enable you to demonstrate your understanding of the softwaredevelopment process, 
from requirements gathering, through design tocoding and testing.
• give you assessed practice in software development through the designand development
of a fully functional (and working) application

VERSION or DATE: v1.0

HOW TO START THIS PROJECT:
Project structure is following: 
1) Main.java: contains main() method, so in order to start/debug the project run it from there 
2) BattleGui.java contains all GUI part + mouse event handlers 
3) Ship.java - definition of ships, their fields and methods
4) Player.java - player's fields and methods, required for collecting game statistic
5) BattleField.java - definition for a game battleField, ship placements methods            
6) images folder: contains image files required for visuals like: boom (when ship was hit) , watter (default on a start), missed shot
7) sounds folder: contains sound files required for sound effects like explosion when player hits a ship      
8) battlegui.txt: txt file to save/load a game data.

How to start a project

1) Navigate to a folder with a project files (by default it is: 'Battleships Game v1.0') 
 > cd  'Battleships Game v1.0'

2) compile all java files: 
 > javac *.java
this will create *.class files in the project directory 
 
3) Start the program from the Main class:  
 > java Main

Note. The game is printing to a console most improtant events like 'New game selected', 
'Load game selected' and etc. This information can be useful in case if some issue / or bug will appear.   

AUTHORS: developed by Chanieva Anna-Oleksandra 2616332 

USER INSTRUCTIONS: 

Main game screen contains:  
1) a battlefield (grid 10x10), 
2) player statistics:
 a) total hits
 b) succesful hits
3) enable sound option 
4) a 'Battle Menu' with following menu items: 
 a) New game
 b) Save game
 c) Load game
 d) About

How to play. 
To start a new game, select a 'Battle Menu' >  'New game'. 
The game will prepare a battlefield for you and reset player statistics for total and succesful hits to zero.
Click on a desired cell in order to test your luck and check if the enemy ship is there!
Continue clicking on the cells until all enemy ships will be found and destroyed. 
At the end, when all hidden ships will be discovered player will get a message:
Congratulations you've won a battleship game!      

How to Save a game. 
To save a game: select 'Battle Menu' > 'Save game'.
This will save to the battlegui.txt file all game data.

How to Load previously saved game
To load a game: select 'Battle Menu' > 'Load game'.
This will load from the battlegui.txt file all game data.
So player can continue his game from where it was on a game save. 