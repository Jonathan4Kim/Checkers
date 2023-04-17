package org.cis120.checkers;

import java.awt.*;

/**
 * An object in the game.
 *
 * Game objects exist in the game court. They have a position, velocity, size
 * and bounds. Their velocity controls how they move; their position should
 * always be within their bounds.
 */
public class Piece {
    /*
     * Current position of the object (in terms of graphics coordinates)
     *
     * Coordinates are given by the upper-left hand corner of the object. This
     * position should always be within bounds:
     * 0 <= px <= maxX 0 <= py <= maxY
     */
    private int row;
    private int col;

    /* Size of object, in pixels. */
    private final int width;
    private final int height;

    // boolean for which player and if the piece is a king
    private final boolean isPlayer1;
    private boolean isKing;

    /**
     * Constructor
     */
    public Piece(
            int row, int col, int width, int height, boolean p1Bool, boolean king
    ) {
        this.row = row;
        this.col = col;
        this.width = width;
        this.height = height;
        this.isPlayer1 = p1Bool;
        this.isKing = king;
    }

    // **********************************************************************************
    // * GETTERS
    // **********************************************************************************
    public int getRow() {
        return this.row;
    }

    public int getCol() {
        return this.col;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public boolean getIsPlayer1() {
        return this.isPlayer1;
    }

    public boolean getIsKing() {
        return this.isKing;
    }

    // **************************************************************************
    // * SETTERS
    // **************************************************************************
    public void setRow(int r1) {
        this.row = r1;
        clip();
    }

    public void setCol(int c1) {
        this.col = c1;
        clip();
    }

    public void setIsKing() {
        this.isKing = !this.isKing;
    }

    // **************************************************************************
    // * UPDATES AND OTHER METHODS
    // **************************************************************************

    /**
     * Prevents the object from going outside the bounds of the area
     * designated for the object (i.e. Object cannot go outside the active
     * area the user defines for it).
     */
    private void clip() {
        /*
         * Upper bounds of the area in which the object can be positioned. Maximum
         * permissible x, y positions for the upper-left hand corner of the object.
         */
        int maxX = 7;
        this.col = Math.min(Math.max(this.col, 0), maxX);
        int maxY = 7;
        this.row = Math.min(Math.max(this.row, 0), maxY);
    }

}