package Tetris;

import TetrisBlocks.*;
import java.awt.*;
import javax.swing.*;

public class NextBlockPanel extends JPanel {
    private Tetrimonoes block;
    private static final int GRID_SIZE = 4; // 4x4 grid
    private static final int CELL_SIZE = 25; // Each square is 25 pixels

    public NextBlockPanel() {
        setBounds(350, 200, GRID_SIZE * CELL_SIZE, GRID_SIZE * CELL_SIZE);
        setOpaque(false);
        setBorder(BorderFactory.createLineBorder(new Color(192, 192, 192), 5)); 
    }

    public void setBlock(Tetrimonoes block) {
        this.block = block;
        repaint(); // Call repaint to trigger a redraw when the block is set
    }

    private void drawBackground(Graphics g) {
        // Draw the entire grid background
        g.setColor(Color.WHITE);
        g.fillRoundRect(0, 0, GRID_SIZE * CELL_SIZE, GRID_SIZE * CELL_SIZE, 10, 10); // Rounded corners for the grid
    }

    private void drawBlock(Graphics g) {
        if (block != null) {
            int startX = (GRID_SIZE * CELL_SIZE - (block.getWidth() * CELL_SIZE)) / 2;
            int startY = (GRID_SIZE * CELL_SIZE - (block.getHeight() * CELL_SIZE)) / 2;

            for (int row = 0; row < block.getHeight(); row++) {
                for (int col = 0; col < block.getWidth(); col++) {
                    if (block.getShape()[row][col] == 1) {
                        int x = startX + (col * CELL_SIZE);
                        int y = startY + (row * CELL_SIZE);

                        g.setColor(block.getColor());
                        g.fillRect(x, y, CELL_SIZE, CELL_SIZE); // Draw the filled block
                        g.setColor(Color.WHITE);
                        g.drawRect(x, y, CELL_SIZE, CELL_SIZE); // Draw the border
                    }
                }
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBackground(g);
        drawBlock(g); 
    }
}
