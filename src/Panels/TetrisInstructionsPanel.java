package Panels;

import javax.swing.*;
import java.awt.*;

public class TetrisInstructionsPanel extends JPanel {
    public TetrisInstructionsPanel() {
        setLayout(new BorderLayout());
        
        // Heading
        JLabel heading = new JLabel("How to Play");
        heading.setFont(new Font("Arial", Font.BOLD, 24));
        heading.setHorizontalAlignment(SwingConstants.CENTER);
        
        // Instructions text
        JTextArea instructionsText = new JTextArea(
            "Controls:\n" +
            "LEFT Arrow - Move Left\n" +
            "RIGHT Arrow - Move Right\n" +
            "DOWN Arrow - Move Down Faster\n" +
            "UP Arrow - Rotate\n" +
            "\nObjective:\n" +
            "Clear lines by filling each row completely.\n" +
            "The game ends when blocks reach the top of the board.\n\n" +
            "Tips:\n" +
            "- Plan ahead to keep your board manageable.\n" +
            "- Clear multiple lines at once for higher scores."
        );
        
        // Styling the JTextArea
        instructionsText.setFont(new Font("Arial", Font.PLAIN, 16));
        instructionsText.setEditable(false);
        instructionsText.setOpaque(false);
        instructionsText.setLineWrap(true);
        instructionsText.setWrapStyleWord(true);

        // Add heading and instructions to the panel
        add(heading, BorderLayout.NORTH);
        add(instructionsText, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Tetris Instructions");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        
        // Add the instructions panel
        frame.add(new TetrisInstructionsPanel());
        
        frame.setVisible(true);
    }
}
