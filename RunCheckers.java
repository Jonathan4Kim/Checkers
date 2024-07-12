package org.cis120.checkers;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * This class sets up the top-level frame and widgets for the GUI.
 * 
 * This game adheres to a Model-View-Controller design framework. This
 * framework is very effective for turn-based games. We STRONGLY
 * recommend you review these lecture slides, starting at slide 8,
 * for more details on Model-View-Controller:
 * https://www.seas.upenn.edu/~cis120/current/files/slides/lec37.pdf
 * 
 * In a Model-View-Controller framework, Game initializes the view,
 * implements a bit of controller functionality through the reset
 * button, and then instantiates a GameBoard. The GameBoard will
 * handle the rest of the game's view and controller functionality, and
 * it will instantiate a TicTacToe object to serve as the game's model.
 */
public class RunCheckers implements Runnable {
    public void run() {
        // NOTE: the 'final' keyword denotes immutability even for local variables.
        // Top-level frame in which game components live
        final JFrame frame = new JFrame("Checkers");
        frame.setLocation(800, 800);

        final JPanel m = new JPanel();
        m.setLocation(800, 800);

        final String s = "Welcome to Checkers! Start the game if you’re player 1 " +
                "(and use the start button to start the game!).  " +
                "You can only move one checker one diagonal space forward " +
                "(toward your opponent’s checkers) during a regular turn. " +
                "Checkers must stay on the dark squares.  Once the player with " +
                "red checkers moves, the player with white checkers moves, and then " +
                "turns will be taken.  Jump over your opponent's checkers to remove them f" +
                "rom the board. If your checker is in the diagonal space nearest to one of y" +
                "our opponent's checkers, then you can jump and capture that checker. To capture a"
                +
                " checker, move two diagonal spaces in the direction of the checker you’re attacking, "
                +
                "like you are hopping over your opponent's piece.  King your pieces when your ch" +
                "eckers reach the end of your oppon" +
                "ent's side. To crown a checker and make it a" +
                " king checker, simply place one of your own captured pieces on top of it. The king can "
                +
                "move forward and backward " +
                "diagonally on the dark squares, so it's easier for king checkers " +
                "to capture your opponent's checkers. Player 1 (Blue) starts, followed by player 2 (red), and the "
                +
                "game continues from there .  There is a new game button to reset, a save button to save a piece, "
                +
                "and an undo button as well.";

        String s2 = " checker, move two diagonal spaces in the direction of the checker you’re attacking, "
                + "like you are hopping over your opponent's piece.  King your pieces when your ch" +
                "eckers reach the end of your oppon" +
                "ent's side. To crown a checker and make it a" +
                " king checker, simply place one of your own captured pieces on top of it. The king can "
                +
                "move forward and backward " +
                "diagonally on the dark squares, so it's easier for king checkers " +
                "to capture your opponent's checkers. Player 1 (Blue) starts, followed by player 2 (red), and the "
                +
                "game continues from there .  There is a new game button to reset, a save button to save a piece, "
                + "and an undo button as well.";


        final JLabel instructions = new JLabel(s);
        m.add(instructions, BorderLayout.CENTER);

        final JLabel instructions2 = new JLabel(s);
        m.add(instructions2, BorderLayout.CENTER);

        final JLabel instructions3 = new JLabel("");
        m.add(instructions3, BorderLayout.CENTER);

        final JButton start = new JButton("Go Play");
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e1) {

                frame.remove(m);

                // Status panel
                final JPanel status_panel = new JPanel();
                frame.add(status_panel, BorderLayout.SOUTH);

                final JLabel status = new JLabel("Setting up...");
                status_panel.add(status);
                // Game board
                final Board board = new Board(status);
                frame.add(board, BorderLayout.CENTER);

                // Reset button
                final JPanel control_panel = new JPanel();
                frame.add(control_panel, BorderLayout.NORTH);

                final JButton upload = new JButton("Get Past Game");
                upload.addActionListener(e -> board.upload());
                control_panel.add(upload);

                // Note here that when we add an action listener to the reset button, we
                // define it as an anonymous inner class that is an instance of
                // ActionListener with its actionPerformed() method overridden. When the
                // button is pressed, actionPerformed() will be called.

                final JButton reset = new JButton("New Game");
                reset.addActionListener(e -> board.reset());
                control_panel.add(reset);

                final JButton save = new JButton("Save Game");
                save.addActionListener(e -> board.save());
                control_panel.add(save);

                final JButton undo = new JButton("Undo");
                undo.addActionListener(e -> board.undo());
                control_panel.add(undo);

                // Start the game
                board.reset();

            }
        });
        m.add(start);
        frame.add(m);

        // Put the frame on the screen
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
