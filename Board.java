package org.cis120.checkers;

/*
 * CIS 120 HW09 - TicTacToe Demo
 * (c) University of Pennsylvania
 * Created by Bayley Tuch, Sabrina Green, and Nicolas Corona in Fall 2020.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.file.Files;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Collection;

/**
 * This class instantiates a Checkers object, which is the model for the game.
 * As the user clicks the game board, the model is updated. Whenever the model
 * is updated, the game board repaints itself and updates its status JLabel to
 * reflect the current state of the model.
 * 
 * This game adheres to a Model-View-Controller design framework. This
 * framework is very effective for turn-based games. We STRONGLY
 * recommend you review these lecture slides, starting at slide 8,
 * for more details on Model-View-Controller:
 * https://www.seas.upenn.edu/~cis120/current/files/slides/lec37.pdf
 * 
 * In a Model-View-Controller framework, GameBoard stores the model as a field
 * and acts as both the controller (with a MouseListener) and the view (with
 * its paintComponent method and the status JLabel).
 */
@SuppressWarnings("serial")

public class Board extends JPanel {

    private Checkers checkers; // model for the game
    private JLabel status; // current status text
    ArrayList<Moves> pastMoves; // for undo
    Piece p; // piece that will move if applicable
    File file;

    // Game constants
    public static final int BOARD_WIDTH = 800;
    public static final int BOARD_HEIGHT = 800;

    /**
     * Initializes the game board.
     */
    public Board(JLabel statusInit) {
        // creates border around the court area, JComponent method
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // Enable keyboard focus on the court area. When this component has the
        // keyboard focus, key events are handled by its key listener.
        setFocusable(true);

        checkers = new Checkers(); // initializes model for the game
        status = statusInit; // initializes the status JLabel
        pastMoves = new ArrayList<Moves>();
        /*
         * Listens for mouseclicks. Updates the model, then updates the game
         * board based off of the updated model.
         */
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                if (checkers.isGameGoingOn()) {
                    int col = evt.getX() / 100;
                    int row = evt.getY() / 100;
                    if (p == null) {
                        p = checkers.getPiece(col, row);
                        if (p != null && !(p.getIsPlayer1() == checkers.getCurrentPlayer())) {
                            p = null;
                        }
                    } else {
                        if (checkers.isValidMove(p, row, col)
                                && p.getIsPlayer1() == checkers.getCurrentPlayer()) {
                            Piece[][] board = checkers.getBoard();
                            boolean isPlayer1 = checkers.getCurrentPlayer();
                            pastMoves.add(new Moves(board, isPlayer1));
                            System.out.println(pastMoves.size());
                            checkers.makeMove(p, row, col);
                            p = null;
                            checkers.playTurn();
                            System.out.println("move");
                        } else {
                            p = null;
                        }

                    }
                    System.out.println(p);
                    // int col = evt.getX() / 100;
                    // int row = evt.getY() / 100;
                    // p = checkers.getPiece(col, row);
                    updateStatus();
                    repaint();
                }
            }
        });
    }

    /**
     * (Re-)sets the game to its initial state.
     */
    public void reset() {
        checkers.reset();
        if (checkers.getCurrentPlayer()) {
            status.setText("Player 1's Turn");
        } else {
            status.setText("Player 2's Turn");
        }
        repaint();

        // Makes sure this component has keyboard/mouse focus
        requestFocusInWindow();
    }

    /**
     * saves a game to where it was.
     *
     *
     *
     *
     */
    public void save() {
        Piece[][] board = checkers.getBoard();
        String s = "";

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j] == null) {
                    s = s + "0";
                } else if (board[i][j].getIsPlayer1() && !board[i][j].getIsKing()) {
                    s = s + "1";
                } else if (!board[i][j].getIsPlayer1() && !board[i][j].getIsKing()) {
                    s = s + "2";
                } else if (board[i][j].getIsPlayer1() && board[i][j].getIsKing()) {
                    s = s + "3";
                } else {
                    s = s + "4";
                }
            }
        }
        boolean playerTurn = checkers.getCurrentPlayer();
        if (playerTurn) {
            s = s + "t";
        } else {
            s = s + "f";
        }
        try {
            file = new File("savedGame");
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(s);
            bw.close();
            System.out.println("Game Saved?");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * undoes the game to its past state.
     */
    public void undo() {
        if (pastMoves.size() > 0) {
            Moves m = pastMoves.remove(pastMoves.size() - 1);
            printB(m.getBoard());
            checkers.setBoard(m.getBoard());
            checkers.setCurrentPlayer(m.getPlayer1Turn());
            p = null;
            updateStatus();
            repaint();
        }
    }

    public void upload() {
        boolean Player1;
        Piece[][] board = new Piece[8][8];
        String s = "";
        try {
            FileReader fr = new FileReader("savedGame");
            BufferedReader br = new BufferedReader(fr);
            String sLine = "";
            while ((sLine = br.readLine()) != null) {
                s += sLine;
            }
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    char ch = s.charAt(8 * i + j);
                    if (ch == '0') {
                        board[i][j] = null;
                    } else if (ch == '1') {
                        board[i][j] = new Piece(j, i, 40, 40, true, false);
                    } else if (ch == '2') {
                        board[i][j] = new Piece(j, i, 40, 40, false, false);
                    } else if (ch == '3') {
                        board[i][j] = new Piece(j, i, 40, 40, true, true);
                    } else {
                        board[i][j] = new Piece(j, i, 40, 40, false, true);
                    }
                }
            }
            char ch = s.charAt(64);
            if (ch == 't') {
                checkers.setCurrentPlayer(true);
            } else {
                checkers.setCurrentPlayer(false);
            }
            checkers.setBoard(board);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void printB(Piece[][] b) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (b[i][j] != null) {
                    if (b[i][j].getIsPlayer1()) {
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
     * Updates the JLabel to reflect the current state of the game.
     */
    private void updateStatus() {
        if (checkers.getCurrentPlayer()) {
            status.setText("Player 1's Turn");
        } else {
            status.setText("Player 2's Turn");
        }

        int winner = checkers.checkWinner();
        if (winner == 1) {
            status.setText("Player 1 wins!!!");
        } else if (winner == 2) {
            status.setText("Player 2 wins!!!");
        } else if (winner == 3) {
            status.setText("It's a tie.");
        }
    }

    /**
     * Draws the game board.
     * <p>
     * There are many ways to draw a game board. This approach
     * will not be sufficient for most games, because it is not
     * modular. All of the logic for drawing the game board is
     * in this method, and it does not take advantage of helper
     * methods. Consider breaking up your paintComponent logic
     * into multiple methods or classes, like Mushroom of Doom.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (checkers.isGameOver()) {
            gameOverDisplay(g);
        } else {
            // Draws board grid
            g.setColor(Color.BLACK);

            // vertical lines
            g.drawLine(100, 0, 100, 800);
            g.drawLine(200, 0, 200, 800);
            g.drawLine(300, 0, 300, 800);
            g.drawLine(400, 0, 400, 800);
            g.drawLine(500, 0, 500, 800);
            g.drawLine(600, 0, 600, 800);
            g.drawLine(700, 0, 700, 800);
            g.drawLine(800, 0, 800, 800);

            // horizontal lines
            g.drawLine(0, 100, 800, 100);
            g.drawLine(0, 200, 800, 200);
            g.drawLine(0, 300, 800, 300);
            g.drawLine(0, 400, 800, 400);
            g.drawLine(0, 500, 800, 500);
            g.drawLine(0, 600, 800, 600);
            g.drawLine(0, 700, 800, 700);
            g.drawLine(0, 800, 800, 800);

            // drawing the black squares on the checkers board
            for (int i = 0; i < 8; i = i + 2) {
                for (int j = 0; j < 8; j = j + 2) {
                    g.fillRect(100 * j, 100 * i, 100, 100);

                }
            }

            // drawing the black squares on the checkers board
            for (int i = 1; i < 8; i = i + 2) {
                for (int j = 1; j < 8; j = j + 2) {
                    g.fillRect(100 * j, 100 * i, 100, 100);
                }
            }

            // drawing the pieces (player 1)
            g.setColor(Color.RED);
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j = j + 2) {
                    if (i % 2 == 0) {
                        Piece p = checkers.getPiece(j, i);
                        if (p != null && p.getIsPlayer1()) {
                            if (p.getIsKing()) {
                                g.setFont(new Font("TimesRoman", Font.PLAIN, 75));
                                g.drawString("K", 25 + 100 * j, 70 + 100 * i);
                            } else {
                                g.fillOval(30 + 100 * j, 30 + 100 * i, p.getWidth(), p.getHeight());
                            }
                        }
                    } else {
                        Piece p = checkers.getPiece(j + 1, i);
                        if (p != null && p.getIsPlayer1()) {
                            if (p.getIsKing()) {
                                g.setFont(new Font("TimesRoman", Font.PLAIN, 75));
                                g.drawString("K", 25 + 100 * j, 70 + 100 * i);
                            } else {
                                g.fillOval(
                                        130 + 100 * j, 30 + 100 * i, p.getWidth(), p.getHeight()
                                );
                            }
                        }
                    }
                }
            }

            // drawing the pieces (player 2)
            g.setColor(Color.BLUE);
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j = j + 2) {
                    if (i % 2 == 0) {
                        Piece p = checkers.getPiece(j, i);
                        if (p != null && !p.getIsPlayer1()) {
                            if (p.getIsKing()) {
                                g.setFont(new Font("TimesRoman", Font.PLAIN, 75));
                                g.drawString("K", 25 + 100 * j, 70 + 100 * i);
                            } else {
                                g.fillOval(30 + 100 * j, 30 + 100 * i, p.getWidth(), p.getHeight());
                            }
                        }
                    } else {
                        Piece p = checkers.getPiece(j + 1, i);
                        if (p != null && !p.getIsPlayer1()) {
                            if (p.getIsKing()) {
                                g.setFont(new Font("TimesRoman", Font.PLAIN, 75));
                                g.drawString("K", 125 + 100 * j, 70 + 100 * i);
                            } else {
                                g.fillOval(
                                        130 + 100 * j, 30 + 100 * i, p.getWidth(), p.getHeight()
                                );
                            }
                        }
                    }
                }
            }
        }
        //
        // int r = rowSelected;
        // int c = colSelected;
        // Piece pieceSelected = checkers.getPiece(c, r);
        // if (pieceSelected != null) {
        // Moves[] legalMoves = checkers.getAllPotentialMoves(pieceSelected);
        // for (int i = 0; i < 8; i++) {
        // for (int j = 0; j < 8; j = j + 2) {
        // Piece p1 = checkers.getPiece(j, i);
        // if (p1 != null) {
        // if (i % 2 == 0) {
        //
        // } else {
        //
        // }
        // }
        // }
        // }
        // }

    }

    public void gameOverDisplay(Graphics g) { // Displays the game over message
        String msg = "Game Over";
        Font small = new Font("Helvetica", Font.BOLD, 20);
        FontMetrics metr = getFontMetrics(small);
        g.setColor(Color.RED);
        g.setFont(small);
        g.drawString(msg, (BOARD_WIDTH - metr.stringWidth(msg)) / 2, BOARD_HEIGHT / 2);
    }

    /**
     * Respond to a user click on the board. If no game is in progress, show
     * an error message. Otherwise, find the row and column that the user
     * clicked and call doClickSquare() to handle it.
     */

    /**
     * Returns the size of the game board.
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(BOARD_WIDTH, BOARD_HEIGHT);
    }

    // Methods that must be included
    public void mouseClicked(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void actionPerformed(ActionEvent e) {
    }

}
