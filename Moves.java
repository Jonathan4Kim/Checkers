package org.cis120.checkers;

/**
 * This class creates a move object. It does not assume that the inputs given
 * are in fact valid, but this will be tested differently. it has the
 * row and column of where it was and where it wants to be.
 *
 * The only method here is the constructor and a boolean return function
 * that tests to see if a jump is possible in the move. For
 * isItAJump, inputs are assumed to be valid.
 */

public class Moves {
    private Piece[][] board;
    private Boolean player1Turn;

    Moves(Piece[][] board, boolean player1Turn) {
        this.board = board;
        this.player1Turn = player1Turn;

    }

    /**
     * isItAJump() lets us see if a jump has occurred.
     *
     * @return whether the movement is a jump.
     */

    // getter board
    public Piece[][] getBoard() {
        return this.board;
    }

    // getter
    public boolean getPlayer1Turn() {
        return this.player1Turn;
    }
}
