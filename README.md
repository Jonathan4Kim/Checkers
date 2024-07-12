Checkers Game
Overview
This project implements a Checkers game in Java. The game includes a graphical user interface (GUI) and follows a Model-View-Controller (MVC) design framework. The game allows two players to play against each other, moving their pieces on the board and capturing opponent pieces by jumping over them.

Features
Two-player functionality: Player 1 (blue) and Player 2 (red) take turns.
King pieces: When a piece reaches the opponent's side, it becomes a king and can move both forward and backward.
GUI: The game uses a graphical user interface for ease of play.
Game controls: Includes buttons to start a new game, save the current game state, load a past game, and undo the last move.
Game rules: Adheres to standard Checkers rules.
Getting Started
Prerequisites
Java Development Kit (JDK) installed
A Java IDE or a text editor and command line to run Java programs
Installation
Clone the repository:
sh
Copy code
git clone https://github.com/your-username/checkers-game.git
Navigate to the project directory:
sh
Copy code
cd checkers-game
Open the project in your preferred Java IDE or text editor.
Running the Game
To run the game, execute the RunCheckers class. This can be done from your IDE by running the RunCheckers file, or from the command line:

sh
Copy code
javac -d bin src/org/cis120/checkers/*.java
java -cp bin org.cis120.checkers.RunCheckers
Code Structure
Piece.java: Defines the Piece class representing a game piece on the board.
Moves.java: Defines the Moves class responsible for creating move objects and checking for jumps.
RunCheckers.java: The main class to set up and start the game, including the GUI components.
Game Instructions
Start the game by running the RunCheckers class.
Player 1 (blue) starts the game.
Move your pieces diagonally forward to empty squares.
Jump over opponent pieces to capture them.
Reach the opponent's side to king your pieces, allowing them to move both forward and backward.
Use the buttons to start a new game, save the game state, load a past game, and undo the last move.
License
This project is licensed under the MIT License - see the LICENSE file for details.
