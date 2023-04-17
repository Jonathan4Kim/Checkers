package org.cis120.checkers;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InitialPreview extends JFrame {

    public InitialPreview() {
        super("Instructions:");
        this.setSize(400, 200);
        this.setResizable(false);

        // center this window on the root pane
        // int x = root.getX() + root.getWidth()/2 - this.getWidth()/2;
        // int y = root.getY() + root.getHeight()/2 - this.getHeight()/2;
        int x = 1;
        int y = 2;
        this.setLocation(x, y);

        // create text area
        JTextArea text = new JTextArea();
        text.setText(
                "How To Play:\n" +

                        "Pieces can only be moved diagonally" +

                        "Start the game if you’re the player with red checkers.  " +
                        "You can only move one checker one diagonal space forward (toward your opponent’s checkers) "
                        +
                        "during a regular turn. Checkers must stay on the dark squares. Once the player with black checkers makes "
                        +
                        "their first move, the player with white checkers moves, and then you’ll take turns.  "
                        +
                        "Jump over your opponent's checkers " +
                        "to remove them from the board.  If your checker is in the diagonal space nearest to "
                        +
                        "one of your opponent's checkers, then you " +
                        "can jump and capture that checker. To capture a checker, move two diagonal spaces in "
                        +
                        "the direction of the checker you’re attacking, " +
                        "like you are hopping over your opponent's piece.  King your pieces when your checkers reach "
                        +
                        "the end of your opponent's sid.  " +
                        "To crown a checker and make it a king checker, simply place one of your own captured pieces on top of it. "
                        +
                        "The king can move forward and backward diagonally on the dark squares, " +
                        "so it's easier for king checkers to capture your " +
                        "opponent's checkers.  Keep jumping and capturing to win the game." +
                        "Continue jumping and capturing your opponent's checkers until they are all removed "
                        +
                        "from the board. Once you have captured all of your opponent’s checkers, you have won the game!  "
                        +
                        "Player 1 (Blue) starts, followed by player 2 (red), " +
                        "and the game continues from there.  Make sure you avoid a stalemate, " +
                        "where the player whose turn it is can’t move!  That results in a draw here.\n"
        );
        text.setLineWrap(true);
        text.setEditable(false);
        text.setFont(new Font("Arial", Font.BOLD, 12));
        // create scrolling feature
        JScrollPane pane = new JScrollPane(text);
        pane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        this.add(pane);

        // create start button
        JButton start = new JButton("Start");
        start.addActionListener(new Start(this));
        this.add(start, BorderLayout.SOUTH);
        this.setVisible(true);
    }
}

/**
 * Listener for the start button. When this button is
 * pressed this window will be disposed.
 */
class Start implements ActionListener {
    private InitialPreview pane;

    public Start(InitialPreview pane) {
        this.pane = pane;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.pane.dispose();
    }
}
