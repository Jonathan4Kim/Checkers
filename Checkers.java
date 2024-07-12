package org.cis120.checkers;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

/**
 * This class is a model for Checkers.
 */
public class Checkers {

    private Piece[][] board;
    private int numTurns;
    private boolean player1;
    private boolean gameOver;
    private boolean gameGoingOn;

    /**
     * Constructor sets up game state.
     */
    public Checkers() {
        reset();
    }

    /**
     * playTurn allows players to play a turn. Returns true if the move is
     * successful and false if a player tries to play in a location that is
     * taken or after the game has ended. If the turn is successful and the game
     * has not ended, the player is changed. If the turn is unsuccessful or the
     * game has ended, the player is not changed.
     *
     * @return whether the turn was successful
     */
    public boolean playTurn() {
        if (checkWinner() == 0) {
            player1 = !player1;
            numTurns++;
            return true;
        } else {
            return false;
        }

        // if (isValidMove(p, r2, c2)) {
        // // handles movement
        // getBoard().makeMove(p, r2, c2);
        // ArrayList<Moves> jumpMoves = ggetBoard().etPotentialJumps(p);
        // while (jumpMoves.size() != 0) {
        // // get the move and then
        // jumpMoves = getBoard().getPotentialJumps(p);
        // }
        //
        // numTurns++;
        // if (checkWinner() == 0) {
        // player1 = !player1;
        // }
        // return true;
        // } else {
        // // if it doesn't, the piece shouldn't move
        // return false;
        // }
        // if it can move, let it move
        // if it jumped over anything, delete the piece
        // if it can double jump, let the user double jump
    }

    /**
     * checkWinner checks whether the game has reached a win condition.
     * checkWinner only looks for horizontal wins.
     *
     * @return 0 if nobody has won yet, 1 if player 1 has won, and 2 if player 2
     *         has won, 3 if the game hits stalemate
     */
    public int checkWinner() {
        // Check for no pieces for p1 and p2, respectively
        boolean p2HasPieces = false;
        boolean p1HasPieces = false;
        boolean notStalemate = false;

        // checks that players 1 and 2 have pieces on the board
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece p = board[i][j];
                if (p != null) {
                    if (p.getIsPlayer1()) {
                        p1HasPieces = true;
                    } else {
                        p2HasPieces = true;
                    }
                }
            }
        }

        // check for stalemate
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece p = board[i][j];
                if (p != null) {
                    notStalemate = true;
                }
            }
        }
        // after stalemate check, check to see if they have pieces on the board
        if (p1HasPieces && !p2HasPieces) {
            gameGoingOn = false;
            return 1;
        } else if (!p1HasPieces && p2HasPieces) {
            gameGoingOn = false;
            return 2;
        } else if (!notStalemate) {
            gameGoingOn = false;
            return 3;
        }

        return 0;
    }

    /**
     * printGameState prints the current game state
     * for debugging.
     */
    public void printGameState() {
        System.out.println("\n\nTurn " + numTurns + ":\n");
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                System.out.print(board[i][j]);
                if (j < 2) {
                    System.out.print(" | ");
                }
            }
            if (i < 2) {
                System.out.println("\n---------");
            }
        }
    }

    public void printBoard() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j] != null) {
                    if (board[i][j].getIsPlayer1()) {
                        System.out.print("1");
                    } else {
                        System.out.print("2");
                    }
                } else {
                    System.out.print("o");
                }
            }
            System.out.print("\n");
        }
    }

    /**
     * reset (re-)sets the game state to start a new game.
     */
    public void reset() {
        board = new Piece[8][8];
        numTurns = 0;
        player1 = true;
        gameOver = false;
        gameGoingOn = true;

        if (gameGoingOn) {
            System.out.println("Game still going on");
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 8; j = j + 2) {
                if (i % 2 == 0) {
                    board[i][j] = new Piece(i, j, 40, 40, false, false);
                } else {
                    board[i][j + 1] = new Piece(i, j + 1, 40, 40, false, false);
                }
            }
        }

        for (int i = 5; i < 8; i++) {
            for (int j = 0; j < 8; j = j + 2) {
                if (i % 2 == 0) {
                    board[i][j] = new Piece(i, j, 40, 40, true, false);
                } else {
                    board[i][j + 1] = new Piece(i, j + 1, 40, 40, true, false);
                }
            }
        }
        gameGoingOn = true;
    }

    /**
     * getCurrentPlayer is a getter for the player
     * whose turn it is in the game.
     * 
     * @return true if it's Player 1's turn,
     *         false if it's Player 2's turn.
     */
    public boolean getCurrentPlayer() {
        return player1;
    }

    public void setCurrentPlayer(boolean b) {
        player1 = b;
    }

    public Piece[][] getBoard() {
        Piece[][] ans = new Piece[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                ans[i][j] = board[i][j];
            }
        }
        return ans;
    }

    public void setBoard(Piece[][] b) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                board[i][j] = b[i][j];
            }
        }
    }

    public boolean isGameGoingOn() {
        return gameGoingOn;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    /**
     * getCell is a getter for the contents of the cell specified by the method
     * arguments.
     *
     * @param c column to retrieve
     * @param r row to retrieve
     * @return an integer denoting the contents of the corresponding cell on the
     *         game board. 0 = empty, 1 = Player 1, 2 = Player 2
     */
    public Piece getPiece(int c, int r) {
        return board[r][c];
    }

    // **************************************************************************
    // * MOVEMENT
    // **************************************************************************

    /**
     * makeMove takes a piece and puts it in a new position
     *
     * @param p  piece to check in
     * @param r2 row to go to
     * @param c2 column to go to
     *
     */
    public void makeMove(Piece p, int r2, int c2) {
        if (p == null) {
            return;
        }
        int r1 = p.getRow();
        int c1 = p.getCol();
        board[r2][c2] = p;
        p.setRow(r2);
        p.setCol(c2);
        board[r1][c1] = null;
        // if we have a jump
        if (r1 - r2 == -2 || r1 - r2 == 2) {
            int newRowJump = (r1 + r2) / 2;
            int newColJump = (c1 + c2) / 2;
            board[newRowJump][newColJump] = null;
        }

        if (r2 == 0 && p.getIsPlayer1() && !p.getIsKing()) {
            p.setIsKing();
        } else if (r2 == 7 && !p.getIsPlayer1() && !p.getIsKing()) {
            p.setIsKing();
        }
    }

    /**
     * isValid Move checks if a piece can move to a certain location.
     * It will need to check:
     * 1. it could be moved because the location is adjacent diagonally
     * to its initial location and
     *
     * @param p piece to check in
     * @param c column to retrieve
     * @param r row to retrieve
     * @return a boolean that gives true if there is a valid move
     *
     */
    public boolean isValidMove(Piece p, int r, int c) {
        if (board[r][c] != null) {
            return false;
        }
        int row = p.getRow();
        int col = p.getCol();
        if (p.getIsPlayer1()) {
            if (r - row == -1 && (c - col == 1 || c - col == -1)) {
                return true;
            }
            if (r - row == -2 && (c - col == 2 || c - col == -2)) {
                return true;
            }
            if (p.getIsKing()) {
                if (r - row == 1 && (c - col == 1 || c - col == -1)) {
                    return true;
                } else if (r - row == 2 && (c - col == 2 || c - col == -2)) {
                    return true;
                }
            }
        } else {
            if (r - row == 1 && (c - col == 1 || c - col == -1)) {
                return true;
            } else if (r - row == 2 && (c - col == 2 || c - col == -2)) {
                return true;
            }
            if (p.getIsKing()) {
                if (r - row == -1 && (c - col == 1 || c - col == -1)) {
                    return true;
                }
                if (r - row == -2 && (c - col == 2 || c - col == -2)) {
                    return true;
                }
            }
        }
        return false;
    }

    // **************************************************************************
    // * TESTING FUNCTIONS FOR JUNIT TESTING
    // **************************************************************************
    /**
     * A testing function that makes the board filled with null pieces. return no
     * value.
     *
     **/

    public void clearBoard() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j] != null) {
                    board[i][j] = null;
                }
            }
        }
    }

    /**
     * A testing function that puts a piece on a board. for JUnit testing.
     * Assumes that in the given location, there is no piece, and the location is
     * valid.
     *
     * @param p piece to consider
     *          nothing to return.
     *
     **/

    public void putPieceOnBoard(Piece p) {
        int r = p.getRow();
        int c = p.getCol();
        board[r][c] = p;
    }

    // **************************************************************************
    // * MAIN METHOD
    // **************************************************************************

    /**
     * This main method illustrates how the model is completely independent of
     * the view and controller. We can play the game from start to finish
     * without ever creating a Java Swing object.
     *
     * This is modularity in action, and modularity is the bedrock of the
     * Model-View-Controller design framework.
     *
     * Run this file to see the output of this method in your console.
     */
    public static void main(String[] args) {
        Checkers c = new Checkers();
        int z = 0;
        int r = 0;
        while (!c.gameOver) {
            // c.playTurn(z, r);
            if (c.checkWinner() == 1 || c.checkWinner() == 2) {
                System.out.println();
                System.out.println();
                System.out.println("Winner is: " + c.checkWinner());
            } else if (c.checkWinner() == 3) {
                System.out.println();
                System.out.println();
                System.out.println("It's a tie");
                c.printGameState();
            } else {
                z++;
                r++;
            }
        }
    }
}
